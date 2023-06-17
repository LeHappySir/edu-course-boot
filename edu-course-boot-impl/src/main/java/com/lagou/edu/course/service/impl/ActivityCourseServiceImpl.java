package com.lagou.edu.course.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lagou.edu.common.constant.CacheDefine;
import com.lagou.edu.common.response.ResponseDTO;
import com.lagou.edu.common.utils.CoverUtil;
import com.lagou.edu.common.utils.DateUtil;
import com.lagou.edu.common.utils.LocalDateUtil;
import com.lagou.edu.common.utils.ValidateUtils;
import com.lagou.edu.course.client.dto.ActivityCourseDTO;
import com.lagou.edu.course.client.enums.ActivityCourseStatus;
import com.lagou.edu.course.entity.ActivityCourse;
import com.lagou.edu.course.mapper.ActivityCourseMapper;
import com.lagou.edu.course.service.IActivityCourseService;
import com.lagou.edu.order.client.dto.UserCourseOrderDTO;
import com.lagou.edu.order.client.remote.UserCourseOrderRemoteService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.ConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 活动课程表 服务实现类
 * </p>
 *
 * @author xianhongle
 * @since 2022-06-03
 */
@Slf4j
@Service
public class ActivityCourseServiceImpl extends ServiceImpl<ActivityCourseMapper, ActivityCourse> implements IActivityCourseService {


    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Autowired
    private UserCourseOrderRemoteService userCourseOrderRemoteService;

    /**
     * @author: ma wei long
     * @date:   2020年7月7日 下午7:57:29
     */
    @Override
    public void saveActivityCourse(ActivityCourseDTO reqDTO) {
        log.info("saveActivityCourse - reqDTO:{}",JSON.toJSONString(reqDTO));
        checkParam(reqDTO);
        ActivityCourse activityCourse = CoverUtil.cover(reqDTO, ActivityCourse.class);
        activityCourse.setCreateTime(LocalDateUtil.asLocalDateTime(new Date()));
        activityCourse.setCreateUser("auto");//TODO 记得取当前登录用户
        activityCourse.setUpdateTime(activityCourse.getCreateTime());
        activityCourse.setUpdateUser("auto");//TODO 记得取当前登录用户

        save(activityCourse);
    }


    /**
     * @author: ma wei long
     * @date:   2020年7月7日 下午7:57:29
     */
    @Override
    public boolean updateActivityCourseStatus(ActivityCourseDTO reqDTO) {
        log.info("updateActivityCourseStatus - reqDTO:{}",JSON.toJSONString(reqDTO));
        ValidateUtils.notNullParam(reqDTO);
        ValidateUtils.notNullParam(reqDTO.getId());
        ValidateUtils.isTrue(reqDTO.getId() > 0, "活动课程id必须大于零");
        ValidateUtils.notNullParam(ActivityCourseStatus.parse(reqDTO.getStatus()));

        ActivityCourse activityCourseDB = getById(reqDTO.getId());
        ValidateUtils.notNullParam(activityCourseDB);

        if(activityCourseDB.getStatus().equals(reqDTO.getStatus())) {
            return true;
        }

        activityCourseDB.setStatus(reqDTO.getStatus());
        boolean res = updateById(activityCourseDB);
        ValidateUtils.isTrue(res, "更新状态失败");

        redisTemplate.opsForValue().set(CacheDefine.ActivityCourse.getKey(activityCourseDB.getCourseId()), JSON.toJSONString(ConvertUtils.convert(activityCourseDB, ActivityCourseDTO.class)), DateUtil.getSecond(new Date(), LocalDateUtil.asDate(activityCourseDB.getEndTime())), TimeUnit.SECONDS);
        redisTemplate.opsForValue().set(CacheDefine.ActivityCourse.getStockKey(activityCourseDB.getCourseId()), activityCourseDB.getStock().toString(), DateUtil.getSecond(new Date(), LocalDateUtil.asDate(activityCourseDB.getEndTime())), TimeUnit.SECONDS);

        return res;
    }

    /**
     * @author: ma wei long
     * @date:   2020年7月8日 上午10:31:41
     */
    @Override
    public ActivityCourseDTO getByCourseId(Integer courseId) {
        log.info("getByCourseId - courseId:{}",courseId);
        ValidateUtils.notNullParam(courseId);
        ValidateUtils.isTrue(courseId > 0, "课程id必须大于零");
        ActivityCourse activityCourseDB = getOne(new QueryWrapper<ActivityCourse>().eq("course_id", courseId));
//		ValidateUtils.notNullParam(activityCourseDB);
        if(null == activityCourseDB) {
            return null;
        }
        return CoverUtil.cover(activityCourseDB, ActivityCourseDTO.class);
    }


    /**
     * @author: ma wei long
     * @date:   2020年7月8日 上午11:37:32
     */
    @Override
    public void updateActivityCourseStock(Integer courseId,String orderNo) {
        log.info("updateActivityCourseStock - courseId；{} orderNo:{}",courseId,orderNo);
        ValidateUtils.notNullParam(orderNo);
        ResponseDTO<UserCourseOrderDTO> resp = userCourseOrderRemoteService.getCourseOrderByOrderNo(orderNo);
        ValidateUtils.isTrue(resp.isSuccess(), resp.getState(), resp.getMessage());
        if(resp.getContent().getActivityCourseId() == 0) {
            return;
        }
        ActivityCourseDTO activityCourseDTO = getByCourseId(courseId);
        int res = getBaseMapper().updateStock(activityCourseDTO.getId(),-1);
        ValidateUtils.isTrue(res == 1, "updateStock is fail id:" + activityCourseDTO.getId());
    }


    /**
     * @author: ma wei long
     * @date:   2020年7月9日 下午6:56:25
     */
    @Override
    public void saveOrUpdateActivityCourse(ActivityCourseDTO reqDTO) {
        log.info("saveOrUpdateActivityCourse - reqDTO:{}",JSON.toJSONString(reqDTO));
        checkParam(reqDTO);
        ActivityCourse activityCourse = CoverUtil.cover(reqDTO, ActivityCourse.class);
        activityCourse.setUpdateTime(activityCourse.getCreateTime());
        activityCourse.setUpdateUser("auto");//TODO 记得取当前登录用户

        ActivityCourseDTO activityCourseDTODB = getByCourseId(reqDTO.getCourseId());
        if(null == activityCourseDTODB) {
            activityCourse.setCreateTime(LocalDateUtil.asLocalDateTime(new Date()));
            activityCourse.setCreateUser("auto");//TODO 记得取当前登录用户
            save(activityCourse);
        }else {
            activityCourse.setId(activityCourseDTODB.getId());
            ValidateUtils.isTrue(updateById(activityCourse), "活动课程更新失败，id:"+activityCourse.getId());
        }
        redisTemplate.opsForValue().set(CacheDefine.ActivityCourse.getKey(activityCourse.getCourseId()), JSON.toJSONString(ConvertUtils.convert(activityCourse, ActivityCourseDTO.class)), DateUtil.getSecond(new Date(), LocalDateUtil.asDate(activityCourse.getEndTime())), TimeUnit.SECONDS);
        redisTemplate.opsForValue().set(CacheDefine.ActivityCourse.getStockKey(activityCourse.getCourseId()), activityCourse.getStock().toString(), DateUtil.getSecond(new Date(), LocalDateUtil.asDate(activityCourse.getEndTime())), TimeUnit.SECONDS);
    }

    /**
     * @author: ma wei long
     * @date:   2020年7月9日 下午7:00:01
     */
    private void checkParam(ActivityCourseDTO reqDTO) {
        ValidateUtils.notNullParam(reqDTO);
        ValidateUtils.notNullParam(reqDTO.getAmount());
        ValidateUtils.isTrue(reqDTO.getAmount() > 0, "价格必须大于零");
        ValidateUtils.notNullParam(reqDTO.getCourseId());
        ValidateUtils.isTrue(reqDTO.getCourseId() > 0, "课程必须大于零");
        ValidateUtils.notNullParam(reqDTO.getStock());
        ValidateUtils.isTrue(reqDTO.getStock() > 0, "库存必须大于零");
        ValidateUtils.notNullParam(reqDTO.getBeginTime());
        ValidateUtils.notNullParam(reqDTO.getEndTime());
        ValidateUtils.isTrue(DateUtil.isBefore(reqDTO.getEndTime(), reqDTO.getBeginTime()), "结束时间必须大于开始时间");
    }
}
