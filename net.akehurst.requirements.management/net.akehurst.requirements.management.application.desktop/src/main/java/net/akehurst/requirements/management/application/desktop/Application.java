package net.akehurst.requirements.management.application.desktop;

import net.akehurst.application.framework.os.AbstractActiveObject;
import net.akehurst.application.framework.os.IActiveObject;
import net.akehurst.requirements.management.computational.requirementsManagement.RequirementsManager;
import net.akehurst.requirements.management.engineering.user2Gui.UserToGui;
import net.akehurst.requirements.management.technology.gui.jfx.JfxWindow;

public class Application extends AbstractActiveObject implements IActiveObject {

	public Application() {
		super("application");
		this.requirementsManager = new RequirementsManager("requirementsManager");
		this.userToGui = new UserToGui("userToGui");
		this.gui = new JfxWindow("gui",JfxWindow.class.getClassLoader().getResource("fxml/Gui.fxml"));
		
		this.requirementsManager.portUserInterface().connect(this.userToGui.portUserInterface());
		this.userToGui.portGui().connect( this.gui.portGui() );
		
	}
	
	RequirementsManager requirementsManager;
	
	UserToGui userToGui;
	
	JfxWindow gui;

	@Override
	public void afRun() {
		try {
		
			this.gui.afStart();
			
			this.requirementsManager.afStart();
			

			
			this.requirementsManager.afJoin();

			this.gui.afJoin();
			
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}

}
