package com.learn.ad.dao;

import com.learn.ad.enitiy.AdUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdUserRepository  extends JpaRepository<AdUser, Long> {
    AdUser findByUsername(String userName);

}
