import React, { useState, useEffect } from 'react';
import { css } from "@emotion/react";
import axios from "axios";
import hljs from "highlight.js";

export const CodeWindow = () => {
    const [codeData, setCodeData] = useState({ list: [] });
    // const [highlightedHTML1, setHighlightedCode1] = useState("");
    // const [highlightedHTML2, setHighlightedCode2] = useState("");

    useEffect(() => {

        const apiUrl = 'http://13.124.131.171:8080/api/v1/members/profile/test';
    
        axios.get(apiUrl)
          .then((response) => {
            setCodeData(response.data);
          })
          .catch((error) => {
            console.error('API 요청 오류:', error);
          });
      }, []);

      // useEffect(() => {
      //   // <line> 태그에서 코드 데이터를 추출
      //   const code = codeData.replace(/<line>/g, "").replace(/<\/line>/g, "");
        
      //   const language1 = "javascript";
      //   const language2 = "python";
        
      //   const highlightedCode1 = hljs.highlight(language1, code).value;
      //   const highlightedCode2 = hljs.highlight(language2, code).value;
        
      //   setHighlightedCode1(highlightedCode1);
      //   setHighlightedCode2(highlightedCode2);
        
      // }, [codeData]);


      if (codeData.list && codeData.list.length > 0) {
        for (let i = 0; i < codeData.list.length; i++) {
          const item = codeData.list[i];

      return(
        <>
          <div key={i} css={css`overflow-y: auto; max-height: 270px; margin-bottom: 20px;`}>
            <pre css={css`font-size: 20px; white-space: pre-wrap;`} >
              {item.code.map((line, lineIndex) => (
              <React.Fragment key={lineIndex}>
                {lineIndex} {' '} {line}
                <br />
              </React.Fragment>
              ))}
            </pre>
            {/* <pre dangerouslySetInnerHTML={{ __html: highlightedHTML }}/> */}
          </div>
        </>
      );
    }
  }
}