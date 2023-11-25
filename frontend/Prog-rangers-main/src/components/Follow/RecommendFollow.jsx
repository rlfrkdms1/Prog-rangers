import React, { useState, useEffect, useRef } from 'react';
import { css } from '@emotion/react';
import { theme } from '../Header/theme';
import { buttonSytle, fontSize14, fontSize12 } from './FollowStyle';
import axios from "axios";
import ProfileImg from '../../components/SolutionDetail/profile/default.png';
import { useNavigate } from 'react-router-dom';

export const RecommendFollow = () => {
  // 추천 팔로우
  const [followStatus, setFollowStatus] = useState({});
  const [data, setData] = useState({ recommends: [] });

    useEffect(() => {
        const apiUrl = 'http://13.124.131.171:8080/api/v1/follows';
        const token = localStorage.getItem('token');
        
        axios.get(apiUrl, {
            method: "GET",
            headers: {Authorization: `Bearer ${token}`},
          })
          .then((response) => {
            setData(response.data);

            const initialFollowStatus = {};
            response.data.recommends.forEach(item => {
              initialFollowStatus[item.id] = false;
            });
            setFollowStatus(initialFollowStatus);
          })
          .catch((error) => {
            console.error('API 요청 오류:', error);
          });
      }, []);
      
  // 가로 스크롤    
  const containerRef = useRef(null);
  const [startX, setStartX] = useState(null);
  const [isMouseDown, setIsMouseDown] = useState(false);

  const handleMouseDown = (e) => {
    setIsMouseDown(true);
    setStartX(e.clientX);
    containerRef.current.style.scrollBehavior = 'initial';
  };

  const handleMouseUp = () => {
    setIsMouseDown(false);
    containerRef.current.style.scrollBehavior = 'smooth';
  };

  const handleMouseMove = (e) => {
    if (isMouseDown) {
      if (startX !== null) {
        const scrollX = startX - e.clientX;
        containerRef.current.scrollLeft += scrollX;
        setStartX(e.clientX);
      }
    }
  };
  
  const handleMouseLeave = () => {
    if (isMouseDown) {
      setIsMouseDown(false);
      containerRef.current.style.scrollBehavior = 'smooth';
    }
  };

  // 팔로우 기능
  const [followedAccounts, setFollowedAccounts] = useState([]);
  const [recommendedAccounts, setRecommendedAccounts] = useState(data.recommends);
  const handleFollowClick = (item) => {    
    const memberId = item.id;
    const token = localStorage.getItem('token');

    fetch(`http://13.124.131.171:8080/api/v1/members/${memberId}/following`, {
      method: followStatus[memberId] ? 'DELETE' : 'POST',
      headers: { Authorization: `Bearer ${token}`, },
    })
    .then(response => {
      const updatedFollowStatus = { ...followStatus };
        updatedFollowStatus[memberId] = !followStatus[memberId];
        setFollowStatus(updatedFollowStatus);
        if (followStatus[memberId]) {
          console.log('언팔로우 성공');
        } else {
          console.log('팔로우 성공');
        }        
    })
    .catch(error => {
      console.error('API 요청 실패', error);
    });

      // 클릭한 계정이 이미 팔로우한 계정 목록에 있는지 확인
      const isAlreadyFollowed = followedAccounts.some(account => account.id === item.id);

      // 팔로우한 계정 목록을 업데이트
      if (isAlreadyFollowed) {
        // 이미 팔로우한 경우, 해당 계정을 제거
        const updatedFollowedAccounts = followedAccounts.filter(account => account.id !== item.id);
        setFollowedAccounts(updatedFollowedAccounts);
      } else {
        // 아직 팔로우하지 않은 경우, 해당 계정을 추가
        const updatedFollowedAccounts = [...followedAccounts, item];
        setFollowedAccounts(updatedFollowedAccounts);
      }

      // 팔로우 버튼 클릭 시 팔로우 상태를 토글
      const updatedRecommendedAccounts = recommendedAccounts.map(account => {
        if (account.id === item.id) {
          return {
            ...account,
            isFollowing: !isAlreadyFollowed,
          };
        }
        return account;
      });

      setRecommendedAccounts(updatedRecommendedAccounts);
  };

  const navigate = useNavigate();
    const onClickName = (nickname) => {
      navigate(`/profile/${nickname}`); 
    };  

  return (
    <div ref = {containerRef} 
      css={css`width: 800px; height: 90px; background-color:  ${theme.colors.light3}; padding: 20px; overflow-x: scroll; overflow-y: hidden; white-space: nowrap; &::-webkit-scrollbar {display: none;}
      &::-webkit-user-select:none;
      &::-moz-user-select:none;
      &::-ms-user-select:none;
      user-select: none;`}
      onMouseDown={handleMouseDown}
      onMouseUp={handleMouseUp}
      onMouseMove={handleMouseMove}
      onMouseLeave={handleMouseLeave}
      >

    {data.recommends.map((item, index) => (
    <div key={index} 
        css={css`display: inline-flex; flex-direction: row;`}>
    <div css={css`width:252px; max-height: 50px; display: flex; align-items: center; justify-content: space-between; margin-right:20px; border-right: 1px solid #959595;`}>           
        <div css={css`display: flex; align-items: center; gap: 15px;`}>
        <img
            src= {item.photo || ProfileImg}                 
            alt='profileImg'
            width='50px'
            css={css`
            border-radius: 50%;
            `}></img>
        <div css={css`display: flex; flex-direction: column; gap: 4px;`}>
            <div onClick={()=>onClickName(item.nickname)} css={css`${fontSize12} cursor: pointer;`}>{item.nickname}</div>
            <div css={css`width: 165px; display: flex; justify-content: space-between;`}>
            <div css={css`${fontSize14} `}>{item.introduction}</div>
            <button onClick={() => handleFollowClick(item)}
                    css={css`${buttonSytle} background-color:${followStatus[item.id] ? theme.colors.light2 : theme.colors.main30}`}>
                     {followStatus[item.id] ? '팔로잉' : '팔로우'}</button>
            </div>
        </div>
        </div>
    </div>
    </div>
))}
    </div>

  );
}
