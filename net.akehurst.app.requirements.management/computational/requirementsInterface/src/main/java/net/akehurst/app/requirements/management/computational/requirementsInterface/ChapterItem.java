package net.akehurst.app.requirements.management.computational.requirementsInterface;

import net.akehurst.application.framework.common.AbstractDataType;
import net.akehurst.application.framework.common.annotations.declaration.DataType;

@DataType
public abstract class ChapterItem extends AbstractDataType implements IChapterItem {

	public ChapterItem(final IChapter owner, final ChapterItemIdentity identity) {
		super(owner, identity);
	}

	public IChapter getOwner() {
		return (IChapter) super.getIdentityValues().get(0);
	}

	public ChapterItemIdentity getIdentity() {
		return (ChapterItemIdentity) super.getIdentityValues().get(1);
	}
}
