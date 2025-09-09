import { useEffect, useRef, useState } from 'react'
import '../App.css'

function CreatePollComponent({setPollId}) {
    const questionRef = useRef(null);
    const validUntilRef = useRef(null);

    const submitPoll = () => {
        //Logic for submitting new poll here
        console.log(questionRef.current.value, validUntilRef.current.value);
    }

    return (
        <div className="createMenu">
            <h2 className="header">Create Poll</h2>
            <div>
                <div className='spacing-top'>
                    <input type="text" title="Question for the poll" ref={questionRef} placeholder='Question'/>
                    <input type="datetime-local" title="Date & time the poll no longer is valid after" ref={validUntilRef} placeholder='Question'/>
                </div>
                <div className='voteOptions'>
                    <div>Vote Options</div>
                    <div className='options'>
                        <div className="option">
                            <div>Option</div>
                            <div className="controls">
                                <div className='orderControls'>
                                    <div>↑</div>
                                    <div>↓</div>
                                </div>
                                <input type="text" defaultValue={""} placeholder='Caption'/>
                                <input type="button" style={{width: 80}} value='Delete'/>
                            </div>
                        </div>
                    </div>
                    <input type="button" style={{width: 80}} value="Add"/>
                </div>
                <div className='spacing-bottom'>
                    <input type="button" onClick={() => submitPoll()} value="Publish"/>
                </div>
            </div>
        </div>
    )
}

export default CreatePollComponent