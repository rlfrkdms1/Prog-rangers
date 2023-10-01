import styled from '@emotion/styled';
import React from 'react';
<<<<<<< HEAD
import { theme } from '../Header/theme';
=======
import Kakao from '../../assets/icons/signin-kakao-logo.svg';
import Naver from '../../assets/icons/signin-naver-logo.svg';
import Google from '../../assets/icons/signin-google-logo.svg';
import { GOOGLE_AUTH_URL, KAKAO_AUTH_URL, NAVER_AUTH_URL } from './OAuth';
>>>>>>> e1cb18d0af8a45d3c312367c939a2bfacdeb7498

const Wrapper = styled.div`
  margin-top: 140px;
  text-align: center;
`;

<<<<<<< HEAD
const Heading = styled.h2`
  margin-top: 90px;
  color: ${theme.dark1};
  font-size: 24px;
  font-style: normal;
  font-weight: 700;
  line-height: normal;
  margin-bottom: 50px;
`;
=======
export const SocialLoginButtons = () => {  
  const onClickKakao = () => {
    window.location.href = KAKAO_AUTH_URL;
  };
  const onClickNaver = () => {
    window.location.href = NAVER_AUTH_URL;
  };
  const onClickGoogle = () => {
    window.location.href = GOOGLE_AUTH_URL;
  };
>>>>>>> e1cb18d0af8a45d3c312367c939a2bfacdeb7498

export const SocialLoginButtons = () => {
  return (
    <Wrapper>
      <Heading>소셜 로그인</Heading>

      <div>
        <button>카카오톡으로 로그인</button>
        <button> 네이버로 로그인</button>
        <button> Google로 로그인</button>
      </div>
<<<<<<< HEAD
    </Wrapper>
=======
      <div css={css`${Wrapper} background-color: #06BD34;`} onClick={onClickNaver}>
        <img src={Naver} alt="naver_logo" css={css`margin-left: 100px;`}/>
        <div css={css` text-align: center; margin-left: 90px; color: #FFF;`}>네이버로 로그인</div>
      </div>
      <div css={css`${Wrapper} border: 1px solid #F0F0F0;`} onClick={onClickGoogle}>
        <img src={Google} alt="google_logo" css={css`margin-left: 110px;`}/>
        <div css={css` text-align: center; margin-left: 94px; color: #545454;`}>Google로 로그인</div>
      </div>
    </div>
  </div>
>>>>>>> e1cb18d0af8a45d3c312367c939a2bfacdeb7498
  );
}
