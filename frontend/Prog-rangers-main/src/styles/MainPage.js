import { css } from '@emotion/react';
import { theme } from './theme';

export const secLayout = css`
  width: 1200px;
  margin: 0 auto;
`;

export const sec = css`
  width: 100%;
  height: calc(100vh - 120px);
  background-color: ${theme.colors.light4};
`;

//fonts

export const fontSize50 = css`
  font-size: 50px;
  font-weight: 700;
  color: ${theme.colors.dark1};
`;

export const fontSize60 = css`
  font-size: 60px;
  font-weight: 700;
  color: ${theme.colors.dark1};
`;

export const fontSize70 = css`
  font-size: 70px;
  font-weight: 700;
  color: ${theme.colors.dark1};
`;

export const fontSize30 = css`
  font-size: 30px;
  font-weight: 500;
  color: ${theme.colors.dark2};
`;

export const alignCenter = css`
  text-align: center;
`;
