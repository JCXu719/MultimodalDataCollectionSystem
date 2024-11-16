/**
 * @FileName ILogRecordService
 * @Description TODO
 * @Author 57004
 * @date 2024-11-14
 **/
package com.rgfxyjz.test.service.impl;

import com.mzt.logapi.service.ILogRecordService;

import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

@Service
public class FileLogRecordService implements ILogRecordService {

    private static final String LOG_FILE_PATH = "biz-log-output.txt";

    private String formatLogEntry(com.mzt.logapi.beans.LogRecord logRecord) {
        return String.format(
                "Timestamp: %s, BizNo: %s, Operator: %s, action: %s",
                logRecord.getCreateTime(),
                logRecord.getBizNo(),
                logRecord.getOperator(),
                logRecord.getAction()

        );
    }

    @Override
    public void record(com.mzt.logapi.beans.LogRecord logRecord) {
        // 将日志记录内容格式化
        String logEntry = formatLogEntry(logRecord);

        // 写入日志文件
        try (BufferedWriter writer = Files.newBufferedWriter(
                Paths.get(LOG_FILE_PATH),
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND)) {
            writer.write(logEntry);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<com.mzt.logapi.beans.LogRecord> queryLog(String bizNo, String type) {
        return List.of();
    }

    @Override
    public List<com.mzt.logapi.beans.LogRecord> queryLogByBizNo(String bizNo, String type, String subType) {
        return List.of();
    }
}

