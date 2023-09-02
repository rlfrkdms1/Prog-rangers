import { css } from '@emotion/react';
import { theme } from '../Header/theme';

export const selectTabStyle = css`
  background-color: ${theme.colors.light2};
  color: ${theme.colors.dark1};
  font-weight: 700;

  display: flex;
  flex-direction: row;
  align-items: center;

  margin-top: 60px;
  margin-bottom: 30px;
`;
