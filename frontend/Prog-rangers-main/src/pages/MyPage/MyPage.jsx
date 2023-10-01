import React, { useState, useEffect } from 'react';
import { css } from '@emotion/react';
import { theme } from '../../components/Header/theme';
import { SideBar } from '../../components/SideBar/SideBar';
import { LeftBody, MainBody, RightBody } from './MainBody';
import axios from 'axios';
import { 
  fontSize14,
  fontSizeBold14,
  fontSize16,
  fontSize20,
  alignCenter, 
  dateStyle,
  gridStyle,
  Divline,
  ojName,
  RecentlyTitle
 } from './MyPageStyle';

export const MyPage = () => {

  // API 가져오기
  const [monthlyStudyCalendar, setMonthlyStudyCalendar] = useState([]);
  const [recentProblems, setrecentProblems] = useState([]);
  const [followingRecentSolutions, setfollowingRecentSolutions] = useState([]);
  const [notifications, setnotifications] = useState([]);

  // MonthlyStudyCalendar
  const [value, setValue] = useState(new Date());
  
  useEffect(() => {
    
    const token = "eyJhbGciOiJIUzUxMiJ9.eyJtZW1iZXJJZCI6MSwiZXhwIjoxNjk2MTMzMDE5LCJpYXQiOjE2OTYwOTcwMTksImlzcyI6IlByb2dyYW5nZXJzIn0.gIJ0vSlGlBaISvpBp2fskz1DGW8abIcnWspD_sqaoE6EqdtXkhjXyQWR4CVu_cFMtSVfvpeh_Xv0JG9SQ-XJVg";
    
    fetch("http://13.124.131.171:8080/api/v1/mypage/dashboard", 
    {
      method: "GET",
      headers: {Authorization: `Bearer ${token}`},
    })
    .then((response) => response.json())
    .then((json) => {

         // API 응답이 정상적일 때 데이터를 설정
        setMonthlyStudyCalendar(json.monthlyStudyCalendar || []);
        setrecentProblems(json.recentProblems || []);
        setfollowingRecentSolutions(json.followingRecentSolutions || []);
        setnotifications(json.notifications || []);
        setValue(new Date());
        
    })
    .catch((error) => {
      console.error(error);
    })
    
  }, []);

  const sortedData = recentProblems.sort((a, b) => b.solutionId - a.solutionId);
  const top3Data = sortedData.slice(0, 3);

  const sortedFollowingData = followingRecentSolutions.sort((a, b) => b.solutionId - a.solutionId);
  const top5Data = sortedFollowingData.slice(0, 5);

  const daysInMonth = new Date(value.getFullYear(), value.getMonth() + 1, 0).getDate();
  const currentMonth = value.toLocaleString('en-US', { month: 'long' }).toUpperCase();
  const currentYear = value.getFullYear();

  const renderCalendar = () => {
    const calendar = [];

    for (let day = 1; day <= daysInMonth; day++) {
      const currentDate = new Date(value.getFullYear(), value.getMonth(), day);
      
      const isCurrentDate = currentDate.getDate() === day;
      const studiedData = monthlyStudyCalendar.find(item => item.day === day);

      const studyTrue = studiedData && studiedData.studied !== undefined && studiedData.studied;

      calendar.push(
        <div key={currentDate.toISOString()}>
          <div css={css`
          ${dateStyle}
          background-color: ${ studyTrue ? '#70A9BC' : '#D9D9D9'};
          `}>
            {currentDate.getDate()}
            </div>
        </div>
      );
    }
    return calendar;
};

  const goToPreviousMonth = () => {
    const previousMonth = new Date(value.getFullYear(), value.getMonth() - 1, 1);
    setValue(previousMonth);
  };

  const goToNextMonth = () => {
    const nextMonth = new Date(value.getFullYear(), value.getMonth() + 1, 1);
    setValue(nextMonth);
  };

  // 최근 쓴 풀이 리스트업
 const listRecently = top3Data.map((item) => (
  <div key={item.solutionId}>
    <div css={css`${Divline} 
              display: flex; 
              align-items: center; 
              justify-content: space-between;`}>
      <div css={css`${RecentlyTitle}`}> {item.title} </div>
      <div css={css`
                ${ojName}
                font-size: 12px;
                margin-right: 10px;
                background-color: ${item.ojName === "프로그래머스" ? "#6AB4AC" : "#3578BF"};`}>
                {item.ojName}
      </div>
    </div>
  </div>
 ));
  

  // 팔로우 최근 풀이 리스트업
  const listFollwingRecently = top5Data.map((item) => (
    <div key={item.solutionId}>
      <div css={css` ${Divline} 
                display: flex; 
                align-items: center;
                justify-content: space-between;`}>
        <div css={css `${RecentlyTitle}`}> {item.title} </div>
        <div css={css`
                  ${ojName} 
                  font-size : 12px;
                  margin-right: 10px;
                  background-color: ${item.ojName === "프로그래머스" ? "#6AB4AC" : "#3578BF"};`}>
                  {item.ojName}
                </div>
              </div>
            </div>
   ));

  const infoList = notifications.map((info) => (
    <div key={info.solutionId} css={css`overflow-y: auto; max-height: 565px; margin-bottom: 20px;`}>
      <div css={css`
              width: 345px;
              height: 80px;
              margin-top: 25px;
              margin-left: 10px;
              background-color: ${theme.colors.light3}`}>
        <div css={css`
            display: flex;
            align-items: center;
            margin-top: 9px;
            margin-left: 10px;
            padding-top: 10px;
            gap: 4px;
            ${fontSizeBold14}
            `}>
            <svg xmlns="http://www.w3.org/2000/svg" width="18" height="12" viewBox="0 0 18 12" fill="none">
              <rect width="18" height="12" rx="2" fill="#545454"/>
              <path d="M0.75 1.875L9 6.375L17.25 1.875" stroke="white" strokeLinecap="round"/>
            </svg>
            {info.type}
        </div>
        <div css={css`
            margin-left: 12px;
            margin-right: 12px;
            margin-top: 5px;
            ${fontSize14}
            `}>
              <span css={css`font-weight: 700;`}>
                {info.nickname} </span>
                님이 {' '}
              <span css={css` font-weight: 700;`}>
                {info.solutionTitle} </span>
                풀이에 {info.type}을 남겼습니다 : {info.content}
                </div>
            </div>
      </div>
  ));

  return(
    <div 
    className='container' 
    css={css`
    width: 1200px;
    height: 100%;
    display: flex;
    justify-content: space-between;
    margin: 0 auto;
  ` }
  >
    <SideBar />
    <div 
    css={css`${MainBody}
  `}>    
   
    <div
      css={css`
        ${LeftBody}
      `}>
          <div css={css`
          width: 365px;
          height: 123px;
          `}>
            <div css={css`
              display: flex;
              align-items: center;
              `}>
              <button onClick={goToPreviousMonth} css={css`
              flex: 1;`}>
                <svg xmlns="http://www.w3.org/2000/svg" width="6" height="14" viewBox="0 0 6 14" fill="none" >
                  <path d="M5.5 1L1 7L5.5 13" stroke="#545454"/>
                </svg>
              </button>
              <div css={css`
              ${[fontSize16, alignCenter]} 
              margin-bottom: 10px`}>
                {currentYear} {currentMonth}
              </div>
              <button onClick={goToNextMonth} css={css`
              flex: 1;`}>
              <svg xmlns="http://www.w3.org/2000/svg" width="6" height="14" viewBox="0 0 6 14" fill="none">
                <path d="M0.5 13L5 7L0.500001 1" stroke="#545454"/>
              </svg>
              </button>
            </div>

            <div css={css`
            width: 365px;
            height: 90px;
            border-radius: 5px;
            padding-top: 3px;
            background-color: ${theme.colors.light4}
            
            }`}>
                <div css={css`
                ${[gridStyle, alignCenter]}`}>
                  {renderCalendar()}
                  </div>
              </div>
          </div>

          <div css={css`
          ${fontSize20}
          margin-top: 50px
          `}>최근 쓴 풀이</div>

          <div css={css`
          width: 365px;
          height: 180px;
          border-radius: 5px;
          margin-top: 10px;
          background-color: ${theme.colors.light4};
          `}>
          {listRecently}
          </div>

          <div css={css`
          ${fontSize20}
          margin-top: 50px
          `}>팔로우의 최근 풀이</div>

          <div css={css`
          width: 365px;
          height: 300px;
          border-radius: 5px;
          margin-top: 10px;
          background-color: ${theme.colors.light4};
          `}>
          {listFollwingRecently}
        </div>
    </div>  
     <div css={css`
      ${RightBody}`}>
        <div css={css`
          width: 365px;
          height: 123px;
          `}>
            <div css={css`
            ${[fontSize20, alignCenter]}
            `}> 달성 </div> 
            <div css={css`
            width: 365px;
            height: 90px;
            border-radius: 5px;
            margin-top: 4px;
            background-color: ${theme.colors.light4}`}>
            </div>  
          </div>

          <div css={css`
          width: 365px;
          height: 604px;
          padding-top: 50px;
          `}>
            <div css={css`${fontSize20}`}>알림</div>
            <div css={css`
            display: flex;
            width: 365px;
            height: 565px;
            border-radius: 5px;
            margin-top: 10px;
            background-color: ${theme.colors.light4}
            `}>
            {infoList}
            </div>
          </div>
        </div>
      </div>
    </div>
  )
}