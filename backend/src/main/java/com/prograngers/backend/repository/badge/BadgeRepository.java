package com.prograngers.backend.repository.badge;

import com.prograngers.backend.entity.Badge;
import com.prograngers.backend.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BadgeRepository extends JpaRepository<Badge, Long> {
    List<Badge> findAllByMember(Member member);
}
