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
package net.akehurst.requirements.management.computational.requirementsManagement;

import net.akehurst.application.framework.components.AbstractComponent;
import net.akehurst.application.framework.components.Port;
import net.akehurst.application.framework.os.AbstractActiveObject;
import net.akehurst.application.framework.os.IActiveObject;
import net.akehurst.requirements.management.computational.authInterface.ICAuthenticatorNotification;
import net.akehurst.requirements.management.computational.authInterface.ICAuthenticatorRequest;
import net.akehurst.requirements.management.computational.userInterface.IUserAuthenticationNotification;
import net.akehurst.requirements.management.computational.userInterface.IUserAuthenticationRequest;
import net.akehurst.requirements.management.computational.userInterface.IUserHomeNotification;
import net.akehurst.requirements.management.computational.userInterface.IUserHomeRequest;
import net.akehurst.requirements.management.computational.userInterface.IUserWelcomeNotification;
import net.akehurst.requirements.management.computational.userInterface.IUserWelcomeRequest;
import net.akehurst.requirements.management.computational.userInterface.UserSession;

public class RequirementsManager extends AbstractComponent
implements IUserWelcomeRequest, IUserAuthenticationRequest, IUserHomeRequest, ICAuthenticatorNotification {

	public RequirementsManager(String objectId) {
		super(objectId);
	}
	
	
	@Override
	public void afRun() {

	}

	//--------- IUserWelcomeRequest ---------
	@Override
	public void requestStart(UserSession session) {
		this.portUserInterface().out(IUserWelcomeNotification.class).notifyWelcomeMessage(session, "Welcome to the Requirements Manager");
	}
	
	// --------- IUserAuthenticationRequest ---------
	@Override
	public void requestLogin(UserSession session, String username, String password) {
		this.portAuth().out(ICAuthenticatorRequest.class).requestLogin(session, username, password);
	}
	
	@Override
	public void requestLogout(UserSession session) {
		this.portAuth().out(ICAuthenticatorRequest.class).requestLogout(session);
	}
	
	// --------- IUserHomeRequest ---------
	
	
	//--------- ICAuthenticatorNotification ---------
	public void notifyAuthenticationSuccess(UserSession session) {
		this.portUserInterface().out(IUserAuthenticationNotification.class).notifyAuthenticationSuccess(session);
	};
	
	@Override
	public void notifyAuthenticationFailure(UserSession session, String message) {
		this.portUserInterface().out(IUserAuthenticationNotification.class).notifyAuthenticationFailure( session,  message);
	}
	
	@Override
	public void notifyAuthenticationCleared(UserSession session) {
		this.portUserInterface().out(IUserAuthenticationNotification.class).notifyAuthenticationCleared( session);
	}

	//--------- Ports
	Port portUserInterface;
	public Port portUserInterface() {
		if (null==this.portUserInterface) {
			this.portUserInterface = new Port("portUserInterface",this)
					.provides(IUserWelcomeRequest.class, RequirementsManager.this)
					.provides(IUserAuthenticationRequest.class, RequirementsManager.this)
					.provides(IUserHomeRequest.class, RequirementsManager.this)
					.requires(IUserWelcomeNotification.class)
					.requires(IUserAuthenticationNotification.class)
					.requires(IUserHomeNotification.class);
		}
		return this.portUserInterface;
	}
	
	Port portAuth;
	public Port portAuth() {
		if (null==this.portAuth) {
			this.portAuth = new Port("portAuth",this)
					.provides(ICAuthenticatorNotification.class, RequirementsManager.this)
					.requires(ICAuthenticatorRequest.class);
		}
		return this.portAuth;
	}
}
