package net.akehurst.app.requirements.management.engineering.channel.dataStore.jdo;

import java.util.List;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class JdoProject {

	@PrimaryKey
	@Persistent
	public String getIdentity() {
		return this.identity;
	}

	public void setIdentity(final String value) {
		this.identity = value;
	}

	private String identity;

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

	public void setDescription(final String value) {
		this.description = value;
	}

	private String description;

	@Persistent
	public List<JdoTopChapter> getChapters() {
		return this.chapters;
	}

	public void setChapters(final List<JdoTopChapter> value) {
		this.chapters = value;
	}

	private List<JdoTopChapter> chapters;

}
