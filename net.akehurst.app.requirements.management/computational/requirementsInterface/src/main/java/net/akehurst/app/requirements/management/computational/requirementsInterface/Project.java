package net.akehurst.app.requirements.management.computational.requirementsInterface;

import net.akehurst.application.framework.common.annotations.declaration.DataType;

@DataType
public class Project {

	public Project(final String name, final String owner) {
		this.name = name;
		this.owner = owner;
	}

	String name;

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	String owner;

	public String getOwner() {
		return this.owner;
	}

	public void setOwner(final String owner) {
		this.owner = owner;
	}

}
