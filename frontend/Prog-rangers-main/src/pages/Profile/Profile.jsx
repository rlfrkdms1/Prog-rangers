import React, { useState, useEffect, useRef } from 'react';
import { css } from '@emotion/react';
import { theme } from '../../components/Header/theme';
import { LeftBody, MainBody, RightBody } from './MainBody';
import axios from 'axios';
import { SolvingList } from '../../components/Profile/SolvingList';
import github from '../../assets/icons/github-mark.svg'
import achievemark from '../../assets/icons/achieve-mark.svg'
import {
  alignCenter,
  fontSize14,
  fontSizebold16,
  fontSize20,
  fontSize24
} from './ProfileStyle';


export const Profile = () => {

  const [data, setData] = useState([]);

  //팔로우 버튼
  const [isFollowing, setIsFollowing] = useState(false);
  const buttonColor = isFollowing ? theme.colors.light3 : theme.colors.main30;

  const handleFollowClick = () => {
    setIsFollowing(!isFollowing); 
  };

  useEffect(() => {

    const apiUrl = 'http://13.124.131.171:8080/prog-rangers/members/profile/test';

    axios.get(apiUrl)
      .then((response) => {
        setData(response.data);
      })
      .catch((error) => {
        console.error('API 요청 오류:', error);
      });
  }, []);

    return (
    <div css={css`
    ${MainBody}`}>

      <div css={css`
      ${LeftBody}`}>
        <div css={css`
          width: 250px;
          height: 250px;
          border-radius: 250px;
          object-fit: cover;
          border: 1px solid black;
          `}>
          {data.photo} 
          </div> 
          
        <div css={css`
          ${fontSize20}
          ${alignCenter}
          margin-top: 8px;`}>
          {data.nickname}
          </div>

          <div css={css`
          ${fontSize14}
          ${alignCenter}
          margin-top: 7px;`}>
          {data.introduction}
          </div>

          <button 
          onClick={handleFollowClick}
          css={css`
          width: 245px;
          height: 40px;
          border-radius: 25px;
          margin-top: 10px;
          ${fontSize20}
          ${alignCenter}
          background-color: ${buttonColor}
          `}>
            {isFollowing ? '팔로잉' : '팔로우'}
          </button>

        <div css={css`
          display: flex;
          margin-top: 10px;
          justify-content: center;
          gap: 10px;
          `}>
            <div css={css`
            ${fontSizebold16}`}> 팔로우 </div>
            <div css={css `font-weight: 700;`}> {data.follow} </div>

            <div css={css`
            ${fontSizebold16}
            margin-left: 15px;`}> 팔로잉 </div>
            <div css={css `font-weight: 700;`}> {data.following} </div>
          </div>

          <div css={css`
          display: flex;
          margin-top: 30px;
          gap: 20px;
          align-items: center;
          `}>

            <div css={css`
                 width: 30px;
                 height: 30px;`}>
              <img src={github} alt="GitHub Logo" />
            </div>

            <div css={css`
              font-size: 16px;
              font-weight: 400;
              color: ${theme.colors.light1};
              `}> 
              {data.github}
            </div>
          </div>

          <div css={css`
            width: 250px;
            margin-top: 105px;
            margin-bottom: 5px;
            border: 1px solid ${theme.colors.light2};
            `}></div>
          
          <div css={css`
          display: flex;
          align-items: center;
          gap: 10px;          
          ${fontSize20}`}>
            <div css={css`
            margin-top: 5px;`}>
            <img src={achievemark} alt="achieve_mark"/>
            </div>
            달성
          </div>  
      </div>

 
      <div css={css`
      ${RightBody}`}>

        <div css={css`
        ${fontSize24}
        `}>풀이</div>
      
        <SolvingList/>
        
      </div>
  </div>
  )
}
        