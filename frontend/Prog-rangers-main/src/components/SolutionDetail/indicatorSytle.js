import { css } from '@emotion/react';
import { theme } from '../Header/theme';

export const indiLayout = css`
  width: 956px;
  margin: 0 auto;
  padding: 30px 0;
`;

export const MyindiLayout = css`
  width: 700px;
  margin: 0 auto;
  padding: 30px 0;
`;

export const flexLayout = css`
  display: flex;
  flex-direction: row;
  align-items: center;
`;

export const Divline = css`
  position: relative;
  width: 740px;
  height: 0;
  border-top: 1px solid ${theme.colors.light3};
  `;
