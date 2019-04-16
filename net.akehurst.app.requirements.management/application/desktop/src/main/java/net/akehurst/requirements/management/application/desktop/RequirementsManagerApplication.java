package net.akehurst.requirements.management.application.desktop;

import net.akehurst.application.framework.common.annotations.declaration.Application;
import net.akehurst.application.framework.common.annotations.instance.ComponentInstance;
import net.akehurst.application.framework.common.annotations.instance.ServiceInstance;
import net.akehurst.application.framework.realisation.AbstractApplication;
import net.akehurst.application.framework.technology.filesystem.StandardFilesystem;
import net.akehurst.application.framework.technology.gui.jfx.JfxWindow;
import net.akehurst.application.framework.technology.log4j.Log4JLogger;
import net.akehurst.application.framework.technology.persistence.filesystem.HJsonFile;
import net.akehurst.requirements.management.computational.requirementsManagement.RequirementsManager;
import net.akehurst.requirements.management.engineering.user2Gui.UserToGui;

@Application
public class RequirementsManagerApplication extends AbstractApplication {

	public RequirementsManagerApplication(final String id) {
		super(id);

	}

	@ServiceInstance
	Log4JLogger logger;

	@ServiceInstance
	StandardFilesystem fs;

	@ServiceInstance
	HJsonFile configuration;

	@ComponentInstance
	RequirementsManager requirementsManager;

	@ComponentInstance
	UserToGui userToGui;

	@ComponentInstance
	JfxWindow gui;

	@Override
	public void afConnectParts() {
		this.requirementsManager.portUser().connect(this.userToGui.portUserInterface());
		this.userToGui.portGui().connect(this.gui.portGui());
	}

}
