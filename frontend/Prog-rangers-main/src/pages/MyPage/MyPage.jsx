import React, { useState, useEffect } from 'react';
import { css } from '@emotion/react';
import { theme } from '../../components/Header/theme';
import { SideBar } from '../../components/SideBar/SideBar';
import { LeftBody, MainBody, RightBody } from './MainBody';
import axios from 'axios';
import { 
  fontSize12,
  fontSize14,
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
  const [monthlyStudyCalendar, setMonthlyStudyCalendar] = useState({
    day: [],
    studied: []
  });
  const [myRecentSolutionInfos, setMyRecentSolutionInfos] = useState([]);
  const [followingRecentSolutionInfos, setFollowingRecentSolutionInfos] = useState([]);
  const [notificationInfoList, setNotificationInfoList] = useState([]);

  // MonthlyStudyCalendar
  const [value, setValue] = useState(new Date());
  
  useEffect(() => {
    
    const token = 'eyJhbGciOiJIUzUxMiJ9.eyJtZW1iZXJJZCI6MSwiZXhwIjoxNjk1NTY4MDU0LCJpYXQiOjE2OTU1MzIwNTQsImlzcyI6IlByb2dyYW5nZXJzIn0.HMVpVlOM5NNNnnuKcB0VOr4415zsElzsu1r8t4jAjyUFroZJ71Gn5VwH0mIbkgQssJw2m3KyIzMtDf7IGSV1lQ';
    
    fetch("http://13.124.131.171:8080/prog-rangers/mypage/dashboard?date=2023-09", 
    {
      method: "GET",
      headers: {Authorization: `Bearer ${token}`},
    })
    .then((response) => response.json())
    .then((json) => {

         // API 응답이 정상적일 때 데이터를 설정
        setMonthlyStudyCalendar(json.monthlyStudyCalendar || []);
        setMyRecentSolutionInfos(json.myRecentSolutionInfos || []);
        setFollowingRecentSolutionInfos(json.followingRecentSolutionInfos || []);
        setNotificationInfoList(json.notificationInfoList || []);
        setValue(new Date());
        
    })
    .catch((error) => {
      console.error(error);
    })
    
  }, []);

  const sortedData = myRecentSolutionInfos.sort((a, b) => b.solutionId - a.solutionId);
  const top3Data = sortedData.slice(0, 3);

  const sortedFollowingData = followingRecentSolutionInfos.sort((a, b) => b.solutionId - a.solutionId);
  const top5Data = sortedFollowingData.slice(0, 5);

  const daysInMonth = new Date(value.getFullYear(), value.getMonth() + 1, 0).getDate();
  const currentMonth = value.toLocaleString('en-US', { month: 'long' }).toUpperCase();
  const currentYear = value.getFullYear();

  const renderCalendar = () => {
    const calendar = [];

    for (let day = 1; day <= daysInMonth; day++) {
      const currentDate = new Date(value.getFullYear(), value.getMonth(), day);
      
      const studyData = Array.isArray(monthlyStudyCalendar.day) ? monthlyStudyCalendar.day : [];
      
      const studiedFromMonthlyData = studyData.includes(day);

      const isCurrentDate = currentDate.getDate() === day;
      const studyTrue = studiedFromMonthlyData && isCurrentDate;

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
      <div css={css`${Divline} display: flex; align-items: center;  gap: 30px;`}>
      <div css={`${RecentlyTitle} 
      `}> {item.title} </div>
      <div css={css`
              height: 39px;; 
              margin-top: 10px;
              display: flex;
              flex-direction: row;
              justify-content: space-between;
            `}>
        <div css={css`
                  font-size: 12px;          
                  ${ojName} 
                  background-color: ${item.ojName === "프로그래머스" ? "#6AB4AC" : "#3578BF"};`}>
                  {item.ojName}
                </div>
              </div>
            </div>
            </div>
   ));
  

  // 팔로우 최근 풀이 리스트업
  const listFollwingRecently = top5Data.map((item) => (
    <div key={item.solutionId} css={`${Divline}`}>
      <div css={css` display: flex; align-items: center;  gap: 30px;`}>
      <div css={`${RecentlyTitle}`}> {item.title} </div>
      <div css={css`
              height: 39px; 
              margin-top: 10px;
              display: flex;
              flex-direction: row;
              justify-content: space-between;
            `}>
        <div css={css`
                  ${ojName} 
                  font-size : 12px;
                  float: right;
                  background-color: ${item.ojName === "프로그래머스" ? "#6AB4AC" : "#3578BF"};`}>
                  {item.ojName}
                </div>
              </div>
            </div>
            </div>
  ));

  const infoList = notificationInfoList.map((info) => (
    <div key={info.solutionId}>
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
            gap: 4px;
            ${fontSize12}
            `}>
              {info.type}
            <svg xmlns="http://www.w3.org/2000/svg" width="18" height="12" viewBox="0 0 18 12" fill="none">
              <rect width="18" height="12" rx="2" fill="#545454"/>
              <path d="M0.75 1.875L9 6.375L17.25 1.875" stroke="white" strokeLinecap="round"/>
            </svg>
        </div>
        <div css={css`
            margin-left: 12px;
            margin-right: 12px;
            ${fontSize14}
            `}>
              {info.nickname}님이 {info.solutionTitle}풀이에 {info.type}을 남겼습니다: {info.content}
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
          height: 181px;
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
          height: 295px;
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