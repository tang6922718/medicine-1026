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
        String ID="";

        if (name.contains(",")){
            String[] token=name.split(",");
            for (int i = 0; i < token.length; i++) {
                for (Map temp: categroylist) {
                    if (token[i].equals(temp.get("name").toString()) || temp.get("othername").toString().contains(token[i])){
                        ID+=(temp.get("id").toString()+",");
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
}
