package edu.unimi.prog.entities;

public abstract class Controller {
	
	public static boolean invalidCLArgs(String[] args) {
		
		if(args.length > 1) {
			System.err.println(Strings.get("TOO_MANY_PARAMS"));
			return true;
		} else if(args.length == 0) {
			System.err.println(Strings.get("MISSING_PARAM"));
			return true;
		} else {
			boolean parameterTooLong = args[0].length() != 1;
			boolean invalidParameter = !args[0].equals("0") && !args[0].equals("1");
			
			if(parameterTooLong || invalidParameter) {
				System.err.println(Strings.get("INVALID_PARAM"));
				return true;
			}
		}
		return false;
	}
	
	public static void welcome() {
		
		String c = "*";
		System.out.println(Utility.repeatChar(c,48));
		System.out.println(
				Utility.repeatChar(c,18) +
				" MASTERMIND " +
				Utility.repeatChar(c,18)
		);
		System.out.println(Utility.repeatChar(c,48));
	}
		
	public static boolean mainMenu() {
		
		Utility.printFileContent(Strings.get("MENU_FILEPATH"));
		
		char c;
		do {
			c = UserInput.getCharFromList("012");
			switch(c) {
				case '1':
					Utility.printFileContent(Strings.get("RULES_FILEPATH"));
					Utility.printFileContent(Strings.get("MENU_FILEPATH"));
				case '0': break;
				case '2': 
					System.out.println(Strings.get("THANKS"));
					return false;
			}
		} while(c != '0');
		
		return true;
	}
	
	public static boolean chooseGamemode() {
		
		Utility.printFileContent(Strings.get("NEWGAME_FILEPATH"));
		
		switch(UserInput.getCharFromList("012")) {
			case '0':
				Settings.configure((byte) 6, (byte) 4, (byte) 12);
				break;
			case '1':
				Settings.configure(
						UserInput.getNumInRange(Strings.get("INS_RANGE") , (byte) 2, (byte) 20),
						UserInput.getNumInRange(Strings.get("INS_LENGTH"), (byte) 4, (byte) 10),
						UserInput.getNumInRange(Strings.get("INS_ATMPTS"), (byte) 7, (byte) 20)
				);
				break;
			case '2': return false;
		}

		return true;
	}
	
	public static boolean newGame() {
		
		boolean guessed = false;
		
		String code = Utility.generateCode();
		if(Settings.debugEnabled())
			System.out.println("\n"+Strings.get("PRINT_CODE") + code+"\n");
		
		for(byte attempts = Settings.getAttempts(), round = 1;   attempts>0;   attempts--, round++) {
			
			System.out.printf(Strings.get("ATTEMPTS"), String.valueOf(round), String.valueOf(attempts));
			
			guessed = Utility.checkGuess(Utility.makeValidGuess(), code);
			if(guessed) break;
		}
		
		return replay(guessed);
	}
	
	private static boolean replay(boolean guessed) {
		
		System.out.println((guessed
							? Strings.get("CONGRATS")
							: Strings.get("TOO_BAD"))
		+ "\n" + Strings.get("REPLAY"));
		
		return UserInput.getCharFromUser() == '0';
	}
}
