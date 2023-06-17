package com.lagou.edu.course.service;

import com.lagou.edu.course.entity.Lesson;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 课程节内容 服务类
 * </p>
 *
 * @author xianhongle
 * @since 2022-06-03
 */
public interface ILessonService extends IService<Lesson> {

    Integer getReleaseCourse(Integer courseId);

    List<Lesson> getBySectionId(Integer sectionId);
}
