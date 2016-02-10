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

import net.akehurst.application.framework.components.AbstractComponent;
import net.akehurst.application.framework.components.Port;
import net.akehurst.application.framework.os.AbstractActiveObject;
import net.akehurst.application.framework.technology.authentication.IAuthenticatorNotification;
import net.akehurst.application.framework.technology.authentication.IAuthenticatorRequest;
import net.akehurst.application.framework.technology.authentication.ISession;
import net.akehurst.application.framework.technology.commsInterface.IPublishSubscribeNotification;
import net.akehurst.application.framework.technology.commsInterface.IPublishSubscribeRequest;
import net.akehurst.application.framework.technology.guiInterface.IGuiNotification;
import net.akehurst.application.framework.technology.guiInterface.IGuiRequest;
import net.akehurst.requirements.management.computational.authInterface.ICAuthenticatorNotification;
import net.akehurst.requirements.management.computational.authInterface.ICAuthenticatorRequest;
import net.akehurst.requirements.management.computational.userInterface.IUserAuthenticationNotification;
import net.akehurst.requirements.management.computational.userInterface.UserSession;

public class AuthenticatorToGui extends AbstractComponent implements ICAuthenticatorRequest, IAuthenticatorNotification {

	public AuthenticatorToGui(String id) {
		super(id);
	}
	
	@Override
	public void afRun() {
		// TODO Auto-generated method stub
		
	}
	
	// --------- ICAuthenticatorRequest ---------
	@Override
	public void requestLogin(UserSession session, String username, String password) {
		portGui().out(IAuthenticatorRequest.class).requestLogin((ISession)session.techObj, username, password);
	}
	
	@Override
	public void requestLogout(UserSession session) {
		portGui().out(IAuthenticatorRequest.class).requestLogout((ISession)session.techObj);
	}
	
	// --------- IAuthenticatorNotification ---------
	@Override
	public void notifyAuthenticationFailure(ISession session, String message) {
		portAuth().out(ICAuthenticatorNotification.class).notifyAuthenticationFailure(new UserSession(session), message);
	}
	
	@Override
	public void notifyAuthenticationSuccess(ISession session) {
		portAuth().out(ICAuthenticatorNotification.class).notifyAuthenticationSuccess(new UserSession(session));
	}
	
	@Override
	public void notifyAuthenticationCleared(ISession session) {
		portAuth().out(ICAuthenticatorNotification.class).notifyAuthenticationCleared(new UserSession(session));
	}
	
	// --------- Ports ---------
	Port portAuth;
	public Port portAuth() {
		if (null==this.portAuth) {
			this.portAuth = new Port("portAuth", this)
					.provides(ICAuthenticatorRequest.class, AuthenticatorToGui.this)
					.requires(ICAuthenticatorNotification.class);
		}
		return this.portAuth;
	}
	
	Port portGui;
	public Port portGui() {
		if (null==this.portGui) {
			this.portGui = new Port("portGui", this)
					.provides(IAuthenticatorNotification.class, AuthenticatorToGui.this)
					.requires(IAuthenticatorRequest.class);
		}
		return this.portGui;
	}
}
