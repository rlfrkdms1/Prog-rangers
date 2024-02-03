import React, {
  useContext,
  useEffect,
  useState,
} from 'react';
import { css } from '@emotion/react';
import { theme } from './theme';
import { Link } from 'react-router-dom';
import Logo from '../../assets/logo.svg';
import { IoSearchOutline } from 'react-icons/io5';
import { AfterLoginNav } from './AfterLoginNav';
import { BeforeLoginNav } from './BeforeLoginNav';
import { useIsLoginState } from '../../context/AuthContext';
import axios from 'axios';
import { SearchContext } from '../../context/SearchContext';
import { useNavigate } from 'react-router-dom';

const flexAlign = css`
  display: flex;
  align-items: center;
`;
// 수정

export const Header = () => {
  const isLogin = useIsLoginState();
  const [data, setData] = useState([]);
  const [search, setSearch] = useState('');
  const [showDropdown, setShowDropdown] = useState(false);
  const [results, setResults] = useState([]);

  useEffect(() => {
    const fetchData = async () => {
      const result = await axios.get(
        'http://13.125.13.131:8080/api/v1/problems'
      );
      setData(result.data.problems);
    };

    fetchData();
  }, []);

  const { setSearchTerm } = useContext(SearchContext);

  const navigate = useNavigate();

  const handleSearch = (event) => {
    event.preventDefault();

    const filteredData = data.filter((item) => {
      return item.title
        .toLowerCase()
        .includes(search.toLocaleLowerCase());
    });
    // setData(filteredData);
    setResults(filteredData);
    setSearchTerm(search);
    navigate('/problems');
    setShowDropdown(false);
  };

  const handleChange = (event) => {
    setSearch(event.target.value);
    setSearchTerm(event.target.value);
  };

  const handleReset = (event) => {
    setSearch('');
    setSearchTerm('');
    setResults([]);
    navigate('/problems');
    setShowDropdown(false);
  };

  useEffect(() => {
    const handleClickOutside = (event) => {
      if (!event.target.closest('.searchAll')) {
        setShowDropdown(false);
      }
    };

    document.addEventListener('click', handleClickOutside);

    return () => {
      document.removeEventListener(
        'click',
        handleClickOutside
      );
    };
  }, []);

  return (
    <div
      className="NavbarWarp"
      css={css`
        width: 100%;
        height: 100px;

        border-bottom: 1px solid ${theme.colors.light2};
      `}
    >
      <div
        className="Navbar"
        css={css`
          width: 1200px;
          margin: 0 auto;
          padding: 15px;

          ${flexAlign};
        `}
      >
        <Link to="/">
          <img
            src={Logo}
            alt="Prog-rangers"
            css={css`
              height: 65px;
            `}
            onClick={() => setShowDropdown(false)}
            className="navbar-logo"
          />
        </Link>
        <div
          className="searchAll"
          css={css`
            ${flexAlign}

            margin-top: 5px;
            margin-left: 30px;
          `}
        >
          <div
            className="search"
            css={css`
              width: 500px;
              height: 50px;

              border: 1px solid ${theme.colors.dark1};
              border-radius: 25px;

              padding-left: 20px;
            `}
            onClick={(event) => {
              event.stopPropagation(); // 클릭 이벤트의 버블링 중단
              setShowDropdown(false);
            }}
          >
            <form onSubmit={handleSearch}>
              <input
                type="text"
                placeholder="문제 제목을 검색해보세요!"
                value={search}
                onChange={handleChange}
                css={css`
                  outline: none;
                  border: none;

                  width: 420px;
                `}
              />
              <button
                type="button"
                onClick={handleReset}
                css={css`
                  line-height: 50px;
                `}
                className="search-button"
              >
                <IoSearchOutline
                  size="25"
                  color="#303030"
                  css={css`
                    vertical-align: middle;
                    margin-left: 10px;
                  `}
                  className="search-icon"
                />
              </button>
            </form>
          </div>
          {/* <Link to="/problems">
            <button
              type="button"
              onClick={handleReset}
              css={css`
                width: 100px;
                height: 50px;

                border: 1px solid ${theme.colors.main};
                border-radius: 25px;

                color: ${theme.colors.main};

                margin-left: 10px;
              `}
              className="advanced-search"
            >
              상세검색
            </button>
          </Link> */}
        </div>
        <div
          css={css`
            flex-grow: 1;

            margin-top: 5px;
          `}
        >
          {isLogin ? <AfterLoginNav /> : <BeforeLoginNav />}
        </div>
      </div>
    </div>
  );
};
