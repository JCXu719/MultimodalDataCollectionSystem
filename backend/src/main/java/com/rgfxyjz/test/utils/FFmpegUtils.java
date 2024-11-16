package com.rgfxyjz.test.utils;

import ws.schild.jave.Encoder;
import ws.schild.jave.EncoderException;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.encode.AudioAttributes;
import ws.schild.jave.encode.EncodingAttributes;
import ws.schild.jave.encode.VideoAttributes;
import ws.schild.jave.info.MultimediaInfo;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class FFmpegUtils {

    //格式转换
    public static boolean formatToMp4(String url, String targetPath) {
        File target = new File(targetPath);
        MultimediaObject multimediaObject;
        try {
            multimediaObject = new MultimediaObject(new File(url));
            //multimediaObject = new MultimediaObject(new URL(url));
            EncodingAttributes attributes = new EncodingAttributes();
            // 设置视频的音频参数
            AudioAttributes audioAttributes = new AudioAttributes();
            attributes.setAudioAttributes(audioAttributes);
            // 设置视频的视频参数
            VideoAttributes videoAttributes = new VideoAttributes();
            // 设置帧率
            videoAttributes.setFrameRate(25);
            attributes.setVideoAttributes(videoAttributes);
            // 设置输出格式
            attributes.setOutputFormat("mp4");
            Encoder encoder = new Encoder();
            encoder.encode(multimediaObject, target, attributes);
            return true;
        } catch (Exception e) {
            System.out.println("格式化视频异常");
            e.printStackTrace();
            return false;
        }
    }
    //获取视频信息
    public static Map<String,Object> getVideoInfo(String filePath) {
        try {
            MultimediaObject multimediaObject = new MultimediaObject(new File(filePath));
            MultimediaInfo info = multimediaObject.getInfo();

            double frameRate = info.getVideo().getFrameRate();
            double bitRate = info.getVideo().getBitRate();
            Map<String, Object> videoDetails = new HashMap<>();
            videoDetails.put("bitRate", bitRate / 1000);
            videoDetails.put("frameRate", frameRate);
            return videoDetails;
        } catch (Exception e) {
            System.err.println("获取视频信息失败: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
