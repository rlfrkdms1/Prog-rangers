package com.prograngers.backend.repository.badge;

import com.prograngers.backend.entity.badge.Badge;
import com.prograngers.backend.entity.member.Member;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BadgeRepository extends JpaRepository<Badge, Long> {
    List<Badge> findAllByMember(Member member);
}
