import { navStyle, StyledLink } from './headerStyle';

export const BeforeLoginNav = () => {
  return (
    <div className="BeforeLoginNav" css={navStyle}>
      <StyledLink to="/problems">문제 보러가기</StyledLink>
      <StyledLink to="login">로그인</StyledLink>
      <StyledLink to="signUp">회원가입</StyledLink>
    </div>
  );
};
