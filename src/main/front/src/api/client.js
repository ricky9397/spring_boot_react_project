import axios from "axios";

const client = axios.create();

// API 주소
// client.defaults.baseURL = 'http://localhost:8080';

// 헤더 설정
// client.defaults.headers.common['Authorization'] = 'Bearer ' + token;

// axios.interceptors.response.use(
//     response => {
//         // 요청 성공 시 특정 작업 수행
//         return response;
//     },
//     error => {
//         return Promise.reject(error);
//     }
// )

export default client;