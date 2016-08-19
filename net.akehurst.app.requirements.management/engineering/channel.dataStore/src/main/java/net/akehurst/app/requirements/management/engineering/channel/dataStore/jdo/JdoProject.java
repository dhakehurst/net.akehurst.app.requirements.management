package net.akehurst.app.requirements.management.engineering.channel.dataStore.jdo;

import java.util.List;

import javax.jdo.annotations.PersistenceCapable;

import net.akehurst.app.requirements.management.computational.requirementsInterface.Project;

@PersistenceCapable
public class JdoProject {

	public JdoProject() {}

	public JdoProject(final Project project) {
		this.name = project.getName();
		this.owner = project.getOwner();
	}

	String name;
	String owner;

	List<JdoChapter> chapters;

}
