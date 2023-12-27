import React, { useState, useEffect } from 'react';
import { css } from '@emotion/react';
import { MainBody } from '../Problems/MainBody';
import { FilterBar } from '../../components/FilterBar';
import { QSolving } from '../../components/Solving';
import { Pagination } from '../../components/Pagination/Pagination';
import { useLocation, useParams } from 'react-router-dom';
import axios from 'axios';
import {
  fontSize24,
  filterStyle,
  ojNameTag,
} from './SolutionsStyle';
import sort from '../../db/autocomplete.json';

const Solutions = () => {
  const location = useLocation();
  const [page, setPage] = useState(1);
  const [Solvings, setSolvings] = useState([]);
  const [totalPages, setTotalPages] = useState(1);
  const [questionTitle, setQuestionTitle] = useState('');
  const [ojName, setOjName] = useState('');
  const currentPath = window.location.pathname;
  const { problemId } = useParams();

  const handlePageChange = (e, page) => {
    setPage(page);
  };

  const WantSolutions = async () => {
    const response = await axios.get(
      `http://13.125.13.131:8080/api/v1/problems/${problemId}/solutions?page=${
        page - 1
      }&size=5`
    );
    setTotalPages(response.data.totalPages);
    setSolvings(response.data.solutions);
    setQuestionTitle(response.data.problemName);
    setOjName(response.data.ojName);
  };

  useEffect(() => {
    WantSolutions();
  }, [page]);

  return (
    <div
      css={css`
        display: flex;
        justify-content: center;
      `}
    >
      <div
        css={css`
          ${MainBody}
        `}
      >
        <div
          css={css`
            ${fontSize24}
          `}
        >
          원하는 조건으로 풀이를 찾아보세요
        </div>
        <div
          className="filter"
          css={css`
            ${filterStyle}
          `}
        >
          <FilterBar options={sort.LANGUAGE} />
          <FilterBar options={sort.ALGORITHM} />
          <FilterBar options={sort.DATASTRUCTURE} />
          <FilterBar options={sort.SORT} />
        </div>
        <div
          css={css`
            display: flex;
            margin-top: 53px;
          `}
        >
          <div
            css={css`
              ${fontSize24}
              margin-right: 20px;
            `}
          >
            {questionTitle}
          </div>
          <div
            css={css`
              ${ojNameTag}
              background-color: ${ojName === '프로그래머스'
                ? '#6AB4AC'
                : '#3578BF'};
              color: white;
            `}
          >
            {ojName}
          </div>
        </div>
        <div
          css={css`
            height: 690px;
            width: 980px;
            margin-top: 20px;
          `}
        >
          <QSolving data={Solvings} />
        </div>
        <div
          css={css`
            margin-top: 110px;
            height: 50px;
            display: flex;
            justify-content: center;
            align-items: center;
          `}
        >
          <Pagination
            page={page}
            totalPages={totalPages}
            handlePageChange={handlePageChange}
          />
        </div>
      </div>
    </div>
  );
};

export default Solutions;
