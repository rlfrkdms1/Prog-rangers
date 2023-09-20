import { css } from '@emotion/react';
import { theme } from '../Header/theme';

//solutionTab

export const tapLayout = css`
  width: 996px;
  margin: 50px auto 0;
`;

export const btnStyle = css`
  width: 125px;
  height: 40px;
  background-color: ${theme.colors.light3};
  border-radius: 20px 20px 0 0;
  margin-right: 5px;
`;

//viewSolution & lineReview

export const contentLayout = css`
  padding: 30px 20px 50px;
`;

export const contentMock = css`
  width: 956px;
  height: 300px;
  margin: 0 20px;
  background-color: ${theme.colors.dark2};
`;
