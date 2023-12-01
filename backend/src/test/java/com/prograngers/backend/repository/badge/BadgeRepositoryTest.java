package com.prograngers.backend.repository.badge;

import static com.prograngers.backend.entity.badge.BadgeConstant.꽃봉오리;
import static com.prograngers.backend.entity.badge.BadgeConstant.새싹;
import static com.prograngers.backend.entity.badge.BadgeConstant.잎과줄기;
import static com.prograngers.backend.support.fixture.MemberFixture.길가은;
import static com.prograngers.backend.support.fixture.MemberFixture.장지담;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.prograngers.backend.entity.badge.Badge;
import com.prograngers.backend.entity.badge.BadgeConstant;
import com.prograngers.backend.entity.member.Member;
import com.prograngers.backend.repository.member.MemberRepository;
import com.prograngers.backend.support.RepositoryTest;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@RepositoryTest
class BadgeRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private BadgeRepository badgeRepository;

    @Test
    @DisplayName("회원이 주어졌을 때 해당 회원이 가지고 있는 모든 뱃지를 조회할 수 있다.")
    void 회원_뱃치_찾기() {
        Member member1 = 저장(길가은.기본_정보_생성());
        Member member2 = 저장(장지담.기본_정보_생성());
        Badge badge1 = 뱃지_저장(member1, 새싹);
        Badge badge3 = 뱃지_저장(member2, 새싹);
        Badge badge2 = 뱃지_저장(member1, 잎과줄기);
        Badge badge4 = 뱃지_저장(member2, 잎과줄기);
        Badge badge5 = 뱃지_저장(member2, 꽃봉오리);
        List<Badge> badges1 = badgeRepository.findAllByMember(member1);
        List<Badge> badges2 = badgeRepository.findAllByMember(member2);
        assertAll(
                () -> assertThat(badges1).contains(badge1, badge2).doesNotContain(badge3, badge4, badge5),
                () -> assertThat(badges2).contains(badge3, badge4, badge5).doesNotContain(badge1, badge2)
        );
    }

    private Member 저장(Member member) {
        return memberRepository.save(member);
    }

    private Badge 뱃지_저장(Member member, BadgeConstant badge) {
        return badgeRepository.save(Badge.builder()
                .member(member)
                .badgeType(badge).build());
    }
}