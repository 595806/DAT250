import { useEffect, useState } from 'react'
import '../App.css'

function VoteComponent({setPollId}) {

  return (
        <div className="pollMenu">
            <h3>Welcome to Poll ###, USERNAME</h3>
            <h2>
                POLL QUESTION GOES HERE
            </h2>
            <div className="answerField">
                <div className="answer">
                    <div className='caption'>Text</div>
                    <div className="controls">VoteButton</div>
                    <div className="votes">## Votes</div>
                </div>
                <div className="answer">
                    <div className='caption'>Text</div>
                    <div className="controls">VoteButton</div>
                    <div className="votes">## Votes</div>
                </div>
            </div>
        </div>
  )
}

export default VoteComponent