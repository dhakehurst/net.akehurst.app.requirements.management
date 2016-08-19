package net.akehurst.requirements.management.engineering.user2Gui;

import java.net.URL;
import java.util.AbstractList;
import java.util.Arrays;
import java.util.List;

import net.akehurst.app.requirements.management.computational.requirementsInterface.Project;
import net.akehurst.application.framework.common.UserDetails;
import net.akehurst.application.framework.common.UserSession;
import net.akehurst.application.framework.common.annotations.instance.ConfiguredValue;
import net.akehurst.application.framework.computational.interfaceUser.authentication.IUserAuthenticationNotification;
import net.akehurst.application.framework.computational.interfaceUser.authentication.IUserAuthenticationRequest;
import net.akehurst.application.framework.engineering.common.gui.ISignInScene;
import net.akehurst.application.framework.technology.gui.common.AbstractGuiHandler;
import net.akehurst.application.framework.technology.guiInterface.GuiEvent;
import net.akehurst.application.framework.technology.guiInterface.IGuiNotification;
import net.akehurst.application.framework.technology.guiInterface.IGuiScene;
import net.akehurst.application.framework.technology.guiInterface.SceneIdentity;
import net.akehurst.application.framework.technology.guiInterface.StageIdentity;
import net.akehurst.application.framework.technology.guiInterface.data.table.IGuiTableCell;
import net.akehurst.application.framework.technology.guiInterface.data.table.IGuiTableData;
import net.akehurst.application.framework.technology.guiInterface.data.table.IGuiTableRow;
import net.akehurst.requirements.management.computational.userInterface.IUserHomeNotification;
import net.akehurst.requirements.management.computational.userInterface.IUserHomeRequest;

public class GuiHandler extends AbstractGuiHandler implements IUserAuthenticationNotification, IUserHomeNotification, IGuiNotification {

	public GuiHandler(final String id) {
		super(id);
	}

	IUserAuthenticationRequest userAuthenticationRequest;
	IUserHomeRequest userHomeRequest;

	// ---------- IUserAuthenticationNotification ---------
	@Override
	public void notifyAuthenticationSuccess(final UserSession session) {
		final UserSession sess = new UserSession(session.getId(), new UserDetails(session.getUser().getName()));
		this.guiRequest.switchTo(session, this.stageId, this.homeSceneId);
	}

	@Override
	public void notifyAuthenticationFailure(final UserSession session, final String message) {
		final UserSession ts = new UserSession(session.getId(), null);
		this.signInScene.getMessage().setText("Sign In Failed, please try again.");
	}

	@Override
	public void notifyAuthenticationCleared(final UserSession session) {
		final UserSession ts = new UserSession(session.getId(), null);
		this.getGuiRequest().switchTo(session, this.authStageId, this.signInSceneId);
	}

	// --- IUserHomeNotification ---
	@Override
	public void notifyProjectList(final UserSession session, final List<Project> projectList) {

		this.homeScene.getProjectTable().setData(session, new IGuiTableData<String, Integer>() {

			@Override
			public void getCellData(final String columnName, final Integer row) {

			}

			@Override
			public List<IGuiTableRow<String, Integer>> getRows() {
				return new AbstractList<IGuiTableRow<String, Integer>>() {

					@Override
					public IGuiTableRow<String, Integer> get(final int index) {
						final Project p = projectList.get(index);
						return () -> {
							final IGuiTableCell<String, Integer> c1 = () -> p.getName();
							final IGuiTableCell<String, Integer> c2 = () -> p.getName();
							final IGuiTableCell<String, Integer> c3 = () -> p.getOwner();
							return Arrays.asList(c1, c2, c3);
						};
					}

					@Override
					public int size() {
						return projectList.size();
					}
				};
			}

		});
	}

	// --------- AbstractGuiHandler ---------

	@ConfiguredValue(defaultValue = "/auth")
	StageIdentity authStageId;

	@ConfiguredValue(defaultValue = "/rm")
	StageIdentity stageId;

	@ConfiguredValue(defaultValue = "/home/")
	SceneIdentity homeSceneId;

	@ConfiguredValue(defaultValue = "/signin/")
	SceneIdentity signInSceneId;

	// @ConfiguredValue(defaultValue = "/Gui.fxml")
	// String urlStr;

	IHomeScene homeScene;
	ISignInScene signInScene;

	@Override
	public void notifyReady() {
		final URL rootUrl = this.getClass().getResource("/site");
		this.getGuiRequest().createStage(this.stageId, false, rootUrl);

		final URL rootUrl2 = this.getClass().getResource("/af");
		this.getGuiRequest().createStage(this.authStageId, false, rootUrl2);
		//
		// final URL homeUrl = this.getClass().getResource("/home");
		// this.portGui().out(IGuiRequest.class).createStage(UserToGui.STAGE_HOME, true, homeUrl);
	}

	@Override
	protected void onStageCreated(final GuiEvent event) {
		final URL content = null;// this.getClass().getResource(this.urlStr);
		this.homeScene = this.getGuiRequest().createScene(this.stageId, this.homeSceneId, IHomeScene.class, content);
		this.signInScene = this.getGuiRequest().createScene(this.authStageId, this.signInSceneId, ISignInScene.class, content);
	}

	@Override
	protected void onSceneLoaded(final GuiEvent event) {

		final SceneIdentity currentSceneId = event.getSignature().getSceneId();

		if (this.homeSceneId.equals(currentSceneId)) {
			this.homeSceneLoaded(event);
		} else if (this.signInSceneId.equals(currentSceneId)) {
			this.signInSceneLoaded(event);
		}
	}

	private void homeSceneLoaded(final GuiEvent event) {
		final UserSession session = event.getSession();
		final SceneIdentity currentSceneId = event.getSignature().getSceneId();
		this.getGuiRequest().setTitle(session, this.stageId, currentSceneId, "Requirements Management");

		if (this.isAuthenticated(session)) {
			this.homeScene.getUsername().setText(session, session.getUser().getName());
			this.userHomeRequest.requestStartHome(session);
		} else {
			this.getGuiRequest().switchTo(session, this.authStageId, this.signInSceneId);
		}

		// if (event.get.equals(this.sceneId) || "index".equals(this.sceneId)) {
		//
		// this.getGuiRequest().addElement(session, UserToGui.STAGE_WELCOME, "root", "textTitle", "h1");
		// this.getGuiRequest().addElement(session, UserToGui.STAGE_WELCOME, "root", "textWelcome", "p");
		//
		// this.getGuiRequest().setText(session, UserToGui.STAGE_WELCOME, "textTitle", "Requirements Management");
		// } else if (UserToGui.STAGE_HOME.equals(this.sceneId)) {
		// this.getGuiRequest().addElement(session, UserToGui.STAGE_HOME, "root", "textTitle", "h1");
		// this.getGuiRequest().setText(session, UserToGui.STAGE_HOME, "textTitle", "Home for " + session.getUser().getName());
		// }
	}

	private void signInSceneLoaded(final GuiEvent event) {

		this.signInScene.getActionSignIn().onEvent(event.getSession(), "click", (e) -> {
			final String username = (String) e.getDataItem("inputEmail");
			final String password = (String) e.getDataItem("inputPassword");

			this.userAuthenticationRequest.requestLogin(e.getSession(), username, password);

			//
		});

	}

	@Override
	protected IGuiScene getScene(final SceneIdentity sceneId) {
		if (this.homeSceneId.equals(sceneId)) {
			return this.homeScene;
		} else if (this.signInSceneId.equals(sceneId)) {
			return this.signInScene;
		} else {
			return null;
		}
	}

	// ---
	boolean isAuthenticated(final UserSession session) {
		return null != session.getUser();
	}
}
