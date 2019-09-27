package com.learn.ad.service.impl;

import com.learn.ad.Utils.CommonUtils;
import com.learn.ad.constant.CommonStatus;
import com.learn.ad.constant.Constants;
import com.learn.ad.dao.AdPlanRepository;
import com.learn.ad.dao.AdUserRepository;
import com.learn.ad.enitiy.AdPlan;
import com.learn.ad.enitiy.AdUser;
import com.learn.ad.exception.AdException;
import com.learn.ad.service.IAdPlanService;
import com.learn.ad.vo.AdPlanGetRequest;
import com.learn.ad.vo.AdPlanRequest;
import com.learn.ad.vo.AdPlanResponse;
import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class AdPlanServiceImpl implements IAdPlanService {
    private AdUserRepository userRepository;
    private AdPlanRepository planRepository;

    @Autowired
    public AdPlanServiceImpl(AdUserRepository userRepository, AdPlanRepository planRepository) {
        this.userRepository = userRepository;
        this.planRepository = planRepository;
    }


    @Override
    @Transactional
    public AdPlanResponse createPlan(AdPlanRequest request) throws AdException {
        if(!request.createValidate()){
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }
        Optional<AdUser> adUser= userRepository.findById(request.getUserId());
        if(!adUser.isPresent()){
            throw new AdException(Constants.ErrorMsg.CAN_NOT_FIND_RECORD);
        }
        AdPlan oldPlan= planRepository.findByUserIdAndPlanName(request.getUserId(),request.getPlanName());
        if(oldPlan!=null)
            throw new AdException(Constants.ErrorMsg.SAME_NAME_ERROR);
        AdPlan newAdPlan= planRepository.save(new AdPlan(request.getUserId(),request.getPlanName(),
                CommonUtils.parseStringDate(request.getStartDate()),
                CommonUtils.parseStringDate(request.getEndDate())));
        return new AdPlanResponse(newAdPlan.getId(),newAdPlan.getPlanName());
    }

    @Override
    public List<AdPlan> getAdPlanByIds(AdPlanGetRequest request) throws AdException {
        if(!request.validate())
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        log.info("成了");
        return planRepository.findAllByIdInAndUserId(request.getIds(),request.getUserId());

    }

    @Override
    @Transactional
    public AdPlanResponse updateAdPlanRequest(AdPlanRequest request) throws AdException {
        if(!request.updateValidate())
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        AdPlan plan=planRepository.findByIdAndUserId(request.getId(),request.getUserId());
        if(plan==null)
            throw new AdException(Constants.ErrorMsg.CAN_NOT_FIND_RECORD);
        if(request.getPlanName()!=null){
            plan.setPlanName(request.getPlanName());
        }
        if(request.getStartDate()!=null){
            plan.setStartDate(CommonUtils.parseStringDate(request.getStartDate()));
        }
        if(request.getEndDate()!=null){
            plan.setEndDate(CommonUtils.parseStringDate(request.getEndDate()));
        }
        plan.setUpdateTime(new Date());
        plan= planRepository.save(plan);
        return new AdPlanResponse(plan.getId(),plan.getPlanName());
    }

    @Override
    @Transactional
    public void deleteAdPlan(AdPlanRequest request) throws AdException {
        if(!request.deleteValidate())
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        AdPlan plan= planRepository.findByIdAndUserId(request.getId(),request.getUserId());
        if(plan==null)
            throw new AdException(Constants.ErrorMsg.CAN_NOT_FIND_RECORD);
        plan.setPlanStatus(CommonStatus.INVALID.getStatus());
        plan.setUpdateTime(new Date());
        planRepository.save(plan);

    }
}
