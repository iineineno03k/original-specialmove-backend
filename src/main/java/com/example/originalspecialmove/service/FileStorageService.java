package com.example.originalspecialmove.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;

@Service
public class FileStorageService {
    @Autowired
    private AmazonS3 fileClient;

    public String fileUpload(MultipartFile image) throws Exception {
        String fileName = createFileName(image);
        File uploadFile = new File(fileName);

        // try-with-resources
        try (FileOutputStream uploadFileStream = new FileOutputStream(uploadFile)) {
            byte[] bytes = image.getBytes();
            uploadFileStream.write(bytes);

            // 引数：S3の格納先オブジェクト名,ファイル名,ファイル
            fileClient.putObject("originalmove", fileName, uploadFile);
            uploadFile.delete();
            return fileName;
        } catch (AmazonServiceException e) {
            e.printStackTrace();
            throw e;
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }

    }

    private String createFileName(MultipartFile image) throws Exception {
        // fileタイプチェック
        if (!image.getContentType().equals("image/jpeg")
                && !image.getContentType().equals("image/png")
                && !image.getContentType().equals("image/gif")) {
            throw new Exception("file type error");
        }

        int idx = image.getOriginalFilename().lastIndexOf(".");
        // 拡張子取得
        String extName = image.getOriginalFilename().substring(idx + 1);
        String fileName = RandomStringUtils.randomAlphabetic(20) + "." + extName;
        return fileName;
    }
}
