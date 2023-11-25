import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useParams } from 'react-router-dom';
import { useNavigate } from 'react-router-dom';

import { css } from '@emotion/react';
import { theme } from '../Header/theme';

export const RightBar = () => {

    const navigate = useNavigate();
    const onClickSols = (solutionId) => {
      navigate(`/mySolution/${solutionId}`); 
    };

    const navigate1 = useNavigate();
    const onClickScrape = (solutionId) => {
      navigate1(`/solutions/${solutionId}`); 
    };

    const navigate2 = useNavigate();
    const onClickAddSol = () => {
      navigate2(`/myPage/addsolution`); 
    };

    const { solutionId } = useParams();
    const [ MySolList, setMySolList ] = useState([]);
    const [ MyScrapeList, setScrapeList ] = useState([]);
    
    // const SolData = MySolList.sort((a, b) => b.id - a.id);
    // const top3Sol = SolData.slice(0, 5);
    
    // const ScrapeData = MyScrapeList.sort((a, b) => b.id - a.id);
    // const top3Scrape = ScrapeData.slice(0, 5);

    useEffect(() => {

      const token = localStorage.getItem('token');
      const apiUrl = `http://13.124.131.171:8080/api/v1/solutions/${solutionId}`;

      axios
        .get(apiUrl, {
          method: "GET",
          headers: {Authorization: `Bearer ${token}`}
        })
        .then((response) => {
          setMySolList(response.data.mySolutionList);
          setScrapeList(response.data.myScrapSolutionList);
        })
        .catch((error) => {
          console.error('API 요청 오류:', error);
        });
    }, []);

  return (
    <div className="RightbarWarp">
      <div css={css`
        height: 100%;
        alignItems: stretch;
        border-left: 1px solid ${theme.colors.light3};
      `}>
        <div css={css`
           padding-top: 220px;
           margin-left: 20px;
           font-size: 16px;
           `}>
            <div css={css`
            font-weight: 700;
            padding-bottom: 10px;
            `}> 
             풀이목록 
            </div>
            
            {/* {top3Sol.map((item) => (
                // 데이터가 있는 경우에만 렌더링
                item ? (
                <div css={css` 
                     margin-top: 10px;
                     max-width: 170px; 

                     overflow: hidden;
                     text-overflow: ellipsis;
                     white-space: nowrap;
                     
                     &:hover{
                      cursor: pointer;
                      text-decoration: underline;
                    }`}
                onClick={() => onClickSols(item.id)}
                key={item.id}
                >
                {item.title}
            </div>
            ) : null
            ))} */}

            <button 
            css={css`
            width: 170px;
            height: 40px;
            border-radius: 20px;
            margin-top: 30px;
            text-align: center;
            background-color: ${theme.colors.main30}
            `}
            onClick={() => onClickAddSol()}
            > 풀이 추가하기</button>
            
            <div css={css`
            font-weight: 700;
            margin-top: 100px;
            padding-bottom: 10px;
            `}> 
                스크랩한 풀이
            </div>

            {/* {top3Scrape.map((item) => (
                // 데이터가 있는 경우에만 렌더링
                item ? (
                  <div css={css` 
                  margin-top: 10px;
                  max-width: 170px; 

                  overflow: hidden;
                  text-overflow: ellipsis;
                  white-space: nowrap;
                  
                  &:hover{
                   cursor: pointer;
                   text-decoration: underline;
                 }`}
                onClick={() => onClickScrape(item.id)}
                key={item.id}
                >
                {item.title}
            </div>
            ) : null
            ))} */}
        </div>        
      </div>      
    </div>
  )}