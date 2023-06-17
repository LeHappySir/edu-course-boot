package com.lagou.edu.course.controller;


import com.lagou.edu.course.client.dto.LessonDTO;
import com.lagou.edu.course.remote.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程节内容 前端控制器
 * </p>
 *
 * @author xianhongle
 * @since 2022-06-03
 */
@RestController
@RequestMapping("/course/lesson")
public class LessonController {
    @Autowired
    private LessonService lessonService;


    @GetMapping(value = "/getByIds")
    Map<Integer,String> getByIds(@RequestParam("lessonIds") List<Integer> lessonIds){

        return lessonService.getByIds(lessonIds);
    }
    /**
     * 保存或者更新课程
     * @param lessonDTO
     * @return
     */
    @PostMapping(value = "/saveOrUpdate")
    boolean saveOrUpdate(@RequestBody LessonDTO lessonDTO){
        return lessonService.saveOrUpdate(lessonDTO);
    }
    /**
     * 通过lessonId获取课时
     * @param lessonId
     * @return
     */
    @GetMapping(value = "/getById")
    LessonDTO getById(@RequestParam("lessonId") Integer lessonId){
        return lessonService.getById(lessonId);
    }

}
