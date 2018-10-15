package com.bonc.medicine.utils;

import java.util.List;
import java.util.Map;

/**
 * @ClassName ExchangeCategroyNameID
 * @Description 品种名和品种ID互换
 * @Author YQ
 * @Date 2018/9/16 15:56
 * @Version 1.0
 */
public class ExchangeCategroyNameID {

    public static String NameToId(String name,List<Map> categroylist){

        name=trimFirstAndLastChar(name,",");

        String ID="";

        if (name.contains(",")){
            String[] token=name.split(",");
            for (int i = 0; i < token.length; i++) {
                for (Map temp: categroylist) {
                    if (token[i].equals(temp.get("name").toString())){
                        ID+=(temp.get("id").toString()+",");
                        break;
                    }
                    if (temp.get("othername")!=null){
                        if (temp.get("othername").toString().contains(token[i]))
                        {
                            ID+=(temp.get("id").toString()+",");
                            break;
                        }
                    }
                }
            }
        }else {
            for (Map temp: categroylist) {
                if (temp.get("name").toString().equals(name)){
                    ID=temp.get("id").toString()+",";
                    break;
                }
            }
        }


        if (ID.length()>0) return ID.substring(0,ID.length()-1);
        else return "";
    }

    public static String IDToName(String ID,List<Map> categroylist){
        ID=trimFirstAndLastChar(ID,",");

        String name="";

        if (ID.contains(",")){
            String[] token=ID.split(",");
            for (int i = 0; i < token.length; i++) {
                for (Map temp: categroylist) {
                    if (token[i].equals(temp.get("id").toString())){
                        name+=(temp.get("name")+",");
                    }
                }
            }
        }else {
            for (Map temp: categroylist) {
                if (temp.get("id").toString().equals(ID)){
                    name=temp.get("name").toString()+",";
                    break;
                }
            }
        }

        if (name.length()>0) return name.substring(0,name.length()-1);
        else return "";
    }



    public static String CityNameToCode(String name,List<Map> categroylist){

        name=trimFirstAndLastChar(name,",");

        String ID="";
        for (Map temp: categroylist) {
            if (name.indexOf(temp.get("name").toString())==0){
                ID=temp.get("id").toString()+",";
                break;
            }
        }

        if (ID.length()>0) return ID.substring(0,ID.length()-1);
        else return "";
    }



    /**
     * 去除字符串首尾出现的某个字符.
     *
     * @param source  源字符串.
     * @param element 需要去除的字符.
     * @return String.
     */
    public static  String trimFirstAndLastChar(String source, String element) {
        if(source==null){
            return "";
        }
        source = source.trim(); // 循环去掉字符串首的beTrim字符
        if(source.isEmpty()){
            return "";
        }
        String beginChar = source.substring(0, 1);
        if (beginChar.equalsIgnoreCase(element)) {
            source = source.substring(1, source.length());
        }
        if(source.isEmpty()){
            return "";
        }
        // 循环去掉字符串尾的beTrim字符
        String endChar = source.substring(source.length() - 1, source.length());
        if (endChar.equalsIgnoreCase(element)) {
            source = source.substring(0, source.length()-1);
        }
        if(source.isEmpty()){
            return "";
        }
        return source;
    }

}
