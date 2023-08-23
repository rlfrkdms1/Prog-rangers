import { css } from '@emotion/react';
import { theme } from '../styles/theme';
import {
  sec,
  secLayout,
  fontSize30,
  fontSize50,
  fontSize60,
  fontSize70,
  alignCenter,
} from '../styles/MainPage';

const MainPage = () => {
  return (
    <div className="wrap">
      <div
        className="secWrap"
        css={css`
          ${sec}
        `}>
        <div
          className="sec sec1"
          css={css`
            ${secLayout}
            height: 100%;
            position: relative;
          `}>
          <div
            className="secTitle"
            css={css`
              ${alignCenter}
              width: 100%;
              position: absolute;
              top: 50%;
              left: 50%;
              transform: translate(-50%, -50%);
            `}>
            <div
              className="title"
              css={css`
                ${fontSize70}
              `}>
              코딩테스트 리뷰 서비스
            </div>
            <div
              className="title"
              css={css`
                ${fontSize60}
                color: ${theme.colors.main}
              `}>
              Prog-rangers
            </div>
          </div>
        </div>
      </div>
      <div className="sec sec2"></div>
      <div className="sec sec3"></div>
      <div className="sec sec4"></div>
    </div>
  );
};

export default MainPage;
