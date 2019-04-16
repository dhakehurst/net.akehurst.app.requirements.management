package net.akehurst.requirements.management.engineering.user2Gui;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.akehurst.app.requirements.management.computational.requirementsInterface.Project;
import net.akehurst.app.requirements.management.computational.requirementsInterface.ProjectIdentity;
import net.akehurst.application.framework.common.interfaceUser.UserSession;
import net.akehurst.application.framework.realisation.AbstractIdentifiableObject;
import net.akehurst.application.framework.technology.interfaceGui.GuiEvent;
import net.akehurst.application.framework.technology.interfaceGui.GuiEventType;
import net.akehurst.application.framework.technology.interfaceGui.IGuiHandler;
import net.akehurst.application.framework.technology.interfaceGui.IGuiScene;
import net.akehurst.application.framework.technology.interfaceGui.IGuiSceneHandler;
import net.akehurst.application.framework.technology.interfaceGui.SceneIdentity;
import net.akehurst.application.framework.technology.interfaceGui.StageIdentity;
import net.akehurst.application.framework.technology.interfaceGui.data.table.AbstractGuiTableData;
import net.akehurst.application.framework.technology.interfaceGui.data.table.IGuiTableData;
import net.akehurst.requirements.management.computational.userInterface.IUserHomeNotification;
import net.akehurst.requirements.management.computational.userInterface.IUserHomeRequest;

public class SceneHandlerHome extends AbstractIdentifiableObject implements IUserHomeNotification, IGuiSceneHandler {

	public SceneHandlerHome(final String afId) {
		super(afId);
	}

	ISceneHome scene;
	IGuiHandler gui;
	public IUserHomeRequest userHomeRequest;

	private boolean isAuthenticated(final UserSession session) {
		return null != session.getUser();
	}

	@Override
	public ISceneHome createScene(final IGuiHandler gui, final StageIdentity stageId, final URL content) {
		this.scene = gui.createScene(stageId, SceneHandlerCommon.sceneIdHome, ISceneHome.class, this, content);
		return this.scene;
	}

	@Override
	public void loaded(final IGuiHandler gui, final IGuiScene guiScene, final GuiEvent event) {
		final UserSession session = event.getSession();
		final SceneIdentity currentSceneId = event.getSignature().getSceneId();
		// this.getGuiRequest().setTitle(session, SceneHandlerCommon.sceneIdHome, currentSceneId, "Requirements Management");

		this.scene.getUsername().setText(session, session.getUser().getName());

		this.scene.getActionNewProject().onEvent(session, GuiEventType.CLICK, (ev) -> {
			this.userHomeRequest.requestCreateProject(session, new ProjectIdentity("newProj"));
		});
		this.scene.getActionDeleteProject().onEvent(session, GuiEventType.CLICK, (ev) -> {
			final String projectId = (String) ev.getDataItem("projectId");
			this.userHomeRequest.requestDeleteProject(session, new ProjectIdentity(projectId));
		});
		this.userHomeRequest.requestStartHome(session);
	}

	@Override
	public void notifyProjectCreated(final UserSession session, final Project project) {
		// TODO Auto-generated method stub

	}

	@Override
	public void notifyProjectList(final UserSession session, final List<Project> projectList) {
		final IGuiTableData<String, Integer> data = new AbstractGuiTableData<String, Integer>() {

			@Override
			public Map<String, Object> getRowData(final int index) {
				final Project p = projectList.get(index);
				final Map<String, Object> r = new HashMap<>();
				r.put("identity", p.getIdentity().asPrimitive());
				r.put("name", p.getName());
				r.put("description", p.getDescription());
				r.put("count", p.getRequirementsCount());
				return r;
			}

			@Override
			public int getNumberOfRows() {
				return projectList.size();
			}

		};

		this.scene.getProjectTable().setData(session, data);
	}

	@Override
	public void notifyProject(final UserSession session, final Project project) {
		// TODO Auto-generated method stub

	}

	@Override
	public void notifyProjectUpdated(final UserSession session, final Project project) {
		// TODO Auto-generated method stub

	}

	@Override
	public void notifyProjectDeleted(final UserSession session, final ProjectIdentity projectId) {
		// TODO Auto-generated method stub

	}
}
