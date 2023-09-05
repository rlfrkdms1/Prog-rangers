import React, { useState } from 'react';
import { css } from '@emotion/react';
import { theme } from '../../components/Header/theme';
import { MainBody } from './MainBody';
import 'react-calendar/dist/Calendar.css';
import { 
  fontSize16,
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
    css={css`
      display: flex; 
      justify-content: center;
  `}>    
    <div
      css={css`
        ${MainBody}
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
        </div>
      </div>
  )
}

