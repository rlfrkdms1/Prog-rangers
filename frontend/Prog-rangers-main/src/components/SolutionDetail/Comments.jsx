import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useParams } from 'react-router-dom';

import { css } from '@emotion/react';
import { theme } from '../Header/theme';
import { CommentForm } from './CommentForm';
import { CommentList } from './CommentList';
import {
  wrapStyle,
  flexLayout
} from './commentsStyle';

export const Comments = () => {

  const { solutionId } = useParams();
  const [ comment, setComment ] = useState([]);
  const [ commentCount, setCommentCount ] = useState(0);

    useEffect(() => {
      
      const apiUrl = `http://13.124.131.171:8080/api/v1/solutions/${solutionId}`;
      
      axios
        .get(apiUrl)
        .then((response) => {
          // 댓글 갯수          
          setCommentCount(response.data.comments.length);
          // 삭제된 댓글 제외
          const newComments = response.data.comments;
          setCommentsWithCountCheck(newComments);
        })
        .catch((error) => {
          console.error('API 요청 오류:', error);
        });
    }, []);

    const addComment = (newComment) => {
      setComment([...comment, newComment]);
      setCommentCount(commentCount + 1);
    }

    const setCommentsWithCountCheck = (newComments) => {
      const deletedCommentsCount = newComments.reduce((count, comment) => {
        return comment.status === "DELETED" ? count + 1 : count;
      }, 0);
  
      setCommentCount((prevCount) => prevCount - deletedCommentsCount);
  
      const nonDeletedComments = newComments.filter(comment => comment.status !== "DELETED");
      setComment(nonDeletedComments);
    };

  return (
    <div className="wrap" css={wrapStyle}>
      <div className="commentArea">
      <CommentForm addComment={addComment} solutionId={solutionId} />

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
