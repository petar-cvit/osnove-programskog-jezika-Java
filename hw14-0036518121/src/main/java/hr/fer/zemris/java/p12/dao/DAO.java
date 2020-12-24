package hr.fer.zemris.java.p12.dao;

import java.util.List;

import hr.fer.zemris.java.p12.model.Option;
import hr.fer.zemris.java.p12.model.Poll;

public interface DAO {

	/**
	 * Gets all polls from database in a {@link Poll} list.
	 * 
	 * @return list of polls
	 * @throws DAOException in case of error
	 */
	public List<Poll> getPolls() throws DAOException;
	
	/**
	 * Gets poll from Polls table with given id.
	 * 
	 * @param id
	 * @return requested poll
	 * @throws DAOException
	 */
	public Poll getPoll(long id) throws DAOException;
	
	/**
	 * Gets all poll options referring to poll with id that is given as argument.
	 * 
	 * @param pollID
	 * @return list of poll options
	 * @throws DAOException
	 */
	public List<Option> getPollOptions(long pollID) throws DAOException;
	
	/**
	 * Increments votesCount field in database for poll option with given id.
	 * 
	 * @param optionID
	 * @throws DAOException
	 */
	public void incrementVote(long optionID) throws DAOException;
	
}