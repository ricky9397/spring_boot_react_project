import { call, put } from 'redux-saga/effects';
import { startLoading, finishLoading } from '../modules/loading';
import Cookies from 'universal-cookie';

const cookies = new Cookies(); // 토큰을 담을 Cookies

export const createRequestActionTypes = type => {
    const SUCCESS = `${type}_SUCCESS`;
    const FAILURE = `${type}_FAILURE`;
    return [type, SUCCESS, FAILURE];
};

export default function createRequestSaga(type, request) {

    const SUCCESS = `${type}_SUCCESS`;
    const FAILURE = `${type}_FAILURE`;

    
    

    return function* (action) {
        
        console.log("로딩시작");
        console.log("action====",action);
        console.log("SUCCESS",SUCCESS);
        console.log("FAILURE",FAILURE);
        yield put(startLoading(type)); // 로딩 시작
        try {
            const response = yield call(request, action.payload);
            yield put({
                type: SUCCESS,
                payload: response.data,
                meta: response,
            });
            
        } catch (e) {
            yield put({
                type: FAILURE,
                payload: e,
                error: true,
            });
        }
        
        console.log("로딩끝");

        yield put(finishLoading(type)); // 로딩 끝
    };
}
