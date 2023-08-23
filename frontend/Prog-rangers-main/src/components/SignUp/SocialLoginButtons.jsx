import styled from '@emotion/styled';
import React from 'react';
import { theme } from '../../styles/theme';

const Wrapper = styled.div`
  margin-top: 140px;
  text-align: center;
`;

const Heading = styled.h2`
  margin-top: 90px;
  color: ${theme.dark1};
  font-size: 24px;
  font-style: normal;
  font-weight: 700;
  line-height: normal;
  margin-bottom: 50px;
`;

export default function SocialLoginButtons() {
  return (
    <Wrapper>
      <Heading>소셜 로그인</Heading>

      <div>
        <button>카카오톡으로 로그인</button>
        <button> 네이버로 로그인</button>
        <button> Google로 로그인</button>
      </div>
    </Wrapper>
  );
}
