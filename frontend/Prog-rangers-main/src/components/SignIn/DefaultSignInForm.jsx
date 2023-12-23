import React from 'react';
import styled from '@emotion/styled';
import { css } from '@emotion/react';
import { useForm } from 'react-hook-form';
import ErrorText from '../common/ErrorText';
import { inputStyle } from '../SignUp/signUpPage';
import { signIn } from '../../apis/signin';
import { useNavigate } from 'react-router-dom';
import { IsLoginContext } from '../../context/AuthContext';
import { useContext } from 'react';

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
export const DefaultSignInForm = () =>  {
  const {
    register,
    handleSubmit,
    formState: { isSubmitting, errors },
  } = useForm();
  const navigate = useNavigate();
  const { setIsLogin } = useContext(IsLoginContext);

  const onSubmit = async ({email,password}) => {
    try {
      const newAccess = await signIn({email,password})
      alert(JSON.stringify(`로그인 성공!`));
      setIsLogin(true);
      localStorage.setItem('token', newAccess.accessToken);
      localStorage.setItem('nickname', newAccess.nickname);
      navigate('/');
    } catch (error) {
      console.log('Error:', error);
      alert('로그인 실패! 아이디 또는 비밀번호를 확인해주세요.');
    }
  };

  return (
    <form css={formStyle} onSubmit={handleSubmit(onSubmit)}>
      <input
        type="email"
        {...register('email', {
          required: { value: true, message: '이메일을 입력해주세요' },
        })}
        placeholder="이메일"
        css={inputStyle}
      />
      {errors['email']?.message && <ErrorText text={errors['email'].message} />}
      <input
        type="password"
        placeholder="비밀번호"
        css={inputStyle}
        {...register('password', {
          required: { value: true, message: '비밀번호를 입력해주세요' },
        })}
      />
      {errors['password']?.message && (
        <ErrorText text={errors['password'].message} />
      )}
      <StyledButton disabled={isSubmitting}>로그인</StyledButton>
    </form>
  );
}
