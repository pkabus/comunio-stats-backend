package pkabus.comuniostatsbackend;

import javax.validation.constraints.NotEmpty;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@Validated
@ConfigurationProperties("test.db")
public class TestDbProperties {

	@NotEmpty
	private String driver;

	@NotEmpty
	private String url;

	@NotEmpty
	private String username;

	@NotEmpty
	private String password;

	public String getDriver() {
		return driver;
	}

	public String getUrl() {
		return url;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "TestProperties [driver=" + driver + ", url=" + url + ", username=" + username + ", password=" + password
				+ "]";
	}
}
