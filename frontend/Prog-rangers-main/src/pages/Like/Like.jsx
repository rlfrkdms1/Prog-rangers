import React, { useState, useEffect, useRef } from 'react';
import { css } from '@emotion/react';
import { theme } from '../../components/Header/theme';
import { MainBody, RightBody } from './MainBody';
import { SideBar } from '../../components/SideBar/SideBar';
import { LikeList } from '../../components/Like/LikeList';

const Like = () => {
  return (
    <div
      css={css`${MainBody}`}>

      <SideBar />

      <div
        css={css`${RightBody}`}>
          <LikeList />
      </div>
    </div>
  );
};
export default Like;

