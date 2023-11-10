import React, { useState, useEffect } from 'react';
import { css } from '@emotion/react';
import { MainBody } from './MainBody';
import { SideBar } from '../../components/SideBar/SideBar';
import { IoSearchOutline } from 'react-icons/io5';
import { FilterBar2 } from '../../components/FilterBar';
import { MySolutionForm } from '../../components/MySolution';
import { Pagination } from '../../components/Pagination/Pagination';
import sort from '../../db/autocomplete.json';
import axios from 'axios';
import{
  filterStyle
} from './MySolutionStyle'


export const MySolution = () => {

  const [ page, setPage ] = useState(1);
  const [ solutions, setSolutions ] = useState([]);
  const [ totalPages, setTotalPages ] = useState(1);

  const Solutionpages = async () => {
    try {
      const token = localStorage.getItem('token');
      const response = await axios.get(`http://13.124.131.171:8080/api/v1/mypage/solutions?page=${page}`, 
      {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      setTotalPages(response.data.totalPage);
      setSolutions(response.data.solutions);
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

  return (
    <div css={css`
      display: flex; 
      justify-content: center;
    `}>
      <div 
        className='container' 
        css={css`
        width: 1200px;
        height: 100%;
        display: flex;
        justify-content: space-between;
        margin: 0 auto; 
        `}>

      <SideBar />
      <div css={css`${MainBody}`}>
        <div className="search"
        css={css`
        width: 800px;
        height: 50px;
        border: 1px solid #111;
        border-radius: 25px;
        margin-top: 100px;
        padding-left: 30px;
        `}>
          <input type="text"
            placeholder="내 풀이에서 문제 검색하기"
            css={css`
              outline: none;
              border: none;
              width: 93%;
              `}/>
              <button
              type="submit"
              css={css`line-height: 50px;`}>
                <IoSearchOutline
                size="27"
                color="#303030"
                css={css`vertical-align: middle;
                `}/>
              </button>
        </div>
        
        <div className="filter"
          css={css`${filterStyle}`}>
              <FilterBar2 options={sort.LANGUAGE}/>
              <FilterBar2 options={sort.ALGORITHM}/>
              <FilterBar2 options={sort.DATASTRUCTURE}/>
              <FilterBar2 options={sort.LEVEL}/>
        </div>

        <div css={css`width: 800px; margin-top: 20px; margin-right: 111px;`}>
          <MySolutionForm data={solutions}/>
        </div>

        <div css={css`
          width: 800px;
          height: 50px; 
          display: flex; 
          margin-top: 50px;
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
  </div>
  );
}
