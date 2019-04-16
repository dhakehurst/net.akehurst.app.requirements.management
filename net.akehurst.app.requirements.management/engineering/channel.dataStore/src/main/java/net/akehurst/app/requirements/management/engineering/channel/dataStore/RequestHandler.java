package net.akehurst.app.requirements.management.engineering.channel.dataStore;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Future;

import net.akehurst.app.requirements.management.computational.requirementsInterface.IRequirementsRequest;
import net.akehurst.app.requirements.management.computational.requirementsInterface.Project;
import net.akehurst.app.requirements.management.computational.requirementsInterface.ProjectIdentity;
import net.akehurst.app.requirements.management.engineering.channel.dataStore.jdo.JdoProject;
import net.akehurst.app.requirements.management.engineering.channel.dataStore.transform.DataTransformer;
import net.akehurst.app.requirements.management.engineering.channel.dataStore.transform.Project2JdoProject;
import net.akehurst.application.framework.common.interfaceUser.UserSession;
import net.akehurst.application.framework.realisation.AbstractActiveSignalProcessingObject;
import net.akehurst.application.framework.technology.interfacePersistence.IPersistenceTransaction;
import net.akehurst.application.framework.technology.interfacePersistence.IPersistentStore;
import net.akehurst.application.framework.technology.interfacePersistence.PersistentItemQuery;

public class RequestHandler extends AbstractActiveSignalProcessingObject implements IRequirementsRequest {

	public RequestHandler(final String afId) {
		super(afId);
	}

	public IPersistentStore store;

	@Override
	public Future<Void> requestCreateProject(final UserSession session, final Project project) {
		return super.submit("requestCreateProject", () -> {
			final PersistentItemQuery query = new PersistentItemQuery("");

			final IPersistenceTransaction transaction = this.store.startTransaction();

			final DataITransformer transformer = new DataTransformer();
			final JdoProject jdoProject = transformer.transformLeft2Right(Project2JdoProject.class, project);

			this.store.store(transaction, query, jdoProject, JdoProject.class);
			this.store.commitTransaction(transaction);

		});
	}

	@Override
	public Future<Project> fetchProject(final UserSession session, final ProjectIdentity projectId) {
		return super.submit("fetchProject", Project.class, () -> {
			final PersistentItemQuery query = new PersistentItemQuery("identity==" + projectId.asPrimitive());

			final IPersistenceTransaction transaction = this.store.startTransaction();

			final JdoProject jdoProject = this.store.retrieve(transaction, query, JdoProject.class);

			final DataITransformer transformer = new DataTransformer();
			final Project project = transformer.transformRight2Left(Project2JdoProject.class, jdoProject);

			this.store.commitTransaction(transaction);

			return project;
		});
	}

	@Override
	public Future<List<Project>> fetchAllProject(final UserSession session) {
		return (Future<List<Project>>) (Object) super.submit("fetchAllProject", List.class, () -> {
			final PersistentItemQuery query = new PersistentItemQuery("");

			final IPersistenceTransaction transaction = this.store.startTransaction();

			final Set<JdoProject> jdoProjects = this.store.retrieveAll(transaction, JdoProject.class);

			final DataITransformer transformer = new DataTransformer();
			final List<Project> projects = (List<Project>) transformer.transformAllRight2Left(Project2JdoProject.class, new ArrayList<>(jdoProjects));

			this.store.commitTransaction(transaction);

			return projects;
		});
	}

	@Override
	public Future<Void> requestUpdateProject(final UserSession session, final ProjectIdentity projectId, final Project project) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Future<Void> requestDeleteProject(final UserSession session, final ProjectIdentity projectId) {
		return super.submit("requestCreateProject", () -> {
			final PersistentItemQuery query = new PersistentItemQuery("identity==" + projectId.asPrimitive());

			final IPersistenceTransaction transaction = this.store.startTransaction();

			this.store.remove(transaction, query, JdoProject.class);

			this.store.commitTransaction(transaction);

		});
	}

}
