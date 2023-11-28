package com.prograngers.backend.repository.member;

import com.prograngers.backend.entity.member.Member;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface MemberRepository extends JpaRepository<Member, Long>, MemberCustomRepository {
    Optional<Member> findByEmail(String email);

    boolean existsByEmail(String email);

    Optional<Member> findByNickname(String nickname);

    boolean existsByNickname(String nickname);

    Optional<Member> findBySocialId(Long socialId);

    @Query("select m from Member m join Follow f on f.followingId = m.id where f.followerId = :memberId order by f.id desc")
    List<Member> findAllByFollower(@Param("memberId") Long memberId);

    @Query("select m from Member m join Follow f on f.followerId = m.id where f.followingId = :memberId order by f.id desc")
    List<Member> findAllByFollowing(@Param("memberId") Long memberId);
}
