package com.bonc.medicine.controller.management;

import com.bonc.medicine.hbase.HbaseUploadFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;

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
    public String uploadFileToHbase(@RequestParam MultipartFile myfile,@RequestParam(required = false) String key) throws IOException {
        return  uploadFile.uploadFileToHbase(myfile,key);
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

    /**
     * Hbase存储文件下载
     * @param key
     * @param res
     * @throws Exception
     */
    @GetMapping("/downloadFile")
    public void downloadFile(@RequestParam String key,HttpServletResponse res) throws Exception{
        res.setHeader("content-type", "application/octet-stream");
        res.setContentType("application/octet-stream");
        res.setHeader("Content-Disposition", "attachment;filename=" + key);
        byte[] buff = uploadFile.downloadFile(key);

        OutputStream os = res.getOutputStream();
        os.write(buff);
        os.flush();
        os.close();
    }

    @GetMapping("/getImgStream")
    public void getImgStream(String key,HttpServletResponse response){
        getFileStream(key,response);
    }

    @GetMapping("/getAudioStream")
    public void getAudioStream(String key,HttpServletResponse response){
        getFileStream(key,response);
    }

    @GetMapping("/getVideoStream")
    public void getVideoStream(String key,HttpServletResponse response){
        getFileStream(key,response);
    }


    private void getFileStream(String key,HttpServletResponse response){
        try {
            byte[] file = uploadFile.downloadFile(key);
            // 清空response
            response.reset();
            // 设置response的Header
    //        response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename));
    //        response.addHeader("Content-Length", "" + file.length());
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            toClient.write(file);
            toClient.flush();
            toClient.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
