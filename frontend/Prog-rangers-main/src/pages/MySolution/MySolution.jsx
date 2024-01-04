import React, { useState, useEffect } from 'react';
import { css } from '@emotion/react';
import { MainBody, RightBody } from './MainBody';
import { SideBar } from '../../components/SideBar/SideBar';
import { IoSearchOutline } from 'react-icons/io5';
import { FilterBar2 } from '../../components/FilterBar';
import { MySolutionForm } from '../../components/MySolution';
import { Pagination } from '../../components/Pagination/Pagination';
import sort from '../../db/autocomplete.json';
import axios from 'axios';
import { filterStyle } from './MySolutionStyle';

const MySolution = () => {
  const [page, setPage] = useState(1);
  const [solutions, setSolutions] = useState([]);
  const [totalPages, setTotalPages] = useState(1);

  //필터 변수 선언
  const [filteredSolutions, setFilteredSolutions] =
    useState([]);
  const [selectedAlgorithm, setSelectedAlgorithm] =
    useState('ALL');
  const [selectedDataStructure, setSelectedDataStructure] =
    useState('ALL');
  const [selectedLanguage, setSelectedLanguage] =
    useState('ALL');
  const [selectedLevel, setSelectedLevel] = useState('ALL');

  const Solutionpages = async () => {
    try {
      const token = localStorage.getItem('token');
      const response = await axios.get(
        `http://13.125.13.131:8080/api/v1/solutions?page=${
          page - 1
        }&size=${5}`,
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );
      setTotalPages(response.data.totalPage);
      setSolutions(response.data.contents);
    } catch (error) {
      console.error('API 요청 오류:', error);
    }
  };

  const handlePageChange = (e, page) => {
    setPage(page);
  };

  useEffect(() => {
    Solutionpages();
  }, [page]);

  // 필터링 로직
  const handleLanguageChange = (selected) => {
    setSelectedLanguage(selected.value);
  };

  const handleAlgorithmChange = (selected) => {
    setSelectedAlgorithm(selected.value);
  };

  const handleDataStructureChange = (selected) => {
    setSelectedDataStructure(selected.value);
  };

  const handleLevelChange = (selected) => {
    setSelectedLevel(selected.value);
  };

  useEffect(() => {
    const filterSolutions = () => {
      let filteredData = solutions.filter((item) => {
        if (!item.solution) return false;

        // 각 조건에 따라 필터링
        if (
          (selectedLanguage !== 'ALL' &&
            item.solution.language !== selectedLanguage) ||
          (selectedAlgorithm !== 'ALL' &&
            item.solution.algorithm !==
              selectedAlgorithm) ||
          (selectedDataStructure !== 'ALL' &&
            item.solution.dataStructure !==
              selectedDataStructure) ||
          (selectedLevel !== 'ALL' &&
            item.solution.level !== selectedLevel)
        ) {
          return false;
        }
        return true;
      });

      return filteredData;
    };

    const filtered = filterSolutions();
    setFilteredSolutions(filtered); // 필터링된 데이터 상태 업데이트
  }, [
    selectedLanguage,
    selectedAlgorithm,
    selectedDataStructure,
    selectedLevel,
    solutions,
  ]);

  return (
    <div
      css={css`
        display: flex;
      `}
    >
      <div
        className="container"
        css={css`
          ${MainBody}
        `}
      >
        <SideBar />
        <div
          css={css`
            ${RightBody}
          `}
        >
          <div
            className="search"
            css={css`
              width: 800px;
              height: 50px;
              border: 1px solid #111;
              border-radius: 25px;
              margin-top: 50px;
              padding-left: 30px;
            `}
          >
            <input
              type="text"
              placeholder="내 풀이에서 문제 검색하기"
              css={css`
                outline: none;
                border: none;
                width: 93%;
              `}
            />
            <button
              type="submit"
              css={css`
                line-height: 50px;
              `}
            >
              <IoSearchOutline
                size="27"
                color="#303030"
                css={css`
                  vertical-align: middle;
                `}
              />
            </button>
          </div>

          <div
            className="filter"
            css={css`
              ${filterStyle}
            `}
          >
            <FilterBar2
              title="language"
              options={sort.LANGUAGE}
              onSelect={handleLanguageChange}
            />
            <FilterBar2
              title="algorithm"
              options={sort.ALGORITHM}
              onSelect={handleAlgorithmChange}
            />
            <FilterBar2
              title="datastructure"
              options={sort.DATASTRUCTURE}
              onSelect={handleDataStructureChange}
            />
            <FilterBar2
              title="level"
              options={sort.LEVEL}
              onSelect={handleLevelChange}
            />
          </div>

          <div
            css={css`
              width: 800px;
              margin-top: 20px;
              margin-right: 111px;
            `}
          >
            <MySolutionForm data={filteredSolutions} />
          </div>

          <div
            css={css`
              width: 800px;
              height: 50px;
              display: flex;
              margin-top: 50px;
              justify-content: center;
              align-items: center;
            `}
          >
            <Pagination
              totalPages={totalPages}
              page={page}
              handlePageChange={handlePageChange}
            />
          </div>
        </div>
      </div>
    </div>
  );
};

export default MySolution;
