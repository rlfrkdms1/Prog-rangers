import React from 'react';
import { css } from '@emotion/react';

import { SideBar } from '../components/SideBar/SideBar';

export const Like = () => {
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
        width: 100%;

        `}
        >
        content
      </div>
    </div>
  );
}
