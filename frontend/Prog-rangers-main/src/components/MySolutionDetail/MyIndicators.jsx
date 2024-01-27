import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useParams } from 'react-router-dom';

import { FcLike } from 'react-icons/fc';
import { RiShareBoxLine } from 'react-icons/ri';
import {
  flexLayout,
  MyindiLayout,
  Divline,
} from '../SolutionDetail/indicatorSytle';
import { css } from '@emotion/react';
import { theme } from '../Header/theme';

export const MyIndicators = () => {
  const { solutionId } = useParams();
  const [solution, setSolution] = useState({});

  useEffect(() => {
    const token = localStorage.getItem('token');
    const apiUrl = `http://13.125.13.131:8080/api/v1/solutions/${solutionId}/mine`;

    axios
      .get(apiUrl, {
        method: 'GET',
        headers: { Authorization: `Bearer ${token}` },
      })
      .then((response) => {
        setSolution(response.data.solution);
      })
      .catch((error) => {
        console.error('API 요청 오류:', error);
      });
  }, [solutionId]);

  return (
    <>
      <div
        className="indicatorWrap"
        css={css`
          ${MyindiLayout} color: ${theme.colors.dark2};
        `}
      >
        <div
          className="allIndicators"
          css={css`
            ${flexLayout}
          `}
        >
          <div className="like" css={flexLayout}>
            <div
              className="icon"
              css={css`
                padding-right: 15px;
              `}
            >
              <FcLike size="25" />
            </div>
            <div>{solution.likes}개</div>
          </div>
          <div className="scrap" css={flexLayout}>
            <div
              className="icon"
              css={css`
                padding: 5px 15px 0 30px;
              `}
            >
              <RiShareBoxLine size="25" color="#3486A0" />
            </div>
            <div>{solution.scraps}회</div>
          </div>
        </div>
      </div>

      <div
        css={css`
          ${Divline}
        `}
      ></div>
    </>
  );
};
