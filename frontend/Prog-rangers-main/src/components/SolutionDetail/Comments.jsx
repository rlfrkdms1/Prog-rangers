import { css } from '@emotion/react';
import { theme } from '../Header/theme';

import {
  wrapStyle,
  flexLayout,
  rowFlex,
  rowFlexRecomment,
} from './commentsStyle';

import ProfileImg from './profile/default.png';

export const Comments = () => {
  return (
    <div className="wrap" css={wrapStyle}>
      <div className="commentArea">
        <div className="title" css={flexLayout}>
          <div
            className="titleName"
            css={css`
              font-size: 20px;
              font-weight: bold;
              color: ${theme.colors.dark1};

              margin-right: 10px;
            `}
          >
            댓글
          </div>
          <div
            className="count"
            css={css`
              color: ${theme.colors.dark2};
            `}
          >
            3개
          </div>
        </div>
        <div className="comments">
          <div className="comment" css={rowFlex}>
            <div className="profile">
              <img
                src={ProfileImg}
                alt="profile image"
                css={css`
                  width: 50px;
                  height: 50px;
                  border: 1px solid ${theme.colors.light1};
                  border-radius: 100%;
                `}
              />
            </div>
            <div
              className="text"
              css={css`
                margin-left: 20px;
              `}
            >
              <div
                className="nickname"
                css={css`
                  font-size: 14px;
                  color: ${theme.colors.light1};
                  margin-bottom: 5px;
                `}
              >
                ddongguri-bing
              </div>
              <div className="commnetText">
                이런 부분은 생각도 못했는데 대단하시네요!
              </div>
            </div>
          </div>
          <div className="comment" css={rowFlex}>
            <div className="profile">
              <img
                src={ProfileImg}
                alt="profile image"
                css={css`
                  width: 50px;
                  height: 50px;
                  border: 1px solid ${theme.colors.light1};
                  border-radius: 100%;
                `}
              />
            </div>
            <div
              className="text"
              css={css`
                margin-left: 20px;
              `}
            >
              <div
                className="nickname"
                css={css`
                  font-size: 14px;
                  color: ${theme.colors.light1};
                  margin-bottom: 5px;
                `}
              >
                ddongguri-bing
              </div>
              <div className="commnetText">
                이런 부분은 생각도 못했는데 대단하시네요!
              </div>
            </div>
          </div>
          <div className="recomment" css={rowFlexRecomment}>
            <div className="profile">
              <img
                src={ProfileImg}
                alt="profile image"
                css={css`
                  width: 40px;
                  height: 40px;
                  border: 1px solid ${theme.colors.light1};
                  border-radius: 100%;
                `}
              />
            </div>
            <div
              className="text"
              css={css`
                margin-left: 20px;
              `}
            >
              <div
                className="nickname"
                css={css`
                  font-size: 12px;
                  color: ${theme.colors.light1};
                  margin-bottom: 5px;
                `}
              >
                ddongguri-bing
              </div>
              <div
                className="commnetText"
                css={css`
                  font-size: 14px;
                `}
              >
                <span
                  className="tag"
                  css={css`
                    font-size: 12px;
                    color: ${theme.colors.main};
                    margin-right: 10px;
                  `}
                >
                  @lo_0a
                </span>
                이런 부분은 생각도 못했는데 대단하시네요!
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};
