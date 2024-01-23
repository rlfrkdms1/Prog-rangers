import React, {useState, useEffect} from 'react';
import { useForm } from 'react-hook-form';
import { 
  labelStyle, 
  formStyle, 
  inputStyle,
  inputBoxStyle,
  submitButtonStyle,
  inputContainerStyle,
} from './signUpPage';
import { theme } from '../Header/theme';
import styled from '@emotion/styled';
import ErrorText from '../common/ErrorText';
import { checkNicknameDuplication, signup } from '../../apis/singup';
import { useNavigate } from 'react-router-dom';
import { css } from '@emotion/react';
import eyeOpen from '../../assets/icons/mypage-eye-open.svg';
import eyeClosed from '../../assets/icons/mypage-eye-closed.svg';

export const SignUpForm = () => {
  const {
    register,
    handleSubmit,
    watch,
    trigger,
    formState: { isSubmitting, errors },
  } = useForm();
  const navigate = useNavigate()

  const onSubmit = async ({email, password, nickname}) => {
    try {
      const response = await signup({email,password,nickname});
      console.log(response)
      alert('회원가입 성공')
      navigate('/')
    } catch (error) {
      console.error(error)
    }
  };

  const password = watch('password', '');
  const nickname = watch('nickname', '');

  const [nicknameChecked, setNickNameChecked] = useState(false);

  const checkNickname = async () => {
    if(nickname.trim().length === 0) return;
    if(nicknameChecked) return;
    try {
      await checkNicknameDuplication({nickname})
      setNickNameChecked(true);
    } catch (error) {
      //TODO errorCode 상수처리
      if(error.response.data.errorCode === "ALREADY_EXIST_NICKNAME"){
        alert('중복된 닉네임입니다!');
      }else{
        console.error(error)
      }

    }
  };

  useEffect(() => {
    if (nicknameChecked) {
      trigger('nickname');
    }
  }, [nicknameChecked, trigger]);

  useEffect(() => {
    setNickNameChecked(false);
  }, [nickname]);

  const [showPassword, setShowPassword] = useState(false);
  const [showPassword2, setShowPassword2] = useState(false);
  const togglePasswordVisibility = () => {
    setShowPassword(!showPassword);
  };
  const togglePasswordVisibility2 = () => {
    setShowPassword2(!showPassword2);
  };

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
      <label htmlFor="email" css={labelStyle}>
        비밀번호
      </label>
      <div css={inputBoxStyle}>
      <input
        type={showPassword ? 'text' : 'password'}
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
        css={css`
          width: 100%;
          font-size: 14px;
          outline: none;
          border: none;
          `}
      />
      <img
        src={
          showPassword
            ? eyeOpen
            : eyeClosed
        }
        alt={
          showPassword
            ? '눈이 떠있는'
            : '눈이 감겨있는'
        }
        onClick={togglePasswordVisibility}
        style={{ cursor: 'pointer' }}
      />
      </div>
      {errors['password']?.message && (
        <ErrorText text={errors['password'].message} />
      )}
      <div css={inputBoxStyle}>
      <input
        type={showPassword2 ? 'text' : 'password'}
        placeholder="비밀번호를 다시 입력해주세요"
        {...register('passwordCheck', {
          required: {
            value: true,
            message: '비밀번호를 한번 더 입력해주세요.',
          },
          validate: (value) =>
            value === password || '비밀번호가 일치하지 않습니다.',
        })}
        css={css`
          width: 100%;
          font-size: 14px;
          outline: none;
          border: none;
          `}
      />
      <img
        src={
          showPassword2
            ? eyeOpen
            : eyeClosed
        }
        alt={
          showPassword2
            ? '눈이 떠있는'
            : '눈이 감겨있는'
        }
        onClick={togglePasswordVisibility2}
        style={{ cursor: 'pointer' }}
      />
      </div>
      {errors['passwordCheck']?.message && (
        <ErrorText text={errors['passwordCheck'].message} />
      )}
      <label htmlFor="nickname" css={labelStyle}>
        닉네임
      </label>
      <div css={inputContainerStyle}>
        <input
          placeholder="닉네임을 입력해주세요"
          {...register('nickname', {
            required: { value: true, message: '닉네임을 입력해주세요.' },
            validate: ()=>
              nicknameChecked || '닉네임 중복체크를 해주세요'
          })}
          css={inputStyle}
        />
        {errors['nickname']?.message && (
          <ErrorText text={errors['nickname'].message} />
        )}
        <ConfirmButton type="button" onClick={checkNickname} isChecked={nicknameChecked}>
          { nicknameChecked ? '인증 완료' : '중복 확인' }
          </ConfirmButton>
      </div>
      <button css={submitButtonStyle} disabled={isSubmitting} type="submit">
        회원가입
      </button>
    </form>
  );
}


const ConfirmButton = styled.button`
  position: absolute;
  top: 10px;
  right: 10px;
  width: 100px;
  height: 30px;
  border-radius: 12px;
  color:#303030;
  font-size: 14px;
  font-weight: 400;
  background-color: ${({isChecked}) => isChecked ?`${theme.colors.programmers}`: `${theme.colors.main30}`};
`;
