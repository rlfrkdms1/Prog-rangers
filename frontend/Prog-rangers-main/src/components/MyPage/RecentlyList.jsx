// 마이페이지 대시보드 최근 쓴 풀이

import React from 'react';
import { css } from "@emotion/react";
import { useNavigate } from 'react-router-dom';
import {
  Divline,
  ojName,
  RecentlyTitle
 } from '../../pages/MyPage/MyPageStyle';
  
export const RecentlyList = ({data}) => {
    const navigate = useNavigate();
    const onClickSols = (solutionId) => {
      navigate(`/solution?${solutionId}`);
    };

    return(
        data.map((item) => (
            <div key={item.solutionId}>
              <div 
              onClick={(e) => onClickSols(item.solutionId)}
              css={css`${Divline} 
                        display: flex; 
                        align-items: center; 
                        justify-content: space-between;`}>

                <div css={css`${RecentlyTitle}`}> {item.title} </div>

                <div css={css`
                          ${ojName}
                          font-size: 12px;
                          margin-right: 10px;
                          background-color: ${item.ojName === "프로그래머스" ? "#6AB4AC" : "#3578BF"};`}>
                          {item.ojName}
                </div>
              </div>
            </div>
        )))
}