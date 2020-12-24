package hr.fer.zemris.java.p12;

import java.beans.PropertyVetoException;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;

import hr.fer.zemris.java.p12.dao.DAOException;
import hr.fer.zemris.java.p12.model.Poll;

/**
 * Reads configuration file and creates a connection URL for database connection. If tables Polls or PollOptions
 * do not exist in database, they are created with SQL commands. In WEB-INF folder there is another folder named
 * populating. That folder contains files that contain poll title in the first row, insert command for certain
 * poll in second row and insert commands for poll options referring to poll that was mentioned in the second
 * row. For every file in that folder first line in taken. If Polls table does not contain entry with title
 * matching title from file, this poll is inserted with SQL command provided in second row. Key that is generated
 * when poll was inserted is used to insert poll options.
 * 
 * @author Petar Cvitanović
 *
 */
@WebListener
public class Initialization implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		String fileName = sce.getServletContext().getRealPath("/WEB-INF");

		ClassLoader loader = null;

		try {
			File file = new File(fileName);
			URL[] urls = {file.toURI().toURL()};
			loader = new URLClassLoader(urls);
		} catch (Exception e) {
		}

		ResourceBundle config = ResourceBundle.getBundle("dbsettings", Locale.getDefault(), loader);

		String connectionURL = "jdbc:derby://" + config.getString("host") + ":" + config.getString("port") + "/" + config.getString("name");

		for(String key : config.keySet()) {
			if(config.getString(key) == null) {
				throw new NullPointerException();
			}
		}

		ComboPooledDataSource cpds = new ComboPooledDataSource();
		try {
			cpds.setDriverClass("org.apache.derby.jdbc.ClientDriver");
		} catch (PropertyVetoException e1) {
			throw new RuntimeException("Pogreška prilikom inicijalizacije poola.", e1);
		}
		cpds.setJdbcUrl(connectionURL);
		cpds.setUser(config.getString("user"));
		cpds.setPassword(config.getString("password"));
		cpds.setInitialPoolSize(5);
		cpds.setMinPoolSize(5);
		cpds.setAcquireIncrement(5);
		cpds.setMaxPoolSize(20);

		try {
			createIfNeeded(cpds, "Polls",
					"CREATE TABLE Polls" + 
							" (id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY," + 
							" title VARCHAR(150) NOT NULL," + 
							" message CLOB(2048) NOT NULL" + 
							")"
					);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		try {
			createIfNeeded(cpds, "PollOptions", 
					"CREATE TABLE PollOptions" + 
							" (id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY," + 
							" optionTitle VARCHAR(100) NOT NULL," + 
							" optionLink VARCHAR(150) NOT NULL," + 
							" pollID BIGINT," + 
							" votesCount BIGINT," + 
							" FOREIGN KEY (pollID) REFERENCES Polls(id)" + 
							")"
					);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		populateAll(cpds, sce);

		sce.getServletContext().setAttribute("hr.fer.zemris.dbpool", cpds);
	}

	private void createIfNeeded(DataSource dataSource, String tableName, String sqlFunction) throws SQLException {
		Connection con = null;
		try {
			con = dataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(0);
		}

		ResultSet res = con.getMetaData().getTables(null, "IVICA", tableName.toUpperCase(), null);
		if(!res.next()) {
			Statement pst = con.createStatement();
			pst.execute(sqlFunction);
		}
	}

	private void populateAll(DataSource dataSource, ServletContextEvent sce) {
		File populateFolder = new File(sce.getServletContext().getRealPath("/WEB-INF/populating"));

		for(File populateFile : populateFolder.listFiles()) {
			if(!populateFile.isFile()) {
				continue;
			}

			try(BufferedReader br = Files.newBufferedReader(Paths.get(populateFile.getAbsolutePath()))) {
				String pollTitle = br.readLine();
				String pollInsert = br.readLine();

				List<String> pollOptionsInsert = new ArrayList<String>();
				String line = null;
				while(true) {
					line = br.readLine();
					if(line == null) {
						break;
					}
					pollOptionsInsert.add(line);
				}

				Poll poll = null;
				try {
					poll = getPollByTitle(dataSource, pollTitle);
				} catch (DAOException | SQLException e) {
					e.printStackTrace();
				}
				
				Long pollID = null;
				
				if(poll == null) {
					try {
						ResultSet rs = insertPoll(dataSource, pollInsert);
						if(rs.next()) {
							pollID = rs.getLong(1);
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				} else {
					pollID = poll.getId();
				}
				
				try {
					insertOptions(dataSource, pollID, pollOptionsInsert);
				} catch (SQLException e) {
					e.printStackTrace();
				}

			} catch (IOException e) {

			}
		}	
	}

	private ResultSet insertPoll(DataSource dataSource, String SQLCommand) throws SQLException {
		Connection con = null;
		try {
			con = dataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(0);
		}

		PreparedStatement insert = con.prepareStatement(SQLCommand, Statement.RETURN_GENERATED_KEYS);
		insert.executeUpdate();

		return insert.getGeneratedKeys();
	}

	private void insertOptions(DataSource dataSource, Long generatedKey, List<String> SQLCommands) throws SQLException {
		Connection con = null;
		try {
			con = dataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		PreparedStatement existing = con.prepareStatement("SELECT * FROM pollOptions WHERE pollID=?");
		existing.setLong(1, generatedKey);
		ResultSet rs = existing.executeQuery();
		
		if(rs.next()) {
			return;
		}

		for(String insertCommand : SQLCommands) {
			PreparedStatement insert = con.prepareStatement(insertCommand);
			insert.setLong(1, generatedKey);
			insert.executeUpdate();
		}
	}

	public Poll getPollByTitle(DataSource dataSource, String title) throws DAOException, SQLException {
		Poll poll = null;

		Connection con = null;
		try {
			con = dataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(0);
		}

		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement("SELECT id, title, message FROM Polls WHERE title=?");
			pst.setString(1, title);
			
			System.out.println(pst.toString());
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

		//con.close();
		return poll;
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		ComboPooledDataSource cpds = (ComboPooledDataSource)sce.getServletContext().getAttribute("hr.fer.zemris.dbpool");
		if(cpds!=null) {
			try {
				DataSources.destroy(cpds);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}