import React, {
  useContext,
  useEffect,
  useState,
} from 'react';
import { css } from '@emotion/react';
import { MainBody } from './MainBody';
import { FilterBar } from '../../components/FilterBar';
import { QuestionForm } from '../../components/Question';
import { Pagination } from '../../components/Pagination/Pagination';
import sort from '../../db/autocomplete.json';
import axios from 'axios';
import { SearchContext } from '../../context/SearchContext';
import { useAtom } from 'jotai';
import {
  valueAtom,
  valueScope,
} from '../BoardPage/AddSolution';

const Problems = () => {
  const [page, setPage] = useState(1);
  const [Questions, setQuestions] = useState([]); //전체 문제 데이터
  const [totalPages, setTotalPages] = useState(1);
  const [filter, setFilter] = useAtom(
    valueAtom,
    valueScope
  );
  const [filterBox, setFilterBox] = useState([]);
  let FilterBox = [];

  //filtered 이 선택된 값들을 filterBox에 [[{}] [{}]] 이렇게 넣어놓음.
  const [relatedQs, setRelatedQs] = useState(Questions);
  useEffect(() => {
    const categories = [
      'ALGORITHM',
      'DATASTRUCTURE',
      'SORT',
    ];
    setFilterBox((prev) => {
      return categories.map((category, index) => {
        const filtered = sort[category].filter((item) =>
          item.value.includes(filter)
        );
        return filtered.length > 0 ? filtered : prev[index];
      });
    });
  }, [filter]);

  // filterbar 클릭할때마다 렌더링
  useEffect(() => {
    let tags = Array(3).fill('');
    //filterBox의 각 인덱스 길이가 1인경우
    if (filterBox[0]?.length == 1) {
      tags[0] = filterBox[0][0].value;
    }
    if (filterBox[1]?.length == 1) {
      tags[1] = filterBox[1][0].value;
    }
    if (filterBox[2]?.length == 1) {
      tags[2] = filterBox[2][0].value;
    }
    const related =
      Questions &&
      Questions.filter((item) =>
        item.tags.some((item) => tags.includes(item))
      );
    setRelatedQs(related);
  }, [filterBox]);

  //데이터 불러오기 api 연결
  const AllQuestions = async () => {
    const response = await axios.get(
      `http://13.125.13.131:8080/api/v1/problems?page=${
        page - 1
      }&size=5`
    );
    setQuestions(response.data.problems);
    setTotalPages(response.data.totalCount);
  };

  //pagination
  useEffect(() => {
    AllQuestions();
  }, [page]);
  const handlePageChange = (e, page) => {
    setPage(page);
  };

  //검색 기능 추가
  const { searchTerm } = useContext(SearchContext);
  const [filteredQuestions, setFilteredQuestions] =
    useState(Questions);
  const filterData = () => {
    let filteredResults = Questions.filter((item) =>
      item.title
        .toLowerCase()
        .includes(searchTerm.toLocaleLowerCase())
    );
    setFilteredQuestions(filteredResults);
  };
  useEffect(() => {
    filterData();
  }, [searchTerm, Questions]);
  // 검색 기능 끝

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
          `}
        >
          <FilterBar
            title="algorithm"
            options={sort.ALGORITHM}
          />
          <FilterBar
            title="datastructure"
            options={sort.DATASTRUCTURE}
          />
          <FilterBar title="sort" options={sort.SORT} />
        </div>
        <div
          css={css`
            height: 690px;
            width: 980px;
            margin-top: 20px;
          `}
        >
          <QuestionForm
            data={
              searchTerm
                ? filteredQuestions
                : filter
                ? relatedQs
                : Questions
              // { filterBox ? relatedQs : Questions }
            }
          />
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

export default Problems;
