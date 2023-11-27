import { css } from '@emotion/react';
import { Link } from 'react-router-dom';
import { theme } from '../Header/theme';
import styled from '@emotion/styled';

export const navStyle = css`
  font-size: 16px;
  display: flex;
  justify-content: flex-end;
  gap: 40px;
  align-items: center;
`;

export const linkDefault = css`
  text-decoration: none;
  color: inherit;

  position: relative;
`;

export const StyledLink = styled(Link)`
  text-decoration: none;
  color: inherit;
  &:hover {
    color: #3486a0;
    cursor: pointer;
  }
`;

export const ProfileImg = css`
  width: 40px;
  height: 40px;
  margin-right: 10px;
`;

export const DropdownStyle = css`
  width: 146px;
  height: 91px;
  background-color: ${theme.colors.light3};
  border-radius: 12px;
  text-align: center;

  box-size: border-box;
  padding-top: 5px;
  padding-bottom: 5px;

  display: flex;
  flex-direction: column;
  justify-content: space-around;

  position: absolute;
  top: 50px;
  z-index: 999;
`;
