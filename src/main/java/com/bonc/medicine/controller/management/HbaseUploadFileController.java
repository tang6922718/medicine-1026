package com.bonc.medicine.controller.management;

import com.bonc.medicine.hbase.HbaseUploadFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Created by Administrator on 2018/9/3.
 */
@RestController
@RequestMapping("/file")
public class HbaseUploadFileController {

    @Autowired
    HbaseUploadFile uploadFile;

    @PostMapping("/upload")
    @ResponseBody
    public String uploadFileToHbase(@RequestParam MultipartFile myfile) throws IOException {
        return  uploadFile.uploadFileToHbase(myfile);
    }

    @PostMapping("/read")
    @ResponseBody
    public String readFileFromHbase(@RequestParam String key,@RequestParam String dirPath) throws Exception {
        return  uploadFile.readFileFromHbase(key,dirPath);
    }

    /**
     * Hbase图片加载
     * @param key
     * @return 返回base64
     * @throws Exception
     */
    @GetMapping("/readImgByByte")
    public String readImgByByte(@RequestParam String key) throws Exception {
        return  uploadFile.readImgByByte(key);
    }

}
