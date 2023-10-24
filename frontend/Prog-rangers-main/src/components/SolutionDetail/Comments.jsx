import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import { useParams } from 'react-router-dom';

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

  const navigate = useNavigate();
    const onClickName = (nickname) => {
      navigate(`/profile/${nickname}`); 
    };

  const { solutionId } = useParams();
  const [ comment, setComment ] = useState([]);
  const [ commentCount, setCommentCount ] = useState(0);

    useEffect(() => {
      const apiUrl = `http://13.124.131.171:8080/api/v1/solutions/${solutionId}`;
      // const apiUrl = `http://13.124.131.171:8080/api/v1/solutions/1`;

      axios
        .get(apiUrl)
        .then((response) => {
          setComment(response.data.comments);
          setCommentCount(response.data.comments.length);
        })
        .catch((error) => {
          console.error('API 요청 오류:', error);
        });
    }, []);

  return (
    <div className="wrap" css={wrapStyle}>
      <div className="commentArea">
      <div className="search"
        css={css`
        width: 996px;
        height: 50px;
        border: 1px solid #111;
        border-radius: 25px;
        margin-top: 30px;
        padding-top: 10px;
        padding-left: 30px;
        `}>
          <input type="text"
            placeholder="댓글을 입력해주세요"
            css={css`
              outline: none;
              border: none;
              width: 88%;
              `}/>
              <button
              type="submit"
              css={css`
              width: 80px;
              height: 30px;
              border-radius: 15px;
              
              align-items: center;
              background-color: #C2DBE3`}>
                입력
              </button>
        </div>
        <div className="title" css={flexLayout}>
          <div
            className="titleName"
            css={css`
              font-size: 20px;
              font-weight: bold;
              color: ${theme.colors.dark1};
              margin-top: 30px;
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
            <span>{commentCount}</span>
            <span>개</span>
          </div>
        </div>
        <div className="comments">

        {comment.map((commentItem) => (
          <div className="comment" css={rowFlex} key={commentItem.id}>
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
                onClick={() => onClickName(commentItem.nickname)} 
              >
                {commentItem.nickname}
              </div>
              <div className="commnetText">
                {commentItem.content}
              </div>
            </div>
          </div>
        ))}
        
        {comment.replies && Array.isArray(comment.replies) && comment.replies.map((replyItem) => (
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
                onClick={() => onClickName(replyItem.nickname)} 
              >
                {replyItem.nickname}
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
                  @{comment.nickname}
                </span>
                {replyItem.content}
              </div>
            </div>
          </div>
        ))}
        </div>
      </div>
    </div>
  );
};
