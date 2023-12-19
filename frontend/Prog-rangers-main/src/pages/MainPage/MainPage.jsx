import React, { useRef, useEffect } from 'react';
import { css } from '@emotion/react';
import { theme } from '../../components/Header/theme';
import { Link } from 'react-router-dom';
import {
  secLight,
  secMain,
  secLayout,
  fontSize26,
  fontSize40,
  fontSize50,
  fontSize70,
  alignCenter,
  flexRowLayout,
  flexColLayout,
  buttonSytle,
} from './MainStyle';
import { ScrollDownArrow } from '../../components/Home/ScrollDownArrow';
import Sec2Mockup from '../../assets/main-sec2.png';
import Sec3Mock1 from '../../assets/main-sec3-1.png';
import Sec3Mock2 from '../../assets/main-sec3-2.jpg';
import {
  bookPosition,
  chatPosition,
  commentsPosition,
  folderPosition,
  notePosition,
  penPosition,
} from './MainPageIcons';
import IconPen from '../../assets/icons/main-pen.svg';
import IconFolder from '../../assets/icons/main-folder.svg';
import IconNote from '../../assets/icons/main-note-n-pen.svg';
import IconCommets from '../../assets/icons/main-comments.svg';
import IconChat from '../../assets/icons/main-chat.svg';
import IconBook from '../../assets/icons/main-openbook.svg';

// aos
import AOS from 'aos';
import 'aos/dist/aos.css';

const displayBlock = css`
  display: block;
`;

 const MainPage = () => {
  const element = useRef();
  const moveToElement = () => {
    element.current.scrollIntoView({ behavior: 'smooth' });
  };

  useEffect(() => {
    AOS.init();
  });

  return (
    <div className="wrap">
      <div
        className="secWrap"
        css={css`
          ${secLight}
          height: calc(100vh - 100px);
        `}
      >
        <div
          className="sec sec1"
          css={css`
            ${secLayout}
            height: 100%;
            position: relative;
          `}
        >
          <div className="icon">
            <img
              src={IconPen}
              alt="pen icon"
              css={penPosition}
            />
            <img
              src={IconFolder}
              alt="folder icon"
              css={folderPosition}
            />
          </div>
          <div
            className="secTitle"
            css={css`
              ${alignCenter}
              width: 100%;
              position: absolute;
              top: 50%;
              left: 50%;
              transform: translate(-50%, -50%);
            `}
          >
            <div className="title" css={fontSize70}>
              코딩테스트 리뷰 서비스
            </div>
            <div
              className="subtitle"
              css={css`
                ${fontSize50}
                color: ${theme.colors.main}
              `}
            >
              Prog-rangers
            </div>
          </div>
          <button onClick={moveToElement}>
            <ScrollDownArrow />
          </button>
        </div>
      </div>
      <div
        className="secWrap"
        css={css`
          ${secMain}
        `}
      >
        <div
          className="sec sec2"
          ref={element}
          css={css`
            ${flexRowLayout}
            ${secLayout}
            position: relative;
          `}
        >
          <div className="icon">
            <img
              src={IconNote}
              alt="note icon"
              css={notePosition}
            />
          </div>
          <div
            className="sec2Left"
            css={css`
              padding-top: 52vh;
              padding-left: 30px;
            `}
          >
            <div
              className="title"
              css={css`
                padding-bottom: 50px;
                ${fontSize50}
              `}
            >
              코딩테스트 문제를
              <span css={displayBlock}>풀이해봐요</span>
            </div>
            <div className="description" css={fontSize26}>
              <span>
                나만의 방법으로 푼 문제를 올려보세요.
              </span>
              <span css={displayBlock}>
                차곡차곡 모으면
              </span>
              <span>나만의 노트가 될 거예요.</span>
            </div>
          </div>
          <div className="sec2Right">
            <img
              src={Sec2Mockup}
              alt="사이트 문제 목록 페이지 목업"
              css={css`
                width: 650px;

                padding-top: 100px;
              `}
              data-aos="fade-up"
              data-aos-anchor-placement="center-center"
              data-aos-duration="1800"
            />
          </div>
        </div>
      </div>
      <div className="secWrap" css={secLight}>
        <div
          className="sec sec3"
          css={css`
            height: 100%;
            position: relative;
            ${secLayout};
            ${flexRowLayout};
          `}
        >
          <div className="icon">
            <img
              src={IconCommets}
              alt="comments icon"
              css={commentsPosition}
            />
          </div>
          <div
            className="sec3Left"
            css={css`
              width: 50%;
              ${flexColLayout}
            `}
          >
            <div className="imgArea">
              <img
                src={Sec3Mock1}
                alt="다른 사람의 프로필 보기 페이지 목업"
                css={css`
                  width: 500px;
                `}
                data-aos="fade-up"
                data-aos-anchor-placement="center-center"
                data-aos-duration="1800"
              />
            </div>
            <div
              className="description"
              css={css`
                margin-bottom: 60px;
                margin-left: -20px;
                ${fontSize26}
              `}
            >
              <span>
                다른 사람의 풀이에 댓글을 달아보세요.
              </span>
              <span css={displayBlock}>
                서로 이야기하면서
              </span>
              <span>
                코드에 대한 이해를 높일 수 있어요.
              </span>
            </div>
          </div>
          <div
            className="sec3Right"
            css={css`
              width: 50%;
              ${flexColLayout};
            `}
          >
            <div
              className="title"
              css={css`
                padding-top: 100px;
                margin-bottom: 50px;
                ${fontSize50};
              `}
            >
              <span>다른 사람들에게</span>
              <span css={displayBlock}>
                내 풀이를{' '}
                <i
                  css={css`
                    color: ${theme.colors.main};
                  `}
                >
                  공유
                </i>
                하고
              </span>
              <span>
                함께{' '}
                <i
                  css={css`
                    color: ${theme.colors.main};
                  `}
                >
                  소통
                </i>
                해요
              </span>
            </div>
            <div className="imgArea">
              <img
                src={Sec3Mock2}
                alt="풀이 상세보기 페이지 목업"
                css={css`
                  width: 500px;
                  margin-bottom: 100px;
                `}
                data-aos="fade-up"
                data-aos-anchor-placement="top-center"
                data-aos-duration="1800"
              />
            </div>
          </div>
        </div>
      </div>
      <div
        className="secWrap"
        css={css`
          ${secMain};
          height: 510px;
        `}
      >
        <div
          className="sec sec4"
          css={css`
            height: 100%;
            position: relative;
            ${secLayout};
            ${flexColLayout};
            justify-content: space-evenly;
          `}
        >
          <div className="icon">
            <img
              src={IconChat}
              alt="chat icon"
              css={chatPosition}
            />
            <img
              src={IconBook}
              alt="book icon"
              css={bookPosition}
            />
          </div>
          <div
            className="ctaDesc"
            css={css`
              text-align: center;
              ${fontSize40}
            `}
          >
            <span>다른 사람들과 함께 소통하며</span>
            <span css={displayBlock}>즐겁게 공부해요</span>
          </div>
          <div className="ctaBtnArea">
            <Link to="problems">
              <button css={buttonSytle}>
                Prog-rangers 서비스 시작하기
              </button>
            </Link>
          </div>
        </div>
      </div>
    </div>
  );
};

export default MainPage;
