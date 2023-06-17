package com.lagou.edu.course.controller;


import com.alibaba.fastjson.JSON;
import com.lagou.edu.common.response.ResponseDTO;
import com.lagou.edu.common.utils.CoverUtil;
import com.lagou.edu.common.utils.ValidateUtils;
import com.lagou.edu.course.client.dto.ActivityCourseDTO;
import com.lagou.edu.course.service.IActivityCourseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 活动课程表 前端控制器
 * </p>
 *
 * @author xianhongle
 * @since 2022-06-03
 */
@Slf4j
@RestController
@RequestMapping("/activityCourse")
public class ActivityCourseController {

    @Autowired
    private IActivityCourseService activityCourseService;

    /**
     * @author: ma wei long
     * @date:   2020年7月7日 下午8:05:21
     */
    @PostMapping("/saveActivityCourse")
    public ResponseDTO<?> saveActivityCourse(@RequestBody ActivityCourseDTO reqDTO) {
        log.info("saveActivityCourse - reqDTO:{}",JSON.toJSONString(reqDTO));
        activityCourseService.saveActivityCourse(reqDTO);
        return ResponseDTO.success();
    }

    /**
     * @author: ma wei long
     * @date:   2020年7月7日 下午8:59:06
     */
    @PostMapping("/updateActivityCourseStatus")
    public ResponseDTO<?> updateActivityCourseStatus(@RequestBody ActivityCourseDTO reqDTO) {
        log.info("updateActivityCourseStatus - reqVo:{}",JSON.toJSONString(reqDTO));
        ValidateUtils.isTrue(activityCourseService.updateActivityCourseStatus(reqDTO), "更新状态失败");
        return ResponseDTO.success();
    }


    /**
     * @author: ma wei long
     * @date:   2020年7月7日 下午9:30:01
     */
    @GetMapping("/getById")
    public ResponseDTO<ActivityCourseDTO> getById(@RequestParam("id") Integer id){
        log.info("getById - id:{}",id);
        ValidateUtils.notNullParam(id);
        return ResponseDTO.success(CoverUtil.cover(activityCourseService.getById(id), ActivityCourseDTO.class));
    }

    /**
     * @author: ma wei long
     * @date:   2020年7月7日 下午9:30:01
     */
    @GetMapping("/getByCourseId")
    ResponseDTO<ActivityCourseDTO> getByCourseId(@RequestParam("courseId") Integer courseId){
        log.info("getByCourseId - courseId:{}",courseId);
        ValidateUtils.notNullParam(courseId);
        return ResponseDTO.success(activityCourseService.getByCourseId(courseId));
    }

    /**
     * @author: ma wei long
     * @date:   2020年7月8日 上午11:33:07
     */
    @PostMapping("/updateActivityCourseStock")
    ResponseDTO<?> updateActivityCourseStock(@RequestParam("courseId")Integer courseId,@RequestParam("orderNo")String orderNo){
        activityCourseService.updateActivityCourseStock(courseId,orderNo);
        return ResponseDTO.success();
    }
}
