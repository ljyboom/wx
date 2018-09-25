package com.ljy.wx.service;

import com.ljy.wx.Helper.NetWorkHelper;
import com.ljy.wx.entity.Video;
import com.ljy.wx.repository.VideoRepository;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class VideoService {
    private static String root="http://ugcws.video.gtimg.com/";//视频播放根地址
    @Autowired
    private VideoRepository videoRepository;


    /**
     * 查询视频
     */
    @Cacheable(value = "video", sync = true)
    public List<Video> getVideos(int page,int rows,int type){
        Pageable pageable = new PageRequest(page, rows);
        List<Video> list=videoRepository.findAllVideos(pageable,type);
        NetWorkHelper netWorkHelper = new NetWorkHelper();
        //重新匹配地址
        for (Video it:list){
            String vid=it.getVideoId();
            String filename = vid + ".mp4";
            String path = "http://vv.video.qq.com/getkey?format=2" +
                    "&otype=json&vt=150&vid=" + vid + "&ran=0\\%2E9477521511726081\\\\&charge=0&filename=" + filename + "&platform=11";
            String data=null;
            try {
                 data = netWorkHelper.loadHtml(path,"utf-8");
            }catch (Exception e){
                continue;
            }
            JSONObject json = JSONObject.fromObject(data.substring(data.indexOf("{"), data.length() - 1));
            String vkey = (String) json.get("key");
            String mp4_url = root + filename + "?vkey=" + vkey;
            it.setUrl(mp4_url);
        }

        return list;
    }

}
