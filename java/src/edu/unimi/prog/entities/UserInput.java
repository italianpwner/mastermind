package edu.unimi.prog.entities;

import java.util.Scanner;

abstract class UserInput {
	
	private static Scanner sc;
	
	static {
		sc = new Scanner(System.in);
	}
	
	static char getCharFromUser() {

		String s;		
		while(true) {
			cursor();
			s = sc.nextLine();
			if(s.length() == 1)
				break;
			else if(s.length() == 0)
				System.err.println(Strings.get("NO_CHOICE_MADE"));
			else
				System.err.println(Strings.get("TOO_MANY_CHARS"));
		}
		return s.charAt(0);
	}
	
	static char getCharFromList(String options) {
		
		char c;
		while(true) {
			c = getCharFromUser();
			if(options.indexOf(c) == -1)
				System.err.println(Strings.get("INVALID_OPTION"));
			else
				break;
		}
		return c;
	}

	static byte getNumInRange(String text, byte min, byte max) {
		
		System.out.println("\n"+text);
		
		byte b;
		while(true) {
			try {
				cursor();
				b = Byte.parseByte(sc.nextLine());
				
				if(b >= min && b <= max)
					break;
				else
					System.err.printf(Strings.get("INVALID_NUM"), min, max);
			} catch(NumberFormatException e) {
				System.err.println(Strings.get("NOT_A_NUM"));
			}
		}
		return b;
	}
	
	static String get() { cursor(); return sc.nextLine(); }
	
	private static void cursor() { System.out.print(">>> "); }
}
