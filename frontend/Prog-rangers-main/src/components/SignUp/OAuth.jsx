export const KAKAO_CLIENT_ID = process.env.REACT_APP_KAKAO_CLIENT_ID;
export const KAKAO_REDIRECT_URI = "http://localhost:3000/login/kakao"
export const KAKAO_AUTH_URL = `https://kauth.kakao.com/oauth/authorize?client_id=${KAKAO_CLIENT_ID}&redirect_uri=${KAKAO_REDIRECT_URI}&response_type=code`;
 
export const NAVER_CLIENT_ID = process.env.REACT_APP_NAVER_CLIENT_ID;
export const NAVER_REDIRECT_URI = "http://localhost:3000/login/naver"
// export const NAVER_STATE = new BigInteger(130, random).toString();
// export const NAVER_AUTH_URL =`ttps://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=${NAVER_CLIENT_ID}&redirect_uri=${NAVER_REDIRECT_URI}&state=${NAVER_STATE}`