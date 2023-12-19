import React, {useState} from 'react';
import { css } from '@emotion/react';
import { MainBody, RightBody, SubLeftBody, SubRightBody, SubBottomBody, fontSize18 } from './MainStyle';
import { SideBar } from '../../components/SideBar/SideBar';
import { Following } from '../../components/Follow/Following';
import { Follower } from '../../components/Follow/Follower';
import { RecommendFollow } from '../../components/Follow/RecommendFollow';

const Follow = () => {
  return (
    <div
      css={css`${MainBody}`}>

      <SideBar />

      <div
        css={css`${RightBody}`}>

        <div css={css`display: flex; gap: 60px; margin-bottom: 50px;`}>    
          <div css={css`${SubLeftBody}`}>
            <div css={css`${fontSize18}`}>팔로잉</div>
            <Following />
          </div>
          <div css={css`${SubRightBody}`}>
          <div css={css`${fontSize18}`}>팔로워</div>
            <Follower />
          </div>
        </div>

        <div css={css`${SubBottomBody}`}>
         <div css={css`${fontSize18}`}>추천 팔로우</div>
         <RecommendFollow />
        </div>
      </div>
    </div>
  );
};

export default Follow;