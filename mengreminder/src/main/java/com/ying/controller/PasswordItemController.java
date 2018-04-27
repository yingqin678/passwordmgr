package com.ying.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ying.service.mybatis.PasswordMngservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/item")
public class PasswordItemController {

    @Autowired
    public PasswordMngservice passwordMngservice;

    private static String CODE = "code";

    private static String APPNAME = "appName";

    private static String USERNAME = "userName";

    private static String PASSWORD = "password";

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public void addItem(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject body = getBodyString(request);
        passwordMngservice.addItem(getopenid(body.getString(CODE)), body.getString(APPNAME), body.getString(USERNAME), body.getString(PASSWORD));
    }

    @RequestMapping(value = "/query/app/name", method = RequestMethod.POST)
    public void queryAppName(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject body = getBodyString(request);

        List<String> appName = passwordMngservice.queryAppName(body.getString(CODE));

        renderData(response, JSON.toJSONString(appName));
    }

    @RequestMapping(value = "/query/user/name", method = RequestMethod.POST)
    public void queryUserName(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject body = getBodyString(request);

        List<String> userName = passwordMngservice.queryUserName(body.getString(CODE), body.getString(USERNAME));

        renderData(response, JSON.toJSONString(userName));
    }

    @RequestMapping(value = "/query/password", method = RequestMethod.POST)
    public void queryPassword(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject body = getBodyString(request);

        String password = passwordMngservice.queryPassword(body.getString(CODE), body.getString(APPNAME), body.getString(USERNAME));

        renderData(response, password);
    }

    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    public void modifyItem(HttpServletRequest request, HttpServletResponse response) {

    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public void deleteItem(HttpServletRequest request, HttpServletResponse response) {

    }

    private JSONObject getBodyString(HttpServletRequest request) throws IOException {
        BufferedReader bufferedReader = request.getReader();

        String body = "", temp;
        while ((temp = bufferedReader.readLine()) != null)
        {
            body += temp;
        }

        return JSON.parseObject(body);
    }

    /**
     * 通过PrintWriter将响应数据写入response，ajax可以接受到这个数据
     *
     * @param response
     * @param data
     */
    private void renderData(HttpServletResponse response, String data) {
        PrintWriter printWriter = null;
        try {
            printWriter = response.getWriter();
            printWriter.print(data);
        } catch (IOException ex) {
        } finally {
            if (null != printWriter) {
                printWriter.flush();
                printWriter.close();
            }
        }
    }

    private String getopenid(String code){

        String requestUrl =  "https://api.weixin.qq.com/sns/jscode2session";
        Map<String,String> requestUrlParam = new HashMap<String,String>();
        requestUrlParam.put("appid", "wx74ff0bb7e4cf6afc");  //开发者设置中的appId
        requestUrlParam.put("secret", "4f0271d286732c4fed936bce47f7688e"); //开发者设置中的appSecret
        requestUrlParam.put("js_code", code); //小程序调用wx.login返回的code
        requestUrlParam.put("grant_type", "authorization_code");    //默认参数

        //发送post请求读取调用微信 https://api.weixin.qq.com/sns/jscode2session 接口获取openid用户唯一标识
        JSONObject jsonObject = JSON.parseObject(sendPost(requestUrl, requestUrlParam));
        return jsonObject.getString("openid");
    }

    private String sendPost(String url, Map<String, ?> paramMap) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";

        String param = "";
        Iterator<String> it = paramMap.keySet().iterator();

        while(it.hasNext()) {
            String key = it.next();
            param += key + "=" + paramMap.get(key) + "&";
        }

        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("Accept-Charset", "utf-8");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }
}
