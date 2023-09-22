import { css } from '@emotion/react';
import { theme } from '../Header/theme';

export const indiLayout = css`
  width: 956px;
  margin: 0 auto;
  padding: 30px 0;

  border-bottom: 1px solid ${theme.colors.light3};
`;

export const flexLayout = css`
  display: flex;
  flex-direction: row;
  align-items: center;
`;
