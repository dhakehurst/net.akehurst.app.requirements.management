package net.akehurst.app.requirements.management.computational.requirementsInterface;

import net.akehurst.application.framework.common.annotations.declaration.DataType;

@DataType
public class TopChapter extends AbstractChapter implements IChapter {

	public TopChapter(final Project owner, final ChapterIdentity identity) {
		super(owner, identity);
	}

	@Override
	public Project getOwner() {
		return (Project) super.getIdentityValues().get(0);
	}
}
