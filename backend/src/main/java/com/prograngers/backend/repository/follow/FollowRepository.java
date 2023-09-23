package com.prograngers.backend.repository.follow;

import com.prograngers.backend.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FollowRepository extends JpaRepository<Follow,Long>,  QueryDslFollowRepository{

    Optional<Follow> findByFollowerIdAndFollowingId(Long followerId, Long followingId);

}
