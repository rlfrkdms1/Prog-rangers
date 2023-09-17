export const KAKAO_CLIENT_ID = process.env.KAKAO_CLIENT_ID;
export const KAKAO_REDIRECT_URI = "http://localhost:3000/signin/kakao"
export const KAKAO_AUTH_URL = `https://kauth.kakao.com/oauth/authorize?client_id=${KAKAO_CLIENT_ID}&redirect_uri=${KAKAO_REDIRECT_URI}&response_type=code`;
 