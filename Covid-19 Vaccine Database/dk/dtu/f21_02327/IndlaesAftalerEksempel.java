package dk.dtu.f21_02327;

import java.io.IOException;
import java.util.List;

public class IndlaesAftalerEksempel {

	public static void main(String[] args) {

		String file = "C:/Users/KrzyS/java kode/dk/dtu/f21_02327/vaccinationsaftaler.csv";
		IndlaesVaccinationsAftaler laeser = new IndlaesVaccinationsAftaler();
		try {
			List<VaccinationsAftale> aftaler = laeser.indlaesAftaler(file);
			for(VaccinationsAftale aftale : aftaler) {
				System.out.println(aftale);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}