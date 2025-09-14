import { useEffect, useRef, useState } from 'react'
import '../App.css'

function CreateUserComponent({setUserId, setUsername}) {
  const [loginMenu, setLoginMenu] = useState(true);

  //Form Data
  const [formusername, setFormUsername] = useState("");
  const [email, setEmail] = useState("");
  const emailRef = useRef(null);

  useEffect(() => { // To stop werid behaviour, the value gets set to the button value from previous form
    if(!loginMenu) emailRef.current.value = email;
  }, [loginMenu]);

  const checkIfExists = async () => {
    let exists = false;
    let user = {};
    try {
        const response = await fetch('http://localhost:8080/api/users', {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
            }
        })
        if(response.ok) {
            const result = await response.json();
            user = result.find(f => f.username == formusername);
            console.log(user);
            exists = user != null;
        }
    } catch (error) {
        console.log(error);
    }
    return {exists: exists, obj: user};
  }

    const loginUser = async () => {
        //Login flow
        const user = await checkIfExists();
        if(user.exists) {
            setUsername(user.obj.username);
            setUserId(user.obj.id);
        }
        console.log("login flow")
        
        //setUserId(0);
        console.log(formusername);
    }

const registerUser = async () => {
    //Register flow
    console.log("register flow")
    const user = await checkIfExists();
    if(user.exists) return; //Add error handling
    try {
        const response = await fetch('http://localhost:8080/api/users', {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                username: formusername,
                email: email,
            })
        })
        if(response.ok) {
            const result = await response.json();
            console.log(result);
            setUserId(result.id);
            setUsername(result.username);
        }
    } catch (error) {
        console.log(error);
    }
    console.log(formusername, email);
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
                        <input id="login-username" onChange={(e) => setFormUsername(e.target.value)} type="text" placeholder='Username'/>
                        <input type="button" value="Login" onClick={() => loginUser()}/>
                    </div>
                    :
                    <div>
                        <input id="register-username" onChange={(e) => setFormUsername(e.target.value)} type="text" placeholder='Username'/>
                        <input id="register-email" ref={emailRef} onChange={(e) => setEmail(e.target.value)} type="text" placeholder='Email'/>
                        <input id="register-button" type="button" onClick={() => registerUser()} value="Register"/>
                    </div>
                }
            </div>
        </div>
  )
}

export default CreateUserComponent
