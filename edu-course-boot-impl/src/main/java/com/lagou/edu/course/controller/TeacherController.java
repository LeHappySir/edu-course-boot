package com.lagou.edu.course.controller;


import com.lagou.edu.course.client.dto.TeacherDTO;
import com.lagou.edu.course.remote.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xianhongle
 * @since 2022-06-03
 */
@RestController
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;
    /**
     * 通过课程Id获取老师信息
     * @param courseId
     * @return
     */
    @GetMapping(value = "/getTeacherByCourseId")
    TeacherDTO getTeacherByCourseId(Integer courseId){
        return teacherService.getByCourseId(courseId);
    }
}
