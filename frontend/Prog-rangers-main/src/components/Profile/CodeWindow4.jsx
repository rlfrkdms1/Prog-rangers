// 내 풀이 한줄리뷰 코드창

import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { css } from "@emotion/react";
import axios from "axios";
import hljs from "highlight.js";
import './github-dark-dimmed.css';
import plusmark from '../../assets/icons/plus-mark.svg';

export const CodeWindow4 = () => {
  const { solutionId } = useParams();
  const [codeData, setData] = useState({ solution: { code: [] } });

  // 한줄리뷰 추가 버튼
  const [hoveredLine, setHoveredLine] = useState(null);
  const [isBoxVisible, setIsBoxVisible] = useState(false);

  // 한줄리뷰
  const [clickedLineId, setClickedLineId] = useState(null);
  const [reviews, setReviews] = useState(Array(10).fill(''));
  const [nickname, setNickname] = useState('');

  const handleMouseEnter = (codeLineNumber) => {
    setHoveredLine(codeLineNumber);
  };

  const handleMouseLeave = () => {
    setHoveredLine(null);
  };

  const handleLineClick = (codeLineNumber) => {
    setClickedLineId(codeLineNumber);
    setIsBoxVisible(!isBoxVisible);
  };

  const handleReviewChange = (codeLineNumber, value) => {
    const updatedReviews = [...reviews];
    updatedReviews[codeLineNumber] = value;
    setReviews(updatedReviews);
  };

  useEffect(() => {
    
    const token = localStorage.getItem('token');
    const apiUrl = `http://13.124.131.171:8080/api/v1/solutions/${solutionId}`;

    axios
      .get(apiUrl, {
          method: "GET",
          headers: {Authorization: `Bearer ${token}`}
        })
      .then((response) => {
        setData(response.data);

      })
      .catch((error) => {
        console.error('API 요청 오류:', error);
      });

    // 사용자 닉네임을 가져오는 API 요청 추가
    if (token) {
      axios
        .get("http://13.124.131.171:8080/api/v1/members", {
          headers: { Authorization: `Bearer ${token}` },
        })
        .then((response) => {
          setNickname(response.data.nickname);
        })
        .catch((error) => {
          console.error('API 요청 오류:', error);
        });
    }
  }, []);

  function ReviewDisplay({ reviews, codeLineNumber }) {
    const review = reviews[codeLineNumber];

    if (!review) {
      return null;
    }

    return (
      <div>
        {review.nickname} : {review.content}
      </div>
    );
  }

  return (
    <div style={{ position: 'relative' } }>
      {codeData.solution.code.map((line, codeLineNumber) => (
        <div key={codeLineNumber} css={css`padding-left: 25px; margin: 6px;`}
             onMouseEnter={() => handleMouseEnter(codeLineNumber)}
             onMouseLeave={handleMouseLeave}
        >
          <pre>
            <span style={{ fontFamily: 'Consolas, Courier New, monospace' }}>
              {hoveredLine === codeLineNumber && (
                <div>
                  <button onClick={() => handleLineClick(codeLineNumber)} style={{ position: 'absolute' }}>
                    <img src={plusmark} />
                  </button>
                </div>
              )}
              {'    '}{codeLineNumber} {'  '}
              <span dangerouslySetInnerHTML={{ __html: hljs.highlightAuto(line).value }} />
              <br />

              {/* 한줄리뷰 */}
              {clickedLineId === codeLineNumber && isBoxVisible && (
                <div style={{
                  width: '750px',
                  height: 'auto',    
                  borderRadius: '20px',
                  background: '#CCDEF2',
                  color: '#000000',
                  display: 'flex',
                  margin: '5px',
                }}>
                  <div
                  
                  onChange={(e) => handleReviewChange(codeLineNumber, e.target.value)}
                  
                  style={{
                    width: '87%',
                    border: 'none',
                    background: 'transparent',
                    outline: 'none',
                    padding: '11px 20px',
                  }}
                  />
                  
                  
                  
                <div>
                  {reviews[codeLineNumber] && (
                  <ReviewDisplay reviews={reviews} codeLineNumber={codeLineNumber} />
                  )}
                </div>
              </div>
)}
            </span>
          </pre>
        </div>
      ))}
    </div>
  );
};
