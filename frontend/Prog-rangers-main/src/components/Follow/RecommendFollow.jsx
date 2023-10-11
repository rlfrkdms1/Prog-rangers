import React, { useState, useEffect, useRef } from 'react';
import { css } from '@emotion/react';
import { theme } from '../Header/theme';
import { buttonSytle, fontSize14, fontSize12 } from './FollowStyle';
import axios from "axios";

export const RecommendFollow = () => {
  const [data, setData] = useState({ recommends: [] });

    useEffect(() => {
        const token = localStorage.getItem('token');
        const apiUrl = 'http://13.124.131.171:8080/api/v1/mypage/follows';
        

        axios.get(apiUrl, {
            method: "GET",
            headers: {Authorization: `Bearer ${token}`},
          })
          .then((response) => {
            setData(response.data);
          })
          .catch((error) => {
            console.error('API 요청 오류:', error);
          });
      }, []);
      
  const containerRef = useRef(null);
  const [startX, setStartX] = useState(null);
  const [isMouseDown, setIsMouseDown] = useState(false);

  const handleMouseDown = (e) => {
    setIsMouseDown(true);
    setStartX(e.clientX);
    containerRef.current.style.scrollBehavior = 'initial';
  };

  const handleMouseUp = () => {
    setIsMouseDown(false);
    containerRef.current.style.scrollBehavior = 'smooth';
  };

  const handleMouseMove = (e) => {
    if (isMouseDown) {
      if (startX !== null) {
        const scrollX = startX - e.clientX;
        containerRef.current.scrollLeft += scrollX;
        setStartX(e.clientX);
      }
    }
  };

  const handleMouseLeave = () => {
    if (isMouseDown) {
      setIsMouseDown(false);
      containerRef.current.style.scrollBehavior = 'smooth';
    }
  };

  return (
    <div ref = {containerRef} 
      css={css`width: 800px; height: 90px; background-color:  ${theme.colors.light3}; padding: 20px; overflow-x: scroll; overflow-y: hidden; white-space: nowrap; &::-webkit-scrollbar {display: none;}
      &::-webkit-user-select:none;
      &::-moz-user-select:none;
      &::-ms-user-select:none;
      user-select: none;`}
      onMouseDown={handleMouseDown}
      onMouseUp={handleMouseUp}
      onMouseMove={handleMouseMove}
      onMouseLeave={handleMouseLeave}
      >

    {data.recommends.map((item, index) => (
    <div key={index} 
        css={css`display: inline-flex; flex-direction: row;`}>
    
    <div css={css`width:252px; max-height: 50px; display: flex; align-items: center; justify-content: space-between; margin-right:20px; border-right: 1px solid #959595;`}>           
        <div css={css`display: flex; align-items: center; gap: 15px;`}>
        <img
            src= {item.photo || 'https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png'}                 
            alt='profileImg'
            width='50px'
            css={css`
            border-radius: 50%;
            `}></img>
        <div css={css`display: flex; flex-direction: column; gap: 4px;`}>
            <div css={css`${fontSize12}`}>{item.nickname}</div>
            <div css={css`width: 165px; display: flex; justify-content: space-between;`}>
            <div css={css`${fontSize14} `}>{item.introduction}</div>
            <button css={css`${buttonSytle}`}>팔로우</button>
            </div>
        </div>
        </div>
    </div>
    </div>
))}
    </div>

  );
}
