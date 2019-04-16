package net.akehurst.requirements.management.engineering.user2Gui;

import java.net.URL;

import net.akehurst.application.framework.common.interfaceUser.UserSession;
import net.akehurst.application.framework.realisation.AbstractIdentifiableObject;
import net.akehurst.application.framework.technology.interfaceGui.GuiEvent;
import net.akehurst.application.framework.technology.interfaceGui.GuiEventType;
import net.akehurst.application.framework.technology.interfaceGui.IGuiHandler;
import net.akehurst.application.framework.technology.interfaceGui.IGuiScene;
import net.akehurst.application.framework.technology.interfaceGui.IGuiSceneHandler;
import net.akehurst.application.framework.technology.interfaceGui.StageIdentity;

public class SceneHandlerWelcome extends AbstractIdentifiableObject implements IGuiSceneHandler {

	public SceneHandlerWelcome(final String afId) {
		super(afId);
	}

	ISceneWelcome scene;

	IGuiHandler gui;

	@Override
	public ISceneWelcome createScene(final IGuiHandler gui, final StageIdentity stageId, final URL content) {
		this.scene = gui.createScene(stageId, SceneHandlerCommon.sceneIdWelcome, ISceneWelcome.class, this, content);
		return this.scene;
	}

	@Override
	public void loaded(final IGuiHandler gui, final IGuiScene guiScene, final GuiEvent event) {
		final UserSession session = event.getSession();

		this.scene.getActionSignIn().onEvent(session, GuiEventType.CLICK, (ev) -> {
			gui.getScene(SceneHandlerCommon.sceneIdHome).switchTo(session);
		});

	}

}
