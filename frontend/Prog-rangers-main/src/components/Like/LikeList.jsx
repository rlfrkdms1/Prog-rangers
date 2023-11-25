import React, { useState, useEffect } from 'react';
import { css } from "@emotion/react";
import { theme } from '../../components/Header/theme';
import sharemark from '../../assets/icons/share-mark.svg'
import axios from "axios";
import { useNavigate } from 'react-router-dom';
import {
    fontSize16,
    fontSizewhite16,
    fontSize18,
    fontSizedark20,
    boxStyle, 
    fontSize14} from './LikeStyle.js';
import hljs from "highlight.js";

export const LikeList = () => {    

    const [data, setData] = useState({ contents: [] });

    const navigate = useNavigate();
    const onClickName = (nickname) => {
      navigate(`/profile/${nickname}`); 
    };  

    useEffect(() => {
        const token = localStorage.getItem('token');
        const apiUrl = 'http://13.124.131.171:8080/api/v1/likes';

        axios.get(apiUrl, {
            method: "GET",
            headers: {Authorization: `Bearer ${token}`},
          })
          .then((response) => {
            setData(response.data);
          })
          .catch((error) => {
            console.error('API 요청 오류:', error);
          });
      }, []);

      if (data.contents && data.contents.length > 0) {
        for (let i = 0; i < data.contents.length; i++) {
          const item = data.contents[i];    

  return(
    <>
    {data.contents.map((item, index) => (
      <div key={index}
       css={css`
        width: 835px;     
        margin-top: 50px;
        `}>

        <div css={css`}
        width: 835px;
        margin-top: 30px;
        `}>
        <div
        css={css`
        ${fontSizedark20}`}> 
        {item.problem.title}
        </div>

        <div css={css`display:flex; gap:20px; margin-top: 10px;`}>
        <div css={css`${fontSize16}`}>{item.solution.title}</div>
        <div css={css`${fontSize16} color:${theme.colors.light1}; cursor: pointer;`} onClick={()=>onClickName(item.solution.author)} >{item.solution.author}</div>
        </div>

        <div css={css`
        ${boxStyle}
        ${fontSize16}
        background-color: ${theme.colors.light3}`}>
        {item.solution.language}
        </div>

        <button css={css`
        width: 30px;
        height: 30px;
        float: right;
        padding-top: 13px;     
        `}>
        <img src={sharemark} alt="share_mark"/>
        </button>

        <div css={css`
        ${boxStyle}
        ${fontSizewhite16}
        float: right;
        margin-right: 30px;
        background-color: ${item.problem.ojName === "프로그래머스" ? "#6AB4AC" : "#3578BF"}
        `}>
        {item.problem.ojName} 
        </div>

        <div css={css`
        width: 835px;
        margin-top: 30px;
        border: 1px solid #F0F0F0;
        `}>
        </div>

        <div css={css`
            ${fontSize18};
            margin-top: 50px;
            margin-left: 10px;
        `}>
            {item.solution.description
                .join('\n')
                .split('\n')
                .map((paragraph, index) => (
                    paragraph.trim() ? <p key={index}>{paragraph}</p> : <br key={index} />
                ))}
        </div>


        <div css={css`
        width: 809px;
        margin-top: 50px;
        color: #FFFFFF;
        background-color: #2A3746;
        `}>
          <div css={css`
          padding: 20px;
          font-weight: 700;`}>
          {item.solution.title}
          </div>

          <div css={css`
          width: 100%;
          border-bottom: 1px solid #1A2333;
          `}></div>
          <div css={css`
          margin-top: 3px;
          width: 100%;
          border-bottom: 1px solid #1A2333;
          `}></div>

          <div css={css`
          ${fontSize14}
          padding: 20px;
          `}>
            <div key={ item.solution.id + i } css={css`overflow-y: auto; max-height: 270px; margin-bottom: 20px;`}>
            <pre css={css`font-size: 20px; white-space: pre-wrap;`} >
            <span style={{ fontFamily: 'Consolas, Courier New, monospace' }}>
              {item.solution.code.map((line, lineIndex) => (
              <React.Fragment key={lineIndex}>
                 {lineIndex} {'  '}
                <span dangerouslySetInnerHTML={{ __html: hljs.highlightAuto(line).value }} />
                <br />
              </React.Fragment>
              ))}
              </span>
            </pre>
          </div>
          </div>

        </div>

        <div css={css`
        margin-top: 50px;
        width: 100%;
        border-bottom: 1px solid #959595;
        `}></div>
      </div>
    </div>
    ))}
    </>
  )
}
}}
