package com.bonc.medicine.hbase;

import org.apache.commons.codec.binary.Base64;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by Administrator on 2018/9/3.
 */
@Component
public class HbaseUploadFile {


    @Value("${hbase.zookeeper.quorum}")
    private String zkQuorum;

    @Value("${hbase.master}")
    private String hBaseMaster;

    private Configuration conf = new Configuration();

    private Configuration getConf(){
        conf.set("hbase.zookeeper.quorum",hBaseMaster);
        return conf;
    }

    /*
     * 文件保存至Hbase
     * */
    public String uploadFileToHbase( MultipartFile myfile) throws IOException{
        String tableName = "image_audio_vedio";
        String family="cf";
        //产生一个UUID字符串，理论上绝对不会重复
//        String uuid = UUID.randomUUID().toString();
//        String key = myfile.getName()+"_"+uuid;//图片存储获取对应key值

        Long times = new Date().getTime();
        Integer ran = (int)(Math.random()*900)+100;
        String key = times.toString()+ran.toString();//时间戳+3位随机数作为key

        conf = getConf();
        HBaseAdmin hBaseAdmin = new HBaseAdmin(conf);

        //如果表名不存在，则创建
        if (!hBaseAdmin.tableExists(tableName)) { //check
            HTableDescriptor htd = new HTableDescriptor(TableName.valueOf(tableName));
            HColumnDescriptor cf= new HColumnDescriptor(family);
            htd.addFamily(cf);
            hBaseAdmin.createTable(htd);
            hBaseAdmin.close();
        }

        HTable HBasetable = new HTable(conf,TableName.valueOf(tableName));
        byte[] bys = myfile.getBytes();
        Put put = new Put(key.getBytes());
        put.add(family.getBytes(), "value".getBytes(), bys);
        HBasetable.put(put);
        HBasetable.close();
//        System.out.println("put successful!!!");
        return key;
    }

    /*
     * 从hbase读取文件
     * */
    public String readFileFromHbase(String key,String dirPath) throws Exception{
        conf = getConf();
        HTable table = new HTable(conf,"image_audio_vedio");
//        Get get = new Get(key.getBytes());
//        Result rs = table.get(get);
//        byte[] bs = rs.value(); //保存get result的结果，字节数组形式
        byte[] bs = downloadFile(key);
        table.close();
        String filePath = dirPath+"/"+key;
        File file=new File(filePath);//将输出的二进制流转化后的图片的路径
        FileOutputStream fos=new FileOutputStream(file);
        fos.write(bs);
        fos.close();
        return filePath; //返回图片路径
    }


    /**
     * 从hbase读取图片转为base64
     * @param key
     * @return 将二进制转成base64返回
     * @throws Exception
     */
    public String readImgByByte(String key) throws Exception{
        return Base64.encodeBase64String(downloadFile(key));
    }

    /**
     * 从hbase读取文件返回byte[]
     * @param key
     * @return
     * @throws Exception
     */
    public byte[] downloadFile(String key) throws Exception{
        conf = getConf();
        HTable table = new HTable(conf,"image_audio_vedio");
        Get get = new Get(key.getBytes());
        Result rs = table.get(get);
        return rs.value();
    }


}
