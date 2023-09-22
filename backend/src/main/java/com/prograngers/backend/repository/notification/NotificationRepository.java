package com.prograngers.backend.repository.notification;

import com.prograngers.backend.entity.Notification;
import com.prograngers.backend.entity.member.Member;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findTop9ByReceiverOrderByCreatedAtDesc(Member receiver);

}
