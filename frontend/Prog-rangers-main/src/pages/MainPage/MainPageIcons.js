import { css } from '@emotion/react';

const positionA = css`
  position: absolute;
  width: 120px;
  height: 120px;
`;

export const penPosition = css`
  ${positionA}
  top: 190px;
  left: 150px;
`;

export const folderPosition = css`
  ${positionA}
  bottom: 230px;
  right: 180px;
`;

export const notePosition = css`
  ${positionA}
  top: 37vh;
  left: 50px;
`;

export const commentsPosition = css`
  ${positionA}
  bottom: 25vh;
  left: 30px;
`;

export const chatPosition = css`
  ${positionA}
  width: 100px;
  height: 100px;
  top: 100px;
  right: 270px;
`;

export const bookPosition = css`
  ${positionA}
  width: 100px;
  height: 100px;
  top: 215px;
  left: 250px;
`;
