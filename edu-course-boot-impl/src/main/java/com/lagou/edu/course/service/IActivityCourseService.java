package com.lagou.edu.course.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lagou.edu.course.client.dto.ActivityCourseDTO;
import com.lagou.edu.course.entity.ActivityCourse;

/**
 * <p>
 * 活动课程表 服务类
 * </p>
 *
 * @author xianhongle
 * @since 2022-06-03
 */
public interface IActivityCourseService extends IService<ActivityCourse> {

    /**
     * @author: ma wei long
     * @date:   2020年7月7日 下午7:57:29
     */
    void saveActivityCourse(ActivityCourseDTO reqDTO);

    /**
     * @author: ma wei long
     * @date:   2020年7月9日 下午6:56:25
     */
    void saveOrUpdateActivityCourse(ActivityCourseDTO reqDTO);

    /**
     * @author: ma wei long
     * @date:   2020年7月7日 下午7:57:29
     */
    boolean updateActivityCourseStatus(ActivityCourseDTO reqDTO);

    /**
     * @author: ma wei long
     * @date:   2020年7月8日 上午10:31:41
     */
    ActivityCourseDTO getByCourseId(Integer courseId);

    /**
     * @author: ma wei long
     * @date:   2020年7月8日 上午11:37:32
     */
    void updateActivityCourseStock(Integer courseId,String orderNo);
}
