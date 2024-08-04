package com.yash.repo;

import com.yash.models.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepo extends JpaRepository<Message,Long> {
    List<Message> findByChatIdOrderByCreatedDateAsc(Long chatId);
}
