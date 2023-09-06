import React, { useState } from 'react';
import { css } from '@emotion/react';
import { theme } from '../../components/Header/theme';
import { SideBar } from '../../components/SideBar/SideBar';
import { LeftBody, MainBody, RightBody } from './MainBody';
import { 
  fontSize16,
  fontSize20,
  alignCenter, 
  dateStyle,
  gridStyle
 } from './MypageStyle';

export const MyPage = () => {
  const [value, setValue] = useState(new Date());
  const [calendar, setCalendar] = useState([]);

  const startOfMonth = new Date(value.getFullYear(), value.getMonth(), 1);
  const endOfMonth = new Date(value.getFullYear(), value.getMonth() + 1, 0);
  const daysInMonth = endOfMonth.getDate();
  const currentMonth = value.toLocaleString('en-US', { month: 'long' }).toUpperCase();;

  const renderCalendar = () => {
    const calendar = [];

    for (let day = 1; day <= daysInMonth; day++) {
      const currentDate = new Date(value.getFullYear(), value.getMonth(), day);

      calendar.push(
        <div key={currentDate.toISOString()}>
          <div css={css`${dateStyle}`}>
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
    renderCalendar();
  };

  const goToNextMonth = () => {
    const nextMonth = new Date(value.getFullYear(), value.getMonth() + 1, 1);
    setValue(nextMonth);
    renderCalendar();
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
                {currentMonth}
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
            {/* 최근 풀이 3개만 불러오기 */}

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
            {/* 최근 풀이 5개만 불러오기 */}
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
            width: 365px;
            height: 565px;
            border-radius: 5px;
            margin-top: 10px;
            background-color: ${theme.colors.light4}
            `}></div>
        </div>

      </div>
    </div>
    </div>
  )
}

