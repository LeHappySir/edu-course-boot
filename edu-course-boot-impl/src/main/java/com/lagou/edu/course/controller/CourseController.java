package com.lagou.edu.course.controller;


import com.alibaba.fastjson.JSON;
import com.lagou.edu.course.client.dto.CourseDTO;
import com.lagou.edu.course.client.dto.PageResultDTO;
import com.lagou.edu.course.client.param.CourseQueryParam;
import com.lagou.edu.course.remote.CourseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xianhongle
 * @since 2022-06-03
 */
@Slf4j
@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private CourseService courseService;


    /**
     * 获取选课列表
     * @param userId
     * @return
     */
    @GetMapping("/getAllCourses")
    public List<CourseDTO> getAllCourses(@RequestParam(required = false,name = "userId") Integer userId) {
        return  courseService.getAllCourses(userId);
    }


    /**
     * 获取已购课程信息
     * @param userId
     * @return
     */
    @GetMapping("/getPurchasedCourse")
    List<CourseDTO> getPurchasedCourse(@RequestParam("userId") Integer userId){
        return  courseService.getPurchasedCourse(userId);
    }


    /**
     * 获取课程详情
     * @param courseId
     * @return
     */
    @GetMapping("/getCourseById")
    CourseDTO getCourseById(@RequestParam("courseId") Integer courseId,@RequestParam(value = "userId",required = false) Integer userId){

        return courseService.getCourseById(courseId,userId);
    }


    /**
     * 更新课程
     * @param courseDTO
     * @return
     */
    @PostMapping(value = "/saveOrUpdateCourse",consumes = "application/json")
    boolean saveOrUpdateCourse(@RequestBody CourseDTO courseDTO){
        return false;
    }


    @PostMapping(value = "/getQueryCourses",consumes = "application/json")
    PageResultDTO<CourseDTO> getQueryCourses(@RequestBody CourseQueryParam courseQueryParam){
        log.info("分页查询课程信息:{}", JSON.toJSONString(courseQueryParam));
        Integer currentPage = courseQueryParam.getCurrentPage();
        Integer pageSize = courseQueryParam.getPageSize();
        if (null == currentPage || currentPage <= 0) {
            courseQueryParam.setCurrentPage(1);
        }
        if (null == pageSize || pageSize <= 0) {
            courseQueryParam.setPageSize(10);
        }
        try {
            PageResultDTO<CourseDTO> queryCourses = courseService.getQueryCourses(courseQueryParam);
            return queryCourses;
        } catch (Exception e) {
            log.error("分页查询用户信息:", e);
            return null;
        }
    }

}
