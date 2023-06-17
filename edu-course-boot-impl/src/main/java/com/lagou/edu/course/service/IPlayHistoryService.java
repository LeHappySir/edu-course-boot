package com.lagou.edu.course.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lagou.edu.course.client.dto.CoursePlayHistoryDTO;
import com.lagou.edu.course.entity.PlayHistory;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xianhongle
 * @since 2022-06-03
 */
public interface IPlayHistoryService extends IService<PlayHistory> {

    PlayHistory getByUserIdAndCourseId(Integer userId, Integer courseId);

    void saveCourseHistoryNode(CoursePlayHistoryDTO playHistoryDTO);
}
