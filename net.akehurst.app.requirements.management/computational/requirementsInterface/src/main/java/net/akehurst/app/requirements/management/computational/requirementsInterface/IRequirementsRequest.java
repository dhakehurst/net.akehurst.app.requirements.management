package net.akehurst.app.requirements.management.computational.requirementsInterface;

import net.akehurst.application.framework.common.UserSession;

public interface IRequirementsRequest {

	void requestNewProject(UserSession session, Project project);

	void requestNewChapter(UserSession session, Project project, Chapter chapter);
}
