import { css } from '@emotion/react';
import {
  scrollDownArea,
  scrollDownArrow,
  arrow1,
  arrow2,
  arrow3,
} from './MainScrollDown';

export const ScrollDownArrow = () => {
  return (
    <>
      <div
        className="scrollDownArea"
        css={css`
          ${scrollDownArea};
          font-size: 14px;
        `}
      >
        <span
          css={css`
            ${scrollDownArrow}
            ${arrow1}
          `}
        ></span>
        <span
          css={css`
            ${scrollDownArrow}
            ${arrow2}
          `}
        ></span>
        <span
          css={css`
            ${scrollDownArrow}
            ${arrow3}
          `}
        ></span>
        SCROLL DOWN
      </div>
    </>
  );
};
