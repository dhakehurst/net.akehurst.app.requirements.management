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

import net.akehurst.application.framework.common.annotations.declaration.Component;
import net.akehurst.application.framework.common.annotations.declaration.ProvidesInterfaceForPort;
import net.akehurst.application.framework.common.annotations.instance.ActiveObjectInstance;
import net.akehurst.application.framework.common.annotations.instance.PortInstance;
import net.akehurst.application.framework.computational.interfaceUser.authentication.IUserAuthenticationNotification;
import net.akehurst.application.framework.computational.interfaceUser.authentication.IUserAuthenticationRequest;
import net.akehurst.application.framework.realisation.AbstractComponent;
import net.akehurst.application.framework.realisation.Port;
import net.akehurst.application.framework.technology.guiInterface.IGuiNotification;
import net.akehurst.application.framework.technology.guiInterface.IGuiRequest;
import net.akehurst.requirements.management.computational.userInterface.IUserHomeNotification;
import net.akehurst.requirements.management.computational.userInterface.IUserHomeRequest;

@Component
public class UserToGui extends AbstractComponent {

	public UserToGui(final String objectId) {
		super(objectId);
	}

	@ActiveObjectInstance
	@ProvidesInterfaceForPort(portId = "portUserInterface", provides = IUserAuthenticationNotification.class)
	@ProvidesInterfaceForPort(portId = "portUserInterface", provides = IUserHomeNotification.class)
	@ProvidesInterfaceForPort(portId = "portGui", provides = IGuiNotification.class)
	GuiHandler handler;

	@Override
	public void afConnectParts() {
		this.handler.setGuiRequest(this.portGui.out(IGuiRequest.class));
		this.handler.userAuthenticationRequest = this.portUserInterface.out(IUserAuthenticationRequest.class);
		this.handler.userHomeRequest = this.portUserInterface.out(IUserHomeRequest.class);
	}

	// --------- IUserHomeNotification ---------

	// ---------- IGuiNotification ---------

	// @Override
	// public void notifyEventOccured(final UserSession session, final String sceneId, final String elementId, final String eventType,
	// final Map<String, Object> eventData) {
	// System.out.println("Recieved event " + eventType + " from " + elementId);
	// final UserSession us = new UserSession(session.getId(), new UserDetails(null == session.getUser() ? null : session.getUser().getName()));
	// if ("IGuiNotification.notifySceneLoaded".equals(eventType)) {
	// this.notifySceneLoaded(session, sceneId);
	// } else if ("inputLogin".equals(elementId)) {
	// final String userName = (String) eventData.get("username");
	// final String password = (String) eventData.get("password");
	// this.portUserInterface().out(IUserAuthenticationRequest.class).requestLogin(us, userName, password);
	// } else if ("inputLogout".equals(elementId)) {
	// this.portUserInterface().out(IUserAuthenticationRequest.class).requestLogout(us);
	// }
	// }

	// ---------- private -----------

	// --------- Ports ---------
	@PortInstance(provides = { IUserAuthenticationNotification.class, IUserHomeNotification.class }, requires = { IUserAuthenticationRequest.class,
			IUserHomeRequest.class })
	Port portUserInterface;

	public Port portUserInterface() {
		return this.portUserInterface;
	}

	@PortInstance(provides = { IGuiNotification.class }, requires = { IGuiRequest.class })
	Port portGui;

	public Port portGui() {
		return this.portGui;
	}

}
