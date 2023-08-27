import styled from '@emotion/styled';
import React from 'react';
import { theme } from '../../styles/theme';

const StyledSmall = styled.small`
  color: ${theme.colors.red};
  padding-left: 20px;
  font-size: 16px;
  font-weight: 600;
  margin-bottom: 10px;
`;

export default function ErrorText({ text }) {
  return <StyledSmall>{text}</StyledSmall>;
}
