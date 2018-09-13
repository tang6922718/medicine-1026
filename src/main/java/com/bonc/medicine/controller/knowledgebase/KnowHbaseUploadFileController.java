package com.bonc.medicine.controller.knowledgebase;

import com.bonc.medicine.hbase.HbaseUploadFileTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Created by Administrator on 2018/9/3.
 */
@RestController
@RequestMapping("/hfile")
public class KnowHbaseUploadFileController {

    @Autowired
    HbaseUploadFileTest uploadFile;

    @PostMapping("/upload")
    @ResponseBody
    public String uploadFileToHbase(@RequestParam MultipartFile myfile) throws IOException {
        return  uploadFile.uploadFileToHbase(myfile);
    }

    @PostMapping("/read")
    @ResponseBody
    public String readFileFromHbase(@RequestParam MultipartFile myfile) throws IOException {
        return  uploadFile.uploadFileToHbase(myfile);
    }

}
