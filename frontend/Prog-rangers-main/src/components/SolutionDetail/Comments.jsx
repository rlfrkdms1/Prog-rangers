import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useParams } from 'react-router-dom';

import { css } from '@emotion/react';
import { theme } from '../Header/theme';
import { CommentForm } from './CommentForm';
import { CommentList } from './CommentList';
import { wrapStyle, flexLayout } from './commentsStyle';

export const Comments = () => {
  const { solutionId } = useParams();
  const [comment, setComment] = useState([]);
  const [commentCount, setCommentCount] = useState(0);  
  const [replyCount, setReplyCount] = useState(0);

  useEffect(() => {
    const apiUrl = `http://13.125.13.131:8080/api/v1/solutions/${solutionId}`;

    axios
      .get(apiUrl)
      .then((response) => {    
        
        const newComments = response.data.comments;
        
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

  const addComment = (newComment) => {
    setComment([...comment, newComment]);
    setCommentCount((prevCount) => prevCount + calculateCommentCount([newComment]));
  };

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
    <div className="wrap" css={wrapStyle}>
      <div className="commentArea">
        <CommentForm
          addComment={addComment}
          solutionId={solutionId}
        />

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

        <CommentList comments={comment} />
      </div>
    </div>
  );
};
