import React, { useState, useEffect } from 'react';
import { css } from "@emotion/react";
import axios from "axios";
import hljs from "highlight.js";
import './github-dark-dimmed.css';

export const CodeWindow = () => {
    const [codeData, setCodeData] = useState({ list: [] });

    useEffect(() => {

        const apiUrl = 'http://13.124.131.171:8080/api/v1/members/test';

        axios.get(apiUrl)
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

      return(
        <>
          <div key={i} css={css`overflow-y: auto; max-height: 270px; margin-bottom: 20px;`}>
            <pre css={css`font-size: 20px; white-space: pre-wrap;`} >
            <span style={{ fontFamily: 'Consolas, Courier New, monospace' }}>
              {item.code.map((line, lineIndex) => (
              <React.Fragment key={lineIndex}>
                {lineIndex} {' '} {line}
                <br />
              </React.Fragment>
              ))}
              </span>
            </pre>
          </div>
        </>
      );
    }
  }
}