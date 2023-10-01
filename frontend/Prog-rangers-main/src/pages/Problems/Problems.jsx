import React, { Fragment, useEffect, useState } from 'react';
import { css } from '@emotion/react';
import {
  MainBody
} from './MainBody';
import { FilterBar } from '../../components/FilterBar';
import { QuestionForm } from '../../components/Question';
import { Pagination } from '../../components/Pagination/Pagination';
import questions from '../../db/question.json';
import sort from '../../db/autocomplete.json';
import { Provider, atom, useAtom } from 'jotai';
import axios from 'axios';

const questionAtom = atom(questions);

export const Problems = () => {
  const [ page, setPage ] = useState(1);
  const [ Questions, setQuestions ] = useAtom(questionAtom);
  const [ totalPages, setTotalPages ] = useState(1);

  const AllQuestions = async() => {
    const response = await axios.get(`http://13.124.131.171:8080/api/v1/problems?page=${page-1}`);
    setQuestions(response.data.problems);
    setTotalPages(response.data.totalCount);
  }

  const handlePageChange = (e, page) => {
    setPage(page);
  };

  useEffect(() => {
    AllQuestions();
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
          <FilterBar options={sort.ALGORITHM}/>
          <FilterBar options={sort.DATASTRUCTURE}/>
          <FilterBar options={sort.LEVEL}/>
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
