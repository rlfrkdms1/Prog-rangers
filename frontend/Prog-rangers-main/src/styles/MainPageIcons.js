import { css } from '@emotion/react';

const positionA = css`
  position: absolute;
`;

export const penPosition = css`
  ${positionA}
  top: 260px;
  left: 100px;
`;

export const folderPosition = css`
  ${positionA}
  bottom: 330px;
  right: 130px;
`;

export const notePosition = css`
  ${positionA}
  top: 43vh;
  left: -10px;
`;

export const commentsPosition = css`
  ${positionA}
  bottom: 31vh;
`;

export const chatPosition = css`
  ${positionA}
  top: 80px;
  right: 210px;
`;

export const bookPosition = css`
  ${positionA}
  top: 215px;
  left: 210px;
`;
