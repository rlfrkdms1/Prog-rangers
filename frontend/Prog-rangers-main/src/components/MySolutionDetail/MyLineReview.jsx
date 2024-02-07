import React, { useState, useEffect } from 'react';
import { css } from '@mui/material';
import axios from 'axios';
import { useParams } from 'react-router-dom';
import { CodeWindow5 } from '../Profile';
import {
  LineContentMock,
  contentLayout,
} from '../SolutionDetail/solutionTabStyle';

export const MyLineReview = () => {
  const { solutionId } = useParams();
  const [problem, setProblem] = useState({});
  const [solution, setSolution] = useState({});

  useEffect(() => {
    const token = localStorage.getItem('token');
    const apiUrl = `http://13.125.13.131:8080/api/v1/solutions/${solutionId}/mine`;

    axios
      .get(apiUrl, {
        method: 'GET',
        headers: { Authorization: `Bearer ${token}` },
      })
      .then((response) => {
        setProblem(response.data.problem);
        setSolution(response.data.solution);
      })
      .catch((error) => {
        console.error('API 요청 오류:', error);
      });
  }, [solutionId]);

  return (
    <>
      <div className="contentWrap">
        <div
          className="contentText"
          css={css`
            ${contentLayout}
            max-width: 740px;
            white-space: wrap;
          `}
        >
          {solution &&
            solution.description &&
            solution.description
              .split('\n')
              .map((line, index) => (
                <p key={index}>{line}</p>
              ))}
        </div>

        <div className="codeArea" css={LineContentMock}>
          <div
            css={css`
              padding: 15px 0 15px 60px;
              font-weight: 700;
            `}
          >
            {problem.title}
          </div>

          <div
            css={css`
              width: 100%;
              border-bottom: 1px solid #1a2333;
            `}
          ></div>
          <div
            css={css`
              margin-top: 3px;
              width: 100%;
              border-bottom: 1px solid #1a2333;
            `}
          ></div>

          <CodeWindow5 />
        </div>
      </div>
    </>
  );
};
