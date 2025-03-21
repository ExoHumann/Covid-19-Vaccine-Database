package dk.dtu.f21_02327;
/**
 * Denne klasse håndterer indlæsning af vaccinationsaftaler fra den fil der modtages fra sundhedsmyndighederne dagligt.
 * Klassen implementerer en simpel 
 * 
 *  Klassen er en del af projektopgaven på Kursus F21 02327 F21
 * 
 * @author Thorbjørn Konstantinovitz  
 *
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class IndlaesVaccinationsAftaler {

	public static final String SEMICOLON_DELIMITER = ";";
	public static final String COMMA_DELIMITER = ",";
	private static final int NUMBER_OF_FIELDS_EXPECTED = 6; 
	private final String delimiter = SEMICOLON_DELIMITER;
	SimpleDateFormat dateParser = new SimpleDateFormat("yyyyMMddHHmm");

	/**
	 * Denne metode indlæser en fil med vaccinationsaftaler og returnerer en liste med VaccinationsAfale objekter der repræsenterer indholdet i filen.
	 * @param filename filnavn på den fil der skal indlæses (inkl. sti hvis nødvendigt)
	 * @return List indeholdende VaccinationsAftaler
	 * @throws FileNotFoundException 
	 */
	public List<VaccinationsAftale> indlaesAftaler(String filename) throws FileNotFoundException, IOException {
		List<VaccinationsAftale> aftaler = new ArrayList<VaccinationsAftale>();

		BufferedReader in = null;

		try {
			in = new BufferedReader(new FileReader(filename));

		    String line;
		    int lineNbr = 0;
		    while ((line = in.readLine()) != null) {
		    	lineNbr++;
		    	List<String> values = new ArrayList<String>();
		    	try (Scanner rowScanner = new Scanner(line)) {
		    	    rowScanner.useDelimiter(delimiter);
		    	    while (rowScanner.hasNext()) {
		    	        values.add(rowScanner.next());
		    	    }
		    	    switch(values.size()) {
		    	    	case 0: // Ignore empty lines
		    	    		break; 
		    	    	case NUMBER_OF_FIELDS_EXPECTED: // Parse values into VaccinationsAftale
		    	    		
		    	    		String cprnr = values.get(0);
		    	    		String[] cpr = cprnr.split("");
		    	    		if (cpr.length < 10){
		    	    			cprnr = "0" + cprnr;
							}
							String navn = values.get(1);
							String dato = values.get(2);
							String tid = values.get(3);

							// Pad to 4 digits.
							// ----------------
							for(int i = 4 - tid.length(); i > 0 ; i--)
								tid = "0" +tid;
							String vaccineType = values.get(4);
							String vaccinationsSted = values.get(5);
							VaccinationsAftale aftale = new VaccinationsAftale(cprnr, navn, dato, tid, vaccineType, vaccinationsSted);
							aftaler.add(aftale);
		    	    		break;
		    	    	default: // Wrong file format
		    	    		throw new IOException("Ugyldigt antal værdier på linie" +lineNbr +". Forventede " +NUMBER_OF_FIELDS_EXPECTED +" værdier, læste " +values.size());		    	    		
		    	    }
		    	}

		    }
		} finally {
			if(in != null)
				try { in.close(); } catch(Exception e) { /* Ignore */ };
		}
		
		return aftaler;
	}
}