/**
 * Copyright (C) 2016 Dr. David H. Akehurst (http://dr.david.h.akehurst.net)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.akehurst.requirements.management.engineering.user2Gui;

import java.net.URL;
import java.util.Map;

import net.akehurst.application.framework.components.AbstractComponent;
import net.akehurst.application.framework.components.Port;
import net.akehurst.application.framework.os.AbstractActiveObject;
import net.akehurst.application.framework.technology.authentication.IAuthenticatorNotification;
import net.akehurst.application.framework.technology.authentication.ISession;
import net.akehurst.application.framework.technology.authentication.IUser;
import net.akehurst.application.framework.technology.commsInterface.ChannelIdentity;
import net.akehurst.application.framework.technology.commsInterface.IPublishSubscribeNotification;
import net.akehurst.application.framework.technology.commsInterface.IPublishSubscribeRequest;
import net.akehurst.application.framework.technology.commsInterface.ISenderReceiverNotification;
import net.akehurst.application.framework.technology.commsInterface.ISenderReceiverSource;
import net.akehurst.application.framework.technology.guiInterface.IGuiNotification;
import net.akehurst.application.framework.technology.guiInterface.IGuiRequest;
import net.akehurst.requirements.management.computational.authInterface.ICAuthenticatorRequest;
import net.akehurst.requirements.management.computational.userInterface.IUserAuthenticationNotification;
import net.akehurst.requirements.management.computational.userInterface.IUserAuthenticationRequest;
import net.akehurst.requirements.management.computational.userInterface.IUserHomeNotification;
import net.akehurst.requirements.management.computational.userInterface.IUserWelcomeNotification;
import net.akehurst.requirements.management.computational.userInterface.IUserWelcomeRequest;
import net.akehurst.requirements.management.computational.userInterface.UserSession;

public class UserToGui extends AbstractComponent
	implements
		IUserWelcomeNotification,
		IUserAuthenticationNotification,
		IUserHomeNotification,
		IGuiNotification
{

	public UserToGui(String objectId) {
		super(objectId);
	}
		
	//---------- IActiveObject ---------
	@Override
	public void afRun() {

	}
	
	boolean isAuthenticated(IUser user) {
		return !user.getName().equals("<unknown>");
	}

	//---------- IUserAuthenticationNotification ---------
	@Override
	public void notifyAuthenticationSuccess(UserSession session) {
		ISession sess = (ISession)session.techObj;
		this.portGui().out(IGuiRequest.class).switchTo(sess, STAGE_HOME, "");
	}
	
	@Override
	public void notifyAuthenticationFailure(UserSession session, String message) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void notifyAuthenticationCleared(UserSession session) {
		ISession sess = (ISession)session.techObj;
		this.portGui().out(IGuiRequest.class).switchTo(sess, STAGE_WELCOME, "");
	}
	
	//---------- IUserWelcomeNotification ---------
	@Override
	public void notifyWelcomeMessage(UserSession session, String message) {
		ISession sess = (ISession)session.techObj;
		this.portGui().out(IGuiRequest.class).setText(sess, STAGE_WELCOME, "textWelcome", message);
	}
	
	// --------- IUserHomeNotification ---------
	
	
	//---------- IGuiNotification ---------
	static final String STAGE_WELCOME = "/welcome/";
	static final String STAGE_HOME = "/home/";
	/**
	 * Occurs once the GUI is ready for displaying scenes
	 */
	@Override
	public void notifyReady() {
		
		URL rootUrl = this.getClass().getResource("/webroot/");
		portGui().out(IGuiRequest.class).createStage(STAGE_WELCOME, false, rootUrl);
		
		URL homeUrl = this.getClass().getResource("/home/");
		portGui().out(IGuiRequest.class).createStage(STAGE_HOME, true, homeUrl);
		
		
//		portGui().out(I.class).receiverOf("/api/login");
		
	};
	
	@Override
	public void notifyStageLoaded(ISession session, String sceneId) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Occurs after the scene has loaded (is displayed)
	 * for adding dynamic content specific to the user
	 */
	@Override
	public void notifySceneLoaded(ISession session, String sceneId) {
		this.portGui().out(IGuiRequest.class).setTitle(session, sceneId, "Requirements Management");

		if(this.isAuthenticated(session.getUser())) {
			String content = "";
			content += "<input id='inputLogout' type='submit' value='Logout' />";
 			this.portGui().out(IGuiRequest.class).addElement(session, sceneId, "root", "fsAuthentication", "fieldset", "{'method':'post'}", content);
 			this.portGui().out(IGuiRequest.class).requestRecieveEvent(session, sceneId, "inputLogout", "click");
		} else {
			String content = "";
			content += "<form action='javascript:comms_sendMessage()'>";
			content += "  <label name='email'>Email</label>";
			content += "  <input id='username' required type='text' value='' placeholder='email@example.com' />";
			content += "  <label name='password'>Password</label>";
			content += "  <input id='password' required type='password' />";
			content += "  <input id='inputLogin' type='submit' value='Login' />";
			content += "<form>";
 			this.portGui().out(IGuiRequest.class).addElement(session, sceneId,"root", "fsAuthentication", "fieldset", "{}", content);

 			this.portGui().out(IGuiRequest.class).requestRecieveEvent(session, sceneId, "inputLogin", "click");
 			
// 			this.portGui().out(IGuiNotification.class).subscribeTo(SCENE_ROOT, "inputLogin", "click");
		}
		
		if (STAGE_WELCOME.equals(sceneId) || "index".equals(sceneId)) {

			this.portGui().out(IGuiRequest.class).addElement(session, STAGE_WELCOME, "root", "textTitle", "h1");
			this.portGui().out(IGuiRequest.class).addElement(session, STAGE_WELCOME, "root", "textWelcome", "p");
			
			this.portGui().out(IGuiRequest.class).setText(session, STAGE_WELCOME, "textTitle", "Requirements Management");
		} else if (STAGE_HOME.equals(sceneId)) {
			this.portGui().out(IGuiRequest.class).addElement(session, STAGE_HOME, "root", "textTitle", "h1");
			this.portGui().out(IGuiRequest.class).setText(session, STAGE_HOME, "textTitle", "Home for "+session.getUser().getName());
		}
	};
	
	@Override
	public void notifyEventOccured(ISession session, String sceneId, String elementId, String eventType, Map<String, Object> eventData) {
		System.out.println("Recieved event "+eventType + " from "+elementId);
		if ("IGuiNotification.notifySceneLoaded".equals(eventType)) {
			this.notifySceneLoaded(session, sceneId);
		} else if ("inputLogin".equals(elementId)) {
			String userName = (String)eventData.get("username");
			String password = (String)eventData.get("password");
			this.portUserInterface().out(IUserAuthenticationRequest.class).requestLogin(new UserSession(session), userName, password);
		} else if ("inputLogout".equals(elementId)) {
			this.portUserInterface().out(IUserAuthenticationRequest.class).requestLogout(new UserSession(session));
		}
	}
	
	//--------- Ports ---------
	Port portUserInterface;
	public Port portUserInterface() {
		if (null==this.portUserInterface) {
			this.portUserInterface = new Port("portUserInterface",this)
					.provides(IUserWelcomeNotification.class, UserToGui.this)
					.provides(IUserAuthenticationNotification.class, UserToGui.this)
					.provides(IUserHomeNotification.class, UserToGui.this)
					.requires(IUserWelcomeRequest.class)
					.requires(IUserAuthenticationRequest.class);
		}
		return this.portUserInterface;
	}

	Port portGui;
	public Port portGui() {
		if (null==this.portGui) {
			this.portGui = new Port("portGui",this)
					.provides(IGuiNotification.class, UserToGui.this)
					.requires(IGuiRequest.class)
			;
		}
		return this.portGui;
	}
	

}
