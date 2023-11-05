import React, { useState, useEffect, useRef } from 'react';
import { css } from '@emotion/react';
import { theme } from '../Header/theme';
import { buttonSytle, fontSize14, fontSize12 } from './FollowStyle';
import axios from "axios";
import ProfileImg from '../../components/SolutionDetail/profile/default.png';
import { useParams } from 'react-router-dom';

export const RecommendFollow = () => {
  // 추천 팔로우 api
  const [data, setData] = useState({ recommends: [] });

    useEffect(() => {
        const apiUrl = 'http://13.124.131.171:8080/api/v1/mypage/follows';
        const token = localStorage.getItem('token');
        
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
      
  // 가로 스크롤    
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

  // 팔로우 기능
  const [isFollowing, setIsFollowing] = useState(false);
  
  const handleFollowClick = (item) => {    
    const memberId = item.id;
    const token = localStorage.getItem('token');

    fetch(`http://13.124.131.171/api/v1/members/${memberId}/following`, {
      method: "POST",
      headers: { Authorization: `Bearer ${token}`, },
    })
    .then(response => {
      if (response.status === 200) {
        setIsFollowing(true);
        console.log('팔로우 성공');
      } else {
        console.error('팔로우 실패');
      }
    })
    .catch(error => {
      console.error('팔로우 실패', error);
    });
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
            src= {item.photo || ProfileImg}                 
            alt='profileImg'
            width='50px'
            css={css`
            border-radius: 50%;
            `}></img>
        <div css={css`display: flex; flex-direction: column; gap: 4px;`}>
            <div css={css`${fontSize12}`}>{item.nickname}</div>
            <div css={css`width: 165px; display: flex; justify-content: space-between;`}>
            <div css={css`${fontSize14} `}>{item.introduction}</div>
            <button onClick={() => handleFollowClick(item)}
                    css={css`${buttonSytle} background-color:${isFollowing[item.id] ? theme.colors.light2 : theme.colors.main30}`}>
                     {isFollowing[item.id] ? '팔로잉' : '팔로우'}</button>
            </div>
        </div>
        </div>
    </div>
    </div>
))}
    </div>

  );
}
