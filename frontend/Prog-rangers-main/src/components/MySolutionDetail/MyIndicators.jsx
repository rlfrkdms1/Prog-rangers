import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useParams } from 'react-router-dom';

import { FcLike, FcLikePlaceholder } from 'react-icons/fc';
import { RiShareBoxLine } from 'react-icons/ri';
import { flexLayout, MyindiLayout, Divline } from '../SolutionDetail/indicatorSytle';
import { css } from '@emotion/react';
import { theme } from '../Header/theme';

export const MyIndicators = () => {

  const { solutionId } = useParams();
  const [ solution, setSolution ] = useState({});

  const [like, setLike] = useState(0);
  const [isLiked, setIsLiked] = useState(false);

  useEffect(() => {
    const apiUrl = `http://13.124.131.171:8080/api/v1/solutions/${solutionId}`;

    axios
      .get(apiUrl)
      .then((response) => {
        setSolution(response.data.solution);
        setLike(response.data.solution.likes);
      })
      .catch((error) => {
        console.error('API 요청 오류:', error);
      });
  }, [solutionId, like]);
  
  const handleLikeClick = () => {
    
    const token = localStorage.getItem('token');

    axios.post(`http://13.124.131.171:8080/api/v1/solutions/${solutionId}`, 
    null, {
      headers: {
      Authorization: `Bearer ${token}`,
      'Content-Type': 'application/json'
    }
  })
    .then(response => {
      setLike(response.data.likes);
    })

    if (isLiked) {
      setLike(like - 1);
    } else {
      setLike(like + 1);
    }
    setIsLiked(!isLiked);
  };

  return (
    <>
    <div className="indicatorWrap" css={css`${MyindiLayout} color: ${theme.colors.dark2};`}>
      <div className="allIndicators" css={css`${flexLayout}`}>
        <div className="like" css={flexLayout}>
          <button onClick={handleLikeClick} className="icon" css={css`padding-right: 20px;`}>
            {isLiked ? <FcLike size="25" /> : <FcLikePlaceholder size="25" />}
          </button>
          <div
            css={css`
              padding-right: 20px;
            `}
          >
            {like}
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
    
    <div css={css`${Divline} `}></div>
    </>
  );
};
