package com.app.feedback.repository;

import org.springframework.data.repository.CrudRepository;

import com.app.feedback.domain.Poll;

/**
 *This is how crud operations can be handled via springBoot  
 *
 */
public interface PollRepository extends CrudRepository<Poll, Long>{


}
