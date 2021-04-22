package com.xinfago.mall.product.service.impl;

import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinfago.common.utils.PageUtils;
import com.xinfago.common.utils.Query;

import com.xinfago.mall.product.dao.CategoryDao;
import com.xinfago.mall.product.entity.CategoryEntity;
import com.xinfago.mall.product.service.CategoryService;
import org.w3c.dom.ls.LSInput;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<CategoryEntity> listDataWithTree() {

        List<CategoryEntity> list = this.baseMapper.selectList(null);

        return list.stream()
                .filter(item -> item.getParentCid() == 0)
                .peek(item -> item.setChildren(getChildren(item, list)))
                .sorted(Comparator.comparingInt(item -> (item.getSort() == null ? 0 : item.getSort())))
                .collect(Collectors.toList());
    }

    @Override
    public void removeMenusByIds(List<Long> ids) {
        // todo 如果菜单被依赖则不允许删除

        // 删除过程过逻辑删除，不允许直接删除，逻辑删除通过 mybatis-plus 配置实现
        baseMapper.deleteBatchIds(ids);
    }

    private List<CategoryEntity> getChildren(CategoryEntity root, List<CategoryEntity> list) {
        return list.stream().filter(item -> item.getParentCid().equals(root.getCatId()))
                .peek(item -> item.setChildren(getChildren(item, list)))
                .sorted(Comparator.comparingInt(item -> (item.getSort() == null ? 0 : item.getSort())))
                .collect(Collectors.toList());
    }

}