package com.bonc.medicine.utils;


import org.springframework.beans.factory.annotation.Value;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @program: medicine
 * @description:
 * @author: hejiajun
 * @create: 2018-09-20 10:24
 **/
public class HttpTool {

	@Value("${self.sms.requestUrl}")
    private static String requestUrl  = "http://118.178.86.197/cmas/cmasoutapi.do";
    private static String method = "httpSend";
    
    @Value("${self.sms.username}")
    private static String username = "hnxjw";
    
    @Value("${self.sms.password}")
    private static String password = "874639";

    private static String msg = "%d（动态验证码）请勿转发或者告诉别人";
    
    @Value("${self.sms.sign}")
    private static String sign = "bf944381d72d1f05f5d6fe68f8070645";
    private static String needstatus = "true";
    private static String needmo = "false";
    
    @Value("${self.sms.key}")
    private static String key = "CoC5rDRDZoa5CzY9";

    private static String proxyHost = null;
    private static Integer proxyPort = null;


    /*public static void main(String[] args) throws Exception{

        System.out.println(String.format(msg, 66666));
    }*/

    public static String sendPost( String phone, int code) throws Exception {
        String url = requestUrl;

        Map parameterMap = new HashMap();
        parameterMap.put("method", method);
        parameterMap.put("username", username);
        parameterMap.put("password", password);
        parameterMap.put("needstatus", needstatus);
        parameterMap.put("needmo", needmo);
        parameterMap.put("key", key);
        parameterMap.put("sign", sign);
        parameterMap.put("msg", String.format(msg, code));
        parameterMap.put("mobile", phone);

        /* Translate parameter map to parameter date string */
        StringBuffer parameterBuffer = new StringBuffer();
        if (parameterMap != null) {
            Iterator iterator = parameterMap.keySet().iterator();
            String key = null;
            String value = null;
            while (iterator.hasNext()) {
                key = (String)iterator.next();
                if (parameterMap.get(key) != null) {
                    value = (String)parameterMap.get(key);
                } else {
                    value = "";
                }

                parameterBuffer.append(key).append("=").append(value);
                if (iterator.hasNext()) {
                    parameterBuffer.append("&");
                }
            }
        }

       // System.out.println("POST parameter : " + parameterBuffer.toString());

        URL localURL = new URL(url);

        URLConnection connection = openConnection(localURL);
        HttpURLConnection httpURLConnection = (HttpURLConnection)connection;

        httpURLConnection.setDoOutput(true);
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setRequestProperty("Accept-Charset", "UTF-8");
        httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        httpURLConnection.setRequestProperty("Content-Length", String.valueOf(parameterBuffer.length()));

        /*httpURLConnection.setRequestProperty("method", method);
        httpURLConnection.setRequestProperty("username", username);
        httpURLConnection.setRequestProperty("password", password);

        httpURLConnection.setRequestProperty("sign", sign);
        httpURLConnection.setRequestProperty("needstatus", needstatus);
        httpURLConnection.setRequestProperty("needmo", needmo);
        httpURLConnection.setRequestProperty("key", key);

        httpURLConnection.setRequestProperty("msg", msg);
        httpURLConnection.setRequestProperty("mobile", "18980785969");*/

        OutputStream outputStream = null;
        OutputStreamWriter outputStreamWriter = null;
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader reader = null;
        StringBuffer resultBuffer = new StringBuffer();
        String tempLine = null;

        try {
            outputStream = httpURLConnection.getOutputStream();
            outputStreamWriter = new OutputStreamWriter(outputStream);

            outputStreamWriter.write(parameterBuffer.toString());
            outputStreamWriter.flush();

            if (httpURLConnection.getResponseCode() >= 300) {
                throw new Exception("HTTP Request is not success, Response code is " + httpURLConnection.getResponseCode());
            }

            inputStream = httpURLConnection.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream);
            reader = new BufferedReader(inputStreamReader);

            while ((tempLine = reader.readLine()) != null) {
                resultBuffer.append(tempLine);
            }

        } finally {

            if (outputStreamWriter != null) {
                outputStreamWriter.close();
            }

            if (outputStream != null) {
                outputStream.close();
            }

            if (reader != null) {
                reader.close();
            }

            if (inputStreamReader != null) {
                inputStreamReader.close();
            }

            if (inputStream != null) {
                inputStream.close();
            }

        }

        return resultBuffer.toString();
    }

    private static URLConnection openConnection(URL localURL) throws IOException {
        URLConnection connection;
        if (proxyHost != null && proxyPort != null) {
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));
            connection = localURL.openConnection(proxy);
        } else {
            connection = localURL.openConnection();
        }
        return connection;
    }

    public String getProxyHost() {
        return proxyHost;
    }

    public void setProxyHost(String proxyHost) {
        this.proxyHost = proxyHost;
    }

    public Integer getProxyPort() {
        return proxyPort;
    }

    public void setProxyPort(Integer proxyPort) {
        this.proxyPort = proxyPort;
    }

}
