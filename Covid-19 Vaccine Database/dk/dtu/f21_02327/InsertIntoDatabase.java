package dk.dtu.f21_02327;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.List;

/**
 * @author KrzyS Kisiel
 */
public class InsertIntoDatabase {

    public static void main(String[] args) {

        // Tilpas variable til jeres database.
        // -----------------------------------
        String host = "localhost"; //host is "localhost" or "127.0.0.1"
        String port = "3306"; //port is where to communicate with the RDBM system
        String database = "familydb"; //database containing tables to be queried
        String cp = "utf8"; //Database codepage supporting danish (i.e. æøåÆØÅ)

        // Set username og password.
        // -------------------------
        String username = "root";     // Username for connection
        String password = "root";    // Password for username
        String url = "jdbc:mysql://" + host + ":" + port + "/" + database + "?characterEncoding=" + cp;

        //File to be read from
        //Change the filepath to your local file location
        //--------------------
        String file = "C:/Users/KrzyS/java kode/dk/dtu/f21_02327/vaccinationsaftaler.csv";
        IndlaesVaccinationsAftaler laeser = new IndlaesVaccinationsAftaler();

        try {

            List<VaccinationsAftale> aftaler = laeser.indlaesAftaler(file);

            int Medarbejder_ID = 1;
            for (VaccinationsAftale aftale : aftaler) {

                //Splitting names  and Lastnames for database insert
                String[] navne = aftale.getNavn().split(" ");
                String navn = navne[0];

                String efternavn = "";
                for (int i = 1; i < navne.length; i++) {
                    efternavn += navne[i] + " ";
                }

                Medarbejder_ID = ((Medarbejder_ID+1) % 11) +1;

                String lokation = "";
                switch (aftale.getLokation()) {
                    case "kbh":
                        lokation = "København";
                        break;
                    case "hill":
                        lokation = "Hillerød";
                        break;
                    case "aarhus":
                        lokation = "Aarhus";
                        break;
                    case "kolding":
                        lokation = "Kolding";
                        break;
                    case "odense":
                        lokation = "Odense";
                        break;
                    case "nakskov":
                        lokation = "Nakskov";
                        break;
                }

                // Creating table
                String createTableKlient =
                        "CREATE TABLE IF NOT EXISTS Klient (" +
                                "Klient_CPR VARCHAR(10) NOT NULL PRIMARY KEY UNIQUE," +
                                "Fornavn VARCHAR(20) NOT NULL," +
                                "Efternavn VARCHAR(20) NOT NULL ); ";

                String insertIntoTableKlient =
                        "INSERT IGNORE INTO Klient (Klient_CPR, Fornavn, Efternavn) VALUES" +
                                "(" + "\'" + aftale.getCprnr() + "\'" + "," +
                                "\'" + navn + "\'" + "," +
                                "\'" + efternavn + "\'" + ");";


                String createTableAftaler =
                        "CREATE TABLE IF NOT EXISTS Aftale (" +
                                "Klient_CPR  VARCHAR(10) NOT NULL," +
                                "Medarbejder_ID mediumINT NOT NULL," +
                                "Dato INT(8) NOT NULL," +
                                "LandsBy  VARCHAR(20) NOT NULL," +
                                "Vaccinations_Tidspunkt INT(4) NOT NULL," +
                                "VaccineType  VARCHAR(20) NOT NULL," +
                                "PRIMARY KEY (Klient_CPR, Medarbejder_ID, Dato)," +
                                "FOREIGN KEY (Klient_CPR) REFERENCES Klient(Klient_CPR)," +
                                "FOREIGN KEY (LandsBy) REFERENCES Lokation(LandsBy)," +
                                "FOREIGN KEY (Medarbejder_ID) REFERENCES Medarbejder(Medarbejder_ID) );";

                String insertIntoTableAftaler =
                        "INSERT INTO Aftale (Klient_CPR, Medarbejder_ID, Dato, LandsBy, " +
                                "Vaccinations_tidspunkt, VaccineType) VALUES" +
                                "(" + "\'" + aftale.getCprnr() + "\'" + "," +
                                Medarbejder_ID + "," +
                                aftale.getAftaltDato() + "," +
                                "\'" + lokation + "\'" + "," +
                                aftale.getAftaltTid() + "," +
                                "\'" + aftale.getVaccineType() + "\'" + ");";


                // Get a connection.
                // -----------------
                Connection connection = DriverManager.getConnection(url, username, password);

                if (connection != null) {
                    System.out.println("Successfully connected to MySQL database");
                }

                // Create and execute Update.
                // --------------------------
                Statement statement = connection.createStatement();
                statement.executeUpdate(createTableKlient);
                statement.executeUpdate(insertIntoTableKlient);

                statement.executeUpdate(createTableAftaler);
                statement.executeUpdate(insertIntoTableAftaler);
                // Close connection.
                // -----------------
                connection.close();
            }

        } catch (
                Exception e) {
            System.out.println("An error occurred while connecting MySQL databse");
            e.printStackTrace();
        }
    }
}

