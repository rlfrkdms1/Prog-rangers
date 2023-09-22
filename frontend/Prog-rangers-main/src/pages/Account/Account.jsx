import React, { useEffect, useState } from 'react';
import { css } from '@emotion/react';
import { theme } from '../../components/Header/theme';
import { Link } from 'react-router-dom';
import { SideBar } from '../../components/SideBar/SideBar';
import { Button } from '@mui/material';
import { profileContentStyle, editBtnStyle, deleteBtnStyle } from './AccountStyle';
import axios from 'axios';

export const Account = () => {
  const [userData, setUserData] = useState({
    nickname: '',
    email: '',
    github: '',
    introduction: '',
    currentModifiedAt: '',
    photo: '',
  });

  useEffect(() => {
    const apiUrl = 'http://{{HOST}}:8080/prog-rangers/mypage/account-settings';

    axios.get(apiUrl)
      .then(response => {
        const data = response.data;
        setUserData(data);
      })
      .catch(error => {
        console.error('사용자 정보를 가져오는 중 오류 발생:', error);
      });
  }, []);

  return (
    <div 
      className='container' 
      css={css`
      width: 1200px;
      height: 100%;
      display: flex;
      justify-content: space-between;
      margin: 0 auto;
    ` }
    >
      <SideBar />
      <div
        className='content'
        css={css`
        width: 800px;
        margin: 100px 110px 0;
        `}
        >

          <div 
            className='profile'
            css={css`
            height: 330px;
            display: flex;
            justify-content: space-between;
            `}>
              <div css={css`
              width: 250px;
              display: flex;
              flex-direction: column;
              align-items: center;
              `}>
                <img
                  src={userData.photo ? userData.photo : 'https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png'}                  
                  alt='profileImg'
                  width='250px'
                  css={css`
                  border-radius: 50%;
                  `}></img>
                  <Link to='/accountChange'
                  css={editBtnStyle}>수정하기</Link>
              </div>

              <div css={css`
              width: 500px;
              font-size: 18px
              `}>
                <div className='nickname' css={profileContentStyle}>
                  <div css={css`width: 72px`}>닉네임</div>
                  <div>{userData.nickname}rlfrkdms1_API1</div>
                </div>
                <div className='email' css={profileContentStyle}>
                  <div css={css`width: 72px`}>이메일</div>
                  <div>{userData.email}rlfrkdms1@hotmail.com_API2</div>
                </div>
                <div className='github' css={profileContentStyle}>
                 <div css={css`width: 72px`}>깃허브</div>
                 <div>{userData.github}https://github.com/rlfrkdms1_API3</div>
                </div>
                <div className='introduce' css={profileContentStyle}>
                 <div css={css`width: 72px`}>소개</div>
                 <div>{userData.introduction}가은이의 풀이노트_API4</div>
                </div>
                <div className='pw' css={profileContentStyle}>
                 <div css={css`width: 72px`}>비밀번호</div>
                 <div>최근 변경일: {userData.currentModifiedAt}2023년 1월 1일_API5</div>
                </div>
              </div>
            
          </div>

          <div 
            className='accountDelete'
            css={css`
            height: 100px;
            margin-top: 132px;
            `}>
            <div css={css`
            font-size: 20px;
            font-weight: 700;
            padding: 0 0 10px 30px;
            `}>계정삭제</div>

            <div css={css`
            height: 60px;
            padding: 0 10px 0 30px;

            display: flex;
            justify-content: space-between;
            align-items: center; 

            border: 1px solid ${theme.colors.light1};
            border-radius: 30px;
            `}
          >
            <input
              type="text"
              placeholder="계정 삭제시 작성한 리뷰가 전부 사라집니다."
              css={css`
                width: 100%;
                font-size: 16px;
                outline: none;
                border: none;
              `}
            />
            <button
              type="button"
              css={deleteBtnStyle}>
              삭제하기
            </button>
          </div>
          </div>
        
      </div>
    </div>
  );
}