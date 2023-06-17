package com.lagou.edu.course.client.remote;

import com.lagou.edu.course.client.dto.MediaDTO;
import com.lagou.edu.course.client.dto.VideoPlayDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author mkp
 */
@FeignClient(name = "${remote.feign.edu-course-boot.name:edu-course-boot}", path = "/course/media")
public interface MediaRemoteService {

    @GetMapping("/getByLessonId")
    MediaDTO getByLessonId(@RequestParam("lessonId") Integer lessonId);

    /**
     * 获取DK数据
     * @param fileId
     * @param edk
     * @param userId
     * @return
     */
    @GetMapping("/alikey")
    byte[] getCourseMediaDKByFileId(@RequestParam("fileId") String fileId,
                                    @RequestParam("edk")  String edk,
                                    @RequestParam("userId")  Integer userId);

    /**
     * 更新或者保存媒体
     */
    @PostMapping("/updateOrSaveMedia")
    void updateOrSaveMedia(@RequestBody MediaDTO cediaDTO);

    /**
     * 获取是媒体播放信息
     */
    @GetMapping("/getVideoPlayInfo")
    VideoPlayDto getVideoPlayInfo(@RequestParam("lessonId")Integer lessonId,
                                  @RequestParam("userId") Integer userId);
}
