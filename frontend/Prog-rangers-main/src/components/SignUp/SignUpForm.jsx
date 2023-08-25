import React from 'react';
import { useForm } from 'react-hook-form';
import {
  labelStyle,
  formStyle,
  inputStyle,
  submitButtonStyle,
  inputContainerStyle,
} from '../../styles/signUpPage';

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
      <label htmlFor="name" css={labelStyle}>
        이름
      </label>
      <input
        placeholder="이름을 입력해주세요"
        {...register('name', {
          required: { value: true, message: '이름을 입력해주세요.' },
        })}
        css={inputStyle}
      />
      <span>{errors['name']?.message}</span>
      <label
        htmlFor="email"
        placeholder="이메일을 입력해주세요"
        css={labelStyle}>
        이메일
      </label>
      <div css={inputContainerStyle}>
        <input
          type="email"
          {...register('email', {
            required: { value: true, message: '이메일을 입력해주세요.' },
          })}
          css={inputStyle}
        />
        <span>{errors['email']?.message}</span>
      </div>
      <label htmlFor="password" css={labelStyle}>
        비밀번호
      </label>
      {/* TODO 정규식을 이용해 패턴 체크 */}
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
      <span>{errors['password']?.message}</span>
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
      <span>{errors['passwordCheck']?.message}</span>
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
        <span>{errors['nickName']?.message}</span>
      </div>
      <button css={submitButtonStyle} disabled={isSubmitting}>
        회원가입
      </button>
    </form>
  );
}
