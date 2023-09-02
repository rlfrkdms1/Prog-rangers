import React, { useState, useEffect } from 'react';
import { css } from '@emotion/react';
//import { theme } from '../../components/Header/theme';
//import { Link } from 'react-router-dom';
import{ 
  fontSize16,
  fontSize20,
  fontSize24,
  listSytle,
  selectStyle,
  hashtagStyle,
} from './SolutionsStyle';

export const Solutions = () => {
  return( 
    <div classname="wrap">
      <div classname="guidetxt">
        <div css={css`
                ${fontSize24} 
                position : absolute;
                top : 14.5%;
                left : 22%
        `}>
          원하는 조건으로 풀이를 찾아보세요
          </div>
          <div classname="filter"
            css={css`
            position: absolute;
            top : 0%;
            left : 12%;
            display : flex;
            margin : 10%;
            gap : 26px;
            `}>
              <div>
                <select name="language" css={selectStyle}>
                  <option hidden="" disabled="disabled" selected="selected" value="">언어</option>
                  <option key="PYTHON" value="PYTHON">Python</option>
                  <option key="JAVA" value="JAVA">JAVA</option>
                  <option key="C++" value="C++">C++</option>
                </select>
              </div>
              <div>
                <select name="algorism" css={selectStyle}>
                  <option hidden="" disabled="disabled" selected="selected" value="">알고리즘</option>
                  <option key="BUBBLE_SORT" value="BUBBLE_SORT">버블정렬</option>
                  <option key="SELECTION_SORT" value="SELECTION_SORT">선택정렬</option>
                  <option key="INSERTION" value="INSERTION">삽입정렬</option>
                  <option key="HEAP_SORT" value="HEAP_SORT">힙정렬</option>
                  <option key="MERGE_SORT" value="MERGE-SORT">병합정렬</option>
                  <option key="QUICK_SORT" value="QUICK_SORT">퀵정렬</option>
                  <option key="LINEAR_SEARCH" value="LINEAR_SEARCH">선형탐색</option>
                  <option key="BINARY_SERACH" value="BINARY_SEARCH">이진탐색</option>
                  <option key="BFS" value="BFS">BFS</option>
                  <option key="DFS" value="DFS">DFS</option>
                  <option key="DIJKSTRA" value="DIJKSTRA">다익스트라</option>
                </select>
              </div>
              <div>
                <select name="datastructure" css={selectStyle}>
                  <option hidden="" disabled="disabled" selected="selected" value="">자료구조</option>
                  <option key="LIST" value="LIST">리스트</option>
                  <option key="ARRAY" value="ARRAY">배열</option>
                  <option key="STACK" value="STACK">스택</option>
                  <option key="QUEUE" value="QEUEU">큐</option>
                  <option key="MAP" value="MAP">맵</option>
                  <option key="HEAP" value="HEAP">힙</option>
                </select>
              </div>
              <div>
                <select name="sorting" css={selectStyle}>
                  <option hidden="" disabled="disabled" selected="selected" value="">정렬기준</option>
                  <option key="LATEST" value="LATEST">최신순</option>
                  <option key="LIKE" value="LIKE">좋아요</option>
                  <option key="SCRAP" value="SCRAP">스크랩</option>
                </select>
             </div>
            </div>
          </div>
             
    <div classname = "secwrap">
      <div css={css`
                ${fontSize24} 
                position : absolute;
                top : 30%;
                left : 22%
        `}> 쿼리의 모음 개수 </div>
    <br></br>
{/* 더미데이터 */}
    <div css={css`${fontSize20}`}>
      <div classname = "list1" css={listSytle}>
        풀이명
        <br></br>
        <div css={css`${[hashtagStyle, fontSize16]}`}>선택정렬</div>
      </div>
      <div classname = "list2" css={listSytle}>
        풀이명
        <br></br>
        <div css={css`${[hashtagStyle, fontSize16]}`}>선택정렬</div>
      </div>
      <div classname = "list3" css={listSytle}>
        풀이명
        <br></br>
        <div css={css`${[hashtagStyle, fontSize16]}`}>선택정렬</div>
      </div>
      <div classname = "list4" css={listSytle}>
        풀이명
        <br></br>
        <div css={css`${[hashtagStyle, fontSize16]}`}>선택정렬</div>
      </div>
      <div classname = "list5" css={listSytle}>
        풀이명
        <br></br>
        <div css={css`${[hashtagStyle, fontSize16]}`}>선택정렬</div>
      </div>
    </div>
    
{/* 페이지네이션 */}
    <div>
{/* 화살표 - 1 - 2 - 3 - 4 - 5 - 화살표 */}
    </div>

    </div>
    </div>
  )
}