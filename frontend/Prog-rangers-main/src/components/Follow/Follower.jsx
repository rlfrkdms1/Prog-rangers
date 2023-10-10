import React, { useState, useEffect } from 'react';
import { css } from '@emotion/react';
import { theme } from '../Header/theme';
import { fontSize14, fontSize12 } from './FollowStyle';
import axios from "axios";

export const Follower = () => {
  const [data, setData] = useState({ followers: [] });

    useEffect(() => {
        const token = localStorage.getItem('token');
        const apiUrl = 'http://13.124.131.171:8080/api/v1/mypage/follows';
        

        axios.get(apiUrl, {
            method: "GET",
            headers: {Authorization: `Bearer ${token}`},
          })
          .then((response) => {
            setData(response.data);
          })
          .catch((error) => {
            console.error('API 요청 오류:', error);
          });
      }, []);

return(
    <>
    {data.followers.map((item, index) => (
        <div key={index} 
            css={css`
                     width: 370px; height: 400px; 
                     background-color: ${theme.colors.light3}; 
                     padding: 20px;
                     `}>
        <div css={css`display: flex; flex-direction: column; 
                     width: 330px; height: 360px;
                     overflow-y: scroll;
                     
                     &::-webkit-scrollbar {width: 10px; margin-right:20px;}
                     &::-webkit-scrollbar-track {
                        background-color: #D9D9D9;
                        border-radius: 5px;
                      }
                      &::-webkit-scrollbar-thumb {
                        border-radius: 5px;
                        background-color: #545454;
                      }
                     `}>
        
        <div css={css`width:300px; max-height: 60px; display: flex; align-items: center; justify-content: space-between; margin-bottom: 10px; padding-bottom: 10px; border-bottom: 1px solid #959595;`}>           
            <div css={css`display: flex; align-items: center; gap: 15px;`}>
            <img
                src= {item.photo || 'https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png'}                 
                alt='profileImg'
                width='50px'
                css={css`
                border-radius: 50%;
                `}></img>
            <div css={css`display: flex; flex-direction: column;`}>
                <div css={css`${fontSize12}`}>{item.nickname}</div>
                <div css={css`${fontSize14}`}>{item.introduction}intro</div>
            </div>
            </div>
        </div>
        </div>
        </div>
    ))}
    </>
)
}

