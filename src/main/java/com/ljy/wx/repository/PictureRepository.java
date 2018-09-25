package com.ljy.wx.repository;
import com.ljy.wx.entity.Picture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PictureRepository extends JpaRepository<Picture,Long> {

  @Query("select p.name from Picture p")
  List<String> findAllName();
}
