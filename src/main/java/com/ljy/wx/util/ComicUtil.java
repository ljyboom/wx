package com.ljy.wx.util;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ComicUtil {

     public static List<String> run(String p,int a, int c,String t){
         //创建一个脚本引擎管理器
         ScriptEngineManager manager = new ScriptEngineManager();
         //获取一个指定的名称的脚本引擎
         ScriptEngine engine = manager.getEngineByName("javascript");
         List<String> list=new ArrayList<>();
         try{
             //获取当前类的所在目录的路径
             String path = ComicUtil.class.getResource("").getPath();
             // FileReader的参数为所要执行的js文件的路径
             engine.eval(new FileReader(path + "comic.js"));
             if (engine instanceof Invocable) {
                 Invocable invocable = (Invocable) engine;
                 //从脚本引擎中返回一个给定接口的实现
                 Methods executeMethod = invocable.getInterface(Methods.class);
                 //执行指定的js方法
                 String result=executeMethod.run(p,a,c,t);
                 //获取漫画图片地址
//                 Pattern pattern = Pattern.compile("r-lazyload=(.+?)alt");
//                 Matcher matcher = pattern.matcher(result);
                 String cid=result.substring(result.indexOf("var cid=")+8,result.indexOf(";var key="));
                 String key=result.substring(result.indexOf("var key=")+10,result.indexOf("';var pix=")-1);
                 String pix=result.substring(result.indexOf("var pix=")+9,result.indexOf(";var pvalue")-1);
                 String pvalue=result.substring(result.indexOf("var pvalue=[")+12,result.indexOf("];for"));
                 String []pv=pvalue.split(",");
                 if (pv.length>0){
                     for (String s : pv) {
                         String img=pix+s.substring(1,s.length()-1)+"?cid="+cid+"&key="+key;
                         img=img.replace("'","");
//                         System.out.println(img);
                         list.add(img);
                     }
                 }
             }
             return list;
         }catch (Exception ex){
             ex.printStackTrace();
         }
         return null;
     }


     public void aa(){

         String t="|jpg|var|pvalue|525329|pix|cid|key|dm5imagefun|c9b0fb5b7a5313af97f9b9e320871f86|cdndm5|39|98||2_6153|38248|com|function||manhua1032|http|50|174|61|3_5612|for|9_8342|return|length|8_7313|5_2053|4_4937|7_5890|6_8539";
         String p="h 8(){1 5=3;1 7=\'9\';1 6='g://f-k-j-a-b.e.c/l/q/3';1 2=['/p.4','/r.4'];m(1 i=0;i<2.n;i++){2[i]=6+2[i]+\'?5=3&7=9\'}o 2}1 d;d=8();";
         ComicUtil.run(p,34,34,t);
     }
}
