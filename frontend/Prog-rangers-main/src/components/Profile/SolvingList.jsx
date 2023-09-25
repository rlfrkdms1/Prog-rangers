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
    
  return(
    <>
    {data.list.map((item, index) => (
      <div key={item.problemName + index}
       css={css`
        width: 835px;
        height: 660px;        
        margin-top: 50px;
        `}>

        <div css={css`}
        width: 835px;
        margin-top: 30px;
        `}>
        <div
        css={css`
        ${fontSizedark20}`}> 
        {item.problemName}
        </div>
        
        <div css={css`
        ${boxStyle}
        ${fontSize16}
        background-color: ${theme.colors.light3}`}>
        {item.dataStructure}
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
        background-color: ${item.ojName === "프로그래머스" ? "#6AB4AC" : "#3578BF"}
        `}>
        {item.ojName} 
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
        {item.description}
        </div>

        <div css={css`
        width: 809px;
        height: 370px;
        margin-top: 50px;
        color: #FFFFFF;
        background-color: #2A3746;
        `}>
        {item.code}
        </div>
    </div>
    </div>
    ))}
    </>
  )
}