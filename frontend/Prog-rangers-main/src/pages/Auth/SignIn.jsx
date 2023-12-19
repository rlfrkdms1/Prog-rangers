import React, { useEffect } from 'react';
import {
  pageStyle,
  innerPageStyle,
  headingStyle,
} from '../../components/SignUp/signUpPage';
import { DefaultSignInForm } from '../../components/SignIn/DefaultSignInForm';
import { SocialLoginButtons, ForSignUp } from '../../components/SignUp';
import styled from '@emotion/styled';
import { css } from '@emotion/react';
import axios from 'axios';


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

const SignIn = () => {
  return (
    <div css={pageStyle}>
      <div css={css`${innerPageStyle}  margin-bottom: 100px;`}>
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
              <Label
                htmlFor="savePassword"
                css={grayTextStyle}
              >
                자동 로그인
              </Label>
            </div>
          </Options>
        </OptionWrapper>
        <SocialLoginButtons />
        <ForSignUp/>
      </div>
    </div>
  );
};

export default SignIn;
