package com.prograngers.backend.repository.follow;

import static com.prograngers.backend.support.fixture.MemberFixture.길가은;
import static com.prograngers.backend.support.fixture.MemberFixture.이수빈;
import static com.prograngers.backend.support.fixture.MemberFixture.장지담;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.prograngers.backend.entity.Follow;
import com.prograngers.backend.entity.member.Member;
import com.prograngers.backend.repository.member.MemberRepository;
import com.prograngers.backend.support.RepositoryTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


@RepositoryTest
@Slf4j
class FollowRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private FollowRepository followRepository;

    @DisplayName("회원으로 팔로우, 팔로잉 찾기")
    @Test
    void 회원_팔로우_찾기() {
        // given
        Member member1 = 저장(장지담.기본_정보_생성());
        Member member2 = 저장(장지담.기본_정보_생성());
        Follow member1FollowMember2_1 = 저장(팔로우_생성(member1, member2));
        Follow member1FollowMember2_2 = 저장(팔로우_생성(member1, member2));
        Follow member1FollowMember2_3 = 저장(팔로우_생성(member1, member2));
        Follow member2FollowMember1_1 = 저장(팔로우_생성(member2, member1));
        Follow member2FollowMember1_2 = 저장(팔로우_생성(member2, member1));
        Follow member2FollowMember1_3 = 저장(팔로우_생성(member2, member1));
        Follow member2FollowMember1_4 = 저장(팔로우_생성(member2, member1));

        // when
        Long follow = followRepository.countFollow(member1);
        Long following = followRepository.countFollowing(member1);

        // then
        assertAll(
                () -> assertThat(follow).isEqualTo(3L),
                () -> assertThat(following).isEqualTo(4L)
        );
    }

    @Test
    @DisplayName("팔로우를 하면 팔로워와 팔로잉의 아이디로 팔로우 내역을 찾을 수 있다.")
    void 팔로우_내역_찾기() {
        Member member1 = 저장(길가은.기본_정보_생성());
        Member member2 = 저장(장지담.기본_정보_생성());
        Member member3 = 저장(이수빈.기본_정보_생성());

        Follow follow1 = 저장(팔로우_생성(member1, member2));
        Follow follow2 = 저장(팔로우_생성(member1, member3));

        Follow followRecord1 = followRepository.findByFollowerIdAndFollowingId(member1.getId(), member2.getId()).get();
        Follow followRecord2 = followRepository.findByFollowerIdAndFollowingId(member1.getId(), member3.getId()).get();

        assertAll(
                () -> assertThat(followRecord1).isEqualTo(follow1),
                () -> assertThat(followRecord2).isEqualTo(follow2)
        );
    }

    @Test
    @DisplayName("팔로워, 팔로잉 아이디가 주어졌을 때 팔로우 내역이 존재하는지 알 수 있다.")
    void 팔로우_내역_존재성_확인() {

        Member member1 = 저장(길가은.기본_정보_생성());
        Member member2 = 저장(장지담.기본_정보_생성());
        Member member3 = 저장(이수빈.기본_정보_생성());

        저장(팔로우_생성(member1, member2));

        boolean followRecord1 = followRepository.existsByFollowerIdAndFollowingId(member1.getId(), member2.getId());
        boolean followRecord2 = followRepository.existsByFollowerIdAndFollowingId(member1.getId(), member3.getId());

        assertAll(
                () -> assertThat(followRecord1).isTrue(),
                () -> assertThat(followRecord2).isFalse()
        );
    }

    private Follow 팔로우_생성(Member member1, Member member2) {
        return Follow.builder().followerId(member1.getId()).followingId(member2.getId()).build();
    }

    private Member 저장(Member member) {
        return memberRepository.save(member);
    }

    private Follow 저장(Follow follow) {
        return followRepository.save(follow);
    }

}