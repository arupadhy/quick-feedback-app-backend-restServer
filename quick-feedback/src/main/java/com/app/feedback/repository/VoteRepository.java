package com.app.feedback.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.app.feedback.domain.Vote;

public interface VoteRepository extends CrudRepository<Vote, Long>{
	
	//example of writing custom methods here
	//SQL native query to get all votes for a single poll
	@Query(value="select v.* from Option o, Vote v where o.POLL_ID=?1 AND v.OPTION_ID = o.OPTION_ID",
			nativeQuery=true)
	Iterable<Vote> findVotesForASinglePoll(Long pollId);

}
