package com.lagou.edu.course.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lagou.edu.course.client.enums.CourseLessonStatus;
import com.lagou.edu.course.entity.Lesson;
import com.lagou.edu.course.mapper.LessonMapper;
import com.lagou.edu.course.service.ILessonService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

/**
 * <p>
 * 课程节内容 服务实现类
 * </p>
 *
 * @author xianhongle
 * @since 2022-06-03
 */
@Service
public class LessonServiceImpl extends ServiceImpl<LessonMapper, Lesson> implements ILessonService {


    @Override
    public Integer getReleaseCourse(Integer courseId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("course_id",courseId);

        queryWrapper.eq("status", CourseLessonStatus.RELEASE.getCode());
        queryWrapper.eq("is_del",Boolean.FALSE);
        return getBaseMapper().selectCount(queryWrapper);
    }

    @Override
    public List<Lesson> getBySectionId(Integer sectionId) {
        QueryWrapper<Lesson> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("section_id",sectionId);
        queryWrapper.eq("is_del",Boolean.FALSE);
        queryWrapper.orderByAsc("order_num");
        List<Lesson> lessons = getBaseMapper().selectList(queryWrapper);
        if(CollectionUtils.isEmpty(lessons)){
            return Collections.emptyList();
        }
        return lessons;
    }
}
