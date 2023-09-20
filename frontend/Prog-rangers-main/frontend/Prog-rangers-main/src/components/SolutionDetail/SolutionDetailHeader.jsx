import { css } from '@emotion/react';
import { theme } from '../Header/theme';

import { BiSolidLockAlt } from 'react-icons/bi';
import {
  HeaderLayout,
  colFlex,
  dot,
  rowFlex,
} from './headerStyle';

export const SolutionDetailHeader = () => {
  return (
    <div className="headerAll" css={HeaderLayout}>
      <div className="headerLeft">
        <div
          className="problemTitleArea rowFlex"
          css={rowFlex}
        >
          <div
            className="problemTitle"
            css={css`
              font-size: 20px;
              font-weight: bold;
              color: ${theme.colors.dark1};

              padding-right: 10px;
            `}
          >
            쿼리의 모음 개수
          </div>
          <div className="icon">
            <BiSolidLockAlt size="18" color="#D9D9D9" />
          </div>
        </div>
        <div
          className="solutionTitle"
          css={css`
            padding: 15px 0 15px 5px;
          `}
        >
          <span
            css={css`
              padding-right: 20px;
              color: ${theme.colors.dark1};
            `}
          >
            풀이명
          </span>
          <span
            css={css`
              color: ${theme.colors.light1};
            `}
          >
            writer_email@gmail.com
          </span>
        </div>
        <div
          className="options"
          css={css`
            width: 89px;
            height: 36px;
            background-color: ${theme.colors.light3};
            border-radius: 20px;
            text-align: center;
            line-height: 36px;
            color: ${theme.colors.dark1};
          `}
        >
          선택정렬
        </div>
      </div>
      <div className="headerRight rowFlex" css={rowFlex}>
        <div
          className="siteName"
          css={css`
            background-color: ${theme.colors.programmers};
            box-sizing: border-box;
            padding: 8px 20px;
            border-radius: 20px;
            color: ${theme.colors.dark1};
          `}
        >
          프로그래머스
        </div>
        <div className="dropMenu" css={colFlex}>
          <span css={dot}></span>
          <span css={dot}></span>
          <span css={dot}></span>
        </div>
      </div>
    </div>
  );
};
