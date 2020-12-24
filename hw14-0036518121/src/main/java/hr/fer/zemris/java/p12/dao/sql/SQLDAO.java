package hr.fer.zemris.java.p12.dao.sql;

import hr.fer.zemris.java.p12.dao.DAO;
import hr.fer.zemris.java.p12.dao.DAOException;
import hr.fer.zemris.java.p12.model.Option;
import hr.fer.zemris.java.p12.model.Poll;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * {@link DAO} interface implementation using SQL. Gets connection to database with {@link SQLConnectionProvider}
 * class.
 * 
 * @author Petar Cvitanović
 */
public class SQLDAO implements DAO {

	@Override
	public List<Poll> getPolls() throws DAOException {
		
		List<Poll> polls = new ArrayList<Poll>();
		
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement("SELECT id, title, message FROM Polls");
			
			try {
				ResultSet rs = pst.executeQuery();
				try {
					while(rs!=null && rs.next()) {
						Poll poll = new Poll(rs.getLong(1),
												rs.getString(2),
												rs.getString(3)
												);
						
						polls.add(poll);
					}
				} finally {
					try { rs.close(); } catch(Exception ignorable) {}
				}
			} finally {
				try { pst.close(); } catch(Exception ignorable) {}
			}
		} catch(Exception ex) {
			throw new DAOException("Error during query!", ex);
		}
		
		return polls;
	}

	@Override
	public Poll getPoll(long id) throws DAOException {
		Poll poll = null;
		
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement("SELECT id, title, message FROM Polls WHERE id=?");
			pst.setLong(1, Long.valueOf(id));
			try {
				ResultSet rs = pst.executeQuery();
				try {
					while(rs!=null && rs.next()) {
						poll = new Poll(rs.getLong(1),
												rs.getString(2),
												rs.getString(3)
												);
						
					}
				} finally {
					try { rs.close(); } catch(Exception ignorable) {}
				}
			} finally {
				try { pst.close(); } catch(Exception ignorable) {}
			}
		} catch(Exception ex) {
			throw new DAOException("Error during query!", ex);
		}
		
		return poll;
	}
	
	@Override
	public List<Option> getPollOptions(long pollID) throws DAOException {
		List<Option> options = new ArrayList<Option>();
		
		Connection con = SQLConnectionProvider.getConnection();
		
		PreparedStatement optionsStatement = null;
		
		try {
			optionsStatement = con.prepareStatement("SELECT id, optionTitle, optionLink, votesCount FROM PollOptions WHERE pollID=?");
			optionsStatement.setLong(1, Long.valueOf(pollID));
			
			try {
				ResultSet rs = optionsStatement.executeQuery();
				try {
					while(rs!=null && rs.next()) {
						Option option = new Option(rs.getLong(1),
												rs.getString(2),
												rs.getString(3),
												Integer.parseInt(rs.getString(4))
												);
						
						options.add(option);
					}
				} finally {
					try { rs.close(); } catch(Exception ignorable) {}
				}
			} finally {
				try { optionsStatement.close(); } catch(Exception ignorable) {}
			}
		} catch(Exception ex) {
			throw new DAOException("Error during query!", ex);
		}
		
		return options;
	}
	
	@Override
	public void incrementVote(long optionID) throws DAOException {
		Connection con = SQLConnectionProvider.getConnection();
		Statement insertStatement = null;
		try {
			insertStatement = con.createStatement();
			try {
				insertStatement.executeUpdate("UPDATE PollOptions SET votesCount = votesCount + 1 WHERE id=" + optionID);
			} finally {
				try { insertStatement.close(); } catch(Exception ignorable) {}
			}
		} catch(Exception ex) {
			throw new DAOException("Error during query!", ex);
		}
	}
}