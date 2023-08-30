import axios from 'axios';

const API_END_POINT = '/prog-rangers'

export const signup= async (data)=>{
  try {
    const response = await axios.post(`${API_END_POINT}/sign-up`,{...data});
    console.log(response);
  } catch (error) {
    console.log(error)
  }
}

export const checkNicknameDuplication = async (params)=>{
  try {
    const response = await axios.get(`${API_END_POINT}/check-nickname-duplication`,{params});
    console.log(response);
  } catch (error) {
    console.log(error)
  }
}
