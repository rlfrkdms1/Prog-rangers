import React, { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import axios from "axios";

import { css } from "@emotion/react";
import { theme } from "../Header/theme";
import { 
  rowFlex,
  rowFlexRecomment 
} from "./commentsStyle";

import ProfileImg from './profile/default.png';

export const CommentList = ({ comment }) => {

    const navigate = useNavigate();
    const onClickName = (nickname) => {
      navigate(`/profile/${nickname}`); 
    };

  const { solutionId } = useParams();
  const [ comments, setComments ] = useState([]);

    useEffect(() => {
        const apiUrl = `http://13.124.131.171:8080/api/v1/solutions/${solutionId}`;
        
        axios
          .get(apiUrl)
          .then((response) => {
            setComments(response.data.comments);
          })
          .catch((error) => {
            console.error('API 요청 오류:', error);
          });
      }, []);


  return (
    <div className="comments">
        {comments.map((commentItem) => (
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
        
        {comments.replies && Array.isArray(comments.replies) && comments.replies.map((replyItem) => (
          <div key={replyItem.id} className="recomment" css={rowFlexRecomment}>
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
                  @{comments.nickname}
                </span>
                {replyItem.content}
              </div>
            </div>

            // 추가된 댓글
            <div
                className="nickname"
                css={css`
                  font-size: 14px;
                  color: ${theme.colors.light1};
                  margin-bottom: 5px;
                `}
                onClick={() => onClickName(comments.nickname)} 
              >
                {comments.author}
              </div>
              <div className="commnetText">
                {comments.content}
              </div>
          </div>
        ))}
    </div>
  );
};