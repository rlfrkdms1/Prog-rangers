import React from 'react';
import { css } from '@emotion/react';
import { SideBar } from '../components/SideBar/SideBar';
import {
  MyIndicators,
  MySolHeader,
  MySolutionTab,
  MyComments,
  Recommand,
  LeftBar
} from '../components/MySolutionDetail';

export const MySolutionDetail = () => {
  return (
    <div 
      className='container' 
      css={css`
      width: 1200px;
      height: 100%;
      display: flex;
      justify-content: space-between;
      margin: 0 auto;
      ` }
    >
      <SideBar />
    <div css={css`margin-left: 40px;`}>
      <MySolHeader />
      <MySolutionTab />
      <MyIndicators />
      <MyComments />
      <Recommand />
      </div>
      <LeftBar />
  
    </div>
  );
};