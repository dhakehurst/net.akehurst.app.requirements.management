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

import net.akehurst.application.framework.common.annotations.instance.ComponentInstance;
import net.akehurst.application.framework.common.annotations.instance.ServiceInstance;
import net.akehurst.application.framework.engineering.authenticator2Gui.AuthenticatorToGui;
import net.akehurst.application.framework.realisation.AbstractApplication;
import net.akehurst.application.framework.technology.filesystem.StandardFilesystem;
import net.akehurst.application.framework.technology.gui.vertx.VertxWebsite;
import net.akehurst.application.framework.technology.log4j.Log4JLogger;
import net.akehurst.application.framework.technology.persistence.filesystem.HJsonFile;
import net.akehurst.application.framework.technology.persistence.jdo.JdoPersistence;
import net.akehurst.requirements.management.computational.requirementsManagement.RequirementsManager;
import net.akehurst.requirements.management.engineering.user2Gui.UserToGui;

public class RequirementsManagementApplication extends AbstractApplication {

	public RequirementsManagementApplication(final String id) {
		super(id);
	}

	@ServiceInstance
	Log4JLogger logger;

	@ServiceInstance
	StandardFilesystem fs;

	@ServiceInstance
	HJsonFile configuration;

	// --- Computational ---

	@ComponentInstance
	RequirementsManager requirementsManager;

	// --- Engineering ---
	@ComponentInstance
	UserToGui userToGui;

	@ComponentInstance
	AuthenticatorToGui authenticatorToGui;

	// --- Technology ---
	@ComponentInstance
	VertxWebsite gui;

	@ComponentInstance
	JdoPersistence dataStore;

	@Override
	public void afConnectParts() {
		this.requirementsManager.portUserInterface().connect(this.userToGui.portUserInterface());
		this.requirementsManager.portAuth().connect(this.authenticatorToGui.portAuth());
		this.userToGui.portGui().connect(this.gui.portGui());
		this.authenticatorToGui.portGui().connect(this.gui.portGui());
	}

}