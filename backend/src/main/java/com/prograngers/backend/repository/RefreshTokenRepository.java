package com.prograngers.backend.repository;

import com.prograngers.backend.service.auth.RefreshToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface RefreshTokenRepository extends CrudRepository<RefreshToken, Long> {
    Optional<RefreshToken> findById(Long memberId);

    Optional<RefreshToken> findByRefreshToken(String refreshToken);
}
