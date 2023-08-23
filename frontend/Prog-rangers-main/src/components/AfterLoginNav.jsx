import { Link } from 'react-router-dom';
import { navStyle, linkDefault } from '../styles/headerStyle';

const AfterLoginNav = () => {
  return (
    <div className="AfterLoginNav" css={navStyle}>
      <Link to="problems" css={linkDefault}>
        풀이 보러가기
      </Link>
      <Link to="registerReview" css={linkDefault}>
        풀이 쓰러가기
      </Link>
      <Link to="myPage" css={linkDefault}>
        마이페이지
      </Link>
    </div>
  );
};

export default AfterLoginNav;
