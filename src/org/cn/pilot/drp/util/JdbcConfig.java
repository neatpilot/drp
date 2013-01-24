package org.cn.pilot.drp.util;

/**
 * jdbc configuration
 * 
 * @author Pilot
 * 
 */
public class JdbcConfig {
	private String driverName;
	private String url;
	private String user;
	private String password;

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return this.getClass().getName()+"[driverName=" + driverName + ", url=" + url + ", user=" + user
				+ ", password=" + password + "]";
	}

}
