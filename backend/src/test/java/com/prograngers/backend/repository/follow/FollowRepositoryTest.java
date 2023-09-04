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

import static com.prograngers.backend.fixture.MemberFixture.장지담;


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
        Follow follow1 = 저장(createFollow(member1, member2));
        Follow follow2 = 저장(createFollow(member1, member2));
        Follow follow3 = 저장(createFollow(member1, member2));
        Follow follow4 = 저장(createFollow(member2, member1));
        Follow follow5 = 저장(createFollow(member2, member1));
        Follow follow6 = 저장(createFollow(member2, member1));
        Follow follow7 = 저장(createFollow(member2, member1));

        // when
        Long follow = followRepository.getFollow(member1);
        Long following = followRepository.getFollowing(member1);

        // then
        Assertions.assertThat(follow).isEqualTo(3L);
        Assertions.assertThat(following).isEqualTo(4L);

    }

    private static Follow createFollow(Member member1, Member member2) {
        return Follow.builder().member(member1).targetId(member2.getId()).build();
    }


    Member 저장(Member member) {
        return memberRepository.save(member);
    }
    Follow 저장(Follow follow) {return followRepository.save(follow);}

}