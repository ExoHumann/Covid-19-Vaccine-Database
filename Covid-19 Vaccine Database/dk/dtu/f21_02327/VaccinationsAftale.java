package dk.dtu.f21_02327;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Denne klasse repræsenterer en vaccinationsaftale i den fil der modtages fra sundhedsmyndighederne dagligt.
 * 
 * Klassen er en del af projektopgaven på Kursus 02327 F21
 * 
 * @author Thorbjørn Konstantinovitz  
 *
 */
public class VaccinationsAftale {
	private final String cprnr;
	private final String navn;

	private final String aftaltDato;
	private final String aftaltTid;
	private final String vaccineType;
	private final String lokation;
	
	public VaccinationsAftale(String cprnr, String navn, String aftaltDato, String aftaltTid, String vaccineType, String lokation) {
		this.cprnr = cprnr;
		this.navn = navn;
		this.aftaltDato = aftaltDato;
		this.aftaltTid = aftaltTid;
		this.vaccineType = vaccineType;
		this.lokation = lokation;
	}

	public String getCprnr() {
		return cprnr;
	}

	public String getNavn() {
		return navn;
	}

	public String getAftaltTid() {
		return aftaltTid;
	}

	public String getAftaltDato() {
		return aftaltDato;
	}

	public String getVaccineType() {
		return vaccineType;
	}

	public String getLokation() {
		return lokation;
	}
	
	@Override
	public String toString() {
		final String D = ";";
		return getCprnr() +D + getNavn() +D + getAftaltDato() +D + getAftaltTid() +D +getLokation() +D +getVaccineType();
	}
}
