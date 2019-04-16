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

import net.akehurst.application.framework.common.IPort;
import net.akehurst.application.framework.common.annotations.declaration.Component;
import net.akehurst.application.framework.common.annotations.instance.ActiveObjectInstance;
import net.akehurst.application.framework.common.annotations.instance.PortContract;
import net.akehurst.application.framework.common.annotations.instance.PortInstance;
import net.akehurst.application.framework.computational.interfaceUser.authentication.IUserAuthenticationNotification;
import net.akehurst.application.framework.computational.interfaceUser.authentication.IUserAuthenticationRequest;
import net.akehurst.application.framework.realisation.AbstractComponent;
import net.akehurst.application.framework.technology.interfaceGui.IGuiNotification;
import net.akehurst.application.framework.technology.interfaceGui.IGuiRequest;
import net.akehurst.requirements.management.computational.userInterface.IUserHomeNotification;
import net.akehurst.requirements.management.computational.userInterface.IUserHomeRequest;

@Component
public class UserToGui extends AbstractComponent {

	public UserToGui(final String objectId) {
		super(objectId);
	}

	@ActiveObjectInstance
	GuiHandler handler;

	@Override
	public void afConnectParts() {
		// this.handler.setGuiRequest(this.portGui.out(IGuiRequest.class));
		this.portGui().connectInternal(this.handler);
		this.portUserInterface().connectInternal(this.handler.sceneHandlerWelcome);
		this.portUserInterface().connectInternal(this.handler.sceneHandlerAuthentication);
		this.portUserInterface().connectInternal(this.handler.sceneHandlerHome);
		//
		// this.handler.userAuthenticationRequest = this.portUserInterface.out(IUserAuthenticationRequest.class);
		// this.handler.userHomeRequest = this.portUserInterface.out(IUserHomeRequest.class);
	}

	// --------- Ports ---------
	@PortInstance
	@PortContract(provides = IUserAuthenticationNotification.class, requires = IUserAuthenticationRequest.class)
	@PortContract(provides = IUserHomeNotification.class, requires = IUserHomeRequest.class)
	IPort portUserInterface;

	public IPort portUserInterface() {
		return this.portUserInterface;
	}

	@PortInstance
	@PortContract(provides = IGuiNotification.class, requires = IGuiRequest.class)
	IPort portGui;

	public IPort portGui() {
		return this.portGui;
	}

}
