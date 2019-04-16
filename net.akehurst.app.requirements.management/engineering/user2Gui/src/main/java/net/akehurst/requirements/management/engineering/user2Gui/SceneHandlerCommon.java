package net.akehurst.requirements.management.engineering.user2Gui;

import net.akehurst.application.framework.technology.interfaceGui.SceneIdentity;

public class SceneHandlerCommon {

	static public SceneIdentity sceneIdHome;
	static public SceneIdentity sceneIdSignIn;
	static public SceneIdentity sceneIdWelcome;
	static {
		try {
			SceneHandlerCommon.sceneIdHome = new SceneIdentity("user");
			SceneHandlerCommon.sceneIdSignIn = new SceneIdentity("signin");
			SceneHandlerCommon.sceneIdWelcome = new SceneIdentity("");
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}
}
