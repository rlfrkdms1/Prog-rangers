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

  const navigate2 = useNavigate();
  const onClickAddSol = () => {
    navigate2(`/myPage/addsolution`);
  };

  const { solutionId } = useParams();
  const [ data, setData] = useState([]);
  const [currentSolution, setCurrentSolution] = useState(null);

  useEffect(() => {
    const token = localStorage.getItem('token');
    const apiUrl = `http://13.125.13.131:8080/api/v1/solutions/${solutionId}/mine`;

    
    const fetchData = async () => {
      axios
      .get(apiUrl, {
        method: 'GET',
        headers: { Authorization: `Bearer ${token}` },
      })
      .then((response) => {
        setData(response.data);
        const foundSolution = response.data.mySolutionList.find((item) => item.id === parseInt(solutionId));
        setCurrentSolution(foundSolution);
      })
      .catch((error) => {
        console.error('API 요청 오류:', error);
      });
    };
    fetchData();
  }, [solutionId]);

  return (
    <div className="RightbarWarp">
      
      <div
        css={css`
          height: 100%;
          align-items: stretch;
          border-left: 1px solid ${theme.colors.light3};
        `}
      >
        <div
          css={css`
            padding-top: 220px;
            margin-left: 20px;
            font-size: 16px;
          `}
        >
          <div
            css={css`
              font-weight: 700;
              padding-bottom: 10px;
            `}
          >
            풀이목록
          </div>

          {data && data.mySolutionList ? (
            data.mySolutionList.map((item) => (
              <div
                key={item.id}
                css={css`
                  color: ${theme.colors.dark1};
                  font-size: 16px;
                  font-weight: ${item.id === currentSolution?.id ? 700 : 400};                 
                  padding-top: 10px;
                  cursor: pointer;
                `}
                onClick={() => onClickSols(item.id)}
              >
                {item.title}
              </div>
            ))
          ) : (
            <div css={css`
            color: ${theme.colors.light1}`}
            >
              풀이 없음</div>
          )}

          <button
            css={css`
              width: 170px;
              height: 40px;
              border-radius: 20px;
              margin-top: 30px;
              text-align: center;
              background-color: ${theme.colors.main30};
            `}
            onClick={() => onClickAddSol()}
          >
            {' '}
            풀이 추가하기
          </button>

          <div
            css={css`
              font-weight: 700;
              margin-top: 100px;
              padding-bottom: 10px;
            `}
          >
            스크랩한 풀이
          </div>

          {data && data.myScrapSolutionList ? (
            data.myScrapSolutionList.map((item) => (
              <div
                key={item.id}
                css={css`
                  color: ${theme.colors.dark1};
                  font-size: 16px;
                  font-weight: 400;
                  padding-top: 10px;
                  cursor: pointer;
                `}
                onClick={() => onClickSols(item.id)}
              >
                {item.title}
              </div>
            ))
          ) : (
            <div css={css`
              color: ${theme.colors.light1}`}
            >
              스크랩한 풀이 없음
              </div>
          )}
        </div>
      </div>
    </div>
  )
};
