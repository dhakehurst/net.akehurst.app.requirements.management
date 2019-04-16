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

import net.akehurst.app.requirements.management.computational.requirementsInterface.IRequirementsRequest;
import net.akehurst.application.framework.common.IPort;
import net.akehurst.application.framework.common.annotations.declaration.Component;
import net.akehurst.application.framework.common.annotations.instance.ActiveObjectInstance;
import net.akehurst.application.framework.common.annotations.instance.ComponentInstance;
import net.akehurst.application.framework.common.annotations.instance.PortContract;
import net.akehurst.application.framework.common.annotations.instance.PortInstance;
import net.akehurst.application.framework.computational.authentication.AuthenticationDelegator;
import net.akehurst.application.framework.computational.interfaceAuthenticator.ICAuthenticatorNotification;
import net.akehurst.application.framework.computational.interfaceAuthenticator.ICAuthenticatorRequest;
import net.akehurst.application.framework.computational.interfaceUser.authentication.IUserAuthenticationNotification;
import net.akehurst.application.framework.computational.interfaceUser.authentication.IUserAuthenticationRequest;
import net.akehurst.application.framework.realisation.AbstractComponent;
import net.akehurst.requirements.management.computational.userInterface.IUserHomeNotification;
import net.akehurst.requirements.management.computational.userInterface.IUserHomeRequest;

@Component
public class RequirementsManager extends AbstractComponent {

	public RequirementsManager(final String objectId) {
		super(objectId);
	}

	@ComponentInstance
	AuthenticationDelegator authHandler;

	@ActiveObjectInstance
	UserHandler userHandler;

	@Override
	public void afConnectParts() {
		this.portAuth().connectInternal(this.authHandler.portProvider());
		this.portUser().connectInternal(this.authHandler.portUser());
		this.portUser().connectInternal(this.userHandler);
		this.portData().connectInternal(this.userHandler);
	}

	// --------- Ports
	@PortInstance
	@PortContract(provides = IUserHomeRequest.class, requires = IUserHomeNotification.class)
	@PortContract(provides = IUserAuthenticationRequest.class, requires = IUserAuthenticationNotification.class)
	IPort portUser;

	public IPort portUser() {
		return this.portUser;
	}

	@PortInstance
	@PortContract(provides = ICAuthenticatorNotification.class, requires = ICAuthenticatorRequest.class)
	IPort portAuth;

	public IPort portAuth() {
		return this.portAuth;
	}

	@PortInstance
	@PortContract(requires = IRequirementsRequest.class)
	IPort portData;

	public IPort portData() {
		return this.portData;
	}
}
