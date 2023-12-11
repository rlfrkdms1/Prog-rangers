import axios from 'axios';

// TODO response 처리 로직
export const signIn= async (data)=>{
  const response = await axios.post(
    `http://13.124.131.171:8080/api/v1/login`,
    {...data},
    {headers: {'Content-Type': 'application/json'}}
  );  
  
  if(response.status === 200){
    const refreshToken = response.headers['set-cookie'];
    localStorage.setItem('refreshToken', refreshToken);
    console.log('rt: ', refreshToken);
    return response.data;
  }else{
    console.log('Error:', response.status);
  }
}


// // TODO response 처리 로직
// export const signIn = async (data) => {
//   try {
//     const response = await fetch('https://prog-rangers.com/api/v1/login', {
//       method: 'POST',
//       body: JSON.stringify(data),
//       headers: {
//         'Content-Type': 'application/json',
//       },
//     });

//     if (response.status === 200 || response.status === 201) {
//       const refreshToken = response.headers['set-cookie'];
//       AsyncStorage.setItem('refreshToken', refreshToken);
//       console.log('rt: ', refreshToken);
//       return response.data;
//     } else if (response.status === 400) {
//       alert('입력한 값을 다시 확인해주세요.');
//     } else if (response.status === 500) {
//       alert('서버와의 연결이 불안정합니다.\n잠시 후에 다시 시도해주세요.');
//     }
//   } catch (error) {
//     console.log('fetch error', error);
//   }
// };

  // The axios part can remain as it is if needed.
//   const axiosResponse = await axios.post(`https://prog-rangers.com/api/v1/login`, { ...data });

//   if (axiosResponse.status === 200) {
//     console.log(axiosResponse.headers);
//     return axiosResponse.data;
//   } else {
//     console.log('Error:', axiosResponse.status);
//   }
// };

