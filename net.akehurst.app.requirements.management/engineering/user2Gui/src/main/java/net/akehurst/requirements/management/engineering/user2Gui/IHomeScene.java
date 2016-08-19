package net.akehurst.requirements.management.engineering.user2Gui;

import net.akehurst.application.framework.technology.guiInterface.IGuiScene;
import net.akehurst.application.framework.technology.guiInterface.data.table.IGuiTable;
import net.akehurst.application.framework.technology.guiInterface.elements.IGuiText;

public interface IHomeScene extends IGuiScene {

	IGuiText getUsername();

	IGuiTable getProjectTable();

}
