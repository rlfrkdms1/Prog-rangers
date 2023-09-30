import axios from 'axios';

export const API_END_POINT = 'http://13.124.131.171:8080/api/v1'

// TODO response 처리 로직
export const signup= async (data)=>{
    const response = await axios.post(`${API_END_POINT}/sign-up`,{...data});
    console.log(response)
}

export const checkNicknameDuplication = async (params)=>{
    await axios.get(`${API_END_POINT}/check-nickname-duplication`,{params});
}
