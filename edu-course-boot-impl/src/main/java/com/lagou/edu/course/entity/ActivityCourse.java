package com.lagou.edu.course.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 活动课程表
 * </p>
 *
 * @author xianhongle
 * @since 2022-06-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ActivityCourse implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 课程ID
     */
    private Integer courseId;

    /**
     * 活动开始时间
     */
    private LocalDateTime beginTime;

    /**
     * 活动结束时间
     */
    private LocalDateTime endTime;

    /**
     * 活动价格
     */
    private Double amount;

    /**
     * 库存值
     */
    private Integer stock;

    /**
     * 状态 0未上架 10已上架
     */
    private Integer status;

    /**
     * 逻辑删除 0未删除 1删除
     */
    private Boolean isDel;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 更新人
     */
    private String updateUser;


}
