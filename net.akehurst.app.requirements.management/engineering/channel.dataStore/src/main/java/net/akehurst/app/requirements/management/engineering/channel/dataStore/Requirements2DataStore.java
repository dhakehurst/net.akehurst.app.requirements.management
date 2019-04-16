package net.akehurst.app.requirements.management.engineering.channel.dataStore;

import java.util.HashMap;
import java.util.Map;

import net.akehurst.app.requirements.management.computational.requirementsInterface.IRequirementsRequest;
import net.akehurst.application.framework.common.IPort;
import net.akehurst.application.framework.common.annotations.instance.ActiveObjectInstance;
import net.akehurst.application.framework.common.annotations.instance.ConfiguredValue;
import net.akehurst.application.framework.common.annotations.instance.PortContract;
import net.akehurst.application.framework.common.annotations.instance.PortInstance;
import net.akehurst.application.framework.realisation.AbstractComponent;
import net.akehurst.application.framework.technology.interfacePersistence.IPersistentStore;

public class Requirements2DataStore extends AbstractComponent {

	public Requirements2DataStore(final String afId) {
		super(afId);
	}

	@ConfiguredValue(defaultValue = "org.apache.derby.jdbc.EmbeddedDriver")
	String connectionDriver;

	@ConfiguredValue(defaultValue = "jdbc:derby:db/RM;create=true")
	String connectionUrl;

	@Override
	public void afRun() {
		final Map<String, Object> props = new HashMap<>();
		props.put("javax.jdo.PersistenceManagerFactoryClass", "org.datanucleus.api.jdo.JDOPersistenceManagerFactory");
		props.put("javax.jdo.option.ConnectionDriverName", this.connectionDriver);
		props.put("javax.jdo.option.ConnectionURL", this.connectionUrl);
		props.put("datanucleus.schema.autoCreateAll", "true");
		this.portPersist().out(IPersistentStore.class).connect(props);

		super.afRun();
	}

	@ActiveObjectInstance
	RequestHandler handler;

	@Override
	public void afConnectParts() {
		this.portStore().connectInternal(this.handler);
		this.portPersist().connectInternal(this.handler);
	}

	// --- Ports ---

	@PortInstance
	@PortContract(provides = IRequirementsRequest.class)
	IPort portStore;

	public IPort portStore() {
		return this.portStore;
	}

	@PortInstance
	@PortContract(requires = IPersistentStore.class)
	IPort portPersist;

	public IPort portPersist() {
		return this.portPersist;
	}

}
