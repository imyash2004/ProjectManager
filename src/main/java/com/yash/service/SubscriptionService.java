package com.yash.service;

import com.yash.models.PlanType;
import com.yash.models.Subscription;
import com.yash.models.User;

public interface SubscriptionService {

    Subscription createSubscription(User user);


    Subscription getUserSubscription(Long userId)throws Exception;

    Subscription upgradeSubscription(Long userId, PlanType planType);

    boolean isValid(Subscription subscription);
}
