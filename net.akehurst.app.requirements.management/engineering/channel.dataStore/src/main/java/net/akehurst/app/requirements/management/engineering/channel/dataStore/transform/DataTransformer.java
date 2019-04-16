package net.akehurst.app.requirements.management.engineering.channel.dataStore.transform;

import net.akehurst.transform.binary.AbstractTransformer;

public class DataTransformer extends AbstractTransformer {

	public DataTransformer() {
		super.registerRule(Project2JdoProject.class);
	}

}
