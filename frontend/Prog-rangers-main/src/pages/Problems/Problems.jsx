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

const DATASTRUCTURE = [
  { value: "ALL", name: "자료구조" },
  { value: "LIST", name: "리스트" },
  { value: "ARRAY", name: "배열" },
  { value: "STACK", name: "스택" },
  { value: "QUEUE", name: "큐" },
  { value: "MAP", name: "맵" },
  { value: "HEAP", name: "힙" }
];

const LEVEL = [
  { value: "ALL", name: "난이도"},
  { value: "1", name: "1" },
  { value: "2", name: "2" },
  { value: "3", name: "3" },
  { value: "4", name: "4" },
  { value: "5", name: "5" }
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
            width: 742px;
            display: flex;
            flex-direction: row;
            justify-content: space-between;
            z-index: 2;
        `}>
          <FilterBar options={ALGORITHMS}/>
          <FilterBar options={DATASTRUCTURE}/>
          <FilterBar options={LEVEL}/>
        </div>
      </div>
    </div>
  );
}
