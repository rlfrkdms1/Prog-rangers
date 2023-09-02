import axios from 'axios';

const API_END_POINT = '/prog-rangers'

// TODO response 처리 로직
export const signup= async (data)=>{
    const response = await axios.post(`${API_END_POINT}/sign-up`,{...data});
    console.log(response)
}

export const checkNicknameDuplication = async (params)=>{
    await axios.get(`${API_END_POINT}/check-nickname-duplication`,{params});
}
