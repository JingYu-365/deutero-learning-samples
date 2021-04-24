package com.xinfago.mall.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import com.xinfago.common.valid.group.AddGroup;
import com.xinfago.common.valid.group.BaseGroup;
import com.xinfago.common.valid.group.UpdateGroup;
import lombok.Data;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

/**
 * 品牌
 *
 * @author xinfago
 * @email xinfago@163.com
 * @date 2021-04-18 14:48:11
 */
@Data
@TableName("pms_brand")
public class BrandEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 品牌id
     */
    @TableId
    @Null(message = "新增时不允许填写品牌ID", groups = AddGroup.class)
    @NotNull(message = "更新时品牌ID必填", groups = UpdateGroup.class)
    private Long brandId;
    /**
     * 品牌名
     */
    @NotBlank(message = "品牌名不允许为空", groups = {BaseGroup.class})
    private String name;
    /**
     * 品牌logo地址
     */
    @NotNull(message = "品牌地址不允许为空", groups = BaseGroup.class)
    @URL(message = "品牌地址格式有误", groups = BaseGroup.class)
    private String logo;
    /**
     * 介绍
     */
    private String descript;
    /**
     * 显示状态[0-不显示；1-显示]
     */
    @Range(max = 1, min = 0, message = "显示状态格式有误 [0-不显示；1-显示]", groups = BaseGroup.class)
    private Integer showStatus;
    /**
     * 检索首字母
     */
    @NotBlank(message = "检索首字母不允许为空", groups = BaseGroup.class)
    private String firstLetter;
    /**
     * 排序
     */
    @NotNull(message = "排序值不允许为空", groups = BaseGroup.class)
    @Min(value = 0L, message = "排序值必须大于O", groups = BaseGroup.class)
    private Integer sort;

}
