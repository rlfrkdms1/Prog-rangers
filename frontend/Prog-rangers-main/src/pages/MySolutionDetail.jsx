import React from 'react';
import { css } from '@emotion/react';
import { SideBar } from '../components/SideBar/SideBar';
import {
  MyIndicators,
  MySolHeader,
  MySolutionTab,
  MyComments,
  Recommand,
  RightBar
} from '../components/MySolutionDetail';

const MySolutionDetail = () => {
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
    <div css={css`margin: 0 40px 0 35px;`}>
      <MySolHeader />
      <MySolutionTab />
      <MyIndicators />
      <MyComments />
      <Recommand />
      </div>
      <RightBar />
  
    </div>
  );
};

export default MySolutionDetail;