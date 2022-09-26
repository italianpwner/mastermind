package edu.unimi.prog;

import edu.unimi.prog.entities.Controller;
import edu.unimi.prog.entities.Settings;

public class Main {

	public static void main(String[] args) {
		
		if(Controller.invalidCLArgs(args))
			System.exit(1);
		Settings.setDebug(args[0]);

		Controller.welcome();
		while(Controller.mainMenu())
			if(Controller.chooseGamemode())
				while(Controller.newGame());
	}
}
