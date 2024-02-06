// 프로필 안 코드창

import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { css } from '@emotion/react';
import axios from 'axios';
import hljs from 'highlight.js';
import './github-dark-dimmed.css';

export const CodeWindow = () => {
  const { nickname } = useParams();
  const [codeData, setCodeData] = useState({ list: [] });

  useEffect(() => {
    const apiUrl = `http://13.125.13.131:8080/api/v1/members/${nickname}`;

    axios
      .get(apiUrl)
      .then((response) => {
        setCodeData(response.data);
      })
      .catch((error) => {
        console.error('API 요청 오류:', error);
      });
  }, []);

  if (codeData.list && codeData.list.length > 0) {
    for (let i = 0; i < codeData.list.length; i++) {
      const item = codeData.list[i];

      return (
        <>
          <div
            key={item.description}
            css={css`
              padding-left: 40px;
              margin: 6px;
            `}
          >
            <pre
              css={css`
                font-size: 18px;
              `}
            >
              <span
                style={{
                  fontFamily:
                    'Consolas, Courier New, monospace',
                }}
              >
                {item.solution.code.map(
                  (line, lineIndex) => (
                    <React.Fragment key={lineIndex+1}>
                      {lineIndex+1} {'  '}
                      <span
                        dangerouslySetInnerHTML={{
                          __html:
                            hljs.highlightAuto(line).value,
                        }}
                      />
                      <br />
                    </React.Fragment>
                  )
                )}
              </span>
            </pre>
          </div>
        </>
      );
    }
  }
};
