import React, { useState, useEffect } from 'react';
import axios from 'axios';

import { FcLikePlaceholder } from 'react-icons/fc';
import { RiShareBoxLine } from 'react-icons/ri';
import { flexLayout, indiLayout } from './indicatorSytle';
import { css } from '@emotion/react';
import { theme } from '../Header/theme';

export const Indicators = () => {

  const [ solution, setSolution ] = useState({});

  useEffect(() => {
    const apiUrl = `http://13.124.131.171:8080/api/v1/solutions/1`;

    axios
      .get(apiUrl)
      .then((response) => {
        setSolution(response.data.solution);
      })
      .catch((error) => {
        console.error('API 요청 오류:', error);
      });
  }, []);

  return (
    <div className="indicatorWrap" css={css`${indiLayout} color: ${theme.colors.dark2};`}>
      <div className="allIndicators" css={css`${flexLayout}`}>
        <div className="like" css={flexLayout}>
          <button className="icon" css={css`padding-right: 20px;`}>
            <FcLikePlaceholder size="25" />
          </button>
          <div
            css={css`
              padding-right: 20px;
            `}
          >
            {solution.likes}
            <span>개</span>
          </div>
        </div>
        <div className="scrap" css={flexLayout}>
          <button className="icon" css={css`padding-right: 20px;`}>
            <RiShareBoxLine size="25" color="#3486A0" />
          </button>
          <div>
            {solution.scraps}
            <span>회</span>
          </div>
        </div>
      </div>
    </div>
  );
};
