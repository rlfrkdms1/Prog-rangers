// 내 풀이 한줄리뷰 코드창

import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { css } from '@emotion/react';
import axios from 'axios';
import hljs from 'highlight.js';
import './github-dark-dimmed.css';
import plusmark from '../../assets/icons/plus-mark.svg';

export const CodeWindow5 = () => {
  const { solutionId } = useParams();
  const [codeData, setData] = useState({
    solution: { code: [] },
  });

  // 한줄리뷰 추가 버튼
  const [hoveredLine, setHoveredLine] = useState(null);
  const [isBoxVisible, setIsBoxVisible] = useState(false);

  // 한줄리뷰
  const [clickedLineId, setClickedLineId] = useState(null);
  const [isReviewVisible, setIsReviewVisible] =
    useState(true);
  const [nickname, setNickname] = useState('');
  const [content, setContent] = useState('');

  // 한줄리뷰 더보기
  const [showAllReviews, setShowAllReviews] =
    useState(false);

  const handleMouseEnter = (codeLineNumber) => {
    setHoveredLine(codeLineNumber);
  };

  const handleMouseLeave = () => {
    setHoveredLine(null);
  };

  const handleLineClick = (codeLineNumber) => {
    setClickedLineId(codeLineNumber);
    setIsBoxVisible(!isBoxVisible);

    // 해당 코드 줄에 대한 리뷰 정보 찾기
    const reviewForLine = codeData.reviews.find(
      (review) => review.codeLineNumber === codeLineNumber+1
    );

    // 찾은 리뷰 정보의 닉네임을 가져와서 설정
    if (reviewForLine) {
      setNickname(reviewForLine.nickname);
      setContent(reviewForLine.content);
      setIsReviewVisible(true); // 리뷰가 있으면 렌더링
    } else {
      setIsReviewVisible(false); // 리뷰가 없으면 숨김
    }
  };

  const handleToggleReviews = () => {
    setShowAllReviews(!showAllReviews);
    setIsReviewVisible(!isReviewVisible);
  };

  useEffect(() => {
    const token = localStorage.getItem('token');
    const apiUrl = `http://13.125.13.131:8080/api/v1/solutions/${solutionId}`;

    axios
      .get(apiUrl, {
        method: 'GET',
        headers: { Authorization: `Bearer ${token}` },
      })
      .then((response) => {
        setData(response.data);
      })
      .catch((error) => {
        console.error('API 요청 오류:', error);
      });
  }, []);

  return (
    <div style={{ position: 'relative' }}>
      {codeData.solution.code.map(
        (line, codeLineNumber) => (
          <div
            css={css`
              padding-left: 25px;
              margin: 6px;
            `}
            onMouseEnter={() =>
              handleMouseEnter(codeLineNumber)
            }
            onMouseLeave={handleMouseLeave}
          >
            <pre>
              <span
                style={{
                  fontFamily:
                    'Consolas, Courier New, monospace',
                }}
              >
                {hoveredLine === codeLineNumber && (
                  <div>
                    <button
                      onClick={() =>
                        handleLineClick(codeLineNumber)
                      }
                      style={{ position: 'absolute' }}
                    >
                      <img src={plusmark} alt="plusmark" />
                    </button>
                  </div>
                )}
                {'    '}
                {codeLineNumber+1} {'  '}
                <span
                  dangerouslySetInnerHTML={{
                    __html: hljs.highlightAuto(line).value,
                  }}
                />
                <br />
                {/* 한줄리뷰 */}
                {clickedLineId === codeLineNumber &&
                  isBoxVisible && (
                    <div
                      style={{
                        width: '640px',
                        height: 'auto',
                        // borderRadius: '20px',
                        background: '#CCDEF2',
                        color: '#000000',
                        margin: '5px',
                        padding: '5px 15px 7px',
                      }}
                    >
                      <div
                        style={{
                          border: 'none',
                          background: 'transparent',
                          outline: 'none',
                        }}
                      >
                        <div>
                          {showAllReviews && (
                            <div>
                              {codeData.reviews
                                .filter(
                                  (review) =>
                                    review.codeLineNumber ===
                                      codeLineNumber+1 &&
                                    review.content !==
                                      '삭제된 리뷰입니다'
                                )
                                .map((review) => (
                                  <div
                                    key={
                                      review.codeLineNumber
                                    }
                                  >
                                    <div
                                      css={css`
                                        display: flex;
                                        align-items: center;
                                        font-family: Noto
                                          Sans KR;
                                        font-size: 16px;
                                      `}
                                    >
                                      <div
                                        css={css`
                                          font-size: 14px;
                                          color: #545454;
                                        `}
                                      >
                                        {' '}
                                        @{
                                          review.nickname
                                        }{' '}
                                      </div>
                                      {review.content}
                                    </div>
                                  </div>
                                ))}
                            </div>
                          )}

                          {isReviewVisible && (
                            <div
                              css={css`
                                display: flex;
                                align-items: center;
                                font-family: Noto Sans KR;
                                font-size: 16px;
                              `}
                            >
                              <div
                                css={css`
                                  font-size: 14px;
                                  color: #545454;
                                `}
                              >
                                {' '}
                                @{nickname}{' '}
                              </div>
                              {content}
                            </div>
                          )}

                          {codeData.reviews.filter(
                            (review) =>
                              review.codeLineNumber ===
                              codeLineNumber+1
                          ).length > 1 && (
                            <div
                              css={css`
                                display: flex;
                                align-items: center;
                                font-family: Noto Sans KR;
                                font-size: 16px;
                              `}
                            >
                              <button
                                onClick={
                                  handleToggleReviews
                                }
                                css={css`
                                  font-size: 14px;
                                  color: #959595;
                                  padding: 4px 0 0 20px;
                                `}
                              >
                                {showAllReviews
                                  ? '접기'
                                  : '더보기...'}
                              </button>
                            </div>
                          )}
                        </div>
                      </div>
                    </div>
                  )}
              </span>
            </pre>
          </div>
        )
      )}
    </div>
  );
};
