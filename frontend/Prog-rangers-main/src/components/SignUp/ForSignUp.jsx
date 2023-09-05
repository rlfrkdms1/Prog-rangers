import { css } from '@emotion/react';
import React from 'react';
import Arrow from '../../assets/icons/signin-arrow.svg';

const buttonPage = css`
  display: flex; 
  flex-direction: row; 
  
  &:hover{
    cursor: pointer;
  }
`;

export const ForSignUp = () => {
  return(
    <div css={css`
      display: flex; 
      flex-direction: row; 
      justify-content: center; 
      margin-top: 50px; 
      width: 100%; 
      font-size: 12px;
    `}>
      <div css={css`color: #545454;`}>프로그래인저 계정에 아직 가입하지 않으셨나요?</div>
      <div css={css`${buttonPage}`}>
        <div css={css`margin-left: 50px; font-weight: 700; color: #3486A0; width: 55px;`}>회원가입</div>
        <img css={css`margin-left: 10px;`} src={Arrow} alt="next_page"/>
      </div>
    </div>
  );
};