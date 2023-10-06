import React, { useState, useEffect } from 'react';
import { css } from "@emotion/react";
import axios from "axios";
import hljs from "highlight.js";
import './github-dark-dimmed.css';

export const CodeWindow = () => {
    const [codeData, setCodeData] = useState({ list: [] });
    // const [highlightedHTML1, setHighlightedCode1] = useState("");
    // const [highlightedHTML2, setHighlightedCode2] = useState("");
    // const [highlightedHTML3, setHighlightedCode3] = useState("");

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

      // useEffect(() => {
      //   // <line> 태그에서 코드 데이터를 추출
      //   const code = codeData.list.join("");

      //   const language1 = "javascript";
      //   const language2 = "python";
      //   const language3 = "java";

      //   const highlightedCode1 = hljs.highlight(language1, code).value;
      //   const highlightedCode2 = hljs.highlight(language2, code).value;
      //   const highlightedCode3 = hljs.highlight(language3, code).value;

      //   setHighlightedCode1(highlightedCode1);
      //   setHighlightedCode2(highlightedCode2);
      //   setHighlightedCode3(highlightedCode3);

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
                 {lineIndex} {'  '}
                <span dangerouslySetInnerHTML={{ __html: hljs.highlightAuto(line).value }} />
                <br />
              </React.Fragment>
              ))}
            </pre>
            {/* <pre dangerouslySetInnerHTML={{ __html: highlightedHTML1 }}
                 dangerouslySetInnerHTML={{ __html: highlightedHTML2 }}
                 dangerouslySetInnerHTML={{ __html: highlightedHTML3 }}/> */}
          </div>
        </>
      );
    }
  }
}