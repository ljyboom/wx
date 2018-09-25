package com.ljy.wx.Helper;

import com.aliyun.oss.OSSClient;
import java.io.InputStream;
import java.net.URL;

/**
 * oss服務
 */
public class OssHelper {
    private static final String OSS_BUCKET = "ljydzq";
    private   OSSClient ossClient = new OSSClient("oss-cn-beijing.aliyuncs.com", "LTAIVimXXxGDn8gW", "wFYQEQkUAGXwwkhrCOpmHb5YjZxOQK");
    /**
     * 上传到oss
     */
    public void upload(String url,String name,String referer){
        String r=ossClient.putObject(OSS_BUCKET,name,NetWorkHelper.getInputStream(url,referer)).getETag();
        System.out.println(r);
    }

    public String uploadMusic(String url,String name){
        String r=ossClient.putObject(OSS_BUCKET,name,getInputStreamFromSourceURL(url)).getETag();
        return r;
    }

    /**
     * 根据 源地址 生成 input stream
     * @param source
     * @return 如果失败，返回 null
     */
    private static InputStream getInputStreamFromSourceURL(String source) {
        InputStream avatarInputStream = null;
        try {
            avatarInputStream = new URL(source).openStream();
        } catch (Exception e1) {
            try {
                //部分url用http访问失败，改成https则成功
                avatarInputStream = new URL(source.replaceAll("http", "https")).openStream();
            } catch (Exception e2) {
                e1.printStackTrace();
            }
        }
        return avatarInputStream;
    }

    public static void main(String[]str) {
        System.out.println("aaaa");
    }


}
