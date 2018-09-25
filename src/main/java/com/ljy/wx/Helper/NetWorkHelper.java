package com.ljy.wx.Helper;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

import javax.net.ssl.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * 访问网络用到的工具类,用于请求第三方接口
 */
public class NetWorkHelper {

    /**
     * 请求第三方接口
     * @param reqUrl 请求地址
     * @param requestMethod 请求方式
     * @param json post请求时传递的json字符串数据
     * @return
     */
    public String getHttpsResponse(String reqUrl,String requestMethod,String json) {
        URL url;
        InputStream is;
        String resultData = "";
        try {
            url = new URL(reqUrl);
            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
            TrustManager[] tm = {xtm};
            SSLContext ctx = SSLContext.getInstance("TLS");
            ctx.init(null, tm, null);
            con.setSSLSocketFactory(ctx.getSocketFactory());
            con.setHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String arg0, SSLSession arg1) {
                    return true;
                }
            });
            con.setDoInput(true); //允许输入流，即允许下载
            con.setDoOutput(true); //允许输出流，即允许上传
            con.setUseCaches(false); //不使用缓冲
            if (requestMethod.equals("POST")){
            con.setRequestMethod("POST");
            OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
            wr.write(json);
            wr.flush();
            }else{
                con.setRequestMethod("GET"); //使用get请求
            }
            is = con.getInputStream();   //获取输入流，此时才真正建立链接
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader bufferReader = new BufferedReader(isr);
            String inputLine;
            while ((inputLine = bufferReader.readLine()) != null) {
                resultData += inputLine + "\n";
            }
            System.out.println(resultData);
        }catch (Exception e){
            e.printStackTrace();
        }
        return resultData;
    }



    X509TrustManager xtm = new X509TrustManager() {
        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
        public void checkServerTrusted(X509Certificate[] arg0, String arg1)throws CertificateException {}
        public void checkClientTrusted(X509Certificate[] arg0, String arg1)throws CertificateException{}

    };

    public String loadHtml(String address,String charsetName) {
        HttpURLConnection conn=null;
        URL url=null;
        InputStream in=null;
        BufferedReader reader=null;
        StringBuffer stringBuffer=null;
        try {
            url = new URL(address);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("accept-language","zh-CN,zh;q=0.9");
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(15000);
            conn.setReadTimeout(15000);
            conn.setDoInput(true);
            conn.connect();
            in = conn.getInputStream();
            reader = new BufferedReader(new InputStreamReader(in,charsetName));
            stringBuffer = new StringBuffer();
            String line = null;
            while((line = reader.readLine()) != null){
//                System.out.println(line);
                stringBuffer.append(line.trim());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            conn.disconnect();
            try {
                in.close();
                reader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return stringBuffer.toString();
    }

    public String loadaqy(String address) {
        HttpURLConnection conn=null;
        URL url=null;
        InputStream in=null;
        BufferedReader reader=null;
        StringBuffer stringBuffer=null;
        try {
            url = new URL(address);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("User-Agent","Mozilla/5.0 (iPhone; CPU iPhone OS 5_0 like Mac OS X) AppleWebKit/534.46 (KHTML, like Gecko) Version/5.1 Mobile/9A334 Safari/7534.48.3");
            conn.setConnectTimeout(15000);
            conn.setReadTimeout(15000);
            conn.setDoInput(true);
            conn.connect();
            in = conn.getInputStream();
            reader = new BufferedReader(new InputStreamReader(in,"utf-8"));
            stringBuffer = new StringBuffer();
            String line = null;
            while((line = reader.readLine()) != null){
//                System.out.println(line);
                stringBuffer.append(line.trim());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            conn.disconnect();
            try {
                in.close();
                reader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return stringBuffer.toString();
    }

    /**
     * 爬取漫画屋
     * @param url
     * @return
     */
    public static String loadData(String url){
        try{
            URL root = new URL(url);
            HttpURLConnection conn= (HttpURLConnection) root.openConnection();
            conn.setRequestMethod("GET");
            conn.setUseCaches(false);
            conn.setRequestProperty("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
            conn.setRequestProperty("Accept-Encoding","utf-8");
            conn.setRequestProperty("Accept-Language","zh-CN,zh;q=0.9");
            conn.setRequestProperty("Connection","keep-alive");
            conn.setRequestProperty("Cookie","frombot=1; DM5_MACHINEKEY=7c35c389-e0b4-4c80-882e-113f9f973c7b; SERVERID=node1; UM_distinctid=165acd3f913d0-013b569404798e-323b5b03-1fa400-165acd3f914b5d; CNZZDATA30046992=cnzz_eid%3D1241705430-1536198800-%252F%252Fwww.baidu.com%252F%26ntime%3D1536198800; CNZZDATA1258880908=1572928434-1536200214-%252F%252Fwww.baidu.com%252F%7C1536200214; __utma=1.1349165856.1536202898.1536202898.1536202898.1; __utmc=1; __utmz=1.1536202898.1.1.utmcsr=baidu|utmccn=(organic)|utmcmd=organic; CNZZDATA1258751965=2038983368-1536197936-%252F%252Fwww.1kkk.com%252F%7C1536197936; CNZZDATA1258751996=1068135873-1536200534-%252F%252Fwww.1kkk.com%252F%7C1536200534; firsturl=http%3A%2F%2Fwww.1kkk.com%2Fch1-525329%2F; CNZZDATA1258752048=154728340-1536200251-%252F%252Fwww.1kkk.com%252F%7C1536200251; CNZZDATA1261430601=311187979-1536200088-%252F%252Fwww.1kkk.com%252F%7C1536200088; dm5cookieenabletest=1; __AdinAll_SSP_UID=7e5c12af031db2b3e87620ef7f5c5692; __AdinAll_SSP_FRE_TIME=Fri, 07 Sep 2018 03:02:29 GMT; dm5imgcooke=525329%7C9; __utmt=1; ComicHistoryitem_zh=History=38248,636718296332762610,525329,2,0,0,0,1&ViewType=0; readhistory_time=1-38248-525329-2; image_time_cookie=525329|636718296332762610|4; dm5imgpage=525329|2:1:55:0; myTest=1536204045921; __AdinAll_SSP_FRE=18; __utmb=1.24.10.1536202898; __AdinAll_SSP_RECORD=39166-13632-191742-1822-14891-819645-7328-6034-400882-0-0-0-0-0-0-0-0-0-0-0");
            conn.setRequestProperty("Host","www.1kkk.com");
            conn.setRequestProperty("Referer","http://www.1kkk.com/ch1-525329/");
            conn.setRequestProperty("Upgrade-Insecure-Requests","1");
            conn.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36");
            conn.setConnectTimeout(15000);
            conn.setReadTimeout(15000);
            conn.setDoInput(true);
            conn.connect();

            BufferedReader  reader = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));
            String line = null;
            StringBuffer stringBuffer=new StringBuffer();
            while ((line = reader.readLine())!= null) {
                stringBuffer.append(line);
            }
            reader.close();
            //该干的都干完了,记得把连接断了
            conn.disconnect();
            return stringBuffer.toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static String loadComic(String url){
        try{
            URL root = new URL(url);
            HttpURLConnection conn= (HttpURLConnection) root.openConnection();
            conn.setRequestMethod("GET");
            conn.setUseCaches(false);
            conn.setRequestProperty("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
            conn.setRequestProperty("Accept-Encoding","utf-8");
            conn.setRequestProperty("Accept-Language","h-CN,zh;q=0.9");
            conn.setRequestProperty("Connection","keep-alive");
            conn.setRequestProperty("Cookie","SERVERID=node1; __utmc=1; __utmz=1.1536207110.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); UM_distinctid=165ad143ea63a0-0cd861149045fa-323b5b03-1fa400-165ad143ea712; __AdinAll_SSP_UID=25de1e05b90fc831419930872331407c; frame_isMask=no; frombot=1; DM5_MACHINEKEY=bf270d2c-010e-45a4-990a-b3c706be3d10; dm5cookieenabletest=1; __AdinAll_SSP_FRE_TIME=Tue, 11 Sep 2018 02:23:08 GMT; firsturl=http%3A%2F%2Fwww.dm5.com%2Fm495477%2F; MarketGidStorage=%7B%220%22%3A%7B%22svspr%22%3A%22http%3A%2F%2Fwww.dm5.com%2Fmanhua-yuqibeiaiburubei%2F%22%2C%22svsds%22%3A29%2C%22TejndEEDj%22%3A%22rUxS8GN1%2B%22%7D%2C%22C245580%22%3A%7B%22page%22%3A1%2C%22time%22%3A1536558500023%7D%2C%22C261292%22%3A%7B%22page%22%3A1%2C%22time%22%3A1536314573024%7D%7D; CNZZDATA30087176=cnzz_eid%3D1952070049-1536202913-%26ntime%3D1536559314; dm5imgcooke=426475%7C16%2C627972%7C2%2C503211%7C16%2C224372%7C16%2C599859%7C2%2C599847%7C16; readhistory_time=1-41679-599847-24; myTest=1536560172997; ComicHistoryitem_zh=History=38248,636718560409083756,525329,2,0,0,0,1|36975,636721739486336917,495477,1,1,0,1,0|18708,636718412923975856,209140,1,0,0,0,1|41679,636721889819124941,599847,-1,1,0,1,1|432,636719294662407970,-1,0,1,0,1,0|10684,636719372113709808,182730,1,1,0,1,215|37519,636721766794787404,503211,2,1,0,1,1|79,636719289871402630,402389,1,1,0,1,1395|9701,636719311302824237,158977,1,1,0,1,21|874,636719367262855434,29726,1,1,0,1,4|37534,636719370908612751,570559,1,1,0,1,19|19539,636719380680794173,240820,1,1,0,1,91|13413,636719384102787031,201524,38,1,0,1,53|40683,636719389722055076,578500,1,1,0,1,1|7424,636719399459743254,79429,1,1,0,1,1|41084,636719400391394848,585836,-1,1,0,1,1|19459,636719401812288591,220291,48,1,0,1,1|33771,636721722097336750,426475,1,1,0,1,1|20734,636721767141069556,224372,2,1,0,1,1&ViewType=1&OrderBy=1; firsturl=http%3A%2F%2Fwww.dm5.com%2Fm599847-p24%2F; CNZZDATA1261430596=753194807-1536204582-%252F%252Fwww.dm5.com%252F%7C1536561031; CNZZDATA30089965=cnzz_eid%3D42166983-1536206828-%26ntime%3D1536563228; CNZZDATA30090267=cnzz_eid%3D1884248241-1536203589-%26ntime%3D1536561423; __utma=1.798222243.1536207110.1536558493.1536563382.13; __AdinAll_SSP_RECORD=50050-362185-497603-340951-294040-192-583045-2477-30322-2196131-3892-6295-3326-2792-2053601-613972-961868-93396-3207446-51128; __AdinAll_SSP_FRE=37; dm5imgpage=426475|1:1:56:0,627972|1:1:17:0,503211|2:1:56:0,224372|2:1:56:0,599847|1:1:55:0,599859|1:1:56:0; image_time_cookie=599847|636721907004837253|21,599859|636721841318090591|0");
            conn.setRequestProperty("Host","www.dm5.com");
            conn.setRequestProperty("If-Modified-Since","Mon, 10 Sep 2018 07:36:25 GMT");
            conn.setRequestProperty("Upgrade-Insecure-Requests","1");
            conn.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36");
            conn.setConnectTimeout(15000);
            conn.setReadTimeout(15000);
            conn.setDoInput(true);
            conn.connect();

            BufferedReader  reader = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));
            String line = null;
            StringBuffer stringBuffer=new StringBuffer();
            while ((line = reader.readLine())!= null) {
                stringBuffer.append(line);
            }
            reader.close();
            //该干的都干完了,记得把连接断了
            conn.disconnect();
            return stringBuffer.toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static InputStream getInputStream(String url,String referer){
        try {
        URL root = new URL(url);
        HttpURLConnection conn= (HttpURLConnection) root.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept","image/webp,image/apng,image/*,*/*;q=0.8");
        conn.setRequestProperty("Accept-Language","zh-CN,zh;q=0.9");
        conn.setRequestProperty("Connection","keep-alive");
        conn.setRequestProperty("Cookie","Hm_lvt_d9f1c8630a7aa5c7ce2a72d4b564c044=1537241946,1537324986; Hm_lpvt_d9f1c8630a7aa5c7ce2a72d4b564c044=1537329008");
        conn.setRequestProperty("Host","pic.jj20.com");
        conn.setRequestProperty("Referer",referer);
        conn.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36");
        conn.setConnectTimeout(15000);
        conn.setReadTimeout(15000);
        conn.setDoInput(true);
        conn.connect();
        return conn.getInputStream();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void download(String urlList,String localAdd,String mp4name) {
        URL url = null;
        File dir = new File(localAdd);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        try {
            url = new URL(urlList);
            HttpURLConnection conn= (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept","image/webp,image/apng,image/*,*/*;q=0.8");
            conn.setRequestProperty("Accept-Language","zh-CN,zh;q=0.9");
            conn.setRequestProperty("Connection","keep-alive");
            conn.setRequestProperty("Cookie","Hm_lvt_d9f1c8630a7aa5c7ce2a72d4b564c044=1537241946; Hm_lpvt_d9f1c8630a7aa5c7ce2a72d4b564c044=1537257433");
            conn.setRequestProperty("Host","pic.jj20.com");
            conn.setRequestProperty("Referer","http://www.jj20.com/bz/ktmh/dmrw/10908.html");
            conn.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36");
            DataInputStream dataInputStream = new DataInputStream(conn.getInputStream());

            FileOutputStream fileOutputStream = new FileOutputStream(new File(localAdd + "/" + mp4name));
            ByteArrayOutputStream output = new ByteArrayOutputStream();

            byte[] buffer = new byte[1024];
            int length;

            while ((length = dataInputStream.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
            byte[] context=output.toByteArray();
            fileOutputStream.write(output.toByteArray());
            dataInputStream.close();
            fileOutputStream.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
