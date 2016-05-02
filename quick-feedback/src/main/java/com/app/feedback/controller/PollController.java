package com.app.feedback.controller;

import java.net.URI;

import javax.inject.Inject;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.app.feedback.domain.Poll;
import com.app.feedback.repository.PollRepository;

/**
 * This controller deals with creating/updating/deleting/loading/finding polls
 *
 */
@RestController
public class PollController {

	@Inject
	private PollRepository pollRepository;
	
	@CrossOrigin(origins="*")
	@RequestMapping(value="/polls",method=RequestMethod.GET)
	public ResponseEntity<Iterable<Poll>> getAllPolls() {
		return new ResponseEntity<Iterable<Poll>>(pollRepository.findAll(), HttpStatus.OK);
		
	}
	
	/**
	 * 
	 * poll can come as xml/json..spring would look at header of the incoming req 
	 * "content-type" to determine json/xml.
	 * 
	 * After the poll is created, it makes sense to share the resource uri of the newly created resource
	 * to the user. On the reponse, we can set the location header with the uri.
	 */
	@RequestMapping(value="/polls",method=RequestMethod.POST)
	public ResponseEntity<?> createPoll(@RequestBody Poll poll) {
		pollRepository.save(poll);
		URI newPollUri = ServletUriComponentsBuilder.fromCurrentRequest()
								   .path("/{id}")
								   .buildAndExpand(poll.getId())
								   .toUri();
		HttpHeaders responseHeader = new HttpHeaders();
		responseHeader.setLocation(newPollUri);
		return new ResponseEntity<>(null,responseHeader,HttpStatus.CREATED);
	}
	
	@RequestMapping(value="/polls/{pollId}",method=RequestMethod.GET)
	public ResponseEntity<Poll> findPoll(@PathVariable Long pollId) {
		return new ResponseEntity<Poll>(pollRepository.findOne(pollId), HttpStatus.OK);
	}
	
	@RequestMapping(value="/polls/{pollId}",method=RequestMethod.DELETE)
	public ResponseEntity<?> deletePoll(@PathVariable Long pollId) {
		pollRepository.delete(pollId);
		return new ResponseEntity<>(null, HttpStatus.OK);
	}
	
	@RequestMapping(value="/polls",method=RequestMethod.PUT)
	public ResponseEntity<?> updatePoll(@RequestBody Poll updatedPoll) {
		pollRepository.save(updatedPoll);
		
		URI updatedPollUri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(updatedPoll.getId()).toUri();
		HttpHeaders responseHeader = new HttpHeaders();
		responseHeader.setLocation(updatedPollUri);
		return new ResponseEntity<>(null,responseHeader,HttpStatus.OK);
	}
	
	/**
	 * Example of post body for creating a poll
	 * 
	 * {
    {
    "question": "Which framework is best suited for ui dev",
    "options": [
                       {"value": "Angular2"},
                       {"value": "AngularJs"},
                       {"value": "BackBone"},
                       {"value": "Does not matter..as long as it is javascript"}]
}
}
	 */
	
	

}
