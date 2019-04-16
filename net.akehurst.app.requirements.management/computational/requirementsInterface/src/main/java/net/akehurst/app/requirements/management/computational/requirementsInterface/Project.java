package net.akehurst.app.requirements.management.computational.requirementsInterface;

import java.util.ArrayList;
import java.util.List;

import net.akehurst.application.framework.common.AbstractDataType;
import net.akehurst.application.framework.common.annotations.declaration.DataType;

@DataType
public class Project extends AbstractDataType implements IChapterOwner {

	public Project(final ProjectIdentity identity) {
		super(identity);
		this.chapter = new ArrayList<>();
	}

	public ProjectIdentity getIdentity() {
		return (ProjectIdentity) super.getIdentityValues().get(0);
	}

	String name;

	public String getName() {
		return this.name;
	}

	public void setName(final String value) {
		this.name = value;
	}

	String description;

	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String value) {
		this.description = value;
	}

	List<TopChapter> chapter;

	public List<TopChapter> getChapter() {
		return this.chapter;
	}

	public Integer getRequirementsCount() {
		int count = 0;
		for (final TopChapter tc : this.getChapter()) {
			count += tc.getRequirementsCount();
		}
		return count;
	}

}
