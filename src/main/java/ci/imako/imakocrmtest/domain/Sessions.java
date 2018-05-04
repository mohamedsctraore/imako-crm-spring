package ci.imako.imakocrmtest.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by Christian Amani on 30/10/2017.
 */
@Table(name = "sessions")
@Entity
public class Sessions
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@Column(name = "id_session")
	private String idSessionCurrentSession;
	@Column(name = "ip_address")
	private String ipAddressSession;
	@Column(name = "user_agent")
	private String agentSession;

	@Column(name = "heure_connexion")
	private LocalDateTime loginTime;

	@Column(name = "heure_deconnection")
	private LocalDateTime disconnectionTime;

	@ManyToOne
	@JoinColumn(name = "id_account")
	private AccountUser accountUser;


	public Sessions() {

	}

	public Sessions(String idSessionCurrentSession, String ipAddressSession,
			String agentSession, LocalDateTime loginTime, LocalDateTime disconnectionTime) {
		this.idSessionCurrentSession = idSessionCurrentSession;
		this.ipAddressSession = ipAddressSession;
		this.agentSession = agentSession;
		this.loginTime = loginTime;
		this.disconnectionTime = disconnectionTime;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getIdSessionCurrentSession() {
		return idSessionCurrentSession;
	}

	public void setIdSessionCurrentSession(String idSessionCurrentSession) {
		this.idSessionCurrentSession = idSessionCurrentSession;
	}

	public String getIpAddressSession() {
		return ipAddressSession;
	}

	public void setIpAddressSession(String ipAddressSession) {
		this.ipAddressSession = ipAddressSession;
	}

	public String getAgentSession() {
		return agentSession;
	}

	public void setAgentSession(String agentSession) {
		this.agentSession = agentSession;
	}

	public LocalDateTime getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(LocalDateTime loginTime) {
		this.loginTime = loginTime;
	}

	public LocalDateTime getDisconnectionTime() {
		return disconnectionTime;
	}


	public void setDisconnectionTime(LocalDateTime disconnectionTime) {
		this.disconnectionTime = disconnectionTime;
	}

	public AccountUser getAccountUser() {
		return accountUser;
	}

	public void setAccountUser(AccountUser accountUser) {
		this.accountUser = accountUser;
	}
}
