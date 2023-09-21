package com.prograngers.backend.repository.follow;

import com.prograngers.backend.TestConfig;
import com.prograngers.backend.entity.Follow;
import com.prograngers.backend.entity.member.Member;
import com.prograngers.backend.repository.member.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import static com.prograngers.backend.support.fixture.MemberFixture.장지담;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;


@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
@Slf4j
@Transactional
@Import(TestConfig.class)
class FollowRepositoryTest {
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    FollowRepository followRepository;

    @DisplayName("회원으로 팔로우, 팔로잉 찾기")
    @Test
    void 회원_팔로우_찾기(){
        // given
        Member member1 = 저장(장지담.기본_정보_생성());
        Member member2 = 저장(장지담.기본_정보_생성());
        Follow member1FollowMember2_1 = 저장(createFollow(member1, member2));
        Follow member1FollowMember2_2 = 저장(createFollow(member1, member2));
        Follow member1FollowMember2_3 = 저장(createFollow(member1, member2));
        Follow member2FollowMember1_1 = 저장(createFollow(member2, member1));
        Follow member2FollowMember1_2 = 저장(createFollow(member2, member1));
        Follow member2FollowMember1_3 = 저장(createFollow(member2, member1));
        Follow member2FollowMember1_4 = 저장(createFollow(member2, member1));

        // when
        Long follow = followRepository.getFollowCount(member1);
        Long following = followRepository.getFollowingCount(member1);

        // then
        assertAll(
                ()->assertThat(follow).isEqualTo(3L),
                ()->assertThat(following).isEqualTo(4L)
        );
    }

    private static Follow createFollow(Member member1, Member member2) {
        return Follow.builder().followingId(member1.getId()).followerId(member2.getId()).build();
    }


    Member 저장(Member member) {
        return memberRepository.save(member);
    }
    Follow 저장(Follow follow) {return followRepository.save(follow);}

}