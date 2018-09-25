package com.ljy.wx.repository;

import com.ljy.wx.entity.Video;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VideoRepository extends JpaRepository<Video,Long> {

    @Query("select v.videoId from Video v group by v.videoId")
    List<String> findAllByVideoId();

    @Query("select v from Video v where v.type =:type order by function('rand')")
    List<Video> findAllVideos(Pageable pageable,@Param("type")int type);

    List<Video> findAllBySource(@Param("source")String source);
}
