package net.akehurst.app.requirements.management.engineering.channel.dataStore.jdo;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class JdoTopChapter {

	@Persistent
	// @PrimaryKey
	public JdoProject getOwner() {
		return this.owner;
	}

	public void setOwner(final JdoProject value) {
		this.owner = value;
	}

	private JdoProject owner;

	@Persistent
	@PrimaryKey
	public String getIdentity() {
		return this.identity;
	}

	public void setIdentity(final String value) {
		this.identity = value;
	}

	String identity;

	@Persistent
	public String getName() {
		return this.name;
	}

	public void setName(final String value) {
		this.name = value;
	}

	private String name;

	@Persistent
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	private String description;

}
