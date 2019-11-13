package me.jkong.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

/**
 * 资源类型
 *
 * @author Administrator
 * @date 2019/9/2 17:29
 */
@Data
@Document(collection = "resource_type")
public class ResourceType implements Serializable {
    private static final long serialVersionUID = -5156936616242572815L;
    /**
     * 主键ID
     */
    private String id;
    /**
     * 创建者ID
     */
    @Field("creator_id")
    private String creatorId;
    /**
     * 创建者名称
     */
    @Field("creator_name")
    private String creatorName;
    /**
     * 创建组名称
     */
    @Field("creator_group_id")
    private String creatorGroupId;
    /**
     * 创建时间
     */
    @Field("create_time")
    private Long createTime;
    /**
     * 修改时间
     */
    @Field("modified_time")
    private Long modifiedTime;
    /**
     * 序号
     */
    @Field("order_no")
    private Integer orderNo;
    /**
     * 类型描述
     */
    @Field("type_desc")
    private String typeDesc;
    /**
     * 类型名称
     */
    @Field("type_name")
    private String typeName;

    /**
     * 类型状态
     */
    @Field("type_status")
    public TypeStatus typeStatus;

    /**
     * 类型状态枚举
     * 已创建：created; 已启用：enabled; 已停用：disabled；已删除：deleted
     */
    public enum TypeStatus {
        CREATED("CREATED"),
        ENABLED("ENABLED"),
        DISABLED("DISABLED"),
        DELETED("DELETED");

        private String key;

        TypeStatus(String key) {
            this.key = key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getKey() {
            return key;
        }
    }

    /**
     * 业务类型
     */
    @Field("biz_type")
    private String bizType;
}
