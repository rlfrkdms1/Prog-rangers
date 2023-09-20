import { css, keyframes } from '@emotion/react';
import { theme } from '../Header/theme';

const webkitKeyframe = keyframes`
  0% {
    opacity: 0;
  }
  50% {
    opacity: 1;
  }
  100% {
    opacity: 0;
  }
`;

const arrowKeyframes = keyframes`
  0% {
    opacity: 0;
  }
  50% {
    opacity: 1;
  }
  100% {
    opacity: 0;
  }
`;

export const scrollDownArea = css`
  padding-top: 80px;

  position: absolute;
  bottom: 50px;
  left: 50%;
  transform: translateX(-50%);

  color: ${theme.colors.dark2};
  font-weight: 700;
`;

export const scrollDownArrow = css`
  position: absolute;
  top: 0;
  left: 50%;
  width: 18px;
  height: 20px;
  margin-left: -12px;
  border-left: 2px solid ${theme.colors.dark2};
  border-bottom: 2px solid ${theme.colors.dark2};
  -webkit-transform: rotate(-45deg);
  transform: rotate(-45deg);
  -webkit-animation: ${webkitKeyframe} 2s infinite;
  animation: ${arrowKeyframes} 2s infinite;
  opacity: 0;
  box-sizing: border-box;
`;

export const arrow1 = css`
  -webkit-animation-delay: 0s;
  animation-delay: 0s;
`;

export const arrow2 = css`
  top: 16px;
  -webkit-animation-delay: 0.15s;
  animation-delay: 0.15s;
`;

export const arrow3 = css`
  top: 32px;
  -webkit-animation-delay: 0.3s;
  animation-delay: 0.3s;
`;
