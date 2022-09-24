import { call, put } from 'redux-saga/effects';
import { startLoading, finishLoading } from '../modules/loading';

export const createRequestActionTypes = type => {
    const SUCCESS = `${type}_SUCCESS`;
    const FAILURE = `${type}_FAILURE`;
    return [type, SUCCESS, FAILURE];
};

export default function createRequestSaga(type, request) {

    console.log("createRequest 시작");
    console.log(request);
    console.log("type========",type);

    const SUCCESS = `${type}_SUCCESS`;
    const FAILURE = `${type}_FAILURE`;

    console.log(SUCCESS)

    return function* (action) {

        console.log("로딩시작");
        console.log("action====",action);
        
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
