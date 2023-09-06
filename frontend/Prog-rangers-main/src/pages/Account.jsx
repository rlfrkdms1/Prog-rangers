import React from 'react';
import { css } from '@emotion/react';
import { theme } from '../components/Header/theme';
import { Link } from 'react-router-dom';
import { SideBar } from '../components/SideBar/SideBar';

const profileContentStyle = css`
  display: flex;
  flex-direction: row;
  gap: 54px;
  margin-bottom: 30px;
`;

export const Account = () => {
  return (
    <div 
      className='container' 
      css={css`
      width: 1200px;
      height: 89vh;
      display: flex;
      justify-content: space-between;
      margin: 0 auto;
      overflow: hidden;
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
                <div
                  className='profileImg' 
                  css={css`
                  width: 250px;
                  height: 250px;
                  background: lightgray;
                  border-radius: 50%;
                  `}></div>
                  <button css={css`
                  width: 120px;
                  height: 50px;
                  font-size: 18px;
                  border-radius: 25px;
                  background: ${theme.colors.main30};
                  margin-top: 30px;
                  `}>수정하기</button>
              </div>

              <div css={css`
              width: 500px;
              font-size: 18px
              `}>
                <div className='nickname' css={profileContentStyle}>
                  <div css={css`width: 72px`}>닉네임</div>
                  <div>rlfrkdms1</div>
                </div>
                <div className='email' css={profileContentStyle}>
                  <div css={css`width: 72px`}>이메일</div>
                  <div>rlfrkdms1@hotmail.com</div>
                </div>
                <div className='github' css={profileContentStyle}>
                 <div css={css`width: 72px`}>깃허브</div>
                 <div>https://github.com/rlfrkdms1</div>
                </div>
                <div className='introduce' css={profileContentStyle}>
                 <div css={css`width: 72px`}>소개</div>
                 <div>가은이의 풀이노트</div>
                </div>
                <div className='pw' css={profileContentStyle}>
                 <div css={css`width: 72px`}>비밀번호</div>
                 <div>최근 변경일: 2023년 1월 1일</div>
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
              css={css`
                width: 100px;
                height: 40px;

                border: 1px solid ${theme.colors.light1};
                border-radius: 25px;

                color: ${theme.colors.dark1};

                font-size: 14px;
                font-weight: 700;

                margin-left: 10px;
              `}
            >
              삭제하기
            </button>
          </div>
          </div>
        
      </div>
    </div>
  );
}
