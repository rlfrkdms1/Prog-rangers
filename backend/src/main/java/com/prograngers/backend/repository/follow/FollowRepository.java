package com.prograngers.backend.repository.follow;

import com.prograngers.backend.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowRepository extends JpaRepository<Follow,Long>,  QueryDslFollowRepository{
}
