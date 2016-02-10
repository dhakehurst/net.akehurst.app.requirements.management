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
package net.akehurst.requirements.management.technology.gui.vertx;

import io.vertx.ext.web.Session;
import net.akehurst.application.framework.technology.authentication.ISession;
import net.akehurst.application.framework.technology.authentication.IUser;

/**
 * temporary
 * wraps the vertx session
 * TODO: dont pass the vertx session out of the component
 * (just pass the session id perhaps)
 * 
 * @author akehurst
 *
 */
public class SesssionWrapper implements ISession {

	public SesssionWrapper(Session session) {
		this.session = session;
	}
	Session session;
	
	
	@Override
	public IUser getUser() {
		return new UserData(this.session);
	}
	
	public void clearUser() {
		this.put("__vertx.userHolder",null);
	}
	
	@Override
	public <T> T get(String key) {
		return this.session.get(key);
	}
	
	@Override
	public <T> T remove(String key) {
		return this.session.remove(key);
	}
	
	@Override
	public ISession put(String key, Object value) {
		this.session.put(key,value);
		return this;
	}
}
