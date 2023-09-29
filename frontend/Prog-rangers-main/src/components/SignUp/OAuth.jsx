export const KAKAO_CLIENT_ID = process.env.REACT_APP_KAKAO_CLIENT_ID;
export const KAKAO_REDIRECT_URI = "http://localhost:3000/login/kakao"
export const KAKAO_AUTH_URL = `https://kauth.kakao.com/oauth/authorize?client_id=${KAKAO_CLIENT_ID}&redirect_uri=${KAKAO_REDIRECT_URI}&response_type=code`;
 
export const NAVER_CLIENT_ID = process.env.REACT_APP_NAVER_CLIENT_ID;
export const NAVER_REDIRECT_URI = "http://localhost:3000/login/naver"
export const NAVER_STATE_STRING = process.env.REACT_APP_NAVER_STATE_STRING;
export const NAVER_AUTH_URL = `https://nid.naver.com/oauth2.0/authorize?response_type=code&amp;client_id={NAVER_CLIENT_ID}&amp;state={NAVER_STATE_STRING}&amp;redirect_uri={NAVER_REDIRECT_URI}`;
