import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import { useParams } from 'react-router-dom';
import { css } from "@mui/material";
import { theme } from "../Header/theme";
import { MywrapStyle } from "../SolutionDetail/commentsStyle";
import { FcLike } from 'react-icons/fc';

export const Recommand = () => {

  const navigate = useNavigate();
    const onClickSols = (solutionId) => {
      navigate(`/solutions/${solutionId}`);
    };
    
    const { solutionId } = useParams();
    const [ recoSol, setRecoSol ] = useState([]);

    useEffect(() => {

      const token = localStorage.getItem('token');
      const apiUrl = `http://13.124.131.171:8080/api/v1/solutions/${solutionId}`;

      axios
        .get(apiUrl, {
          method: "GET",
          headers: {Authorization: `Bearer ${token}`}
        })

        .then((response) => {
          // 추천풀이 문제제목, 언어 같으면 좋아요 내림차순
          const recommendedSolutions = [...response.data.recommendedSolutions];
          
          recommendedSolutions.sort((a, b) => {
            if (a.title === b.title) {
              if (b.language === a.language) {
                return b.likes - a.likes;
              }
              return a.language.localeCompare(b.language);
            }
            return a.title.localeCompare(b.title);
          });
          
          setRecoSol(recommendedSolutions);
        })

        .catch((error) => {
          console.error('API 요청 오류:', error);
        });
    }, []);

    const gridContainerStyles = css`
      margin-top: 20px;
      display: grid;
      grid-template-columns: repeat(3, 230px);
      grid-template-rows: repeat(2, 126px);
      gap: 20px;
      `
    const gridItemStyles = css`
      width: 230px;
      height: 126px;
      cursor: pointer;
      background-color:${theme.colors.light3}
      `
    return(
    
    <div className="wrap" css={MywrapStyle}>
        <div css={css`
        margin-top: 70px;
        display: flex;            
        padding-left: 8px; 
        align-items: flex-end;
        gap: 10px;
        `}>
            <div css={css`font-size: 20px; font-weight: 700;`}>추천 풀이</div>
            <div css={css`font-size: 14px; color:${theme.colors.light1}`}>내가 푼 문제에 대한 다른 풀이를 살펴봐요</div>
        </div>

        <div css={gridContainerStyles}>
            {recoSol.map((item) => (
                // 데이터가 있는 경우에만 렌더링
                item ? (
                <div css={gridItemStyles}
                onClick={() => onClickSols(item.id)}
                key={item.id}
                >
                    <div className="icon" 
                      css={css` 
                      display: flex;
                      flex-direction: row;
                      align-items: center;
                      padding : 12px 0 0 20px;
                      gap: 10px;
                      font-size: 12px;
                      color: ${theme.colors.dark2}`}>
                      <FcLike size="16" />
                      {item.likes}개
                    </div>

                    <div css={css`
                      padding : 20px 0 0 20px;
                      font-size: 16px;
                      max-width: 205px; 
                      overflow: hidden;
                      text-overflow: ellipsis;
                      white-space: nowrap;`}>
                      {item.solutionName}
                    </div>

                    <div css={css`
                      padding : 10px 0 0 20px;
                      font-size: 14px;
                      color: ${theme.colors.light1}`}>
                      {item.nickname}
                    </div>
                </div>
                ) : null
            ))}
        </div>
    </div>
    )
}