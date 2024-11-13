/**
 * @FileName ScoreController
 * @Description TODO
 * @Author 57004
 * @date 2024-09-03
 **/

package com.rgfxyjz.test.controller;

import com.rgfxyjz.test.cjo.Result;
import com.rgfxyjz.test.pojo.Score;
import com.rgfxyjz.test.pojo.VideoFile;
import com.rgfxyjz.test.service.ScoreService;
import com.rgfxyjz.test.utils.FileUtils;
import com.rgfxyjz.test.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/score")
public class ScoreController {

    @Value("${app.file.upload.path}")
    private String fileUploadPath;

    @Autowired
    private ScoreService scoreService;

    private String cal_score = "";
    private float[] cal_score_array = new float[5];

    // 以下为分数计算索引表，下标直接对应题号，行为大五人格维度类型，列为正向/反向计分（1正向，-1反向）
    String[][] traits_criteria = {
            {"E","1"}, {"A","1"},{"C","-1"},{"N","-1"},{"O","-1"},
            {"E","1"}, {"A","1"},{"C","-1"},{"N","-1"},{"O","1"},
            {"E","-1"}, {"A","-1"},{"C","1"},{"N","1"},{"O","1"},
            {"E","-1"}, {"A","-1"},{"C","1"},{"N","1"},{"O","1"},
            {"E","1"}, {"A","1"},{"C","-1"},{"N","-1"},{"O","-1"},
            {"E","-1"}, {"A","1"},{"C","-1"},{"N","-1"},{"O","-1"},
            {"E","-1"}, {"A","-1"},{"C","1"},{"N","1"},{"O","1"},
            {"E","-1"}, {"A","-1"},{"C","1"},{"N","1"},{"O","1"},
            {"E","1"}, {"A","-1"},{"C","1"},{"N","-1"},{"O","-1"},
            {"E","1"}, {"A","-1"},{"C","-1"},{"N","-1"},{"O","-1"},
            {"E","-1"}, {"A","1"},{"C","1"},{"N","1"},{"O","-1"},
            {"E","1"}, {"A","1"},{"C","1"},{"N","1"},{"O","1"},
    };

    @GetMapping
    public Result list(@RequestHeader("token") String token) {
        try {
            if (JwtUtils.getRoleFromToken(token) == 0) { // Admin access
                List<Score> scores = scoreService.list();
                return Result.success(scores);
            } else {
                return Result.error("无权限");
            }
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @PostMapping("/upload")
    public Result upload(@RequestParam("answer") MultipartFile file,@RequestHeader("token") String token) {

        if (file.isEmpty()) {
            return Result.error("No file selected");
        }

        try {
            // 存放路径
            String path;
            // 文件名
            String fileName;
            // 文件类型
            String fileType = FileUtils.getExtensionFromMimeType(file.getContentType());
            // 上传时间
            Timestamp uploadTime = Timestamp.valueOf(LocalDateTime.now());
            // 文件保存路径
            path = fileUploadPath + "/scales/" + JwtUtils.getStudentIdFromToken(token) + " " + JwtUtils.getFullNameFromToken(token);

            fileName = FileUtils.removeFileExtension(file.getOriginalFilename()) + '_' + new SimpleDateFormat("yyyyMMdd_HHmmss").format(uploadTime) + fileType;

            // 保存文件到服务器
            FileUtils.saveFile(file, path, fileName);

            // 保存文件信息到数据库
            Score score = new Score();
            score.setScaleUrl(path + "/" + fileName);
            score.setUsername(JwtUtils.getUsernameFromToken(token));
            score.setScaleId(1L);
            score.setUploadTime(uploadTime);

            //读取csv文件测试
            String[][] csvData = readCSV(path+"/"+fileName);
            //打印测试，后期可删
            cal_score = calScore(csvData);

            score.setScores(cal_score);

            scoreService.save(score, path);

            // 上传成功则返回计算值
            return Result.success(cal_score_array);
        } catch (Exception e) {
            return Result.error("上传失败: " + e.getMessage());
        }
    }

    // 计算分数
    private String calScore(String[][] csvData){
        //各个维度的总值
        int sum_o = 0;
        int sum_c = 0;
        int sum_e = 0;
        int sum_a = 0;
        int sum_n = 0;

        // 读取第三列的数据，对应题号和
        int i = 0;
        for (String[] row : csvData) {
            switch (traits_criteria[i][0]) {
                case "O" -> {
                    if (traits_criteria[i][1].equals("-1")) {
                        sum_o += (-1) * Integer.parseInt(row[2]);
                    } else {
                        sum_o += Integer.parseInt(row[2]);
                    }
                }
                case "C" -> {
                    if (traits_criteria[i][1].equals("-1")) {
                        sum_c += (-1) * Integer.parseInt(row[2]);
                    } else {
                        sum_c += Integer.parseInt(row[2]);
                    }
                }
                case "E" -> {
                    if (traits_criteria[i][1].equals("-1")) {
                        sum_e += (-1) * Integer.parseInt(row[2]);
                    } else {
                        sum_e += Integer.parseInt(row[2]);
                    }
                }
                case "A" -> {
                    if (traits_criteria[i][1].equals("-1")) {
                        sum_a += (-1) * Integer.parseInt(row[2]);
                    } else {
                        sum_a += Integer.parseInt(row[2]);
                    }
                }
                case "N" -> {
                    if (traits_criteria[i][1].equals("-1")) {
                        sum_n += (-1) * Integer.parseInt(row[2]);
                    } else {
                        sum_n += Integer.parseInt(row[2]);
                    }
                }
            }

            i++;
        }

        // 统计和完成后，计算分数，max-min归一化，保留四位小数
        String score_o = String.format("%.4f",(float)(sum_o + 24)/ 48);
        String score_e = String.format("%.4f",(float)(sum_e + 24)/ 48);
        String score_a = String.format("%.4f",(float)(sum_a + 24)/ 48 );
        String score_n = String.format("%.4f",(float)(sum_n + 24)/ 48);
        String score_c = String.format("%.4f",(float)(sum_c + 18)/ 48 );

        cal_score_array[0] = Float.parseFloat(score_o);
        cal_score_array[1] = Float.parseFloat(score_c);
        cal_score_array[2] = Float.parseFloat(score_e);
        cal_score_array[3] = Float.parseFloat(score_a);
        cal_score_array[4] = Float.parseFloat(score_n);

        // 次序：OCEAN
        return score_o + "," +  score_c + "," + score_e + "," + score_a + "," + score_n;
    }

    // 读取CSV文件
    public static String[][] readCSV(String filePath) {
        List<String[]> csvRows = new ArrayList<>();
        String line;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            while ((line = br.readLine()) != null) {
                String[] values = line.split(","); // 使用逗号分隔
                csvRows.add(values);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        // 将List<String[]>转换为String[][]
        return csvRows.toArray(new String[0][]);
    }

    @GetMapping("/getCurCalScore")
    public Result getCurCalScore() {
        if (!cal_score.isEmpty()){
            return Result.success(cal_score_array);
        }else{
            return Result.error("量表尚未上传或分数尚未计算");
        }
    }

    // 根据用户名
    // 获取得到的人格分数
    /*
    @PostMapping("/upload")
    public Result upload(@RequestBody Score score,@RequestHeader("token") String token) {
        try {
            if (JwtUtils.getRoleFromToken(token) <= 1) {
                scoreService.save(score);
                return Result.success("成功上传");
            } else {
                return Result.error("无权限");
            }
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    */

    @GetMapping("/{id}")
    public Result get(@PathVariable Long id, @RequestHeader("token") String token) {
        try {
            if (JwtUtils.getRoleFromToken(token) == 0) { // Admin access
                Score score = scoreService.get(id);
                if (score != null) {
                    return Result.success(score);
                } else {
                    return Result.error("分数不存在");
                }
            } else {
                return Result.error("无权限");
            }
        } catch (Exception e) {
            return Result.error("获取分数失败: " + e.getMessage());
        }
    }

/*
    @PostMapping("/update/{id}")
    public Result save(@RequestBody Score score, @RequestHeader("token") String token, @PathVariable Long id) {
        try {
            if (JwtUtils.getRoleFromToken(token) == 0) { // Admin access
                score.setId(id);
                scoreService.save(score);
                return Result.success("分数信息更新成功");
            } else {
                return Result.error("无权限");
            }
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }


    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id, @RequestHeader("token") String token) {
        try {
            if (JwtUtils.getRoleFromToken(token) == 0) { // Admin access
                scoreService.delete(id);
                return Result.success("删除分数成功");
            } else {
                return Result.error("无权限");
            }
        } catch (Exception e) {
            return Result.error("删除分数失败: " + e.getMessage());
        }
    }


    @DeleteMapping("/scale/{scaleId}")
    public Result deleteByScaleId(@PathVariable Long scaleId, @RequestHeader("token") String token) {
        try {
            if (JwtUtils.getRoleFromToken(token) == 0) { // Admin access
                scoreService.deleteByScaleId(scaleId);
                return Result.success("删除与量表关联的分数成功");
            } else {
                return Result.error("无权限");
            }
        } catch (Exception e) {
            return Result.error("删除分数失败: " + e.getMessage());
        }
    }


    @GetMapping("/scale/{scaleId}")
    public Result getByScaleId(@PathVariable Long scaleId, @RequestHeader("token") String token) {
        try {
            int role = JwtUtils.getRoleFromToken(token);
            if (role == 0 || role == 1) { // Admin or user with special permissions
                List<Score> scores = scoreService.getByScaleId(scaleId);
                return Result.success(scores);
            } else {
                return Result.error("无权限");
            }
        } catch (Exception e) {
            return Result.error("获取分数失败: " + e.getMessage());
        }
    }*/

}

