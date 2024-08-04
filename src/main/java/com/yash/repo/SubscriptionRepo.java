package com.yash.repo;

import com.yash.models.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

@Repository
public interface SubscriptionRepo extends JpaRepository<Subscription,Long> {
    Subscription findByUserId(Long userId);

}
