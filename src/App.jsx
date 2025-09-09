import { useEffect, useRef, useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'
import CreatePollComponent from './components/CreatePollComponent'
import CreateUserComponent from './components/CreateUserComponent'
import VoteComponent from './components/VoteComponent'

function App() {
  const [userId, setUserId] = useState(-1);
  const [pollId, setPollId] = useState(-1);
  const [create, setCreate] = useState(false);
  const pollInput = useRef(null);

  useEffect(() => {
    console.log(userId);
  },[])

  const joinPoll = () => {
    //Logic for joining a active poll
    console.log(pollInput.current.value);
    setPollId(pollInput.current.value);
  }


  //Save userId in session to stay "logged in" on refresh
  //sessionStorage.setItem('myKey', JSON.stringify(data));


  return (
    userId < 0 ? 
      <CreateUserComponent setUserId={setUserId}/> 
    : 
      pollId >= 0 ? 
        <VoteComponent/>
      :
        (create ?
          <CreatePollComponent/>
        :
        <div className='appMain'>
          <ul>Welcome USERNAME!</ul>
          <input type="text" placeholder='Poll ID' ref={pollInput}/>
          <input type="button" onClick={() => joinPoll()} value='Enter Poll'/>
          <input type="button" value='Create new Poll' onClick={() => setCreate(true)}/>
        </div>)
  )
}

export default App
