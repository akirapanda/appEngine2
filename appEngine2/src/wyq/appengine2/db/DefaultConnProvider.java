package wyq.appengine2.db;

/**
 * This class provides the information which is need by the Connection
 * 
 * @author dewafer
 * 
 */
public class DefaultConnProvider implements ConnectionProvider {

	private String username;
	private String password;
	private String sqlConnProviderClass;
	private String connectionString;

	@Override
	public String getUser() {
		return username;
	}

	public DefaultConnProvider(String sqlConnProviderClass,
			String connectionString, String username, String password) {
		this.sqlConnProviderClass = sqlConnProviderClass;
		this.connectionString = connectionString;
		this.username = username;
		this.password = password;
	}

	@Override
	public String getSqlConnProviderClass() {
		return sqlConnProviderClass;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getConnStr() {
		return connectionString;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getConnectionString() {
		return connectionString;
	}

	public void setConnectionString(String connectionString) {
		this.connectionString = connectionString;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setSqlConnProviderClass(String sqlConnProviderClass) {
		this.sqlConnProviderClass = sqlConnProviderClass;
	}
}
