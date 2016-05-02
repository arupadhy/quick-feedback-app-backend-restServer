package com.app.feedback.repository;

import org.springframework.data.repository.CrudRepository;

import com.app.feedback.domain.Poll;

public interface OptionRepository extends CrudRepository<Poll, Long>{

}
