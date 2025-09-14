import { useEffect, useState } from 'react'
import '../App.css'

function VoteComponent({username, userId, poll, setPoll}) {
    
    const castVote = async (voteOptionId) => {
        try {
        const response = await fetch(`http://localhost:8080/api/vote?userId=${userId}&pollId=${poll.pollId}&voteOption=${voteOptionId}`, {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
            }
        })
        if(response.ok) {
            const result = await response.json();
            console.log(result);
            setPoll(result);
        }
        } catch (error) {
            console.log(error);
        }
    }

  return (
        <div className="pollMenu">
            <input type="button" value="Back" style={{width: 60}} onClick={() => setPoll(null)}/>
            <h3>Welcome to Poll #{poll.pollId}, {username}</h3>
            <h2>
                {poll.question.replaceAll("+", " ")}
            </h2>
            <div className="answerField">
                {poll.voteOptions.map((v, index) => (
                <div key={index} className="answer">
                    <div className='caption'>{v.caption}</div>
                    <div className="controls">
                        <input type="button" value="Vote" onClick={() => castVote(index)} style={{width: 80}}/>
                    </div>
                    <div className="votes">{Object.values(poll.votes).filter(vote => vote.voteOption?.caption === v.caption).length} Votes</div>
                </div>
                ))}
            </div>
        </div>
  )
}

export default VoteComponent