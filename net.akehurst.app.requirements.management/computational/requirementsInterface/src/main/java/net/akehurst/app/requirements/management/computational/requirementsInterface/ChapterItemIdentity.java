package net.akehurst.app.requirements.management.computational.requirementsInterface;

import net.akehurst.application.framework.common.AbstractDataType;
import net.akehurst.application.framework.common.annotations.declaration.DataType;

@DataType
public class ChapterItemIdentity extends AbstractDataType {

	public ChapterItemIdentity(final String value) {
		super(value);
	}

	public String asPrimitive() {
		return (String) super.getIdentityValues().get(0);
	}

}
