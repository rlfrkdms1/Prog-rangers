import React, { useState, useEffect } from 'react';
import { css } from '@emotion/react';
import { theme } from '../../components/Header/theme';
import { SideBar } from '../../components/SideBar/SideBar';
import { RecentlyList } from '../../components/MyPage/RecentlyList';
import { FollowingList } from '../../components/MyPage/FollowingList';
import { InfoList } from '../../components/MyPage/InfoList';
import { LeftBody, MainBody, RightBody } from './MainBody';
import axios from 'axios';
import { 
  fontSize16,
  fontSize20,
  alignCenter, 
  dateStyle,
  gridStyle,
  infoSytle
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

    const token = localStorage.getItem('token');
    const apiUrl = 'http://13.124.131.171:8080/api/v1/dashboard';

    axios.get(apiUrl, {
      method: "GET",
      headers: {Authorization: `Bearer ${token}`}
    })
    .then((response) => {
      // API 응답이 정상적일 때 데이터를 설정
      const data = response.data;
      setMonthlyStudyCalendar(data.monthlyStudyCalendar || []);
      setrecentProblems(data.recentProblems || []);
      setfollowingRecentSolutions(data.followingRecentSolutions || []);
      setnotifications(data.notifications || []);
      setValue(new Date());
    })
    .catch((error) => {
      console.error('API 요청 오류:', error);
    })
  }, []);  

  const sortedData = recentProblems.sort((a, b) => b.solutionId - a.solutionId);
  const top3Data = sortedData.slice(0, 3);

  const sortedFollowingData = followingRecentSolutions.sort((a, b) => b.solutionId - a.solutionId);
  const top5Data = sortedFollowingData.slice(0, 5);

  const daysInMonth = new Date(value.getFullYear(), value.getMonth() + 1, 0).getDate();
  const currentMonth = value.toLocaleString('en-US', { month: 'long' }).toUpperCase();
  const currentYear = value.getFullYear();

  
  const goToPreviousMonth = () => {
    const previousMonth = new Date(value.getFullYear(), value.getMonth() - 1, 1);
    setValue(previousMonth);
  };

  const goToNextMonth = () => {
    const nextMonth = new Date(value.getFullYear(), value.getMonth() + 1, 1);
    setValue(nextMonth);
  };

  const renderCalendar = () => {
    const calendar = [];

    for (let day = 1; day <= daysInMonth; day++) {
      const currentDate = new Date(value.getFullYear(), value.getMonth(), day);

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
          <RecentlyList data={recentProblems}/>
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
          <FollowingList data={followingRecentSolutions}/>
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
            <div css={css`${fontSize20}`}>알림
          </div>


            <div css={css` ${infoSytle}`}>
              <InfoList data={notifications}/>
              </div>
          </div>
        </div>
      </div>
    </div>
  )
}