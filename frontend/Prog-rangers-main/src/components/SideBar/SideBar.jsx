import React from 'react';
import { css } from '@emotion/react';
import { theme } from '../Header/theme';
import { NavLink } from 'react-router-dom';
import { navStyle } from './SideBarStyle';

export const SideBar = () => {
  return (
    <div
      className="SidebarWarp"
      css={css`
        width: 180px;
        height: 100%;
        alignItems: stretch;

        border-left: 1px solid ${theme.colors.light3};
        border-right: 1px solid ${theme.colors.light3};
      `}
    >
        <div
        className="Sidebar"
        css={css`
        margin: 100px 0 690px;
        border-top: 1px solid ${theme.colors.light3};
      `}        
        >
            <NavLink to="/myPage" css={ navStyle } className='dashboard'
            style={({isActive}) => {
              return{fontWeight: isActive ? "bold" : "", backgroundColor: isActive ? "#C2DBE3":""};
              }}>대시보드</NavLink>

            <NavLink to="/account" css={ navStyle } className='account'
            style={({isActive}) => {
              return{fontWeight: isActive ? "bold" : "", backgroundColor: isActive ? "#C2DBE3":""};
              }}>계정관리</NavLink>

            <NavLink to="/accountChange" css={ navStyle } className='account'
            style={({isActive}) => {
              return{fontWeight: isActive ? "bold" : "", backgroundColor: isActive ? "#C2DBE3":"", display: isActive ? "":"none", marginTop: isActive ? "-60px":""};
              }}>계정관리</NavLink>

            <NavLink to="/mySolution" css={ navStyle } className='mysolution'
            style={({isActive}) => {
              return{fontWeight: isActive ? "bold" : "", backgroundColor: isActive ? "#C2DBE3":""};
              }}>내 풀이</NavLink>

            <NavLink to="/myComment" css={ navStyle } className='mycomment'
            style={({isActive}) => {
              return{fontWeight: isActive ? "bold" : "", backgroundColor: isActive ? "#C2DBE3":""};
              }}>내 댓글</NavLink>

            <NavLink to="/like" css={ navStyle } className='like'
            style={({isActive}) => {
              return{fontWeight: isActive ? "bold" : "", backgroundColor: isActive ? "#C2DBE3":""};
              }}>좋아요</NavLink>

            <NavLink to="/follow" css={ navStyle } className='follow'
            style={({isActive}) => {
              return{fontWeight: isActive ? "bold" : "", backgroundColor: isActive ? "#C2DBE3":""};
              }}>팔로우</NavLink>
        </div>
    </div>
  );
};