import axios from 'axios';

const duplicationApiUrl = 'http://{{HOST}}:8080/prog-rangers/check-nickname-duplication?nickname=test';
const inquireApiUrl = 'http://{{HOST}}:8080/prog-rangers/mypage/account-settings'; 
const settingApiUrl = 'http://{{HOST}}:8080/prog-rangers/mypage/account-settings';

export const checkNickname = () => {
  return axios.get(duplicationApiUrl)
    .then((response) => response.data)
    .catch((error) => {
      throw error; 
    });
};

export const inquireProfile = () => {
    return axios.get(inquireApiUrl)
      .then((response) => response.data)
      .catch((error) => {
        throw error; 
      });
  };

  export const settingProfile = () => {
    return axios.get(settingApiUrl)
      .then((response) => response.data)
      .catch((error) => {
        throw error; 
      });
  };