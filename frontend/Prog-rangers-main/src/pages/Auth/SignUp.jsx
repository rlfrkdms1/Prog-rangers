import React from 'react';
import { SignUpForm } from '../../components/SignUp/SignUpForm';
import {
  pageStyle,
  innerPageStyle,
  headingStyle,
} from '../../components/SignUp/signUpPage';

const SignUp = () => {
  return (
    <div css={pageStyle}>
      <div css={innerPageStyle}>
        <h2 css={headingStyle}>회원가입</h2>
        <SignUpForm />
      </div>
    </div>
  );
};

export default SignUp;
