package com.bonc.medicine.controller.management;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bonc.medicine.hdfs.HdfsFileSystem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

//import com.busymonkey.hadoop.*;

@RestController
@RequestMapping("/hdfs")
public class HDFSFileController {

    @RequestMapping("/hello")
    public ModelAndView hello() {
        String message = "Hello World, Spring 3.0!";
        System.out.println(message);
        return new ModelAndView("hello1", "message", message);
    }
    @RequestMapping("/upload")
    public String upLoad(HttpServletRequest request, HttpServletResponse response)
            throws IllegalStateException, IOException{
        //解析器解析request的上下文
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        //先判断request中是否包涵multipart类型的数据，
        if(multipartResolver.isMultipart(request)) {
            //再将request中的数据转化成multipart类型的数据
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            Iterator<String> iter = multiRequest.getFileNames();
            while(iter.hasNext()) {
                MultipartFile file = multiRequest.getFile((String)iter.next());
                if(file != null) {
                    CommonsMultipartFile cf= (CommonsMultipartFile)file;
                    DiskFileItem fi = (DiskFileItem)cf.getFileItem();
                    File inputFile = fi.getStoreLocation();
                    HdfsFileSystem.createFile(inputFile, "hdfs://192.168.241.149:9000/upload/1.mp4");
                }
            }
        }
        return "/hello1";
    }
}