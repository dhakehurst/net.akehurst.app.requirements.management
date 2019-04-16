package net.akehurst.requirements.management.computational.requirementsManagement;

import java.util.List;

import net.akehurst.app.requirements.management.computational.requirementsInterface.IRequirementsRequest;
import net.akehurst.app.requirements.management.computational.requirementsInterface.Project;
import net.akehurst.app.requirements.management.computational.requirementsInterface.ProjectIdentity;
import net.akehurst.application.framework.common.interfaceUser.UserSession;
import net.akehurst.application.framework.realisation.AbstractActiveSignalProcessingObject;
import net.akehurst.requirements.management.computational.userInterface.IUserHomeNotification;
import net.akehurst.requirements.management.computational.userInterface.IUserHomeRequest;

public class UserHandler extends AbstractActiveSignalProcessingObject implements IUserHomeRequest {

	public UserHandler(final String afId) {
		super(afId);
	}

	public IUserHomeNotification userHomeNotification;
	public IRequirementsRequest requirementsRequest;

	@Override
	public void requestStartHome(final UserSession session) {
		super.submit("requestStartHome", () -> {
			final List<Project> projectList = this.requirementsRequest.fetchAllProject(session).get();
			this.userHomeNotification.notifyProjectList(session, projectList);
		});
	}

	@Override
	public void requestCreateProject(final UserSession session, final ProjectIdentity projectId) {
		super.submit("requestCreateProject", () -> {
			final Project project = new Project(projectId);
			project.setName("New Project");
			project.setDescription("Newly created.");
			this.requirementsRequest.requestCreateProject(session, project).get();
			this.userHomeNotification.notifyProjectCreated(session, project);
		});
	}

	@Override
	public void requestUpdateProject(final UserSession session, final ProjectIdentity projectId, final Project project) {
		super.submit("requestUpdateProject", () -> {
			this.requirementsRequest.requestUpdateProject(session, projectId, project);
			this.userHomeNotification.notifyProjectUpdated(session, project);
		});
	}

	@Override
	public void requestDeleteProject(final UserSession session, final ProjectIdentity projectId) {
		super.submit("requestDeleteProject", () -> {
			this.requirementsRequest.requestDeleteProject(session, projectId).get();
			this.userHomeNotification.notifyProjectDeleted(session, projectId);
		});
	}

}
