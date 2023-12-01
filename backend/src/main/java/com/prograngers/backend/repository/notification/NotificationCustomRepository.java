package com.prograngers.backend.repository.notification;

import com.prograngers.backend.entity.Notification;
import java.util.List;

public interface NotificationCustomRepository {

    List<Notification> findByMemberIdAndLimit(Long MemberId, int limit);

}
