import React from 'react';
// import { useForm } from 'react-hook-form';
import {
  labelStyle,
  formStyle,
  inputStyle,
  submitButtonStyle,
  inputContainerStyle,
} from '../../styles/signUpPage';

export default function SignUpForm() {
  return (
    <form css={formStyle}>
      <label htmlFor="email" css={labelStyle}>
        이름
      </label>
      <input placeholder="이름을 입력해주세요" css={inputStyle} />
      <label htmlFor="email" css={labelStyle}>
        아이디
      </label>
      <div css={inputContainerStyle}>
        <input placeholder="아이디를 입력해주세요" css={inputStyle} />
      </div>
      <label htmlFor="email" css={labelStyle}>
        비밀번호
      </label>
      <input
        type="password"
        placeholder="영문+숫자+특수문자 조합 최소 10자"
        css={inputStyle}
      />
      <input
        type="password"
        placeholder="비밀번호를 다시 입력해주세요"
        css={inputStyle}
      />
      <label htmlFor="email" css={labelStyle}>
        닉네임
      </label>
      <div css={inputContainerStyle}>
        <input placeholder="이름을 입력해주세요" css={inputStyle} />
      </div>
      <label htmlFor="email" css={labelStyle}>
        휴대전화
      </label>
      <div css={inputContainerStyle}>
        <input
          type="tel"
          placeholder="전화번호를 입력해주세요 (-생략)"
          css={inputStyle}
        />
      </div>
      <label htmlFor="email" css={labelStyle}>
        이메일
      </label>
      <div css={inputContainerStyle}>
        <input type="email" css={inputStyle} />
      </div>
      <button css={submitButtonStyle}>회원가입</button>
    </form>
  );
}
