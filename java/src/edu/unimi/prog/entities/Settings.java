package edu.unimi.prog.entities;

public abstract class Settings {
	
	private static byte _charRange;
	private static byte _codeLength;
	private static byte _attempts;
	private static boolean debug;

	public static void setDebug(String arg) { debug = arg.equals("0") ? false : true; }
	static void configure(byte charRange, byte codeLength, byte attempts) {
		_charRange = charRange; _codeLength = codeLength; _attempts = attempts;
	}
	
	static byte getCharRange() { return _charRange; }
	static byte getCodeLength() { return _codeLength; }
	static byte getAttempts() { return _attempts; }
	static boolean debugEnabled() { return debug; }
}
