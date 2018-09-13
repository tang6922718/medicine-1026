package com.bonc.medicine.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.UUID;

/**
 * Created by Administrator on 2018/9/3.
 */
@Component("uploadFile")
public class HbaseUploadFileTest {

//    public static void main(String[] args)  throws IOException{
//
////        uploadFile1();
////        uploadFile2();
//        readFile("pngTest");
//    }

    @Value("${hbase.zookeeper.quorum}")
    private String zkQuorum;

    @Value("${hbase.master}")
    private String hBaseMaster;

    Configuration conf = new Configuration();

    private Configuration getConf(){
        conf.set(zkQuorum,hBaseMaster);
        return conf;
    }

    /*
    * 文件保存至Hbase
    * */
    public String uploadFileToHbase( MultipartFile myfile) throws IOException{
        String tableName = "image_audio_vedio";
        String family="cf";
        //产生一个UUID字符串，理论上绝对不会重复
        String uuid = UUID.randomUUID().toString();
        String key = myfile.getName()+"_"+uuid;//图片存储获取对应key值
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
    public byte[] readFileFromHbase(String key) throws Exception{
        conf = getConf();
        HTable table = new HTable(conf,"image_audio_vedio");
        Get get = new Get(key.getBytes());
        Result rs = table.get(get);
        return rs.value(); //保存get result的结果，字节数组形式
    }

    /*
    存储文件
    测试方法一：存入后的二进制读取出的图片被损坏
    */
    public static void uploadFileTest1() throws IOException{
        //writeback to hbase
        Configuration HBASE_CONFIG = new Configuration();
        HBASE_CONFIG.set("hbase.zookeeper.quorum", "192.168.1.24");

        String tableName = "HB_MEM_";
        String family="cf";
        HBaseAdmin hBaseAdmin = new HBaseAdmin(HBASE_CONFIG);

        if (hBaseAdmin.tableExists(tableName)) { //check
            hBaseAdmin.disableTable(tableName);
            hBaseAdmin.deleteTable(tableName);
            System.out.println(tableName + " is exist,detele....");
        }

        HTableDescriptor htd = new HTableDescriptor(TableName.valueOf(tableName));
        HColumnDescriptor cf= new HColumnDescriptor(family);
        htd.addFamily(cf);
        hBaseAdmin.createTable(htd);
        hBaseAdmin.close();
        HTable HBasetable = new HTable(HBASE_CONFIG,TableName.valueOf(tableName));

//        String filePath = "/root/input_2";
        String filePath = "C:\\Users\\Administrator\\Desktop\\test.png";
        //此步上传至服务器后，存储完毕后删除

        File file=new File(filePath);
        InputStreamReader in_stream = new InputStreamReader(new FileInputStream(file));
        BufferedReader in = new BufferedReader(in_stream);
        String s;
        int i=0;
        StringBuilder result = new StringBuilder();

        while ((s=in.readLine())!=null ) {

//            String[] words = s.split(" ");
            result.append(s);

        }
        String key = "pngTest";
        String value=result.toString();

        Put put = new Put(key.getBytes());

        put.add(family.getBytes(), "value".getBytes(), value.getBytes());

        System.out.println("Save to Hbase! key:"+key+" "+"value:"+value);
        HBasetable.put(put);
        HBasetable.close();
        System.out.println("put successful!!!");
    }

    /*
    存储文件
    测试方法二：
    */
    public static void uploadFileTest2() throws IOException{
        Configuration configuration = HBaseConfiguration.create();
        configuration.set("hbase.zookeeper.quorum", "192.168.1.24");
//        HTable table = new HTable(configuration, "HB_MEM_");


//        ==========
        String tableName = "HB_MEM_";
        String family="cf";
        HBaseAdmin hBaseAdmin = new HBaseAdmin(configuration);

        if (hBaseAdmin.tableExists(tableName)) { //check
            hBaseAdmin.disableTable(tableName);
            hBaseAdmin.deleteTable(tableName);
            System.out.println(tableName + " is exist,detele....");
        }

        HTableDescriptor htd = new HTableDescriptor(TableName.valueOf(tableName));
        HColumnDescriptor cf= new HColumnDescriptor(family);
        htd.addFamily(cf);
        hBaseAdmin.createTable(htd);
        hBaseAdmin.close();
        HTable HBasetable = new HTable(configuration,TableName.valueOf(tableName));
//        =========


        String imgPath = "C:\\Users\\Administrator\\Desktop\\test.png";
        FileInputStream fis = new FileInputStream(imgPath);
        byte[] bbb = new byte[fis.available()];//读图为流，但字节数组还是空的
        fis.read(bbb);//将文件内容写入字节数组
        fis.close();

        Put put = new Put("pngTest2".getBytes());
        put.add("cf".getBytes(), "value".getBytes(), bbb);//bbb就是图片转化成的字节数组
        HBasetable.put(put);
        HBasetable.close();
        System.out.println("put successful!!!");
    }

    /*
    * 读取文件*/
    public static void readFileTest(String key) throws IOException{
        //将hbase获取的二进制流转化成文件夹中的图片
        Configuration configuration = HBaseConfiguration.create();
        configuration.set("hbase.zookeeper.quorum", "192.168.1.24");
        HTable table = new HTable(configuration,"HB_MEM_");
        Get get = new Get("pngTest2".getBytes());
        Result rs = table.get(get);
        byte[] bs = rs.value(); //保存get result的结果，字节数组形式
        table.close();
        File file=new File("C:\\Users\\Administrator\\Desktop\\test_re.png");//将输出的二进制流转化后的图片的路径
        FileOutputStream fos=new FileOutputStream(file);
        fos.write(bs);
        fos.close();
    }

}
