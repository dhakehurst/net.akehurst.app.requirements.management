package net.akehurst.app.requirements.management.engineering.channel.dataStore;

import java.util.HashMap;
import java.util.Map;

import net.akehurst.app.requirements.management.computational.requirementsInterface.Chapter;
import net.akehurst.app.requirements.management.computational.requirementsInterface.IRequirementsNotification;
import net.akehurst.app.requirements.management.computational.requirementsInterface.IRequirementsRequest;
import net.akehurst.app.requirements.management.computational.requirementsInterface.Project;
import net.akehurst.app.requirements.management.engineering.channel.dataStore.jdo.JdoProject;
import net.akehurst.application.framework.common.IPort;
import net.akehurst.application.framework.common.UserSession;
import net.akehurst.application.framework.common.annotations.instance.PortInstance;
import net.akehurst.application.framework.realisation.AbstractComponent;
import net.akehurst.application.framework.technology.interfacePersistence.IPersistenceTransaction;
import net.akehurst.application.framework.technology.interfacePersistence.IPersistentStore;
import net.akehurst.application.framework.technology.interfacePersistence.PersistentItemLocation;
import net.akehurst.application.framework.technology.interfacePersistence.PersistentStoreException;

public class Requirements2DataStore extends AbstractComponent implements IRequirementsRequest {

	public Requirements2DataStore(final String afId) {
		super(afId);
	}

	@Override
	public void afRun() {
		final Map<String, Object> props = new HashMap<>();
		props.put("javax.jdo.PersistenceManagerFactoryClass", "org.datanucleus.api.jdo.JDOPersistenceManagerFactory");
		props.put("javax.jdo.option.ConnectionDriverName", "org.apache.derby.jdbc.EmbeddedDriver");
		props.put("javax.jdo.option.ConnectionURL", "jdbc:derby:db/ToDo;create=true");
		this.portPersist().out(IPersistentStore.class).connect(props);
	}

	// --- IRequirementsRequest ---
	@Override
	public void requestNewProject(final UserSession session, final Project project) {
		final PersistentItemLocation id = new PersistentItemLocation("");
		final JdoProject jdo = new JdoProject(project);
		try {
			final IPersistenceTransaction transaction = this.portPersist().out(IPersistentStore.class).startTransaction();
			this.portPersist().out(IPersistentStore.class).store(transaction, id, jdo, JdoProject.class);
			this.portPersist().out(IPersistentStore.class).commitTransaction(transaction);
		} catch (final PersistentStoreException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void requestNewChapter(final UserSession session, final Project project, final Chapter chapter) {
		// TODO Auto-generated method stub

	}

	// --- Ports ---

	@PortInstance(provides = { IRequirementsRequest.class }, requires = { IRequirementsNotification.class })
	IPort portStore;

	public IPort portStore() {
		return this.portStore;
	}

	@PortInstance(provides = {}, requires = { IPersistentStore.class })
	IPort portPersist;

	public IPort portPersist() {
		return this.portPersist;
	}

}
