import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { css } from "@mui/material";
import { theme } from "../Header/theme";
import { wrapStyle } from "./commentsStyle";
import { FcLike } from 'react-icons/fc';

export const Recommand = ({data}) => {
    
    const [ recoSol, setRecoSol ] = useState({});

    useEffect(() => {
      const apiUrl = `http://13.124.131.171:8080/api/v1/solutions/1`;

      axios
        .get(apiUrl)
        .then((response) => {
          setRecoSol(response.data.recommendedSolutions);
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
      background-color:${theme.colors.light3}
      `
    return(
    
    <div className="wrap" css={wrapStyle}>
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
            {/* {data.map((item) => (
                // 데이터가 있는 경우에만 렌더링
                item ? ( */}
                <div css={gridItemStyles}
                // key={recommendedSolutions.id}
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
                      <div>36개</div>
                      {/* {item.recommendedSolutions.likes} */}
                    </div>

                    <div css={css`
                      padding : 20px 0 0 20px;
                      font-size: 16px;`}>
                        풀이제목
                    {/* {item.recommendedSolutions.solutionName} */}
                    </div>

                    <div css={css`
                      padding : 10px 0 0 20px;
                      font-size: 14px;
                      color: ${theme.colors.light1}`}>
                      닉네임
                    {/* {item.recommendedSolutions.nickname} */}
                    </div>
                </div>
                {/* ) : null
            ))} */}
        </div>
    </div>
    )
}