import React, { useState } from 'react';
import { css } from '@emotion/react';
import { theme } from '../../components/Header/theme';
import { LeftBody, MainBody, RightBody } from './MainBody';
import 'react-calendar/dist/Calendar.css';
import { 
  fontSize16,
  fontSize20,
  alignCenter, 
  dateStyle,
  gridStyle
 } from './MypageStyle';

export const MyPage = () => {
  const [value, onChange] = useState(new Date());

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

  return(
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
            ${[fontSize16, alignCenter]} 
            margin-bottom: 10px`}>
              {currentMonth}
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
          `}></div>
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
  )
}

