package com.learn.ad.dao;

import com.learn.ad.enitiy.AdPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdPlanRepository extends JpaRepository<AdPlan, Long> {
    AdPlan findByIdAndUserId(Long id, Long userId);
    List<AdPlan> findAllByIdInAndUserId(List<Long> ids, Long userId);
    AdPlan findByUserIdAndPlanName(Long userId, String planName);
    List<AdPlan> findAllByPlanStatus(Integer status);
}
