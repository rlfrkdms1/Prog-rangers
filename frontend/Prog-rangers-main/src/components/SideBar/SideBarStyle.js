import { css } from '@emotion/react';
import { theme } from '../Header/theme';

export const navStyle = css`
  font-size: 18px;
  display: flex;
  justify-content: center;
  align-items: center;
  height: 60px;
  border-bottom: 1px solid ${theme.colors.light3};
  text-decoration: none;
  color: inherit;
`;