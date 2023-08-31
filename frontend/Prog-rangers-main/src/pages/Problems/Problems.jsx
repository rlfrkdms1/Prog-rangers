import React, { Fragment } from 'react';
import { css } from '@emotion/react';
import {
  MainBody
} from './MainBody';
import { FilterBar } from '../../components/FilterBar';

const ALGORITHMS = [
  { value: "ALL", name: "알고리즘"},
  { value: "BUBBLE_SORT", name: "버블정렬"}
];

export const Problems = () => {
  return (
    <div 
      css={css`
        display: flex; 
        justify-content: center;
    `}>    
      <div
        css={css`
          ${MainBody}
        `}>
        <div 
          css={css`
            font-weight: 700;
            font-size: 24px;
            margin-left: 4px;
          `}
        >
          원하는 조건으로 풀이를 찾아보세요
        </div>
        <div
          css={css`
            margin: 25px 0 0 4px;
            height: 50px;
            width: 742px;
        `}>
          <FilterBar options={ALGORITHMS}/>
        </div>
      </div>
    </div>
  );
}
