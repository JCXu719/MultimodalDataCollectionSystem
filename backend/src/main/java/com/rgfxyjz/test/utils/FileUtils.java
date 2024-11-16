package com.rgfxyjz.test.utils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FileUtils {

    // MIME 类型到扩展名的映射
    private static final Map<String, String> MIME_TYPE_TO_EXTENSION = new HashMap<>();

    static {
        MIME_TYPE_TO_EXTENSION.put("image/jpeg", ".jpg");
        MIME_TYPE_TO_EXTENSION.put("image/png", ".png");
        MIME_TYPE_TO_EXTENSION.put("image/gif", ".gif");
        MIME_TYPE_TO_EXTENSION.put("application/pdf", ".pdf");
        MIME_TYPE_TO_EXTENSION.put("video/mp4", ".mp4");
        MIME_TYPE_TO_EXTENSION.put("text/csv", ".csv");
        MIME_TYPE_TO_EXTENSION.put("video/webm", ".webm");
        // 添加更多 MIME 类型到扩展名的映射
    }

    /**
     * 根据 MIME 类型获取文件扩展名
     * @param mimeType MIME 类型
     * @return 文件扩展名，例如 ".jpg"，如果 MIME 类型未映射则返回 null
     */
    public static String getExtensionFromMimeType(String mimeType) {
        return MIME_TYPE_TO_EXTENSION.get(mimeType);
    }

    /**
     * 保存 MultipartFile 到指定路径，自动添加文件扩展名
     * @param file MultipartFile 对象
     * @param savePath 保存路径
     * @param fileName 保存的文件名
     * @throws IOException 如果保存文件时发生错误
     */
    public static void saveFile(MultipartFile file, String savePath, String fileName) throws IOException {
        // 创建保存目录
        File directory = new File(savePath);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // 获取 MIME 类型
        String contentType = file.getContentType();
        String extension = getExtensionFromMimeType(contentType);

        // 如果文件名没有扩展名，并且 MIME 类型有对应的扩展名，则添加扩展名
        if (!fileName.contains(".") && extension != null) {
            fileName += extension;
        }

        // 保存文件
        File dest = new File(directory, fileName);
        file.transferTo(dest);
    }

    /**
     * 获取文件扩展名
     * @param fileName 文件名
     * @return 文件扩展名
     */
    public static String getFileExtension(String fileName) {
        if (fileName == null || fileName.lastIndexOf('.') == -1) {
            return null;
        }
        return fileName.substring(fileName.lastIndexOf('.') + 1);
    }

    /**
     * 检查文件是否存在
     * @param filePath 文件路径
     * @return 如果文件存在返回true，否则返回false
     */
    public static boolean fileExists(String filePath) {
        File file = new File(filePath);
        return file.exists() && file.isFile();
    }

    /**
     * 删除文件
     * @param filePath 文件路径
     * @return 如果文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String filePath) {
        File file = new File(filePath);
        return file.exists() && file.delete();
    }

    public static String removeFileExtension(String fileName) {
        // 获取最后一个点的位置
        int lastDotIndex = fileName.lastIndexOf(".");

        // 如果没有找到点或者点是第一个字符，返回原文件名
        if (lastDotIndex == -1 || lastDotIndex == 0) {
            return fileName;
        }

        // 返回去除扩展名的文件名
        return fileName.substring(0, lastDotIndex);
    }
}
