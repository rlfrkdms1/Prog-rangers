import React, { useState, useEffect } from 'react';
import { css } from "@emotion/react";
import { theme } from '../../components/Header/theme';
import sharemark from '../../assets/icons/share-mark.svg'
import axios from "axios";
import {
    fontSize16,
    fontSizewhite16,
    fontSize18,
    fontSizedark20,
    boxStyle } from '../../pages/Profile/ProfileStyle';

export const SolvingList = () => {
    // const [data, setData] = useState([]);

    // useEffect(() => {

    //     const apiUrl = 'http://localhost:8080/prog-rangers/members/profile/7';
    
    //     axios.get(apiUrl)
    //       .then((response) => {
    //         setData(response.data.list);
    //       })
    //       .catch((error) => {
    //         console.error('API 요청 오류:', error);
    //       });
    //   }, []);
    
  return(
    <>
    <div css={css`
        width: 835px;
        height: 660px;        
        margin-top: 50px;
        `}>
      {/* {data.list.length > 0 && data.list.map((item, index) => (
        <div key={index} css={css` */}
        <div css={css`}
        width: 835px;
        margin-top: 30px;
        `}>

        <div css={css`
        ${fontSizedark20}`}> 
        {/* {item.problemName} */} 풀이제목
        </div>
        
        <div css={css`
        ${boxStyle}
        ${fontSize16}
        background-color: ${theme.colors.light3}`}>
        {/* {item.dataStructure} */}
        API 8 Js
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
        background-color: ${theme.colors.programmers}
        `}>
        {/* background-color: ${item.ojName === "프로그래머스" ? "#6AB4AC" : "#3578BF"}
        {item.ojName}  */}
        API 9 oj
        </div>

        <div css={css`
        width: 835px;
        margin-top: 30px;
        border: 1px solid #F0F0F0;
        `}>
        </div>
    
        <div css={css`
        ${fontSize18} 
        margin-top: 50px;
        margin-left: 10px;`}> 
        {/* {item.description} */}
        API 10 풀이 설명
        <br></br>
        이 문제는 처음 도전하는 나에게 너무 어려운 문제였다.
        <br></br>
        다음에 한 번 더 도전해봐야겠다.
        </div>

        <div css={css`
        width: 809px;
        height: 370px;
        margin-top: 50px;
        color: #FFFFFF;
        background-color: #2A3746;
        `}>
        {/* {item.code} */}
        API 11 풀이 코드
        </div>
    </div>
    {/* ))} */}
    </div>
    </>
  )
}