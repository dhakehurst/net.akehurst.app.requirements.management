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

import net.akehurst.application.framework.engineering.authenticator2Gui.AuthenticatorToGui;
import net.akehurst.application.framework.os.AbstractActiveObject;
import net.akehurst.application.framework.os.IActiveObject;
import net.akehurst.application.framework.technology.gui.vertx.VertxWebsite;
import net.akehurst.requirements.management.computational.requirementsManagement.RequirementsManager;
import net.akehurst.requirements.management.engineering.user2Gui.UserToGui;

public class Application extends AbstractActiveObject implements IActiveObject {

	public Application() {
		super("application");
		this.requirementsManager = new RequirementsManager("requirementsManager");
		this.userToGui = new UserToGui("userToGui");
		this.authenticatorToGui = new AuthenticatorToGui("authenticatorToGui");
		this.gui = new VertxWebsite("gui",9999);
		
		this.requirementsManager.portUserInterface().connect( this.userToGui.portUserInterface() );
		this.requirementsManager.portAuth().connect( this.authenticatorToGui.portAuth() );
		this.userToGui.portGui().connect( this.gui.portGui() );
		this.authenticatorToGui.portGui().connect( this.gui.portGui() );
	}
	
	RequirementsManager requirementsManager;
	
	UserToGui userToGui;
	AuthenticatorToGui authenticatorToGui;
	
	VertxWebsite gui;

	@Override
	public void afRun() {
		try {
		
			this.gui.afStart();
			this.userToGui.afStart();
			this.authenticatorToGui.afStart();
			this.requirementsManager.afStart();
			
			this.requirementsManager.afJoin();
			this.userToGui.afJoin();
			this.authenticatorToGui.afJoin();
			this.gui.afJoin();
			
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}

}