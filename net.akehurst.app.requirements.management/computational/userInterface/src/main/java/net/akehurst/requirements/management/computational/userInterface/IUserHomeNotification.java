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
package net.akehurst.requirements.management.computational.userInterface;

import java.util.List;

import net.akehurst.app.requirements.management.computational.requirementsInterface.Project;
import net.akehurst.app.requirements.management.computational.requirementsInterface.ProjectIdentity;
import net.akehurst.application.framework.common.interfaceUser.UserSession;

public interface IUserHomeNotification {

	void notifyProjectCreated(UserSession session, Project project);

	void notifyProjectList(UserSession session, List<Project> projectList);

	void notifyProject(UserSession session, Project project);

	void notifyProjectUpdated(UserSession session, Project project);

	void notifyProjectDeleted(UserSession session, ProjectIdentity projectId);

}
