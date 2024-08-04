package com.yash.service.impl;

import com.yash.models.PlanType;
import com.yash.models.Subscription;
import com.yash.models.User;
import com.yash.repo.SubscriptionRepo;
import com.yash.service.SubscriptionService;
import com.yash.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {


    @Autowired
    private UserService userService;

    @Autowired
    private SubscriptionRepo subscriptionRepo;

    @Override
    public Subscription createSubscription(User user) {
        Subscription subscription=new Subscription();
        subscription.setUser(user);
        subscription.setStartDate(LocalDate.now());
        subscription.setEndDate(LocalDate.now().plusMonths(12));
        subscription.setValid(true);
        subscription.setPlanType(PlanType.FREE);

        return subscriptionRepo.save(subscription);
    }

    @Override
    public Subscription getUserSubscription(Long userId) throws Exception {

        Subscription subscription= subscriptionRepo.findByUserId(userId);
        if(!isValid(subscription)){
            subscription.setPlanType(PlanType.FREE);
            subscription.setStartDate(LocalDate.now());
            subscription.setEndDate(LocalDate.now().plusMonths(12));

        }
        return subscriptionRepo.save(subscription);
    }

    @Override
    public Subscription upgradeSubscription(Long userId, PlanType planType) {
        Subscription subscription=subscriptionRepo.findByUserId(userId);
        subscription.setPlanType(planType);
        subscription.setStartDate(LocalDate.now());
        if(planType.equals(PlanType.ANNUALLY)){
            subscription.setEndDate(LocalDate.now().plusMonths(12));

        }
        else {
            subscription.setEndDate(LocalDate.now().plusMonths(1));
        }
        return null;
    }

    @Override
    public boolean isValid(Subscription subscription) {
        if (subscription.getPlanType().equals(PlanType.FREE)){
            return true;
        }
        LocalDate endDate=subscription.getEndDate();
        LocalDate currentDate=LocalDate.now();



        return endDate.isAfter(currentDate) || endDate.isEqual(currentDate);
    }
}
