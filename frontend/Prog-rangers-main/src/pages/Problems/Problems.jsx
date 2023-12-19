import React, {
  Fragment,
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

const Problems = () => {
  const [page, setPage] = useState(1);
  const [Questions, setQuestions] = useState([]);
  const [totalPages, setTotalPages] = useState(1);

  const AllQuestions = async () => {
    const response = await axios.get(
      `http://13.124.131.171:8080/api/v1/problems?page=${page-1}&size=5`
    );
    setQuestions(response.data.problems);
    setTotalPages(response.data.totalCount);
  };

  useEffect(() => {
    AllQuestions();
  }, [page]);

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
      AllQuestions();
      filterData();
    }, [searchTerm]);

    useEffect(() => {
      AllQuestions();
      filterData();
    }, [searchTerm]);

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
          <FilterBar title="algorithm" options={sort.ALGORITHM} />
          <FilterBar title="datastructure" options={sort.DATASTRUCTURE} />
          <FilterBar title="sort" options={sort.SORT} />
        </div>
        <div
          css={css`
            height: 690px;
            width: 980px;
            margin-top: 20px;
          `}
        >
          <QuestionForm data={searchTerm ? filteredQuestions : Questions} />
          <QuestionForm data={searchTerm ? filteredQuestions : Questions} />
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