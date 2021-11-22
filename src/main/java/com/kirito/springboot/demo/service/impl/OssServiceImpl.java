package com.kirito.springboot.demo.service.impl;

import cn.hutool.core.date.DateTime;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.kirito.springboot.demo.service.OssService;
import com.kirito.springboot.demo.util.AliyunConfig;
import com.kirito.springboot.demo.util.FileTypeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * Copyright (c) 2021
 * @Project: SpringbootDemo
 * @Author: Finger
 * @FileName: OssServiceImpl.java
 * @LastModified: 2021/11/10 15:34:10
 */

@Service
@Slf4j
@Configuration
public class OssServiceImpl implements OssService {

    @Resource
    AliyunConfig aliyunConfig;

    @Override
    public String uploadFile(MultipartFile file) throws IOException {
        OSS ossClient = new OSSClientBuilder().build(aliyunConfig.endPoint, aliyunConfig.keyId, aliyunConfig.accesskeySecret);
        InputStream inputStream = file.getInputStream();
        String fileName;
        fileName = UUID.randomUUID().toString().replaceAll("-", "");
        String datePath = new DateTime().toString("yyyy/MM/dd");
        fileName = aliyunConfig.refPath + "/" + datePath + "/" + fileName + "." + FileTypeUtils.getFileTypeByMagicNumber(file.getInputStream());
        ossClient.putObject(aliyunConfig.bucketName, fileName, inputStream);
        ossClient.shutdown();
        return aliyunConfig.refHost + fileName;
    }
}
