package edu.unimi.prog.entities;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import java.util.regex.Pattern;

abstract class Utility {

	static void printFileContent(String filename) {
		
		BufferedReader in = null;
		try {
			System.out.println();
			
			in = new BufferedReader(new FileReader(filename));
			String line;
			while((line = in.readLine()) != null)
				System.out.println(line);
			
		} catch(IOException e) {
			if(e instanceof FileNotFoundException) {
				System.err.printf(Strings.get("FILE_NOT_FOUND"), filename);
			} else {
				e.printStackTrace();
			}
			System.exit(1);
			
		} finally {
			try {
				in.close();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	static String generateCode() {
		
		StringBuilder code = new StringBuilder();
		Random random = new Random();
		char c; int n;
		
		for(int i=0; i<Settings.getCodeLength(); i++) {
			n = random.nextInt(Settings.getCharRange());
			c = (char) (65 + n);
			code.append(c);
		}
		return code.toString();
	}
	
	static String makeValidGuess() {

		String guess = null;
		Pattern pattern = Pattern.compile("[A-"+ (char) (65 + Settings.getCharRange() - 1) + "]{" + Settings.getCodeLength() + "}");
		
		while(true) {
			guess = UserInput.get();
			
			if(regexMatches(guess, pattern))
				break;
			else
				System.err.printf(Strings.get("INVALID_CODE"),
						Settings.getCodeLength(), 65,
						(byte) (65 + Settings.getCharRange() - 1));
		}
		return guess;
	}
	
	static boolean regexMatches(String s, Pattern p) {
		return p.matcher(s).matches();
	}
	
	static boolean checkGuess(String guess, String code) {
		
		java.util.Map<Character, Byte> countCodeLetters = new java.util.HashMap<Character, Byte>();
		java.util.Map<Character, Byte> countGuessLetters = new java.util.HashMap<Character, Byte>();
				
		byte wrongPlace = 0;
		byte rightPlace = 0;
		
		for(int i=0; i<Settings.getCodeLength(); i++) {
			
			char iCode = code.charAt(i), iGuess = guess.charAt(i);
			
			if(iGuess == iCode)
				rightPlace++;
			else {
				countCodeLetters.put( iCode , increment(countCodeLetters.get(iCode)));
				countGuessLetters.put(iGuess, increment(countGuessLetters.get(iGuess)));
			}
		}

		java.util.Set<Character> keys = new java.util.HashSet<Character>();
		keys.addAll(countCodeLetters.keySet());
		keys.addAll(countGuessLetters.keySet());
		
		for(Character key : keys) {
			wrongPlace += min(countCodeLetters.get(key), countGuessLetters.get(key));
		}
		
		if(rightPlace == Settings.getCodeLength())
			return true;
		else
			System.out.printf(Strings.get("RESULT"), rightPlace, wrongPlace);
		return false;
	}
	
	private static byte min(Byte a, Byte b) {
		if(a == null) a = 0;
		if(b == null) b = 0;
		return a<b ? a : b;
	}
	
	private static  Byte increment(Byte b) {
		if(b == null)
			return 1;
		return b++;
	}
	
	static String repeatChar(String c, int n) {
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<n; i++)
			sb.append(c);
		return sb.toString();
	}
}
