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
package net.akehurst.requirements.management.technology.gui.jfx;

import java.net.URL;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import net.akehurst.application.framework.components.Port;
import net.akehurst.application.framework.os.AbstractActiveObject;
import net.akehurst.application.framework.technology.commsInterface.ChannelIdentity;
import net.akehurst.application.framework.technology.guiInterface.IGuiNotification;
import net.akehurst.application.framework.technology.guiInterface.IGuiRequest;

public class JfxWindow extends AbstractActiveObject implements IGuiRequest {

	
	public JfxWindow(String objectId,URL fxmlUrl) {
		super(objectId);
		this.fxmlUrl = fxmlUrl;
	}
	
	URL fxmlUrl;
	
	Stage primary;
//	Canvas canvas;
//	GraphicsContext gc;
	
	@Override
	public void afRun() {
		new JFXPanel();
		Platform.runLater(new Runnable() {
			public void run() {
				try {
					primary = new Stage();
					primary.setTitle("Jfx Gui");

					Parent root = FXMLLoader.load(fxmlUrl);
					Scene scene = new Scene(root);

					primary.setScene(scene);
					primary.sizeToScene();
					primary.showAndWait();

//					gc = canvas.getGraphicsContext2D();
//					gc.setStroke(Color.BLACK);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	@Override
	public void createStage(String stageId, boolean authenticated, URL content) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void createScene(String stageId, String sceneId, URL content) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void addElement(String sceneId, String parentId, String newElementId, String type) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void addElement(String sceneId, String parentId, String newElementId, String type, String attributes, Object content) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void requestRecieveEvent(String sceneId, String elementId, String eventType, ChannelIdentity eventChannelId) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setText(String sceneId, String id, String text) {
		Node n = this.primary.getScene().lookup("#"+id);
		if (n instanceof TextArea) {
			((TextArea)n).setText(text);
		}	
	}
	@Override
	public void setTitle(String sceneId, String text) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void switchTo(String stageId, String sceneId) {
		// TODO Auto-generated method stub
		
	}
	//--------- IGuiNotification ---------




	//--------- Ports ---------
	Port portGui;
	public Port portGui() {
		if (null==this.portGui) {
			this.portGui = new Port()
					.provides(IGuiRequest.class, JfxWindow.this)
					.requires(IGuiNotification.class);
		}
		return this.portGui;
	}
}
