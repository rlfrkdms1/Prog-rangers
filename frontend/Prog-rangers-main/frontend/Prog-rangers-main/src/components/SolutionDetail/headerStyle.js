import { css } from '@emotion/react';
import { theme } from '../Header/theme';

export const HeaderLayout = css`
  width: 996px;
  margin: 50px auto 0;

  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
`;

export const rowFlex = css`
  display: flex;
  flex-direction: row;
  align-items: center;
`;

export const dot = css`
  display: inline-block;
  width: 5px;
  height: 5px;
  border-radius: 2.5px;
  background-color: ${theme.colors.dark1};
  margin: 2px 20px;
`;

export const colFlex = css`
  display: flex;
  flex-direction: column;
`;
