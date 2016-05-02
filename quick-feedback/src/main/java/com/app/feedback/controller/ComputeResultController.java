package com.app.feedback.controller;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.feedback.domain.Vote;
import com.app.feedback.dto.OptionCount;
import com.app.feedback.dto.VoteResult;
import com.app.feedback.repository.VoteRepository;

@RestController
public class ComputeResultController {

	@Inject
	private VoteRepository voteRepository;

	@RequestMapping(value = "/computeResults")
	public ResponseEntity<VoteResult> computeResults(@RequestParam Long pollId) {
		VoteResult voteResult = new VoteResult();
		Iterable<Vote> allVotes = voteRepository.findVotesForASinglePoll(pollId);

		int totalVotes = 0;
		Map<Long, OptionCount> tempMap = new HashMap<Long, OptionCount>();
		for (Vote v : allVotes) {
			totalVotes++;
			// Get the OptionCount corresponding to this Option
			OptionCount optionCount = tempMap.get(v.getOption().getId());
			if (optionCount == null) {
				optionCount = new OptionCount();
				optionCount.setOptionId(v.getOption().getId());
				tempMap.put(v.getOption().getId(), optionCount);
			}
			optionCount.setCount(optionCount.getCount() + 1);
		}
		voteResult.setTotalVotes(totalVotes);
		voteResult.setResults(tempMap.values());

		return new ResponseEntity<VoteResult>(voteResult, HttpStatus.OK);

	}
}
