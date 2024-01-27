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
  const [repliesCount, setRepliesCount] = useState(0); 
  const [totalCount, setTotalCount] = useState(0);

  useEffect(() => {
    const apiUrl = `http://13.125.13.131:8080/api/v1/solutions/${solutionId}`;

    axios
      .get(apiUrl)
      .then((response) => {    
        setComment(response.data.comments);
        const totalCommentCount = calculateTotalCommentCount(response.data.comments);
        setCommentCount(totalCommentCount.commentCount);
        setRepliesCount(totalCommentCount.repliesCount);  
        
        const newTotalCount = totalCommentCount.commentCount + totalCommentCount.repliesCount;
        setTotalCount(newTotalCount);
      })
      .catch((error) => {
        console.error('API 요청 오류:', error);
      });
  }, [solutionId]); 

  const addComment = (newComment) => {
    setComment([...comment, newComment]);
    setCommentCount(commentCount + 1);
  }

  const calculateTotalCommentCount = (comments) => {
    let totalCommentCount = 0;
    let totalRepliesCount = 0;

    if (comments && Array.isArray(comments)) {
      comments.forEach((comment) => {
        totalCommentCount += 1;

        if (comment.replies && Array.isArray(comment.replies)) {
          const nonDeletedReplies = comment.replies.filter(reply => reply.status !== 'DELETED');
          totalRepliesCount += nonDeletedReplies.length;
        }
      });
    }

    return { commentCount: totalCommentCount, repliesCount: totalRepliesCount };
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
            <span>{totalCount}</span>
            <span>개</span>
          </div>
        </div>

        <CommentList comments={comment} key={totalCount} />
      </div>
    </div>
  );
};
