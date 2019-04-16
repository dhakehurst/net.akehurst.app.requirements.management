package net.akehurst.app.requirements.management.computational.requirementsInterface;

import net.akehurst.application.framework.common.annotations.declaration.DataType;

@DataType
public class SubChapter extends AbstractChapter implements IChapter, IChapterItem {

	public SubChapter(final IChapter owner, final ChapterIdentity identity) {
		super(owner, identity);
	}

	@Override
	public IChapter getOwner() {
		return (IChapter) super.getIdentityValues().get(0);
	}

}
