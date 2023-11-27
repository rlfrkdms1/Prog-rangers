// 마이페이지 대시보드 알림창

import React, { useEffect, useState } from 'react';
import { css } from "@emotion/react";
import { theme } from '../Header/theme';
import { useNavigate } from 'react-router-dom';
import { 
    fontSize14,
    fontSizeBold14
} from '../../pages/MyPage/MyPageStyle'

export const InfoList = ({ data, loading, isLogin, queryClient }) => {
  const navigate = useNavigate();

  const onClickSols = (solutionId) => {
    navigate(`/solutions/${solutionId}`);
  };
  
  // SSE 알림 구독
  const [newAlarms, setNewAlarms] = useState(false);
  const [scrolling, setScrolling] = useState(false);

  useEffect(() => {
    if (loading && isLogin) {
      const eventSource = new EventSource(
        'http://13.124.131.171:8080/api/v1/notifications',
        {
          headers: {
            'Accept': 'text/event-stream'
          }
        }
      );

      const scrollHandler = () => {
        // 스크롤 위치 확인
        const scrollY = window.scrollY || document.documentElement.scrollTop;
        const windowHeight = window.innerHeight;
        const documentHeight = document.documentElement.scrollHeight;

        // 스크롤이 끝에 도달하면 SSE 다시 구독 요청
        if (scrollY + windowHeight >= documentHeight - 100) {
          if (!scrolling) {
            setScrolling(true);
            eventSource.close();

            // SSE 다시 구독 요청
            const newEventSource = new EventSource(
              'http://13.124.131.171:8080/api/v1/notifications/subscribe',
              {
                headers: {
                  'Accept': 'text/event-stream'
                }
              }
            );
            newEventSource.onmessage = async (event) => {
              const response = await event.data;
              if (!response.includes("EventStream Created.")) setNewAlarms(true);
              queryClient.invalidateQueries("infoList");
            };
            newEventSource.onerror = async (event) => {
              if (!event.error.message.includes("No activity")) {
                newEventSource.close();
              }
            };
            setScrolling(false);
          }
        }
      };

      window.addEventListener('scroll', scrollHandler);

      return () => {
        eventSource.close();
        window.removeEventListener('scroll', scrollHandler);
      };
    }
  }, [loading, isLogin]);

  // 알림 내용이 넘칠 시 각각의 알림창 css 조절
  const [content, setContent] = useState(''); 
  const [containerHeight, setContainerHeight] = useState('auto');

  
  useEffect(() => {
    const container = document.getElementById('autoResize');
    if (container) {
      // 높이 자동 조절
      setContainerHeight('auto');
      // 내용이 변경될 때마다 높이 다시 계산
      const timeoutId = setTimeout(() => {
        setContainerHeight(`${container.scrollHeight}px`);
      }, 0);
      return () => clearTimeout(timeoutId);
    }
  }, [content]);

  
  return(
    
    data.map((item) => (
    <div key={item.solutionId}>
      <div 
      onClick={(e) => onClickSols(item.solutionId)}
      css={css`
              width: 345px;
              height: containerHeight;
              margin-left: 10px;
              background-color: ${ item.read ? `${theme.colors.light3}` : `${theme.colors.main30}` };
              `}>
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
            {item.type}
        </div>
        <div css={css`
            margin: 5px 12px 0 12px;
            padding-bottom: 10px;
            ${fontSize14}

            &:hover{
              cursor: pointer;
              text-decoration: underline;
            }
            `}>
              <span css={css`font-weight: 700;`}>
                {item.nickname} </span>
                님이 {' '}
              <span css={css` font-weight: 700;`}>
                {item.solutionTitle} </span>
                풀이에 {item.type}을 남겼습니다 : {item.content}
                </div>
            </div>
      </div>
    )
  ));
}