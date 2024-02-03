// 내풀이 상세보기 - 풀이보기용 코드창

import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { css } from '@emotion/react';
import axios from 'axios';
import hljs from 'highlight.js';
import './github-dark-dimmed.css';

export const CodeWindow3 = () => {
  const { solutionId } = useParams();
  const [codeData, setCodeData] = useState({
    solution: { code: [] },
  });

  useEffect(() => {
    const token = localStorage.getItem('token');
    const apiUrl = `http://13.125.13.131:8080/api/v1/solutions/${solutionId}`;

    axios
      .get(apiUrl, {
        method: 'GET',
        headers: { Authorization: `Bearer ${token}` },
      })
      .then((response) => {
        setCodeData(response.data);
      })
      .catch((error) => {
        console.error('API 요청 오류:', error);
      });
  }, [solutionId]);

  return (
    <>
      {codeData.solution.code.map((line, lineIndex) => (
        <div
          key={lineIndex+1}
          css={css`
            font-size: 18px;
            padding-left: 60px;
            margin: 6px;
          `}
        >
          <pre>
            <span
              style={{
                fontFamily:
                  'Consolas, Courier New, monospace',
              }}
            >
              {lineIndex+1} {'  '}
              <span
                dangerouslySetInnerHTML={{
                  __html: hljs.highlightAuto(line).value,
                }}
              />
              <br />
            </span>
          </pre>
        </div>
      ))}
    </>
  );
};
