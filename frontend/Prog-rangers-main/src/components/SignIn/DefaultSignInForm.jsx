import React from 'react';
import styled from '@emotion/styled';
import { css } from '@emotion/react';

import { inputStyle } from '../../styles/signUpPage';

const StyledButton = styled.button`
  width: 600px;
  height: 50px;
  color: #fff;
  font-size: 18px;
  font-weight: 700;
  text-align: center;
  border-radius: 12px;
  background-color: #3486a0;
`;

const formStyle = css`
  width: 600px;
  display: flex;
  flex-direction: column;
`;

export default function DefaultSignInForm() {
  return (
    <form css={formStyle}>
      <input placeholder="아이디" css={inputStyle} />
      <input placeholder="비밀번호" css={inputStyle} />
      <StyledButton>로그인</StyledButton>
    </form>
  );
}
