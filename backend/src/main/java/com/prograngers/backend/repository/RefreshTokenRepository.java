package com.prograngers.backend.repository;

import com.prograngers.backend.RefreshToken;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
public interface RefreshTokenRepository extends CrudRepository<RefreshToken, Long> {
    Optional<RefreshToken> findById(Long memberId);

    Optional<RefreshToken> findByRefreshToken(String refreshToken);
}
