export const KAKAO_CLIENT_ID = process.env.REACT_APP_KAKAO_CLIENT_ID;
export const KAKAO_REDIRECT_URI = "http://13.125.13.131/login/kakao"
export const KAKAO_AUTH_URL = `https://kauth.kakao.com/oauth/authorize?client_id=${KAKAO_CLIENT_ID}&redirect_uri=${KAKAO_REDIRECT_URI}&response_type=code`;
 
export const NAVER_CLIENT_ID = process.env.REACT_APP_NAVER_CLIENT_ID;
export const NAVER_REDIRECT_URI = "http://13.125.13.131/login/naver"
export const NAVER_STATE_STRING = "test_state"
export const NAVER_AUTH_URL = `https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=${NAVER_CLIENT_ID}&state=${NAVER_STATE_STRING}&redirect_uri=${NAVER_REDIRECT_URI}`;

export const GOOGLE_CLIENT_ID = process.env.REACT_APP_GOOGLE_CLIENT_ID;
export const GOOGLE_REDIRECT_URI = "http://13.125.13.131/login/google";
export const GOOGLE_SECRET_ID = process.env.REACT_APP_GOOGLE_SECRET_ID;
export const GOOGLE_SCOPE = "openid email profile";
export const GOOGLE_AUTH_URL = `https://accounts.google.com/o/oauth2/v2/auth?client_id=${GOOGLE_CLIENT_ID}&redirect_uri=${GOOGLE_REDIRECT_URI}&response_type=code&scope=${GOOGLE_SCOPE}`;
