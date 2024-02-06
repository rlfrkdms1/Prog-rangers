import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useParams, useNavigate } from 'react-router-dom';

import { FcLike, FcLikePlaceholder } from 'react-icons/fc';
import { RiShareBoxLine } from 'react-icons/ri';
import { flexLayout, indiLayout } from './indicatorSytle';
import { css } from '@emotion/react';
import { theme } from '../Header/theme';

export const Indicators = () => {
  const { solutionId } = useParams();
  const [solution, setSolution] = useState({});

  const [like, setLike] = useState(0);
  const [isLiked, setIsLiked] = useState(false);

  
  const token = localStorage.getItem('token');

  useEffect(() => {
    const apiUrl = `http://13.125.13.131:8080/api/v1/solutions/${solutionId}`;

     // 토큰이 있는 경우와 없는 경우에 대한 설정
     const axiosConfig = token
     ? { headers: { Authorization: `Bearer ${token}` } }
     : {};

    axios
      .get(apiUrl, axiosConfig)
      .then((response) => {
        setSolution(response.data.solution);
        setLike(response.data.solution.likes);
        setIsLiked(response.data.solution.pushedLike);
      })
      .catch((error) => {
        console.error('API 요청 오류:', error);
      });
  }, [solutionId]);

  // 좋아요 버튼
  const handleLikeClick = () => {
    const method = isLiked ? 'delete' : 'post';
    if (token) {
    axios({
      method: method, 
      headers: { Authorization: `Bearer ${token}` },
      url: `http://13.125.13.131:8080/api/v1/solutions/${solutionId}/likes`,
    })
      .then(() => {
        setLike((prevLike) =>
          isLiked ? prevLike - 1 : prevLike + 1
        );
        setIsLiked(!isLiked);
      })
      .catch((error) => {
        console.error(`좋아요 ${method} 실패`, error);
      });
    }
    else{
      alert('로그인이 필요한 기능입니다.');
    }
  };

  // 스크랩 버튼
  const navigate = useNavigate();

  const onClickScrape = () => {
    if (token) {
      navigate(`/solutions/${solutionId}/detail/scrap`);
    } 
    else {
      alert('로그인이 필요한 기능입니다.');
    }
  };

  return (
    <>
      <div
        className="indicatorWrap"
        css={css`
          ${indiLayout}
          border-bottom: 1px solid ${theme.colors.light3};
          color: ${theme.colors.dark2};
        `}
      >
        <div
          className="allIndicators"
          css={css`
            ${flexLayout}
          `}
        >
          <div className="like" css={flexLayout}>
            <button
              onClick={handleLikeClick}
              className="icon"
              css={css`
                padding-right: 20px;
              `}
            >
              {isLiked ? (
                <FcLike size="25" />
              ) : (
                <FcLikePlaceholder size="25" />
              )}
            </button>
            <div
              css={css`
                padding-right: 20px;
              `}
            >
              {like}개
            </div>
          </div>
          <div className="scrap" css={flexLayout}>
            <button
              onClick={onClickScrape}
              className="icon"
              css={css`
                padding-right: 20px;
              `}
            >
              <RiShareBoxLine size="25" color="#3486A0" />
            </button>
            <div>{solution.scraps}회</div>
          </div>
        </div>
      </div>
    </>
  );
};
