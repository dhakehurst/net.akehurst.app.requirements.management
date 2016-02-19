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
import net.akehurst.application.framework.computational.authenticatorInterface.AuthenticatorSession;
import net.akehurst.application.framework.computational.authenticatorInterface.ICAuthenticatorNotification;
import net.akehurst.application.framework.computational.authenticatorInterface.ICAuthenticatorRequest;
import net.akehurst.application.framework.computational.userInterface.authentication.IUserAuthenticationNotification;
import net.akehurst.application.framework.computational.userInterface.authentication.IUserAuthenticationRequest;
import net.akehurst.application.framework.computational.userInterface.authentication.UserSession;
import net.akehurst.application.framework.os.AbstractActiveObject;
import net.akehurst.application.framework.os.IActiveObject;

import net.akehurst.requirements.management.computational.userInterface.IUserHomeNotification;
import net.akehurst.requirements.management.computational.userInterface.IUserHomeRequest;
import net.akehurst.requirements.management.computational.userInterface.IUserWelcomeNotification;
import net.akehurst.requirements.management.computational.userInterface.IUserWelcomeRequest;


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
		AuthenticatorSession as = new AuthenticatorSession(session.techObj);
		this.portAuth().out(ICAuthenticatorRequest.class).requestLogin(as, username, password);
	}
	
	@Override
	public void requestLogout(UserSession session) {
		AuthenticatorSession as = new AuthenticatorSession(session.techObj);
		this.portAuth().out(ICAuthenticatorRequest.class).requestLogout(as);
	}
	
	// --------- IUserHomeRequest ---------
	
	
	//--------- ICAuthenticatorNotification ---------
	@Override
	public void notifyAuthenticationSuccess(AuthenticatorSession session) {
		UserSession us = new UserSession(session.techObj);
		this.portUserInterface().out(IUserAuthenticationNotification.class).notifyAuthenticationSuccess(us);
	};
	
	@Override
	public void notifyAuthenticationFailure(AuthenticatorSession session, String message) {
		UserSession us = new UserSession(session.techObj);
		this.portUserInterface().out(IUserAuthenticationNotification.class).notifyAuthenticationFailure(us,  message);
	}
	
	@Override
	public void notifyAuthenticationCleared(AuthenticatorSession session) {
		UserSession us = new UserSession(session.techObj);
		this.portUserInterface().out(IUserAuthenticationNotification.class).notifyAuthenticationCleared(us);
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
