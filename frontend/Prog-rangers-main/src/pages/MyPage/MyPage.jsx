import React, { useState, useEffect } from 'react';
import { css } from '@emotion/react';
import { theme } from '../../components/Header/theme';
import { SideBar } from '../../components/SideBar/SideBar';
import { LeftBody, MainBody, RightBody } from './MainBody';
import axios from 'axios';
import { atom, useAtom } from 'jotai';
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
 
const studyAtom= atom([])
const myRecentAtom = atom([])
const followingAtom = atom([])

export const MyPage = () => {

  // API 가져오기
  const [monthlyStudyCalendar, setMonthlyStudyCalendar] = useAtom(studyAtom);
  const [myRecentSolutionInfos, setMyRecentSolutionInfos] = useAtom(myRecentAtom);
  const [followingRecentSolutionInfos, setFollowingRecentSolutionInfos] = useAtom(followingAtom);
  
  // MonthlyStudyCalendar
  const [value, setValue] = useState(new Date());

  useEffect(() => {
    const apiUrl = 'http://localhost:8080/prog-rangers/mypage/dashboard?month=SEP&year=2023';

    axios.get(apiUrl)
      .then((response) => {
        setMonthlyStudyCalendar(response.data.setMonthlyStudyCalendar);

        const sortedData = response.data.myRecentSolutionInfos.sort((a, b) => b.solutionId - a.solutionId);
        const top3Data = sortedData.slice(0, 3);
        setMyRecentSolutionInfos(top3Data);

        const sortedFollowingData = response.data.followingRecentSolutionInfos.sort((a, b) => b.solutionId - a.solutionId);
        const top5Data = sortedFollowingData.slice(0, 5);
        setFollowingRecentSolutionInfos(top5Data);

        const notificationInfoList = response.data.notificationInfoList;
        setFollowingRecentSolutionInfos(notificationInfoList);
      })
      .catch((error) => {
        console.error('API 요청 에러:', error);
      });
  }, []);

  const daysInMonth = new Date(value.getFullYear(), value.getMonth() + 1, 0).getDate();
  const currentMonth = value.toLocaleString('en-US', { month: 'long' }).toUpperCase();
  const currentYear = value.getFullYear();

  const renderCalendar = () => {
    const calendar = [];

    for (let day = 1; day <= daysInMonth; day++) {
      const currentDate = new Date(value.getFullYear(), value.getMonth(), day);
  
      // studyData = {"day": date , "studied": T/F }
      const studyData = monthlyStudyCalendar.find((item) => item.day === day);
      // 학습여부 T/F 저장
      const studiedFromMonthlyData = studyData ? studyData.studied : false;

      const isCurrentDate = currentDate.getDate() === day; 
      const StudyTrue = studiedFromMonthlyData && isCurrentDate;

      calendar.push(
        <div key={currentDate.toISOString()}>
          <div css={css`
          ${dateStyle}
          background-color: ${ StudyTrue ? '#70A9BC' : '#D9D9D9'};
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
  const listRecently = myRecentSolutionInfos.map((item) => (
    <div key={item.solutionId} css={`${Divline}`}>
      <div css={`${RecentlyTitle}`}> {item.title} </div>
      <div css={css`
              width: 100%; 
              height: 39px; 
              margin-top: 10px;
              display: flex;
              flex-direction: row;
              justify-content: space-between;
            `}>
        <div css={css`
                  ${ojName} 
                  background-color: ${item.ojName === "프로그래머스" ? "#6AB4AC" : "#3578BF"};`}>
                  {item.ojName}
                </div>
              </div>
            </div>
  ));

  // 팔로우 최근 풀이 리스트업
  const listFollwingRecently = followingRecentSolutionInfos.map((item) => (
    <div key={item.solutionId} css={`${Divline}`}>
      <div css={`${RecentlyTitle}`}> {item.title} </div>
      <div css={css`
              width: 100%; 
              height: 39px; 
              margin-top: 10px;
              display: flex;
              flex-direction: row;
              justify-content: space-between;
            `}>
        <div css={css`
                  ${ojName} 
                  background-color: ${item.ojName === "프로그래머스" ? "#6AB4AC" : "#3578BF"};`}>
                  {item.ojName}
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
                <svg xmlns="http://www.w3.org/2000/svg" width="18" height="12" viewBox="0 0 18 12" fill="none">
                  <rect width="18" height="12" rx="2" fill="#545454"/>
                  <path d="M0.75 1.875L9 6.375L17.25 1.875" stroke="white" strokeLinecap="round"/>
                </svg>
                  {/* {followingRecentSolutionInfos.map((info) => (
                  <div key={info.solutionId}>
                  {info.type} 
                  </div>
                  ))} */}
                  댓글
                </div>
                <div css={css`
                margin-left: 12px;
                margin-right: 12px;
                ${fontSize14}
                `}>
                  {followingRecentSolutionInfos.map((info) => (
                  <div key={info.solutionId}>
                  {info.nickname}님이 {info.solutionTitle}풀이에 {info.type}을 남겼습니다: {info.content}
                  </div>
                ))}
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
  )
}