package com.github.labazhang.mapper;

import com.github.labazhang.my.mapper.MyMapper;
import com.github.labazhang.pojo.ItemsComments;
import com.github.labazhang.pojo.vo.MyCommentVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ItemsCommentsMapperCustom extends MyMapper<ItemsComments> {

    public void saveComments(Map<String, Object> map);

    public List<MyCommentVO> queryMyComments(@Param("paramsMap") Map<String, Object> map);

}