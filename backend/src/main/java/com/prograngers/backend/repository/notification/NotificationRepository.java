package com.prograngers.backend.repository.notification;

import com.prograngers.backend.entity.Notification;
import io.lettuce.core.dynamic.annotation.Param;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface NotificationRepository extends JpaRepository<Notification, Long>, NotificationCustomRepository {

    @Query("select n from Notification n where n.receiver.id = :memberId and n.isRead = false")
    List<Notification> findAllNotReadByMemberId(@Param("memberId") Long memberId);

    @Query("select n from Notification n join fetch n.solution where n.receiver.id = :memberId order by n.createdAt desc")
    Slice<Notification> findPageByMemberId(@Param("memberId") Long memberId, Pageable pageable);

}
