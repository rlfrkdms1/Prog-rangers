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
    boxStyle, 
    fontSize14} from '../../pages/Profile/ProfileStyle';
import { CodeWindow } from './CodeWindow';
import { useParams, useNavigate } from 'react-router-dom';
import { ProfileContentMock } from '../SolutionDetail/solutionTabStyle';

export const SolvingList = () => {

    const { nickname } = useParams();
    const [data, setData] = useState({ list: [] });

    const navigate = useNavigate();
      const onClickSols = (solutionId) => {
        navigate(`/solutions/${solutionId}`);
      };

    useEffect(() => {

        const apiUrl = `http://13.124.131.171:8080/api/v1/members/${nickname}`;

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
        ${fontSizedark20}
        cursor: pointer;`}
        onClick={() => onClickSols(item.solutionId)}> 
        {item.problemName}
        </div>


        <div css={css`
        ${boxStyle}
        ${fontSize16}
        margin-top: 10px;
        background-color: ${theme.colors.light3}
        ${item.language === null ? 'display: none;' : ''}
        `}>
        {item.language}
        </div>


        <button css={css`
        width: 30px;
        height: 30px;
        float: right;
        padding-top: 4px;     
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

        <div css={css`${ProfileContentMock}
        `}>
          <div css={css`
          padding: 20px 40px 20px;
          font-size: 16px;
          font-weight: 700;`}>
          {item.problemName + index}
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
          
          <CodeWindow/>

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