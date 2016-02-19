package net.akehurst.requirements.management.application.desktop;

public class Main {

	public static void main(String[] args) {
		try {
			Application app = new Application();
			app.afStart();
			app.afJoin();
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}

}
