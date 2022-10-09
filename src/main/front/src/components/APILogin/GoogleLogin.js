import { useRef } from 'react';
import useScript from '../../modules/useScript';

export default function GoogleLogin({
  onGoogleSignIn = () => {},
  text = 'signin_with',
}) {

  const googleSignInButton = useRef(null);

  useScript('https://accounts.google.com/gsi/client', () => {
    window.google.accounts.id.initialize({
      client_id: process.env.REACT_APP_GOOGLE_CLIENT_ID,
      callback: onGoogleSignIn,
    });
    window.google.accounts.id.renderButton(
      googleSignInButton.current,
      { theme: 'filled_blue', size: 'large', text, width: '250', type:'icon', shape:'circle' 
    }); 
  });

  return <div ref={googleSignInButton}></div>;
}