package com.github.labazhang.gridfs.controller;

import com.mongodb.client.gridfs.GridFSFindIterable;
import com.mongodb.client.gridfs.model.GridFSFile;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsCriteria;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * GridFS 测试
 *
 * @author labazhang
 */
@Slf4j
@RestController
@RequestMapping(value = "/fs")
public class GridfsMainController {

    private final GridFsTemplate gridFsTemplate;

    public GridfsMainController(GridFsTemplate gridFsTemplate) {
        this.gridFsTemplate = gridFsTemplate;
    }

    /**
     * 上传文件
     *
     * @param multipartFile 文件
     * @return 上传成功文件id
     */
    @PostMapping("/upload")
    public String uploadFile(@RequestParam(value = "file") MultipartFile multipartFile,
                             @RequestParam("keyword") String keyword) {

        // 设置meta数据值
        Map<String, String> metaData = new HashMap<>(2);
        metaData.put("keyword", keyword);

        try (InputStream inputStream = multipartFile.getInputStream()) {
            // 获取文件的源名称
            String fileName = multipartFile.getOriginalFilename();
            // 进行文件存储
            ObjectId objectId = gridFsTemplate.store(inputStream, fileName, metaData);
            // 返回文件的id
            return objectId.toHexString();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return "OK";
    }

    /**
     * 获取文件信息
     *
     * @param fileId 文件id
     */
    @GetMapping("/download")
    public void downloadFile(@RequestParam(value = "fileId") String fileId,
                             HttpServletResponse response) {
        // 1. 根据id查询文件
        GridFSFile gridFile = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(fileId)));
        if (gridFile == null) {
            throw new RuntimeException("No file with id: " + fileId);
        }

        // 2. 设置文件响应头
        response.setContentType("application/octet-stream");
        response.setContentLength(gridFile.getChunkSize());
        String headerValue = String.format("attachment; filename=\"%s\"", gridFile.getFilename());
        response.setHeader("Content-Disposition", headerValue);

        // 3. 输出文件
        try {
            GridFsResource resource = gridFsTemplate.getResource(gridFile);
            // 获取流中的数据
            IOUtils.copy(resource.getInputStream(), response.getOutputStream());
            response.flushBuffer();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    /**
     * 删除文件
     *
     * @param fileId 文件id
     */
    @DeleteMapping("/delete")
    public String deleteFile(@RequestParam(value = "fileId", required = false) String fileId,
                             @RequestParam(value = "keyword", required = false) String keyword) {
        // 根据文件 id | keyword 删除fs.files和fs.chunks中的记录
        Query query = new Query();
        Criteria criteria = new Criteria();
        if (StringUtils.hasText(fileId)) {
            criteria.and("_id").is(fileId);
        }
        if (StringUtils.hasText(keyword)) {
            // 如果通过 metadata 删除文件，则需要使用 GridFsCriteria#whereMetaData
            criteria.andOperator(GridFsCriteria.whereMetaData("keyword").is(keyword));
        }
        query.addCriteria(criteria);
        gridFsTemplate.delete(query);
        return "OK";
    }

    @GetMapping(value = "/file")
    public Map<String, Object> queryFile(@RequestParam(value = "fileId", required = false) String fileId) {
        // 1. 根据id查询文件
        GridFSFile gridFile = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(fileId)));
        if (gridFile == null) {
            throw new RuntimeException("No file with id: " + fileId);
        }

        Map<String, Object> result = new HashMap<>(4);
        result.put("id: ", gridFile.getId().asObjectId().getValue().toHexString());
        result.put("name", gridFile.getFilename());
        result.put("size: ", gridFile.getLength());
        result.put("metadata: ", gridFile.getMetadata());
        log.info("file info: " + gridFile.toString());
        return result;
    }

    /**
     * 查询文件列表
     *
     * @return 文件名列表
     */
    @GetMapping("/files")
    public List<String> listFiles(@RequestParam(value = "keyword", required = false) String keyword) {
        List<String> result = new ArrayList<>();

        Query query;
        if (StringUtils.hasText(keyword)) {
            // GridFsCriteria extends Criteria
            query = Query.query(GridFsCriteria.whereMetaData("keyword").is(keyword));
        } else {
            query = new Query();
        }
        GridFSFindIterable files = gridFsTemplate.find(query);
        for (GridFSFile file : files) {
            result.add(file.getFilename());
        }
        return result;
    }
}