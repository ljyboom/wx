package com.ljy.wx.task;

import com.ljy.wx.Helper.OssHelper;
import com.ljy.wx.entity.Music;
import com.ljy.wx.repository.MusicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class MusicTask {
    private String oss_path="https://ljydzq.oss-cn-beijing.aliyuncs.com/";
    @Autowired
    private MusicRepository musicRepository;

    public boolean addMusic(String title,String mid,String url,boolean fal){
        String img_url="https://i.ytimg.com/vi/"+mid+"/hqdefault.jpg";
        OssHelper ossHelper = new OssHelper();
        //上传视频
        System.out.println("开始上传");
        if (fal){
            ossHelper.uploadMusic(url,"music/mp4/"+mid+".mp4");
        }
        String mp4_url=oss_path+"music/mp4/"+mid+".mp4";
        System.out.println(mp4_url);
        //上传图片
        ossHelper.uploadMusic(img_url,"music/img/"+mid+".jpg");
        String img=oss_path+"music/img/"+mid+".jpg";
        System.out.println(img);
        Music music=new Music();
        music.setMid(mid);
        music.setTitle(title);
        music.setImg(img);
        music.setUrl(mp4_url);
        music.setCreateTime(new Date());
        musicRepository.save(music);
        System.out.println("上传完成");
        return true;
    }

//    @Scheduled(cron = "0/5 * * * * *")
    public void add() throws InterruptedException {
      String title="[MV] MOMOLAND (모모랜드) _ BBoom BBoom (뿜뿜)";
      String mid="JQGRg8XBnB4";
      String url="https://r4---sn-i3belne6.googlevideo.com/videoplayback?itag=18&sparams=clen,dur,ei,expire,gir,id,ip,ipbits,ipbypass,itag,lmt,mime,mip,mm,mn,ms,mv,pl,ratebypass,requiressl,source&ei=1KmpW6CUCZKFgAOl6JvADQ&id=o-AAAEqFcKwReKNmVztF2MmEXycnYQM85r9Zijs6AMA_df&c=WEB&fvip=4&gir=yes&requiressl=yes&ip=1.10.189.157&lmt=1536164339034474&dur=215.318&ratebypass=yes&source=youtube&key=cms1&clen=19677036&expire=1537867316&pl=26&mime=video%2Fmp4&ipbits=0&signature=56674863691DD2419A2A9092B70FEA2BDE02B5F0.64FFBA78720D335DF13BF152E100514BD1542809&video_id=IHNzOHi8sJs&title=BLACKPINK+-+%E2%80%98%EB%9A%9C%EB%91%90%EB%9A%9C%EB%91%90+%28DDU-DU+DDU-DU%29%E2%80%99+M-V&rm=sn-uvu-c3367r,sn-30alz7z&fexp=23755740,23763599&req_id=a22a3a5e5995a3ee&redirect_counter=2&cms_redirect=yes&ipbypass=yes&mip=27.0.232.197&mm=30&mn=sn-i3belne6&ms=nxu&mt=1537845072&mv=u";
      boolean fal=addMusic(title,mid,url,false);
      if (fal)Thread.sleep(1000000);
    }

}
