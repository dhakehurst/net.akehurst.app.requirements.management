package net.akehurst.requirements.management.engineering.user2Gui;

import java.net.URL;

import net.akehurst.application.framework.common.annotations.instance.ConfiguredValue;
import net.akehurst.application.framework.common.annotations.instance.IdentifiableObjectInstance;
import net.akehurst.application.framework.technology.gui.common.AbstractGuiHandler;
import net.akehurst.application.framework.technology.interfaceGui.GuiEvent;
import net.akehurst.application.framework.technology.interfaceGui.IGuiNotification;
import net.akehurst.application.framework.technology.interfaceGui.StageIdentity;

public class GuiHandler extends AbstractGuiHandler implements IGuiNotification {

	public GuiHandler(final String id) {
		super(id);
	}

	@IdentifiableObjectInstance
	SceneHandlerWelcome sceneHandlerWelcome;

	@IdentifiableObjectInstance
	SceneHandlerAuthentication sceneHandlerAuthentication;

	@IdentifiableObjectInstance
	SceneHandlerHome sceneHandlerHome;

	// --------- AbstractGuiHandler ---------
	@ConfiguredValue(defaultValue = "")
	StageIdentity stageIdWelcome;

	@ConfiguredValue(defaultValue = "auth")
	StageIdentity stageIdAuth;

	@ConfiguredValue(defaultValue = "css")
	StageIdentity stageIdStyle;

	@ConfiguredValue(defaultValue = "home")
	StageIdentity stageIdHome;

	@Override
	public void notifyReady() {

		this.getGuiRequest().createStage(this.stageIdStyle, "/css", null, null);

		this.getGuiRequest().createStage(this.stageIdAuth, "/af", null, null);

		this.getGuiRequest().createStage(this.stageIdHome, "/secure", this.stageIdAuth, SceneHandlerCommon.sceneIdSignIn);

		this.getGuiRequest().createStage(this.stageIdWelcome, "/unsecure", null, null);

	}

	@Override
	protected void onStageCreated(final GuiEvent event) {
		final URL content = null;// this.getClass().getResource(this.urlStr);
		final StageIdentity currentStage = event.getSignature().getStageId();
		if (this.stageIdHome.equals(currentStage)) {
			this.sceneHandlerHome.createScene(this, this.stageIdHome, content);
		} else if (this.stageIdAuth.equals(currentStage)) {
			this.sceneHandlerAuthentication.createScene(this, this.stageIdAuth, content);
		} else if (this.stageIdWelcome.equals(currentStage)) {
			this.sceneHandlerWelcome.createScene(this, this.stageIdWelcome, content);
		}

	}

}
