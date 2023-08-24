import React from 'react';
import { css } from '@emotion/react';
import { theme } from '../styles/theme';
import { Link } from 'react-router-dom';

import Logo from '../assets/logo.svg';
import { IoSearchOutline } from 'react-icons/io5';

// import BeforeLoginNav from './BeforeLoginNav';
import AfterLoginNav from './AfterLoginNav';

const flexAlign = css`
  display: flex;
  align-items: center;
`;

const Header = () => {
  return (
    <div
      className="NavbarWarp"
      css={css`
        width: 100%;
        height: 120px;

        border-bottom: 1px solid ${theme.colors.light2};
      `}>
      <div
        className="Navbar"
        css={css`
          width: 1200px;
          margin: 0 auto;
          padding: 20px;

          ${flexAlign};
        `}>
        <Link to="/">
          <img src={Logo} alt="Prog-rangers" />
        </Link>
        <div
          className="searchAll"
          css={css`
            ${flexAlign}

            margin-left: 50px;
          `}>
          <div
            className="search"
            css={css`
              width: 500px;
              height: 50px;

              border: 1px solid ${theme.colors.dark1};
              border-radius: 25px;

              padding-left: 20px;
            `}>
            <input
              type="text"
              placeholder="문제 제목을 검색해보세요!"
              css={css`
                outline: none;
                border: none;

                width: 420px;
              `}
            />
            <button
              type="submit"
              css={css`
                line-height: 50px;
              `}>
              <IoSearchOutline
                size="25"
                color="#303030"
                css={css`
                  vertical-align: middle;
                  margin-left: 10px;
                `}
              />
            </button>
          </div>
          <Link to="/problems">
            <button
              type="button"
              css={css`
                width: 100px;
                height: 50px;

                border: 1px solid ${theme.colors.main};
                border-radius: 25px;

                color: ${theme.colors.main};

                margin-left: 10px;
              `}>
              상세검색
            </button>
          </Link>
        </div>
        <div
          css={css`
            flex-grow: 1;
          `}>
          <AfterLoginNav />
        </div>
      </div>
    </div>
  );
};

export default Header;
