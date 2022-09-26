package edu.unimi.prog.entities;

abstract class Strings {
	
	private static final java.util.Map<String,String> map;
	
	static {
		map = new java.util.HashMap<String,String>();
		
		String filePath = "src/edu/unimi/prog/text/";
		
		map.put( "MENU_FILEPATH"  , filePath+"menu.txt");
		map.put("NEWGAME_FILEPATH", filePath+"newgame.txt");
		map.put( "RULES_FILEPATH" , filePath+"rules.txt");

		map.put("FILE_NOT_FOUND" , "Il file %s non esiste.%n");
		map.put( "INVALID_CODE"  , "Il codice inserito non è valido. Assicurati che sia lungo"
				+ " %d caratteri e sia composto da lettere del seguente intervallo (%c-%c).%n");
		map.put("NO_CHOICE_MADE" , "Nessuna scelta effettuata!");
		map.put(  "INVALID_NUM"  , "Il numero selezionato dev'essere compreso tra %d e %d!%n");
		map.put("INVALID_OPTION" , "L'opzione selezionata è inesistente!");
		map.put( "INVALID_PARAM" , "Parametro non valido!");
		map.put( "MISSING_PARAM" , "Parametro mancante!");
		map.put(   "NOT_A_NUM"   , "Inserire un numero!");
		map.put("TOO_MANY_CHARS" , "Premi un tasto solo!");
		map.put("TOO_MANY_PARAMS", "Inserire un solo parametro!");

		map.put( "ATTEMPTS" , "Round %s%nTentativi rimasti: %s%n");
		map.put( "CONGRATS" , "\nComplimenti! Hai indovinato il codice!");
		map.put("INS_ATMPTS", "Inserisci il numero massimo di tentativi disponibili per indovinare (7-20): ");
		map.put("INS_RANGE" , "Inserisci il numero di possibili lettere che comporranno il codice (2-20): ");
		map.put("INS_LENGTH", "Inserisci la lunghezza del codice (4-10): ");
		map.put("PRINT_CODE", "[DEBUG] Codice segreto: ");
		map.put(  "REPLAY"  , "Premere '0' per giocare ancora o un tasto qualsiasi per tornare al menù principale.");
		map.put(  "THANKS"  , "\nGrazie per aver giocato!");
		map.put(  "RESULT"  , "%nLettere giuste in posizione giusta: %d%nLettere giuste in posizione errata: %d%n%n");
		map.put( "TOO_BAD"  , "Peccato! Non hai indovinato il codice :(");
	}
	
	static String get(String code) { return map.get(code); }
}
