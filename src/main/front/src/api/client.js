import axios from "axios";
import Cookies from "universal-cookie";

const client = axios.create();

// API 주소
// client.defaults.baseURL = 'http://localhost:8080';

const cookies = new Cookies();

const auth_token = cookies.get('auth_token');
const refresh_token = cookies.get('refresh_token');

// 헤더 설정
client.defaults.headers.common['Authorization'] = refresh_token ? 'Bearer '+refresh_token : null;

axios.interceptors.response.use(
    response => {
        console.log("interceptors.response",response);
        // return response;
    },
    error => {
        return Promise.reject(error);
    }
)

export default client;