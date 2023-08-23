import React from 'react';
import { pageStyle, innerPageStyle, headingStyle } from '../styles/signUpPage';
import DefaultSignInForm from '../components/SignIn/DefaultSignInForm';
import SocialSignInForm from '../components/SignUp/SocialLoginButtons';
import styled from '@emotion/styled';
import { css } from '@emotion/react';

const OptionWrapper = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 600px;
  padding: 15px;
`;

const grayTextStyle = css`
  color: #545454;
  font-size: 14px;
  font-weight: 400;
`;

const Options = styled.div`
  display: flex;
  gap: 15px;
`;

const Label = styled.label`
  margin-left: 5px;
`;

export default function SignIn() {
  return (
    <div css={pageStyle}>
      <div css={innerPageStyle}>
        <h2 css={headingStyle}>로그인</h2>
        <DefaultSignInForm />
        <OptionWrapper>
          <Options>
            <div>
              <input type="checkbox" id="saveId" />
              <Label htmlFor="saveId" css={grayTextStyle}>
                아이디 저장
              </Label>
            </div>
            <div>
              <input type="checkbox" id="savePassword" />
              <Label htmlFor="savePassword" css={grayTextStyle}>
                자동 로그인
              </Label>
            </div>
          </Options>
          <Options>
            <span css={grayTextStyle}>아이디 찾기</span>
            <span css={grayTextStyle}>|</span>
            <span css={grayTextStyle}>비밀번호 찾기</span>
          </Options>
        </OptionWrapper>
        <SocialSignInForm />
      </div>
    </div>
  );
}
