import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { css } from "@emotion/react";
import axios from "axios";
import hljs from "highlight.js";
import './github-dark-dimmed.css';
import plusmark from '../../assets/icons/plus-mark.svg';

export const CodeWindow3 = () => {
  const { solutionId } = useParams();
  const [codeData, setCodeData] = useState({ solution: { code: [] } });
  const [hoveredLine, setHoveredLine] = useState(null);
  const [isBoxVisible, setIsBoxVisible] = useState(false);
  const [clickedLineId, setClickedLineId] = useState(null);
  const [reviews, setReviews] = useState(Array(10).fill(''));
  const [nickname, setNickname] = useState('');

  const handleMouseEnter = (lineIndex) => {
    setHoveredLine(lineIndex);
  };

  const handleMouseLeave = () => {
    setHoveredLine(null);
  };

  const handleLineClick = (lineIndex) => {
    setClickedLineId(lineIndex);
    setIsBoxVisible(!isBoxVisible);
  };

  const handleReviewChange = (lineIndex, value) => {
    const updatedReviews = [...reviews];
    updatedReviews[lineIndex] = value;
    setReviews(updatedReviews);
  };

  // 한줄리뷰 줄 번호 설정
  const handleAddReview = (lineIndex) => {
    if (lineIndex !== clickedLineId) {
      setClickedLineId(lineIndex);
    } else {
      setClickedLineId(null); // 이미 선택한 줄을 다시 클릭하면 선택을 해제합니다.
    }
  };

  useEffect(() => {
    const apiUrl = `http://13.124.131.171:8080/api/v1/solutions/${solutionId}`;

    axios
      .get(apiUrl)
      .then((response) => {
        setCodeData(response.data);
      })
      .catch((error) => {
        console.error('API 요청 오류:', error);
      });

    // 사용자 닉네임을 가져오는 API 요청 추가
    const token = localStorage.getItem('token');
    if (token) {
      axios
        .get("http://13.124.131.171:8080/api/v1/mypage/account-settings", {
          headers: { Authorization: `Bearer ${token}` },
        })
        .then((response) => {
          setNickname(response.data.nickname);
        })
        .catch((error) => {
          console.error('API 요청 오류:', error);
        });
    }
  }, [solutionId]);

  const handleSaveReviews = () => {
    const token = localStorage.getItem('token');
    
    if (!token) {
      alert("한줄리뷰를 작성하려면 로그인이 필요합니다.");
    } else if (reviews[clickedLineId] && reviews[clickedLineId].trim() !== '') {
      const newReview = {
        nickname: nickname,
        lineIndex: clickedLineId,
        content: reviews[clickedLineId],
      };
      
       // 닉네임과 텍스트 내용을 콘솔에 출력
    console.log("닉네임:", newReview.nickname);
    console.log("텍스트 내용:", newReview.content);


      axios
        .post(`http://13.124.131.171:8080/api/v1/solutions/${solutionId}/reviews`, newReview, {
          headers: { Authorization: `Bearer ${token}` },
        })
        .then((response) => {
          console.log('리뷰가 저장되었습니다.');
        })
        .catch((error) => {
          console.error('리뷰 저장 중 오류 발생:', error);
        });
    }
  };

  return (
    <div style={{ position: 'relative' }}>
      {codeData.solution.code.map((line, lineIndex) => (
        <div key={lineIndex} css={css`padding-left: 45px; margin: 6px;`}
             onMouseEnter={() => handleMouseEnter(lineIndex)}
             onMouseLeave={handleMouseLeave}
        >
          <pre css={css`font-size: 16px; white-space: pre-wrap;`} >
            <span style={{ fontFamily: 'Consolas, Courier New, monospace' }}>
              {hoveredLine === lineIndex && (
                <div>
                  <button onClick={() => handleLineClick(lineIndex)} style={{ position: 'absolute' }}>
                    <img src={plusmark} />
                  </button>
                </div>
              )}
              {'    '}{lineIndex} {'  '}
              <span dangerouslySetInnerHTML={{ __html: hljs.highlightAuto(line).value }} />
              <br />
              {clickedLineId === lineIndex && isBoxVisible && (
                <div style={{
                  width: '650px',
                  height: '40px',
                  borderRadius: '20px',
                  background: '#CCDEF2',
                  color: '#000000',
                  display: 'flex',
                  margin: '5px',
                }}>
                  <input type="text"
                  placeholder={`${lineIndex}번째 줄 코드 리뷰를 입력하세요`}
                  value={reviews[lineIndex]}
                  onChange={(e) => handleReviewChange(lineIndex, e.target.value)}
                  
                  style={{
                    width: '87%',
                    border: 'none',
                    background: 'transparent',
                    outline: 'none',
                    padding: '11px 20px',
                  }}
                  />
                  
                  <button
                  type="button"
                  onClick={handleSaveReviews}
                  css={css`
                    width: 75px;
                    height: 28px;
                    border-radius: 15px;
                    align-items: center;
                    background-color: #F0F0F0;
                    margin-top: 6px;
                  `}>
                  등록
                  </button>
                </div>
              )}
            </span>
          </pre>
        </div>
      ))}
    </div>
  );
};
