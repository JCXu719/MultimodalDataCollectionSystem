/**
 * @FileName PictureController
 * @Description TODO
 * @Author 57004
 * @date 2024-08-22
 **/
package com.rgfxyjz.test.controller;

import com.rgfxyjz.test.cjo.Result;
import com.rgfxyjz.test.pojo.Picture;
import com.rgfxyjz.test.pojo.Picture;
import com.rgfxyjz.test.pojo.VideoFile;
import com.rgfxyjz.test.service.PictureService;
import com.rgfxyjz.test.service.UserService;
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

@RestController
@RequestMapping("/picture")
public class PictureController {
    @Value("${app.file.upload.path}")
    private String ResfileUploadPath;

    @Value("${app.users.data}")
    private String UserData;

    @Autowired
    private PictureService pictureService;

    @PostMapping("/upload/res")
    public Result pictureUpload(@RequestParam("signature") MultipartFile file , @RequestHeader("token") String token){
        if(file.isEmpty()) {
            return Result.error("No file selected");
        }
        try {
            String path = ResfileUploadPath + "/pictures/" + JwtUtils.getStudentIdFromToken(token) + " " + JwtUtils.getFullNameFromToken(token);//资源文件保存位置
            // 上传时间
            Timestamp uploadTime = Timestamp.valueOf(LocalDateTime.now());
            String fileType = FileUtils.getExtensionFromMimeType(file.getContentType());//获取文件扩展名
            String fileName = FileUtils.removeFileExtension(file.getOriginalFilename())+ '_'+new SimpleDateFormat("yyyyMMdd_HHmmss").format(uploadTime)  + fileType;//文件命名

            long fileSize = file.getSize();

            // 保存文件到服务器
            FileUtils.saveFile(file,path,fileName);
            // 保存文件信息到数据库
            Picture picture = new Picture();
            picture.setName(fileName);
            picture.setType(fileType);
            picture.setSize(fileSize);
            picture.setUrl(path + '/' + fileName );
            picture.setUploadTime(uploadTime);
            picture.setUsername(JwtUtils.getUsernameFromToken(token));
            pictureService.save(picture,path);

            return Result.success(path);
        } catch (Exception e) {
            return Result.error("上传失败:" + e.getMessage() );
        }
    }
    @PostMapping("/upload/data/{questionnaireId}")
    public Result PictureUpload(@RequestParam("file") MultipartFile file , @RequestHeader("token") String token,@PathVariable String questionnaireId){
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
            Picture picture = new Picture();
            picture.setName(fileName);
            picture.setType(fileType);
            picture.setSize(fileSize);
            picture.setUrl(path + '/' + fileName );
            pictureService.save(picture,path);

            return Result.success(path);
        } catch (Exception e) {
            return Result.error("上传失败:" + e.getMessage() );
        }
    }


    @GetMapping
    public Result list(@RequestHeader("token") String token){
        try{
            if(JwtUtils.getRoleFromToken(token) == 0){
                return Result.success(pictureService.list());
            }
            else{
                return Result.error("无权限");
            }
        }catch (Exception e){
            return Result.error(e.getMessage());
        }
    }


    // 更新图片名字
    @PostMapping("/update/{id}")
    public Result save(@RequestParam String name,
                       @RequestHeader("token") String token,
                       @PathVariable Integer id) {
        try {
            if (JwtUtils.getRoleFromToken(token) == 0) {
                Picture picture = pictureService.get(id);
                if (picture == null) {
                    return Result.error("不存在文件" + id.toString());
                }
                File file = new File(picture.getUrl());
                picture.setName(name);
                pictureService.update(picture);
                return Result.success("视频信息更新成功");
            } else {
                return Result.error("无权限");
            }
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    // 通过ID获取图片
    @GetMapping("/{id}")
    public Result get(@PathVariable Integer id, @RequestHeader("token") String token) {
        try {
            
            if (JwtUtils.getRoleFromToken(token) == 0) { // 管理员权限
                Picture picture = pictureService.get(id);
                if (picture != null) {
                    return Result.success(picture);
                } else {
                    return Result.error("图片不存在");
                }
            } else {
                return Result.error("无权限");
            }
        } catch (Exception e) {
            return Result.error("获取图片失败: " + e.getMessage());
        }
    }

    // 删除图片
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id, @RequestHeader("token") String token) {
        try {
            if (JwtUtils.getRoleFromToken(token) == 0) { // 管理员权限
                if(pictureService.delete(id)){
                    return Result.success("删除图片成功");
                }else {
                    return Result.error("图片" + id.toString() + "文件不存在");
                }

            } else {
                return Result.error("无权限");
            }
        } catch (Exception e) {
            return Result.error("删除图片失败: " + e.getMessage());
        }
    }

    // 搜索图片
    @GetMapping("/search")
    public Result search(@RequestParam String name, @RequestHeader("token") String token) {
        try {
            int role = JwtUtils.getRoleFromToken(token);
            if (role == 0 || role == 1) { // 管理员或具有搜索权限的用户
                return Result.success(pictureService.searchByPictureName(name));
            } else {
                return Result.error("无权限");
            }
        } catch (Exception e) {
            return Result.error("搜索图片失败: " + e.getMessage());
        }
    }

    @GetMapping("/getPicture/{id}")
    public void getPicture(@PathVariable Integer id, HttpServletRequest request, HttpServletResponse response) {
        try {
            Picture picture = pictureService.searchByPictureFileId(id);
            File file = new File(picture.getUrl());
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
