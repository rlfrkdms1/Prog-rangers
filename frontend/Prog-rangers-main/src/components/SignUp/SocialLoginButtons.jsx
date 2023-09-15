import { css } from '@emotion/react';
import React from 'react';
import Kakao from '../../assets/icons/signin-kakao-logo.svg';
import Naver from '../../assets/icons/signin-naver-logo.svg';
import Google from '../../assets/icons/signin-google-logo.svg';
import { KAKAO_AUTH_URL } from './OAuth';

const Wrapper = css`
  display: flex;
  flex-direction: row;
  align-items:center;
  width: 600px;
  height: 50px;
  border-radius: 25px;
  font-size: 20px;
  font-weight: 400;

  &:hover{
    cursor: pointer;
  }
`;

export const SocialLoginButtons = () => {  
  const onClickKakao = () => {
    window.location.href = KAKAO_AUTH_URL;
    console.log(KAKAO_AUTH_URL);
  }

  return (
  <div css={css`
    margin-top: 98px; 
    width: 100%; 
    height: 265px; 
    display: flex; 
    flex-direction: column;
    justify-content: center;
    align-items: center;
  `}>
    <div css={css`
      color: #101010;
      font-size: 24px;
      font-weight: 700;
      margin-bottom: 30px;
    `}>소셜 로그인
    </div>
    <div css={css`display: flex; flex-direction: column; justify-content: space-between; height: 200px;`}>
      <div css={css`${Wrapper} background-color: #FAE100;`} onClick={onClickKakao}>
        <img src={Kakao} alt="kakao_logo" css={css`margin-left: 100px;`}/>
        <div css={css` text-align: center; margin-left: 73px; color: #391E1F;`}>카카오톡으로 로그인</div>
      </div>
      <div css={css`${Wrapper} background-color: #06BD34;`}>
        <img src={Naver} alt="naver_logo" css={css`margin-left: 100px;`}/>
        <div css={css` text-align: center; margin-left: 90px; color: #FFF;`}>네이버로 로그인</div>
      </div>
      <div css={css`${Wrapper} border: 1px solid #F0F0F0;`}>
        <img src={Google} alt="google_logo" css={css`margin-left: 110px;`}/>
        <div css={css` text-align: center; margin-left: 94px; color: #545454;`}>Google로 로그인</div>
      </div>
    </div>
  </div>
  );
}
