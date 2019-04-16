package net.akehurst.requirements.management.engineering.user2Gui;

import net.akehurst.application.framework.technology.interfaceGui.IGuiScene;
import net.akehurst.application.framework.technology.interfaceGui.data.table.IGuiTable;
import net.akehurst.application.framework.technology.interfaceGui.elements.IGuiElement;
import net.akehurst.application.framework.technology.interfaceGui.elements.IGuiText;

public interface ISceneHome extends IGuiScene {

	IGuiText getUsername();

	IGuiTable getProjectTable();

	IGuiElement getActionNewProject();

	IGuiElement getActionDeleteProject();

}
