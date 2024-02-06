import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate, useParams } from 'react-router-dom';

import { css } from '@emotion/react';
import { theme } from '../Header/theme';
import {
  MycommentStyle,
  flexLayout,
  rowFlex,
  rowFlexRecomment,
} from '../SolutionDetail/commentsStyle';

import ProfileImg from './profile/default.png';

export const MyComments = () => {
  const navigate = useNavigate();
  const onClickName = (nickname) => {
    navigate(`/profile/${nickname}`);
  };

  const { solutionId } = useParams();
  const [comment, setComment] = useState([]);
  const [commentCount, setCommentCount] = useState(0);

  useEffect(() => {
    const token = localStorage.getItem('token');
    const apiUrl = `http://13.125.13.131:8080/api/v1/solutions/${solutionId}/mine`;

    axios
      .get(apiUrl, {
        method: 'GET',
        headers: { Authorization: `Bearer ${token}` },
      })
      .then((response) => {
        const newComments = response.data.comments;
        setComment(newComments);
        
        setCommentCount(calculateCommentCount(newComments));

        // 삭제된 댓글 제외

        const nonDeletedComments = newComments.filter(
          (comment) => comment.status !== 'DELETED'
        );
        setCommentsWithCountCheck(nonDeletedComments);
        
      })
      .catch((error) => {
        console.error('API 요청 오류:', error);
      });
  }, [solutionId]);

  const setCommentsWithCountCheck = (newComments) => {
    const deletedCommentsCount = newComments.reduce(
      (count, comment) => {
        return comment.status === 'DELETED'
          ? count + 1
          : count;
      },
      0
    );
    setCommentCount(
      (prevCount) => prevCount - deletedCommentsCount
    );
  };

    const calculateCommentCount = (comments) => {
      return comments.reduce((count, comment) => {
        // 댓글 갯수 더하기
        count += 1;
  
        // 삭제된 답글 갯수 빼기
        count -= comment.replies.reduce(
          (replyCount, reply) =>
            reply.status === 'DELETED' ? replyCount + 1 : replyCount,
          0
        );
  
        return count;
      }, 0);
  };

  return (
    <div className="wrap" css={MycommentStyle}>
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
            <span>{commentCount}</span>
            <span>개</span>
          </div>
        </div>
        <div className="comments">
          {comment
            .filter(
              (commentItem) =>
                commentItem.status !== 'DELETED' ||
                commentItem.replies.length > 0
            )
            .map((commentItem) => (
              <div className="comment" key={commentItem.id}>
                <div css={rowFlex}>
                  {/* 댓글 작성자 프로필 */}
                  <div className="profile">
                    <img
                      src={ProfileImg}
                      alt="profile image"
                      css={css`
                        width: 50px;
                        height: 50px;
                        border: 1px solid
                          ${theme.colors.light1};
                        border-radius: 100%;
                      `}
                    />
                  </div>
                  {/* 댓글 텍스트 */}
                  <div>
                    <div
                      className="text"
                      css={css`
                        margin-left: 20px;
                        gap: 10px;
                        display: flex;
                      `}
                    >
                      <div
                        className="nickname"
                        css={css`
                          font-size: 14px;
                          color: ${theme.colors.light1};
                          margin-bottom: 5px;
                          cursor: pointer;
                        `}
                        onClick={() =>
                          onClickName(commentItem.nickname)
                        }
                      >
                        {commentItem.nickname}
                      </div>

                      <div className="commnetText">
                        {commentItem.content}
                      </div>
                    </div>
                  </div>
                </div>

                {/* 답글 렌더링 */}
                {commentItem.replies &&
                  Array.isArray(commentItem.replies) && (
                    <div className="repliesSection">
                      {commentItem.replies
                        .filter(
                          (reply) =>
                            reply &&
                              reply.status !== 'DELETED'
                        )
                        .map((reply) => (
                          <div
                            key={reply && reply.id}
                            className="recomment"
                            css={rowFlexRecomment}
                          >
                            <div className="profile">
                              <img
                                src={
                                  reply.photo || ProfileImg
                                }
                                alt="profile image"
                                css={css`
                                  width: 40px;
                                  height: 40px;
                                  border: 1px solid
                                    ${theme.colors.light1};
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
                                  color: ${theme.colors
                                    .light1};
                                  margin-bottom: 5px;
                                `}
                                onClick={() =>
                                  onClickName(
                                    reply.nickname
                                  )
                                }
                              >
                                {reply.nickname}
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
                                    color: ${theme.colors
                                      .main};
                                    margin-right: 10px;
                                  `}
                                >
                                  @{commentItem.nickname}
                                </span>
                                {reply.content}
                              </div>
                            </div>
                          </div>
                        ))}
                    </div>
                  )}
              </div>
            ))}
        </div>
      </div>
    </div>
  );
};
