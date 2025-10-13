import { useEffect, useRef, useState } from 'react'
import '../App.css'

function CreatePollComponent({setPoll, setCreate, userId, enterPoll}) {
    const questionRef = useRef(null);
    const validUntilRef = useRef(null);
    const [options, setOptions] = useState([]);

    const submitPoll = () => {
        //Logic for submitting new poll here
        console.log(questionRef.current.value, validUntilRef.current.value);
        if(questionRef.current.value != "" && validUntilRef.current.vlaue != "" && options.length > 0) {
            createPoll(questionRef.current.value, validUntilRef.current.value);
        }
    }

    const createPoll = async (question, validUntil) => {
        try {
            const params = new URLSearchParams({
              userid: String(userId),
              question,
              validUntil: new Date(validUntil).toISOString(),
            });
            const response = await fetch(`http://localhost:8080/api/polls?${params}`, { method: 'POST' });
            if(response.ok) {
                const result = await response.json();
                console.log(result);
                options.forEach(option => {
                    publishOption(result.id, option.caption);
                    console.log(option);
                });
                enterPoll(result.id);
                setCreate(false);
            }
        } catch (error) {
            console.log(error);
        }
    }
    
    const publishOption = async (pollId, caption) => {
        try {
            const params = new URLSearchParams({
              caption
            });
            const response = await fetch(`http://localhost:8080/api/polls/${pollId}/options?${params}`, { method: 'POST' });
            if(response.ok) {
                const result = await response.json();
                console.log(result);
            }
        } catch (error) {
            console.log(error);
        }
    }

    const addOption = () => {
        let newOption = {
            caption: ""
        };
        setOptions(prev => [...prev, newOption])
    }

    useEffect(()=> {
        console.log(options)
    },[options])

    return (
        <div className="createMenu">
            <input type="button" value="Back" style={{width: 60}} onClick={() => setCreate(false)}/>
            <h2 className="header">Create Poll</h2>
            <div>
                <div className='spacing-top'>
                    <input type="text" title="Question for the poll" ref={questionRef} placeholder='Question'/>
                    <input type="datetime-local" title="Date & time the poll no longer is valid after" ref={validUntilRef} placeholder='Question'/>
                </div>
                <div className='voteOptions'>
                    <div>Vote Options</div>
                    <div className='options'>
                        {options?.map((option, index) => (
                            <div key={index} className="option">
                                <div>Option</div>
                                <div className="controls">
                                    <div className='orderControls'>
                                        <div>↑</div>
                                        <div>↓</div>
                                    </div>
                                    <input type="text" defaultValue={""} placeholder='Caption' onChange={(e) => {
                                        const newCaption = e.target.value;
                                        setOptions(prev =>
                                          prev.map((o, i) =>
                                            i === index ? { ...o, caption: newCaption } : o
                                          )
                                        );
                                    }}/>
                                    <input type="button" style={{width: 80}} value='Delete'/>
                                </div>
                            </div>
                        ))}
                    </div>
                    <input type="button" style={{width: 80}} value="Add" onClick={() => addOption()}/>
                </div>
                <div className='spacing-bottom'>
                    <input type="button" onClick={() => submitPoll()} value="Publish"/>
                </div>
            </div>
        </div>
    )
}

export default CreatePollComponent