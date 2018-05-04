package ci.imako.imakocrmtest.domain;

import javax.persistence.*;

/**
 * Created by Christian Amani on 30/10/2017.
 */
@Table(name = "droit")
@Entity
public class Authority {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	long id;
	@Column(name = "nom", unique = true)
	String name;
	String description;

	public Authority() {

	}

	public Authority(String name, String description) {
		this.name = name;
		this.description = description;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
