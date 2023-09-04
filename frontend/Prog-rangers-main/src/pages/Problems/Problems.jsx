import React, { Fragment, useEffect, useState } from 'react';
import { css } from '@emotion/react';
import {
  MainBody
} from './MainBody';
import { FilterBar } from '../../components/FilterBar';
import { QuestionForm } from '../../components/Question';
import { Pagination } from '../../components/Pagination/Pagination';
import questions from '../../db/question.json';
import { Provider, atom, useAtom } from 'jotai';

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

const questionAtom = atom(questions);

export const Problems = () => {
  const [ page, setPage ] = useState(1);
  const [ Questions, setQuestions ] = useAtom(questionAtom);
  const itemsPerPage = 5;
  // const LAST_PAGE = Questions.length % 5 === 0 ? parseInt(Questions.length / 5) : parseInt(Questions.length / 5) + 1;
  const totalQuestions = questions.length;
  const totalPages = Math.ceil(totalQuestions / itemsPerPage);

  const handlePageChange = (e, page) => {
    setPage(page);
  };

  useEffect(() => {
    const startIndex = (page - 1) * itemsPerPage;
    const endIndex = startIndex + itemsPerPage;
    const currentQuestions = questions.slice(startIndex, endIndex);
    setQuestions(currentQuestions);
  }, [page]);

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
          원하는 조건으로 문제를 찾아보세요
        </div>
        <div
          css={css`
            margin-top: 25px;
            height: 50px;
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
        <div css={css`height: 690px; width: 980px;  margin-top: 20px;`}>
          <QuestionForm data={Questions}/>
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
