package com.app.feedback.controller;

import java.net.URI;

import javax.inject.Inject;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.app.feedback.domain.Vote;
import com.app.feedback.repository.VoteRepository;

@RestController
public class VoteController {

	@Inject
	private VoteRepository voteRepository;
	
	@RequestMapping(value="/polls/{pollId}/votes",method=RequestMethod.POST)
	public ResponseEntity<?> subMitVoteForAPoll(@RequestBody Vote vote){
		voteRepository.save(vote);
		
		//set the location header in the response
		URI newVoteURI = ServletUriComponentsBuilder.fromCurrentRequest()
		.path("{/voteId}").buildAndExpand(vote.getId()).toUri();
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setLocation(newVoteURI);
		
		return new ResponseEntity<>(null, responseHeaders,HttpStatus.CREATED);
		
	}

	@RequestMapping(value="/votes/{voteId}",method=RequestMethod.GET)
	public ResponseEntity<Vote> findVote(@PathVariable Long voteId) {
		return new ResponseEntity<Vote>(voteRepository.findOne(voteId), HttpStatus.OK);
	}
	
	@RequestMapping(value="/polls/votes",method=RequestMethod.GET)
	public ResponseEntity<Iterable<Vote>> findAllVotes() {
		return new ResponseEntity<Iterable<Vote>>(voteRepository.findAll(),HttpStatus.OK);
	}
	
	@RequestMapping(value="/polls/{pollId}/votes",method=RequestMethod.GET)
	public ResponseEntity<Iterable<Vote>> findVotesForPoll(@PathVariable Long pollId) {
		
		return new ResponseEntity<Iterable<Vote>>(voteRepository.findVotesForASinglePoll(pollId),
				HttpStatus.OK);
	}
}
