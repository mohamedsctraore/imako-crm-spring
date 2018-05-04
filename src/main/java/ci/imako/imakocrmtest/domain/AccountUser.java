package ci.imako.imakocrmtest.domain;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Christian Amani on 30/10/2017.
 */
@Table(name = "compte")
@Entity
public class AccountUser
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	long id;
	@Column(name = "nom")
	String firstName;
	@Column(unique = true)
	@NotNull
	String login;
	@NotNull
	String password;
	@Email
	String email;
	@Column(name = "etat_connexion")
	boolean stateConnection;
	@Column(name = "etat_compte")
	boolean isEnabled;
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE},fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	List<Authority> authorities = new ArrayList<>();
	@OneToMany(mappedBy = "accountUser", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	@Fetch(value = FetchMode.SUBSELECT)
	List<Sessions> sessions = new ArrayList<>();


	public AccountUser() {

	}

	public AccountUser(String firstName, String login, String email,
					   boolean stateConnection, String password, boolean isEnabled)
	{
		this.firstName = firstName;
		this.login = login;
		this.email = email;
		this.stateConnection = stateConnection;
		this.password = password;
		this.isEnabled = isEnabled;
	}


	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public String getLogin()
	{
		return login;
	}

	public void setLogin(String login)
	{
		this.login = login;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public boolean isConnected()
	{
		return stateConnection;
	}

	public void setStateConnection(boolean stateConnection)
	{
		this.stateConnection = stateConnection;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public boolean isEnabled()
	{
		return isEnabled;
	}

	public void setEnabled(boolean enabled)
	{
		isEnabled = enabled;
	}

	public List<Sessions> getSessions()
	{
		return sessions;
	}


	public List<Authority> getAuthorities()
	{
		return authorities;
	}

	public void addAuthority(Authority authority)
	{
		if(authority != null)
			authorities.add(authority);
	}

	public void removeAuthority(Authority authority) {
		if(authority != null && authority.getId() >= 0)
			authorities.remove(authority);
	}

	public void addSession(Sessions sessions)
	{
		if(sessions != null)
		{
			sessions.setAccountUser(this);
			this.sessions.add(sessions);
		}
	}
}
