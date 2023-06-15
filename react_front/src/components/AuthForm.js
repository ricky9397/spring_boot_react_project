import React, { useState } from 'react';
import "../assets/styles/login.css";
import { GoogleLogin } from "react-google-login";
// import { useDispatch, useSelector } from 'react-redux';
// import { changeField, login, googleLogin } from '../modules/auth';

const AuthForm = () => {
  // 이메일 정규식
  const emailRegExp = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/;
  // 비밀번호 정규식
  const passwordRegEx = /^[A-Za-z0-9]{6,12}$/;
  // 빈값 정규식
  const replaceRegExp = /\s/g;


  const [userEmail, setUserEmail] = useState('');
  const [userPassword, setUserPassword] = useState('');
  // 에러처리
  // const [error, setError] = useState(false);
  // const dispatch = useDispatch();

  // const target = (e) => {
  //   const { value, name } = e.target;
  //   dispatch(
  //     changeField({
  //       form: 'login',
  //       key: name,
  //       value,
  //     }),
  //   );
  // }

  const onEmailHandler = (e) => {
    setUserEmail(e.currentTarget.value.replace(replaceRegExp, ''));
    // target(e);
  }
  const onPwHandler = (e) => {
    setUserPassword(e.currentTarget.value.replace(replaceRegExp, ''));
    // target(e);
  }


  const onSubmit = async (e) => {
    e.preventDefault();

    // if (!emailRegExp.test(userEmail)) {
    //   setError("이메일형식이 잘못되었습니다.");
    //   return false;
    // }
    // if (!passwordRegEx.test(userPassword)) {
    //   setError("비밀번호는 대소문자8자이상가능합니다.");
    //   return false;
    // }
    // if ([userEmail, userPassword].includes('')) {
    //   setError("빈 칸을 모두 입력하세요.");
    //   return false;
    // }
    // // 입력 데이터
    // const data = {
    //   userEmail: userEmail,
    //   userPassword: userPassword,
    // }
    // // redux 서버 호출
    // dispatch(login(data));
  }

  // const { form, auth, authError, user } = useSelector(({ auth, user }) => ({
  //   form: auth.login,
  //   auth: auth.auth,
  //   authError: auth.authError,
  //   user: user.user,
  // }));

  // useEffect(() => {
  //   if (authError) {
  //     console.log('오류 발생');
  //     console.log(authError);
  //     setError('로그인 실패');
  //     return;
  //   }
  //   if (auth) {
  //     // if(auth.user.lockedYn === "Y"){
  //     //   console.log("계정잠김");
  //     //   setError('비밀번호 5회이상 틀렸습니다. 관리자에게 문의하세요.');
  //     //   return false;
  //     // }
  //     console.log('로그인 성공');
  //     console.log(auth);
  //     const data = {
  //       userId: auth.userId,
  //       userName: auth.userName,
  //       role: auth.role
  //     }
  //     dispatch(check(data));
  //   }
  // }, [auth, authError, dispatch]);

  // useEffect(() => {
  //   if (user) {
  //     console.log("check 로그인 성공");
  //     console.log(user);
  //     history.push("/");
  //     try {
  //       localStorage.setItem('user', JSON.stringify(user));
  //     } catch (e) {
  //       console.log('localStorage is not working');
  //     }
  //   }
  // }, [user]);

  // // gapi
  // useEffect(() => {
  //   gapi.load("client:auth2", () => {
  //     gapi.auth2.init({ clientId: googleClientId })
  //   })
  // });

  // // 구글 로그인 성공 핸들러
  // const responseGoogle = async (response) => {
  //   dispatch(googleLogin(response.profileObj));
  // };

  // // 구글 로그인 실패 핸들러
  // const responseFailGoogle = (response) => {
  //   console.log(response);
  //   setError('구글 로그인 실패');
  // };

  return (
    <div className="login">
<<<<<<< HEAD
      <h4 className='login_title'>모 지 ?</h4>
=======
      <h4 className='login_title'> 짝 꿍 ! </h4>
>>>>>>> d0b4077d77c228fd6e0b108ea6ab04b5ee57a8de
      <form onSubmit={onSubmit}>
        <div className="text_area">
          <input
            type="text"
            id="userEmail"
            name="userEmail"
            //defaultValue="userEmail"
            placeholder="Email"
            className="text_input"
            value={userEmail}
            onChange={onEmailHandler}
          />
        </div>
        <div className="text_area">
          <input
            type="password"
            id="userPassword"
            name="userPassword"
            //defaultValue="userPassword"
            placeholder="Password"
            className="text_input"
            value={userPassword}
            onChange={onPwHandler}
          />
        </div>
        <input
          type="submit"
          value="로그인"
          className="btn"
        />
      </form>
      {<a className="link" href="/signup">Sign Up</a>}
        <GoogleLogin />
    </div>
  )
}

export default AuthForm;
