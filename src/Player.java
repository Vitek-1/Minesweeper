import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;

public class Player {
    private String name;
    private int totalTimeS;
    private String OriginalTimeS;

    public Player(String name, int totalTimeS, String OriginalTimeS) {
        this.name = name;
        this.totalTimeS = totalTimeS;
        this.OriginalTimeS = OriginalTimeS;
    }

    public void UpdateLeaderBoard(int newMin, int newSec) {
        String filename = "res/LeaderBoard.csv";
        ArrayList<Player> results = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))){
            String line;

            while ((line = br.readLine()) != null) {
                String[] pole = line.split(";");
                String[] time = pole[1].split(":");

                int total = Integer.parseInt(time[0]) * 60 + Integer.parseInt(time[1]);
                results.add(new Player(pole[0], total, pole[1]));
            }
        } catch (IOException e) {
            System.out.println("Tabulka výsledků zatím neexistuje");
        }

        results.add(new  Player(name, newMin * 60 + newSec, newMin + ":" + String.format("%02d", newSec)));

        results.sort(Comparator.comparingInt(a -> a.totalTimeS));

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename, false))) {
            int count = 0;
            for (Player p : results) {
                bw.write(p.name + ";" + p.OriginalTimeS + "\n");
                count++;
                if (count >= 10) break;
            }
        } catch (IOException e) {
            System.out.println("Tabulka výsledků zatím neexistuje.");
        }
    }

}