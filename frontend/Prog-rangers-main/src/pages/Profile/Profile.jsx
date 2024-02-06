import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { css } from '@emotion/react';
import { theme } from '../../components/Header/theme';
import { LeftBody, MainBody, RightBody } from './MainBody';
import axios from 'axios';
import { SolvingList } from '../../components/Profile/SolvingList';
import github from '../../assets/icons/github-mark.svg';
import achievemark from '../../assets/icons/achieve-mark.svg';
import ProfileImg from '../../components/SolutionDetail/profile/default.png';
import {
  alignCenter,
  fontSize14,
  fontSizebold16,
  fontSize20,
  fontSize24,
  badgeStyle
} from './ProfileStyle';

import plant from '../../assets/badge/plant-pixel.png';
import leaf from '../../assets/badge/stem-and-leaf-pixel.png';
import bud from '../../assets/badge/bud-pixel.png';
import flower from '../../assets/badge/badge-flower-pixel.png';
import flowerbed from '../../assets/badge/badge-flowerbed-pixel.png';
import field from '../../assets/badge/field.png';

const Profile = () => {
  const navigate = useNavigate();
  const { nickname } = useParams();
  const [id, setId ] = useState([]);
  const [data, setData] = useState([]);
  const [cursor, setCursor] = useState('');

  const [myNickname] = useState(
    localStorage.getItem('nickname')
  );
  const [isMine, setIsMine] = useState(false);

  //팔로우 버튼
  const [isFollowing, setIsFollowing] = useState(false);
  const buttonColor = isFollowing
    ? theme.colors.light3
    : theme.colors.main30;

  const [followData, setFollowData] = useState({ followings: [] });

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
        setFollowData(response.data);
        const isFollowingProfile = response.data.followings.some(followings => followings.nickname === nickname);
        setIsFollowing(isFollowingProfile);
      } catch (error) {
        console.error('Error fetching following data:', error);
      }
    };
    fetchData();
  }, [nickname, token]);

  const handleFollowButton = () => {
    fetch(
      `http://13.125.13.131:8080/api/v1/members/${id}/following`,
      {
        method: isFollowing ? 'DELETE' : 'POST',
        headers: { Authorization: `Bearer ${token}` },
      }
    )
      .then((response) => {
        if (!token) {
          alert('로그인 후 이용해주세요');
        }
        else {
          setIsFollowing(!isFollowing);
          if (isFollowing) {
            console.log('언팔로우 성공');
          } else {
            console.log('팔로우 성공');
          }
        }
      })
      .catch((error) => {
        console.error('API 요청 실패', error);
      });
    }

    // 달성 뱃지
    const badgeImages = {
      새싹: plant,
      잎과줄기: leaf,
      꽃봉오리 : bud,
      꽃: flower,
      화단: flowerbed,
      들판: field
    };
  
  // 무한 스크롤 기능
  const handleScroll = () => {
    const isBottom = window.scrollY + window.innerHeight >= document.documentElement.scrollHeight;
    if (isBottom && cursor !== null && cursor !== -1) {
      const apiUrl = `http://13.125.13.131:8080/api/v1/members/${nickname}?page=${cursor}`;


      axios.get(apiUrl)
      .then((response) => {
        if (Array.isArray(response.data.list)) {
          // 중복 방지
          setData((prevData) => {
            const newList = response.data.list.filter((newItem) => {
              return !prevData.list.some((prevItem) => prevItem.solution.id === newItem.solution.id);
            });
            return { ...prevData, list: [...prevData.list, ...newList] };
          });

          setCursor(response.data.cursor);
        }
      });
    }
  };
    
  useEffect(() => {
    handleScroll();
  
    // 스크롤 이벤트 리스너
    const scrollListener = () => {
      handleScroll();
    };

    window.addEventListener('scroll', scrollListener);
    
    return () => {
      window.removeEventListener('scroll', scrollListener);
    };
  }, [cursor, nickname]);

  useEffect(() => {
    const apiUrl = `http://13.125.13.131:8080/api/v1/members/${nickname}`;

    axios
      .get(apiUrl)
      .then((response) => {
        setData(response.data);
        setId(response.data.id);
        if(response.data.nickname === myNickname) setIsMine(true);
      })
      .catch((error) => {
        console.error('API 요청 오류:', error);
        alert(
          '존재하지 않는 사용자의 프로필을 방문할 수 없습니다.'
        );
        navigate(-1);
      });
    }, [isFollowing]);

  return (
    <div
      css={css`
        ${MainBody}
      `}
    >
      <div
        css={css`
          ${LeftBody}
        `}
      >
        <div
          css={css`
            width: 250px;
            height: 250px;
            border-radius: 250px;
            object-fit: cover;
            border: 1px solid black;
          `}
        >
          <img
            src={data.photo || ProfileImg}
            alt="profileImg"
          ></img>
        </div>

        <div
          css={css`
            ${fontSize20}
            ${alignCenter}
          margin-top: 8px;
            color: ${data.nickname === '탈퇴한 사용자'
              ? '#D9D9D9'
              : '#000000'};
          `}
        >
          {data.nickname}
        </div>

        <div
          css={css`
            ${fontSize14}
            ${alignCenter}
          margin-top: 7px;
          `}
        >
          {data.introduction}
        </div>

        <div css={css`display: ${isMine ? 'none' : 'block'};`}>
          <button
            onClick={handleFollowButton}
            css={css`
              width: 245px;
              height: 40px;
              border-radius: 25px;
              margin-top: 10px;
              ${fontSize20}
              ${alignCenter}
              background-color: ${buttonColor}
            `}
          >
            {isFollowing ? '팔로잉' : '팔로우'}
          </button>
        </div>

        <div
          css={css`
            display: flex;
            margin-top: 10px;
            justify-content: center;
            gap: 10px;
          `}
        >
          <div
            css={css`
              ${fontSizebold16}
            `}
          >
            {' '}
            팔로우{' '}
          </div>
          <div
            css={css`
              font-weight: 700;
            `}
          >
            {' '}
            {data.follow}{' '}
          </div>

          <div
            css={css`
              ${fontSizebold16}
              margin-left: 15px;
            `}
          >
            {' '}
            팔로잉{' '}
          </div>
          <div
            css={css`
              font-weight: 700;
            `}
          >
            {' '}
            {data.following}{' '}
          </div>
        </div>

        <div
          css={css`
            display: flex;
            margin-top: 30px;
            gap: 20px;
            align-items: center;
          `}
        >
          
          <div
            css={css`
              width: 30px;
              height: 30px;
            `}
          >
            <img src={github} alt="GitHub Logo" />
          </div>

          {data.github == null ? (
          <div
            css={css`
              font-size: 16px;
              font-weight: 400;
              color: ${theme.colors.light1};
            `}
          >
            ...
          </div>
            ) : (
              <div
            css={css`
              font-size: 16px;
              font-weight: 400;
              color: ${theme.colors.light1};
            `}
          >
            {data.github}
            </div>
            )}

        </div>

        <div
          css={css`
            width: 250px;
            margin-top: 105px;
            margin-bottom: 5px;
            border: 1px solid ${theme.colors.light2};
          `}
        ></div>

        <div
          css={css`
            display: flex;
            align-items: center;
            gap: 10px;
            ${fontSize20}
          `}
        >
          <div
            css={css`
              margin-top: 5px;
            `}
          >
            <img src={achievemark} alt="achieve_mark" />
          </div>
          달성
        </div>
        {badgeImages[data.badge] && (
          <img
            src={badgeImages[data.badge]}
            alt={`${data.badge}`}            
            css={css`${badgeStyle}`}
          />
        )}
      </div>

      <div
        css={css`
          ${RightBody}
        `}
      >
        <div
          css={css`
            ${fontSize24}
          `}
        >
          풀이
        </div>

        {data.list && data.list.length === 0 ? (
          <div
            css={css`
              font-size: 18px;
              font-weight: 500;
              margin-top: 50px;
              color: ${theme.colors.light1};
            `}
          >
            풀이 없음
          </div>
        ) : (
        <SolvingList list={data.list} />
        )}
      
      </div>
    </div>
  );
};

export default Profile;