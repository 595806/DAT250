import { useEffect, useRef, useState } from 'react'
import '../App.css'

function CreateUserComponent({setUserId}) {
  const [loginMenu, setLoginMenu] = useState(true);

  //Form Data
  const [username, setUsername] = useState("");
  const [email, setEmail] = useState("");
  const emailRef = useRef(null);

  useEffect(() => { // To stop werid behaviour, the value gets set to the button value from previous form
    if(!loginMenu) emailRef.current.value = email;
  }, [loginMenu])

  const loginUser = () => {
    //Login flow
    setUserId(0);
    console.log(username);
}

const registerUser = () => {
    //Register flow
    console.log(username, email);
  }

  return (
        <div className="login-page">
            <ul className="description">Welcome<br/>
            Please Login or Register to continue</ul>
            <div className="menuBox">
                <div className='selectorTab'>
                    <div className={loginMenu ? "selectorButton active": "selectorButton"} onClick={() => setLoginMenu(true)}>Login</div>
                    <div className={!loginMenu ? "selectorButton active": "selectorButton"} onClick={() => setLoginMenu(false)}>Register</div>
                </div>
                
                {
                    loginMenu ?
                    <div>
                        <input id="login-username" onChange={(e) => setUsername(e.target.value)} type="text" placeholder='Username'/>
                        <input type="button" value="Login" onClick={() => loginUser()}/>
                    </div>
                    :
                    <div>
                        <input id="register-username" onChange={(e) => setUsername(e.target.value)} type="text" placeholder='Username'/>
                        <input id="register-email" ref={emailRef} onChange={(e) => setEmail(e.target.value)} type="text" placeholder='Email'/>
                        <input id="register-button" type="button" onClick={() => registerUser()} value="Register"/>
                    </div>
                }
            </div>
        </div>
  )
}

export default CreateUserComponent
