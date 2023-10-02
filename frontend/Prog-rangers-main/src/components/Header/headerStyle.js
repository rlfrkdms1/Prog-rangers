import { css } from '@emotion/react';
import { Link } from 'react-router-dom';
import styled from '@emotion/styled';

export const navStyle = css`
  font-size: 16px;
  display: flex;
  justify-content: flex-end;
  gap: 40px;
`;

export const linkDefault = css`
  text-decoration: none;
  color: inherit;
`;

export const StyledLink = styled(Link)`
  text-decoration: none;
  color: inherit;
  &:hover{
    color: #3486A0;
    cursor: pointer;
  }
`;
