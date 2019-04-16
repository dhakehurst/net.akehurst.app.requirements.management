package net.akehurst.app.requirements.management.computational.requirementsInterface;

import net.akehurst.application.framework.common.annotations.declaration.DataType;

@DataType
public interface IChapter extends IChapterOwner {

	Integer getRequirementsCount();
}
