import React, { Fragment } from 'react';
import { css } from '@emotion/react';
import {
  MainBody
} from './MainBody';
import { FilterBar } from '../../components/FilterBar';

const ALGORITHMS = [
  { value: "ALL", name: "알고리즘" },
  { value: "BUBBLE_SORT", name: "버블정렬" },
  { value: "SELECTION_SORT", name: "선택정렬" },
  { value: "INSERTION_SORT", name: "삽입정렬" },
  { value: "HEAP_SORT", name: "힙정렬" },
  { value: "MERGE_SORT", name: "합병정렬" },
  { value: "QUICK_SORT", name: "퀵정렬" },
  { value: "LINEAR_SEARCH", name: "선형탐색" },
  { value: "BINARY_SEARCH", name: "이진탐색" },
  { value: "BFS", name: "너비우선탐색" },
  { value: "DFS", name: "깊이우선탐색" },
  { value: "DIJKSTRA", name: "데이크스트라"}
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
