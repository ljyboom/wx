package com.ljy.wx.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
public class BasisService {
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void batchInsert(List list) {
        for (int i = 0; i < list.size(); i++) {
            em.persist(list.get(i));
            if (i % 30 == 0) {
                em.flush();
                em.clear();
            }
        }
    }
}
