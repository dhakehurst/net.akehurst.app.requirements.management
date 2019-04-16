package net.akehurst.app.requirements.management.computational.requirementsInterface;

import java.util.List;
import java.util.concurrent.Future;

import net.akehurst.application.framework.common.interfaceUser.UserSession;

public interface IRequirementsRequest {

	// --- Project ---
	Future<Void> requestCreateProject(UserSession session, Project project);

	Future<Project> fetchProject(UserSession session, ProjectIdentity projectId);

	Future<List<Project>> fetchAllProject(UserSession session);

	Future<Void> requestUpdateProject(UserSession session, ProjectIdentity projectId, Project project);

	Future<Void> requestDeleteProject(UserSession session, ProjectIdentity projectId);

}
