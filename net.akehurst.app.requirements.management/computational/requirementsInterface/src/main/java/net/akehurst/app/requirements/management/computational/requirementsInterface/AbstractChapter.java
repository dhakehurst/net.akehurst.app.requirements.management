package net.akehurst.app.requirements.management.computational.requirementsInterface;

import java.util.ArrayList;
import java.util.List;

import net.akehurst.application.framework.common.AbstractDataType;
import net.akehurst.application.framework.common.annotations.declaration.DataType;

@DataType
public class AbstractChapter extends AbstractDataType implements IChapter {

	public AbstractChapter(final IChapterOwner owner, final ChapterIdentity identity) {
		super(owner, identity);
		this.item = new ArrayList<>();
	}

	public IChapterOwner getOwner() {
		return (IChapterOwner) super.getIdentityValues().get(0);
	}

	public ChapterIdentity getIdentity() {
		return (ChapterIdentity) super.getIdentityValues().get(1);
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

	List<IChapterItem> item;

	public List<IChapterItem> getItem() {
		return this.item;
	}

	@Override
	public Integer getRequirementsCount() {
		int count = 0;
		for (final IChapterItem item : this.getItem()) {
			if (item instanceof Requirement) {
				count++;
			} else if (item instanceof IChapter) {
				count += ((IChapter) item).getRequirementsCount();
			}
		}
		return count;
	}
}
