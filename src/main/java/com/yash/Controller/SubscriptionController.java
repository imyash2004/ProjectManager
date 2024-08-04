package com.yash.Controller;

import com.yash.models.PlanType;
import com.yash.models.Subscription;
import com.yash.models.User;
import com.yash.service.SubscriptionService;
import com.yash.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/subscriptions")
public class SubscriptionController {
    @Autowired
    private SubscriptionService subscriptionService;
    @Autowired
    private UserService userService;

    @GetMapping("/user")
    public ResponseEntity<Subscription>getUserSubscription(@RequestHeader("Authorization") String jwt)throws Exception{
        User user=userService.findUserProfileByJwt(jwt);
        Subscription subscription=subscriptionService.getUserSubscription(user.getId());
        return ResponseEntity.ok(subscription);
    }


    @PatchMapping("/upgrade")
    public ResponseEntity<Subscription>upgradeSubscription(@RequestHeader("Authorization") String jwt,
                                                            @RequestParam PlanType plantype)throws Exception{
        User user=userService.findUserProfileByJwt(jwt);
        Subscription subscription=subscriptionService.upgradeSubscription(user.getId(),plantype);
        return ResponseEntity.ok(subscription);
    }



}
