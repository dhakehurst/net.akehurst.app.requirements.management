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
package net.akehurst.requirements.management.application.web;

import net.akehurst.app.requirements.management.engineering.channel.dataStore.Requirements2DataStore;
import net.akehurst.application.framework.common.annotations.instance.ComponentInstance;
import net.akehurst.application.framework.common.annotations.instance.ServiceInstance;
import net.akehurst.application.framework.engineering.authenticator.AuthenticatorChannel;
import net.akehurst.application.framework.realisation.AbstractApplication;
import net.akehurst.application.framework.service.configuration.file.HJsonConfigurationService;
import net.akehurst.application.framework.technology.authentication.any.AnyAuthenticator;
import net.akehurst.application.framework.technology.filesystem.StandardFilesystem;
import net.akehurst.application.framework.technology.gui.vertx.VertxWebsite;
import net.akehurst.application.framework.technology.log4j.Log4JLogger;
import net.akehurst.application.framework.technology.persistence.jdo.JdoPersistence;
import net.akehurst.requirements.management.computational.requirementsManagement.RequirementsManager;
import net.akehurst.requirements.management.engineering.user2Gui.UserToGui;

public class RequirementsManagerApplication extends AbstractApplication {

	public RequirementsManagerApplication(final String id) {
		super(id);
	}

	@ServiceInstance
	Log4JLogger logger;

	@ServiceInstance
	StandardFilesystem fs;

	@ServiceInstance
	HJsonConfigurationService configuration;

	// --- Computational ---

	@ComponentInstance
	RequirementsManager requirementsManager;

	// --- Engineering ---
	@ComponentInstance
	UserToGui userToGui;

	@ComponentInstance
	AuthenticatorChannel authenticatorChannel;

	@ComponentInstance
	Requirements2DataStore req2Store;

	// --- Technology ---
	@ComponentInstance
	VertxWebsite gui;

	@ComponentInstance
	JdoPersistence dataStore;

	@ComponentInstance
	AnyAuthenticator authenticator;

	@Override
	public void afConnectParts() {
		// computational <-> engineering
		this.requirementsManager.portUser().connect(this.userToGui.portUserInterface());
		this.requirementsManager.portAuth().connect(this.authenticatorChannel.portComputational());
		this.requirementsManager.portData().connect(this.req2Store.portStore());

		// engineering <-> technology
		this.userToGui.portGui().connect(this.gui.portGui());
		this.authenticatorChannel.portTechnology().connect(this.authenticator.portAuth());
		this.req2Store.portPersist().connect(this.dataStore.portPersist());
	}

}