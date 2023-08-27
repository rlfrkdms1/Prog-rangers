import React from 'react';
import { useForm } from 'react-hook-form';
import {
  labelStyle,
  formStyle,
  inputStyle,
  submitButtonStyle,
  inputContainerStyle,
  confirmButtonStyle,
} from '../../styles/signUpPage';
import ErrorText from '../common/ErrorText';

export default function SignUpForm() {
  const {
    register,
    handleSubmit,
    watch,
    formState: { isSubmitting, errors },
  } = useForm();
  const onSubmit = (data) => {
    alert(JSON.stringify(data));
  };

  const password = watch('password', '');

  return (
    <form css={formStyle} onSubmit={handleSubmit(onSubmit)}>
      <label
        htmlFor="email"
        placeholder="이메일을 입력해주세요"
        css={labelStyle}>
        이메일
      </label>
      <input
        type="email"
        {...register('email', {
          required: { value: true, message: '이메일을 입력해주세요' },
        })}
        css={inputStyle}
        placeholder="이메일을 입력해주세요"
      />
      {errors['email']?.message && <ErrorText text={errors['email'].message} />}
      <label htmlFor="password" css={labelStyle}>
        비밀번호
      </label>
      <input
        type="password"
        placeholder="영문+숫자+특수문자 조합 최소 10자"
        {...register('password', {
          required: { value: true, message: '비밀번호를 입력해주세요.' },
          pattern: {
            value:
              /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{10,}$/,
            message:
              '비밀번호는 영문, 숫자, 특수문자 조합으로 최소 10글자 이상이여야 합니다.',
          },
        })}
        css={inputStyle}
      />
      {errors['password']?.message && (
        <ErrorText text={errors['password'].message} />
      )}
      <input
        type="password"
        placeholder="비밀번호를 다시 입력해주세요"
        {...register('passwordCheck', {
          required: {
            value: true,
            message: '비밀번호를 한번 더 입력해주세요.',
          },
          validate: (value) =>
            value === password || '비밀번호가 일치하지 않습니다.',
        })}
        css={inputStyle}
      />
      {errors['passwordCheck']?.message && (
        <ErrorText text={errors['passwordCheck'].message} />
      )}
      <label htmlFor="nickName" css={labelStyle}>
        닉네임
      </label>
      <div css={inputContainerStyle}>
        <input
          placeholder="닉네임을 입력해주세요"
          {...register('nickName', {
            required: { value: true, message: '닉네임을 입력해주세요.' },
          })}
          css={inputStyle}
        />
        {errors['nickName']?.message && (
          <ErrorText text={errors['nickName'].message} />
        )}
        {/* {TODO 중복확인 체크 안하면 회원가입 못하도록 서버와 통신해야함} */}
        <button type="button" css={confirmButtonStyle}>
          중복 확인
        </button>
      </div>
      <button css={submitButtonStyle} disabled={isSubmitting} type="submit">
        회원가입
      </button>
    </form>
  );
}
