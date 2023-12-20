import React, { useState, useEffect } from 'react';
import { css } from '@emotion/react';
import { theme } from '../Header/theme';
import { fontSize14, fontSize12 } from './FollowStyle';
import { Link } from 'react-router-dom';
import ProfileImg from '../../components/SolutionDetail/profile/default.png';
import axios from 'axios';

export const Following = () => {
  const [data, setData] = useState({ followings: [] });

  const token = localStorage.getItem('token');
  const api = axios.create({
    baseURL: 'http://13.125.13.131:8080/api/v1',
  });

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await api.get('/follows', {
          method: 'GET',
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });
        setData(response.data);
      } catch (error) {
        console.error(
          'Error fetching following data:',
          error
        );
      }
    };
    fetchData();
  }, [data]);

  return (
    <>
      <div
        css={css`
          width: 370px;
          height: 400px;
          background-color: ${theme.colors.light3};
          padding: 20px;
        `}
      >
        <div
          css={css`
            display: flex;
            flex-direction: column;
            width: 330px;
            height: 360px;
            overflow-y: scroll;

            &::-webkit-scrollbar {
              width: 10px;
              margin-right: 20px;
            }
            &::-webkit-scrollbar-track {
              background-color: #d9d9d9;
              border-radius: 5px;
            }
            &::-webkit-scrollbar-thumb {
              border-radius: 5px;
              background-color: #545454;
            }
          `}
        >
          {data.followings.length > 0 &&
            data.followings.map((item, index) => (
              <Link
                to={`/profile/${item.nickname}`}
                css={css`
                  text-decoration: none;
                `}
                key={index}
              >
                <div
                  css={css`
                    width: 300px;
                    max-height: 60px;
                    display: flex;
                    align-items: center;
                    justify-content: space-between;
                    margin-bottom: 10px;
                    padding-bottom: 10px;
                    border-bottom: 1px solid #959595;
                  `}
                >
                  <div
                    css={css`
                      display: flex;
                      align-items: center;
                      gap: 15px;
                    `}
                  >
                    <img
                      src={item.photo || ProfileImg}
                      alt="profileImg"
                      width="50px"
                      css={css`
                        border-radius: 50%;
                      `}
                    ></img>
                    <div
                      css={css`
                        display: flex;
                        flex-direction: column;
                      `}
                    >
                      <div
                        css={css`
                          ${fontSize12}
                        `}
                      >
                        {item.nickname}
                      </div>
                      <div
                        css={css`
                          ${fontSize14} height: 1em;
                        `}
                      >
                        {item.introduction}
                      </div>
                    </div>
                  </div>
                </div>
              </Link>
            ))}
        </div>
      </div>
    </>
  );
};
