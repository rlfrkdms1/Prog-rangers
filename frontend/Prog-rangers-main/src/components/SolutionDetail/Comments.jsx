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
          setComment(response.data.comments);
          setCommentCount(response.data.comments.length);
        })
        .catch((error) => {
          console.error('API 요청 오류:', error);
        });
    }, []);

    const addComment = (newComment) => {
      setComment([...comment, newComment]);
      setCommentCount(commentCount + 1);
    }

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
