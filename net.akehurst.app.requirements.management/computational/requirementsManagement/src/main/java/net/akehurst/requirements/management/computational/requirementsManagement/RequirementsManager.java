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

import java.util.ArrayList;
import java.util.List;

import net.akehurst.app.requirements.management.computational.requirementsInterface.Project;
import net.akehurst.application.framework.common.UserDetails;
import net.akehurst.application.framework.common.UserSession;
import net.akehurst.application.framework.common.annotations.declaration.Component;
import net.akehurst.application.framework.common.annotations.instance.PortInstance;
import net.akehurst.application.framework.computational.interfaceAuthenticator.AuthenticatorSession;
import net.akehurst.application.framework.computational.interfaceAuthenticator.ICAuthenticatorNotification;
import net.akehurst.application.framework.computational.interfaceAuthenticator.ICAuthenticatorRequest;
import net.akehurst.application.framework.computational.interfaceUser.authentication.IUserAuthenticationNotification;
import net.akehurst.application.framework.computational.interfaceUser.authentication.IUserAuthenticationRequest;
import net.akehurst.application.framework.realisation.AbstractComponent;
import net.akehurst.application.framework.realisation.Port;
import net.akehurst.requirements.management.computational.userInterface.IUserHomeNotification;
import net.akehurst.requirements.management.computational.userInterface.IUserHomeRequest;

@Component
public class RequirementsManager extends AbstractComponent implements IUserAuthenticationRequest, IUserHomeRequest, ICAuthenticatorNotification {

	public RequirementsManager(final String objectId) {
		super(objectId);
	}

	@Override
	public void afRun() {

	}

	// --------- IUserAuthenticationRequest ---------
	@Override
	public void requestLogin(final UserSession session, final String username, final String password) {
		final AuthenticatorSession as = new AuthenticatorSession(session.getId(), null);
		this.portAuth().out(ICAuthenticatorRequest.class).requestLogin(as, username, password);
	}

	@Override
	public void requestLogout(final UserSession session) {
		final AuthenticatorSession as = new AuthenticatorSession(session.getId(), null);
		this.portAuth().out(ICAuthenticatorRequest.class).requestLogout(as);
	}

	// --------- IUserHomeRequest ---------

	@Override
	public void requestStartHome(final UserSession session) {
		final List<Project> projectList = new ArrayList<>();

		// TODO: get from datastore
		projectList.add(new Project("Project1", "Dave"));
		projectList.add(new Project("Project2", "Jim"));
		projectList.add(new Project("Project3", "George"));
		projectList.add(new Project("Project4", "Fred"));
		projectList.add(new Project("Project5", "Jane"));
		projectList.add(new Project("Project6", "Jo"));

		this.portUserInterface().out(IUserHomeNotification.class).notifyProjectList(session, projectList);
	}

	@Override
	public void requestNewProject() {
		// TODO Auto-generated method stub

	}

	// --------- ICAuthenticatorNotification ----------
	@Override
	public void notifyAuthenticationSuccess(final AuthenticatorSession session) {
		final UserSession us = new UserSession(session.getId(), new UserDetails(session.getUser().getName()));
		this.portUserInterface().out(IUserAuthenticationNotification.class).notifyAuthenticationSuccess(us);
	};

	@Override
	public void notifyAuthenticationFailure(final AuthenticatorSession session, final String message) {
		final UserSession us = new UserSession(session.getId(), null);
		this.portUserInterface().out(IUserAuthenticationNotification.class).notifyAuthenticationFailure(us, message);
	}

	@Override
	public void notifyAuthenticationCleared(final AuthenticatorSession session) {
		final UserSession us = new UserSession(session.getId(), null);
		this.portUserInterface().out(IUserAuthenticationNotification.class).notifyAuthenticationCleared(us);
	}

	// --------- Ports
	@PortInstance(provides = { IUserAuthenticationRequest.class, IUserHomeRequest.class }, requires = { IUserAuthenticationNotification.class,
			IUserHomeNotification.class })
	Port portUserInterface;

	public Port portUserInterface() {
		return this.portUserInterface;
	}

	@PortInstance(provides = { ICAuthenticatorNotification.class }, requires = { ICAuthenticatorRequest.class })
	Port portAuth;

	public Port portAuth() {
		return this.portAuth;
	}
}
