import React from 'react';
import { css } from '@emotion/react';

import { SideBar } from '../components/SideBar/SideBar';

export const Follow = () => {
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
      <div
        className='content'
        css={css`
        width: 800px;
        margin: 100px 110px 0;
        `}
        >
        content
      </div>
    </div>
  );
}
