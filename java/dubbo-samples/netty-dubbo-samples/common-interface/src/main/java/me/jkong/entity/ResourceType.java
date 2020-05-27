package me.jkong.entity;

import lombok.Data;
import lombok.experimental.Accessors;
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
@Accessors(chain = true)
public class ResourceType implements Serializable {
    private static final long serialVersionUID = -5156936616242572815L;
    /**
     * 主键ID
     */
    private String id;
    /**
     * 创建者ID
     */
    private String creatorId;
    /**
     * 创建者名称
     */
    private String creatorName;
    /**
     * 创建组名称
     */
    private String creatorGroupId;
    /**
     * 创建时间
     */
    private Long createTime;
    /**
     * 修改时间
     */
    private Long modifiedTime;
    /**
     * 序号
     */
    private Integer orderNo;
    /**
     * 类型描述
     */
    private String typeDesc;
    /**
     * 类型名称
     */
    private String typeName;

    /**
     * 类型状态
     */
    public TypeStatus typeStatus;
    /**
     * 业务类型
     */
    private String bizType;

    /**
     * 类型状态枚举
     */
    public enum TypeStatus {
        /**
         * 已创建
         */
        CREATED("CREATED"),
        /**
         * 已启用
         */
        ENABLED("ENABLED"),
        /**
         * 已停用
         */
        DISABLED("DISABLED"),
        /**
         * 已删除
         */
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
}
