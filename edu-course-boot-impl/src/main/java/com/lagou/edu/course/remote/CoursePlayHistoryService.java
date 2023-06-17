package com.lagou.edu.course.remote;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lagou.edu.course.client.dto.CoursePlayHistoryDTO;
import com.lagou.edu.course.entity.PlayHistory;
import com.lagou.edu.course.service.IPlayHistoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Author:   mkp
 * Date:     2020/6/19 20:24
 * Description: 播放历史
 */
@Service
public class CoursePlayHistoryService {
    @Autowired
    private IPlayHistoryService coursePlayHistoryService;

    public void saveCourseHistoryNode(CoursePlayHistoryDTO playHistoryDTO) {
        coursePlayHistoryService.saveCourseHistoryNode(playHistoryDTO);
    }

    public List<Integer> hasStudyLessons(Integer lagouUserId, Integer courseId) {
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",lagouUserId);
        queryWrapper.eq("course_id",courseId);
        queryWrapper.eq("is_del",Boolean.FALSE);
        List<PlayHistory> coursePlayHistorys = coursePlayHistoryService.list(queryWrapper);
        if(CollectionUtils.isEmpty(coursePlayHistorys)){
            return Collections.emptyList();
        }
        return coursePlayHistorys.stream()
                .map(PlayHistory::getLessonId)
                .collect(Collectors.toList());
    }

    public CoursePlayHistoryDTO getByLessonId(Integer lessonId,Integer userId) {
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("lesson_id",lessonId);
        queryWrapper.eq("user_id",userId);
        queryWrapper.eq("is_del",Boolean.FALSE);
        List<PlayHistory> courserPlayHistorys = coursePlayHistoryService.list(queryWrapper);
        if(CollectionUtils.isEmpty(courserPlayHistorys)){
            return null;
        }
        PlayHistory coursePlayHistory = courserPlayHistorys.get(0);
        CoursePlayHistoryDTO coursePlayHistoryDTO = new CoursePlayHistoryDTO();
        BeanUtils.copyProperties(coursePlayHistory,coursePlayHistoryDTO);
        return coursePlayHistoryDTO;
    }

    public CoursePlayHistoryDTO getRecordLearn(Integer courseId,Integer userId) {
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id",courseId);
        queryWrapper.eq("user_id",userId);
        queryWrapper.eq("is_del",Boolean.FALSE);
        queryWrapper.orderByDesc("create_time");
        List<PlayHistory> courserPlayHistorys = coursePlayHistoryService.list(queryWrapper);
        if(CollectionUtils.isEmpty(courserPlayHistorys)){
            return null;
        }
        PlayHistory coursePlayHistory = courserPlayHistorys.get(0);
        CoursePlayHistoryDTO coursePlayHistoryDTO = new CoursePlayHistoryDTO();
        BeanUtils.copyProperties(coursePlayHistory,coursePlayHistoryDTO);
        return coursePlayHistoryDTO;
    }
   public CoursePlayHistoryDTO getByUserIdAndCourseId(Integer userId, Integer courseId){
       PlayHistory coursePlayHistory = coursePlayHistoryService.getByUserIdAndCourseId(userId, courseId);
       if(coursePlayHistory == null){
        return  null;
       }
       CoursePlayHistoryDTO coursePlayHistoryDTO = new CoursePlayHistoryDTO();
       BeanUtils.copyProperties(coursePlayHistory,coursePlayHistoryDTO);
       return coursePlayHistoryDTO;
   }
}
