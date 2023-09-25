package com.prograngers.backend.repository;

import com.prograngers.backend.entity.Notification;
import com.prograngers.backend.entity.member.Member;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    @Query("select n from Notification n where n.receiver= :receiverId order by n.createdAt desc limit 9")
    List<Notification> findAllByReceiver(@Param("receiverId") Long receiverId);

    List<Notification> findTop9ByReceiverOrderByCreatedAtDesc(Member receiver);

}
