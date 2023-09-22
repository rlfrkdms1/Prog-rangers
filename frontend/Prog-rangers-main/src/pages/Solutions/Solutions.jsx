import React, { useState, useEffect } from 'react';
import { css } from '@emotion/react';
import { MainBody } from '../Problems/MainBody';
import { FilterBar } from '../../components/FilterBar';
import { QSolving } from '../../components/Solving';
import { Pagination } from '../../components/Pagination/Pagination';
import questions from '../../db/question.json';
import solvings from '../../db/solving.json';
import { atom, useAtom } from 'jotai';
import{ 
  fontSize24,
  filterStyle,
  ojNameTag
} from './SolutionsStyle';

const LANGUAGE = [
  {value: "ALL", name: "언어"},
  {value: "PYTHON", name: "PYTHON"},
  {value: "JAVA", name: "JAVA"},
  {value: "C++", name: "C++"},
]

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
  { value: "LATEST", name: "최신순"},
  { value: "LIKES", name: "관심순" },
  { value: "VIEWS", name: "조회순" }
];

const solvingAtom = atom(solvings);

export const Solutions = () => {

  const [ page, setPage ] = useState(1);
  const [ Solvings, setSolvings ] = useAtom(solvingAtom);
  const itemsPerPage = 5;
  const totalSolvings = solvings.length;
  const totalPages = Math.ceil(totalSolvings / itemsPerPage);

  const handlePageChange = (e, page) => {
    setPage(page);
  };

  useEffect(() => {
    const startIndex = (page - 1) * itemsPerPage;
    const endIndex = startIndex + itemsPerPage;
    const currentSolvings = solvings.slice(startIndex, endIndex);
    setSolvings(currentSolvings);
  }, [page]);

    // 임시 문제 제목 추출
    const firstItem = questions[0];
  
  return( 
    <div css={css`
          display: flex; 
          justify-content: center;
          `}>
      <div css={css`
          ${MainBody}
        `}>
        <div css={css`${fontSize24}`}>
          원하는 조건으로 풀이를 찾아보세요
          </div>
          <div classname="filter"
            css={css`${filterStyle}`}>
                <FilterBar options={LANGUAGE}/>
                <FilterBar options={ALGORITHMS}/>
                <FilterBar options={DATASTRUCTURE}/>
                <FilterBar options={LEVEL}/>
          </div>

          <div css={css`
          display: flex;
          margin-top: 53px;`}>
          <div css={css`
            ${fontSize24}
            margin-right: 20px;
            `}> {firstItem.title} 
          </div>
          <div css={css`
            ${ojNameTag}
            background-color: ${firstItem.ojName === "프로그래머스" ? "#6AB4AC" : "#3578BF"};
            color: white;
          `}> {firstItem.ojName}
          </div>  
        </div>   

      <div css={css`height: 690px; width: 980px;  margin-top: 20px;`}>
          <QSolving data={Solvings}/>
      </div>

      <div css={css`
          margin-top: 110px; 
          height: 50px; 
          display: flex; 
          justify-content: center; 
          align-items:center;
        `}>
          <Pagination
            totalPages={totalPages}
            page={page}
            handlePageChange={handlePageChange}
          />
        </div>
      </div>
    </div>
  );
}