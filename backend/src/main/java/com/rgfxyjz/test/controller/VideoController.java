package com.rgfxyjz.test.controller;

import com.rgfxyjz.test.cjo.Result;
import com.rgfxyjz.test.pojo.VideoFile;
import com.rgfxyjz.test.pojo.VideoFile;
import com.rgfxyjz.test.service.VideoService;
import com.rgfxyjz.test.utils.FileUtils;
import com.rgfxyjz.test.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/video")
public class VideoController {

    @Value("${app.file.upload.path}")
    private String fileUploadPath;

    @Value("${app.users.data}")
    private String UserData;


    @Autowired
    private VideoService videoService;

    // 上传视频，现在上传视频需要接收两个文件，文件1为视频文件，文件2为关键时间点文件(csv)
    @PostMapping("/upload")
    public Result videoUpload(@RequestParam("video") MultipartFile video,
                              @RequestParam("times") MultipartFile times,
                              @RequestHeader("token") String token) {

        System.out.println(video);
        System.out.println(times);
        System.out.println(token);

        if (video.isEmpty() || times.isEmpty()) {
            return Result.error("No file selected");
        }

        try {
            // 存放路径
            String path;
            // 文件名
            String fileName_video;
            String fileName_times;
            String fileName_answers;
            // 文件类型
            String fileType_video = FileUtils.getExtensionFromMimeType(video.getContentType());
            String fileType_times = FileUtils.getExtensionFromMimeType(times.getContentType());

            // 文件大小
            long fileSize_video = video.getSize();
            long fileSize_times = times.getSize();
            // 上传时间
            Timestamp uploadTime = Timestamp.valueOf(LocalDateTime.now());
            // 文件保存路径 videos/2022200000 xxx
            path = fileUploadPath + "/videos/" + JwtUtils.getStudentIdFromToken(token) + " " + JwtUtils.getFullNameFromToken(token);

            fileName_video = FileUtils.removeFileExtension(video.getOriginalFilename()) + '_' + new SimpleDateFormat("yyyyMMdd_HHmmss").format(uploadTime) + fileType_video;
            fileName_times = FileUtils.removeFileExtension(times.getOriginalFilename()) + '_' + new SimpleDateFormat("yyyyMMdd_HHmmss").format(uploadTime) + fileType_times;

            // 保存文件到服务器
            FileUtils.saveFile(video, path, fileName_video);
            FileUtils.saveFile(times, path, fileName_times);

            // 保存文件信息到数据库
            VideoFile videoFile_video = new VideoFile();
            VideoFile videoFile_times = new VideoFile();

            videoFile_video.setName(fileName_video);
            videoFile_video.setType(fileType_video);
            videoFile_video.setSize(fileSize_video);
            videoFile_video.setUrl(path + '/' + fileName_video);
            videoFile_video.setUploadTime(uploadTime);
            videoFile_video.setUsername(JwtUtils.getUsernameFromToken(token));

            videoFile_times.setName(fileName_times);
            videoFile_times.setType(fileType_times);
            videoFile_times.setSize(fileSize_times);
            videoFile_times.setUrl(path + '/' + fileName_times);
            videoFile_times.setUploadTime(uploadTime);
            videoFile_times.setUsername(JwtUtils.getUsernameFromToken(token));

            videoService.save(videoFile_video, path);
            videoService.save(videoFile_times, path);

            return Result.success(path);
        } catch (Exception e) {
            return Result.error("上传失败: " + e.getMessage());
        }
    }

    // 获取视频列表
    @GetMapping
    public Result list(@RequestHeader("token") String token) {
        try {
            if (JwtUtils.getRoleFromToken(token) == 0) {
                return Result.success(videoService.list());
            } else {
                return Result.error("无权限");
            }
        } catch (Exception e) {
            return Result.error("获取视频列表失败: " + e.getMessage());
        }
    }

    @PostMapping("/upload/data/{questionnaireId}")
    public Result VideoUpload(@RequestParam("file") MultipartFile file , @RequestHeader("token") String token,@PathVariable String questionnaireId){
        if(file.isEmpty()) {
            return Result.error("No file selected");
        }
        try {

            String path = UserData + '/' + questionnaireId + '/' + JwtUtils.getIdFromToken(token);//资源文件保存位置
            String fileType = FileUtils.getExtensionFromMimeType(file.getContentType());//获取文件扩展名
            String fileName = FileUtils.removeFileExtension(file.getOriginalFilename())+ '_'+LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"))  + fileType;//文件命名

            long fileSize = file.getSize();

            // 保存文件到服务器
            FileUtils.saveFile(file,path,fileName);
            // 保存文件信息到数据库
            VideoFile videoFile = new VideoFile();
            videoFile.setName(fileName);
            videoFile.setType(fileType);
            videoFile.setSize(fileSize);
            videoFile.setUrl(path + '/' + fileName );
            videoService.save(videoFile,path);

            return Result.success(path);
        } catch (Exception e) {
            return Result.error("上传失败:" + e.getMessage() );
        }
    }
    // 更新视频信息
    @PostMapping("/update/{id}")
    public Result save(@RequestParam String name,
                       @RequestHeader("token") String token,
                       @PathVariable Integer id) {
        try {
            if (JwtUtils.getRoleFromToken(token) == 0) {
                VideoFile videoFile = videoService.get(id);
                if(videoFile != null){
                    videoFile.setName(name);
                    videoService.update(videoFile);
                    return Result.success("视频信息更新成功");
                }else{
                    return Result.error("id为" + id.toString() +"的视频不存在");
                }
            } else {
                return Result.error("无权限");
            }
        } catch (Exception e) {
            return Result.error("更新视频信息失败: " + e.getMessage());
        }
    }

    // 根据ID获取视频
    @GetMapping("/{id}")
    public Result get(@PathVariable Integer id, @RequestHeader("token") String token) {
        try {
            if (JwtUtils.getRoleFromToken(token) == 0) {
                VideoFile videoFile = videoService.get(id);
                if (videoFile != null) {
                    return Result.success(videoFile);
                } else {
                    return Result.error("视频不存在");
                }
            } else {
                return Result.error("无权限");
            }
        } catch (Exception e) {
            return Result.error("获取视频失败: " + e.getMessage());
        }
    }

    // 删除视频
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id, @RequestHeader("token") String token) {
        try {
            if (JwtUtils.getRoleFromToken(token) == 0) {
                if (videoService.delete(id)) {
                    return Result.success("视频删除成功");
                } else {
                    return Result.error("视频文件不存在");
                }
            } else {
                return Result.error("无权限");
            }
        } catch (Exception e) {
            return Result.error("删除视频失败: " + e.getMessage());
        }
    }

    // 搜索视频
    @GetMapping("/search")
    public Result search(@RequestParam String name, @RequestHeader("token") String token) {
        try {
            int role = JwtUtils.getRoleFromToken(token);
            if (role == 0 || role == 1) { // 管理员或具有搜索权限的用户
                return Result.success(videoService.searchByVideoFilename(name));
            } else {
                return Result.error("无权限");
            }
        } catch (Exception e) {
            return Result.error("搜索视频失败: " + e.getMessage());
        }
    }

    // 获取视频文件
    @GetMapping("/getVideo/{id}")
    public void getVideo(@PathVariable Integer id, HttpServletRequest request, HttpServletResponse response) {
        try {
            VideoFile video = videoService.searchByVideoFileId(id);
            File file = new File(video.getUrl());
            if (!file.exists()) {
                response.setStatus(HttpStatus.NOT_FOUND.value());
                return;
            }
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            response.setHeader("Content-Disposition", "attachment; filename=" + file.getName().replace(" ", "_"));
            try (InputStream iStream = new FileInputStream(file)) {
                StreamUtils.copy(iStream, response.getOutputStream());
            }
            response.flushBuffer();
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }


}
