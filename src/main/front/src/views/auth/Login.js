import React, { useState, useEffect } from "react";
import { useDispatch, useSelector } from 'react-redux';
import { authSaga, changeField, initializeForm, login } from '../../modules/auth';
import { Link } from "react-router-dom";
import { check } from '../../modules/user';

const Login = ({ handleLogin, history }) => {

  const [userEmail, setUserEmail] = useState('');
  const [userPassword, setUserPassword] = useState('');

  // 에러처리
  const [error, setError] = useState(false);

  const dispatch = useDispatch();

  // 이메일 정규식
  const emailRegExp = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/;
  // 비밀번호 정규식
  const passwordRegEx = /^[A-Za-z0-9]{6,12}$/;
  // 빈값 정규식
  const replaceRegExp = /\s/g;

  const target = (e) => {
    const { value, name } = e.target;
    dispatch(
      changeField({
        form: 'login',
        key: name,
        value,
      }),
    );
  }

  const onEmailHandler = (e) => {
    setUserEmail(e.currentTarget.value.replace(replaceRegExp, ''));
    target(e);
  }

  const onPwHandler = (e) => {
    setUserPassword(e.currentTarget.value.replace(replaceRegExp, ''));
    target(e);
  }

  const submit = async (e) => {
    e.preventDefault();

    if (!emailRegExp.test(userEmail)) {
      setError("이메일형식이 잘못되었습니다.");
      return false;
    }
    if (!passwordRegEx.test(userPassword)) {
      setError("비밀번호는 대소문자8자이상가능합니다.");
      return false;
    }
    if ([userEmail, userPassword].includes('')) {
      setError("빈 칸을 모두 입력하세요.");
      return false;
    }
    // 입력 데이터
    const data = {
      userEmail: userEmail,
      userPassword: userPassword,
    }
    // redux 서버 호출
    dispatch(login(data));

    // try {
    //   const data = { 'userEmail' : userEmail, 'userPassword' : userPassword }
    //   await axios.post("http://localhost:8080/auth/login", JSON.stringify(data) , {
    //     headers: {
    //       "Content-Type": "application/json",
    //     },
    //     withCredentials: true
    //   }).then(res => {
    //     if(res.status == 200){
    //       const setToken = {
    //         "auth_token" : res.headers.auth_token,
    //         "refresh_token" : res.headers.refresh_token
    //       }
    //       handleLogin(setToken); // 토큰 발송
    //       history.push("/"); // 로그인 성공 후 메인화면 이동.
    //     } 
    //   });

    // } catch (e) {
    //   console.log(e.message);
    //   handleLogin("undefined");
    // }
  }

  const { form, auth, authError, user } = useSelector(({ auth, user }) => ({
    form: auth.login,
    auth: auth.auth,
    authError: auth.authError,
    user: user.user,
  }));

  useEffect(() => {
    if (authError) {
      console.log('오류 발생');
      console.log(authError);
      setError('로그인 실패');
      return;
    }
    if (auth) {
      // if(auth.user.lockedYn === "Y"){
      //   console.log("계정잠김");
      //   setError('비밀번호 5회이상 틀렸습니다. 관리자에게 문의하세요.');
      //   return false;
      // }
      console.log('로그인 성공');
      console.log(auth);
      const data = {
        userId: auth.user.userId,
        userName: auth.user.userName,
        role: auth.user.role
      }
      dispatch(check(data));
    }
  }, [auth, authError, dispatch]);

  useEffect(() => {
    if (user) {
      console.log("check 로그인 성공");
      console.log(user);
      history.push("/");
      try {
        localStorage.setItem('user', JSON.stringify(user));
      } catch (e) {
        console.log('localStorage is not working');
      }
    }
  }, [user]);

  const googleAction = () => {
    window.location.href = "http://localhost:8080/auth/login/oauth2/code/google";
  };

  return (
    <>
      <div className="container mx-auto px-4 h-full">
        <div className="flex content-center items-center justify-center h-full">
          <div className="w-full lg:w-4/12 px-4">
            <div className="relative flex flex-col min-w-0 break-words w-full mb-6 shadow-lg rounded-lg bg-blueGray-200 border-0">
              <div className="rounded-t mb-0 px-6 py-6">
                <div className="text-center mb-3">
                  <h6 className="text-blueGray-500 text-sm font-bold">
                    간편 로그인
                  </h6>
                </div>
                <div className="btn-wrapper text-center">
                  <button
                    className="bg-white active:bg-blueGray-50 text-blueGray-700 font-normal px-4 py-2 rounded outline-none focus:outline-none mr-2 mb-1 uppercase shadow hover:shadow-md inline-flex items-center font-bold text-xs ease-linear transition-all duration-150"
                    type="button"
                  >
                    <img
                      alt="..."
                      className="w-5 mr-1"
                      src={require("assets/img/github.svg").default}
                    />
                    Github
                  </button>
                  <button
                    className="bg-white active:bg-blueGray-50 text-blueGray-700 font-normal px-4 py-2 rounded outline-none focus:outline-none mr-1 mb-1 uppercase shadow hover:shadow-md inline-flex items-center font-bold text-xs ease-linear transition-all duration-150"
                    type="button" onClick={googleAction}
                  >
                    <img
                      alt="..."
                      className="w-5 mr-1"
                      src={require("assets/img/google.svg").default}
                    />
                    Google
                  </button>
                </div>
                <hr className="mt-6 border-b-1 border-blueGray-300" />
              </div>
              <div className="flex-auto px-4 lg:px-10 py-10 pt-0">
                <div className="text-blueGray-400 text-center mb-3 font-bold">
                  <small>Or sign in with credentials</small>
                </div>
                <form onSubmit={submit}>
                  <div className="relative w-full mb-3">
                    <label
                      className="block uppercase text-blueGray-600 text-xs font-bold mb-2"
                      htmlFor="grid-password"
                    >
                      이메일
                    </label>
                    <input
                      type="text"
                      className="border-0 px-3 py-3 placeholder-blueGray-300 text-blueGray-600 bg-white rounded text-sm shadow focus:outline-none focus:ring w-full ease-linear transition-all duration-150"
                      autoComplete="userEmail" name="userEmail" placeholder="Email" value={userEmail} onChange={onEmailHandler}
                    />
                  </div>

                  <div className="relative w-full mb-3">
                    <label
                      className="block uppercase text-blueGray-600 text-xs font-bold mb-2"
                      htmlFor="grid-password"
                    >
                      비밀번호
                    </label>
                    <input
                      type="password"
                      className="border-0 px-3 py-3 placeholder-blueGray-300 text-blueGray-600 bg-white rounded text-sm shadow focus:outline-none focus:ring w-full ease-linear transition-all duration-150"
                      autoComplete="userPassword" name="userPassword" placeholder="Password" value={userPassword} onChange={onPwHandler}
                    />
                  </div>
                  <div>
                    <label className="inline-flex items-center cursor-pointer">
                      <input
                        id="customCheckLogin"
                        type="checkbox"
                        className="form-checkbox border-0 rounded text-blueGray-700 ml-1 w-5 h-5 ease-linear transition-all duration-150"
                      />
                      <span className="ml-2 text-sm font-semibold text-blueGray-600">
                        아이디 기억하기
                      </span>
                    </label>
                  </div>
                  <div className="text-center mt-2">
                    <span className="text-red-500 text-sm font-semibold">{error}</span>
                  </div>
                  <div className="text-center mt-6">
                    <button
                      className="bg-blueGray-800 text-white active:bg-blueGray-600 text-sm font-bold uppercase px-6 py-3 rounded shadow hover:shadow-lg outline-none focus:outline-none mr-1 mb-1 w-full ease-linear transition-all duration-150"
                      type="submit"
                    >
                      로그인
                    </button>
                  </div>
                </form>
              </div>
            </div>
            <div className="flex flex-wrap mt-6 relative">
              <div className="w-1/2">
                <a
                  href="#pablo"
                  onClick={(e) => e.preventDefault()}
                  className="text-blueGray-200"
                >
                  <small>비밀번호를 잊으셨나요?</small>
                </a>
              </div>
              <div className="w-1/2 text-right">
                <Link to="/auth/register" className="text-blueGray-200">
                  <small>회원가입</small>
                </Link>
              </div>
            </div>
          </div>
        </div>
      </div>
    </>
  );
}

export default Login