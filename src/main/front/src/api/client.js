import axios from "axios";
import Cookies from "universal-cookie";
import * as authAPI from '../api/auth';

const client = axios.create();

// API 주소
// client.defaults.baseURL = 'http://localhost:8080';

const cookies = new Cookies();

const auth_token = cookies.get('auth_token');
const refresh_token = cookies.get('refresh_token');

// 헤더 설정
client.defaults.headers.common['Authorization'] = auth_token ? 'Bearer '+ auth_token : null;
client.defaults.headers.common['refresh_token'] = refresh_token ? 'Bearer '+ refresh_token : null;

client.interceptors.response.use(
    response => {
        console.log("interceptors.response",response);
        return response;
    },
    error => {
        
        console.log(error.request.responseURL);
        console.log("인터셉터 오류 ", error);
        
        if(error.response.data.message === "401"){
            // 토큰 만료시 인터셉터사용하여 token 발행받아온다.    
        }

        return Promise.reject(error);
    }
)

export default client;