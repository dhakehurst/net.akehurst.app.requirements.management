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

import java.lang.reflect.Method;

import org.junit.Test;

import net.akehurst.application.framework.components.test.MockComponentPort;
import net.akehurst.application.framework.computational.authenticatorInterface.ICAuthenticatorRequest;
import net.akehurst.application.framework.computational.userInterface.authentication.IUserAuthenticationRequest;
import net.akehurst.application.framework.computational.userInterface.authentication.UserSession;
import net.akehurst.requirements.management.computational.userInterface.IUserWelcomeNotification;
import net.akehurst.requirements.management.computational.userInterface.IUserWelcomeRequest;


public class test_RequirementsManager {

	Method method(Class<?> class_, String methodName) {
		try {
			class_.getMethod(methodName);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Test
	public void requestStart() {
		
		RequirementsManager sut = new RequirementsManager("sut");
		MockComponentPort mockGui = new MockComponentPort();
		mockGui.connect(sut.portUserInterface());
		
		UserSession session = new UserSession(null);
		sut.portUserInterface().getProvided(IUserWelcomeRequest.class).requestStart(session);

		mockGui.expect(IUserWelcomeNotification.class, "notifyWelcomeMessage" );
		
		
	}
	
	@Test
	public void requestLogin() {
		RequirementsManager sut = new RequirementsManager("sut");
		MockComponentPort mockGui = new MockComponentPort();
		mockGui.connect(sut.portUserInterface());
		
		UserSession session = new UserSession(null);
		String username = "test";
		String password = "test";
		sut.portUserInterface().getProvided(IUserAuthenticationRequest.class).requestLogin(session, username, password);

		mockGui.expect(ICAuthenticatorRequest.class, "requestLogin");
		
	}
	
}
