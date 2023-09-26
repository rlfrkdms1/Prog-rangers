import React, { useState, useEffect } from 'react';
import { css } from "@emotion/react";
import { theme } from '../../components/Header/theme';
import axios from "axios";
import { fontSize16 } from '../../pages/Profile/ProfileStyle';

export const CodeWindow = () => {
    const [data, setData] = useState({ list: [] });

    useEffect(() => {

        const apiUrl = 'http://13.124.131.171:8080/prog-rangers/members/profile/test';
    
        axios.get(apiUrl)
          .then((response) => {
            setData(response.data);
          })
          .catch((error) => {
            console.error('API 요청 오류:', error);
          });
      }, []);

      
      if (data.list && data.list.length > 0) {
        for (let i = 0; i < data.list.length; i++) {
          const item = data.list[i];

      return(
        <>
          <div key={i} css={css`overflow-y: auto; max-height: 270px; margin-bottom: 20px;`}>
            <pre css={css`font-size: 20px; white-space: pre-wrap;`}>
              {item.code.map((line, lineIndex) => (
              <React.Fragment key={lineIndex}>
                {lineIndex} {' '} {line}
                <br />
              </React.Fragment>
              ))}
            </pre>
          </div>
        </>
      );
    }
  }
}