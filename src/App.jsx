import { useEffect, useRef, useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'
import CreatePollComponent from './components/CreatePollComponent'
import CreateUserComponent from './components/CreateUserComponent'
import VoteComponent from './components/VoteComponent'
import useSessionStorageState from 'use-session-storage-state'


function App() {
  const [username, setUsername] = useSessionStorageState("username", { defaultValue: "ERROR"});
  const [userId, setUserId] = useSessionStorageState("userId", {defaultValue: -1});
  const [poll, setPoll] = useSessionStorageState("poll", {defaultValue: null});
  const [create, setCreate] = useSessionStorageState("create", {defaultValue: false});
  const pollInput = useRef(null);

  useEffect(() => {
    console.log(userId);
    checkIfExists();
  },[])

  const joinPoll = () => {
    //Logic for joining a active poll
    console.log(pollInput.current.value);
    setPollId(pollInput.current.value);
  }

  const checkIfExists = async () => {
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
            let user = result.find(f => f.username == username);
            if(user == null) {
              setUsername("ERROR");
              setUserId(-1);
              setPoll(null);
              setCreate(false);
            }
        }
    } catch (error) {
        console.log(error);
    }
  }

  const enterPoll = async (pollId) => {
    try {
        const response = await fetch('http://localhost:8080/api/polls', {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
            }
        })
        if(response.ok) {
            const result = await response.json();
            const poll = result.find(p => p.id == pollId);
            console.log(result, poll)
            if(poll != null) setPoll(poll);
            //exists = user != null;
        }
    } catch (error) {
        console.log(error);
    }
  }


  //Save userId in session to stay "logged in" on refresh
  //sessionStorage.setItem('myKey', JSON.stringify(data));


  return (
    userId < 0 ? 
      <CreateUserComponent setUserId={setUserId} setUsername={setUsername}/> 
    : 
      poll != null ?
        <VoteComponent poll={poll} username={username} userId={userId} setPoll={setPoll}/>
      :
        (create ?
          <CreatePollComponent userId={userId} setCreate={setCreate} setPoll={setPoll} enterPoll={enterPoll}/>
        :
        <div className='appMain'>
          <ul>Welcome {username}!</ul>
          <input type="text" placeholder='Poll ID' ref={pollInput}/>
          <input type="button" onClick={() => enterPoll(pollInput.current.value)} value='Enter Poll'/>
          <input type="button" value='Create new Poll' onClick={() => setCreate(true)}/>
        </div>)
  )
}

export default App
