import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LeaderboardWindow extends JDialog {

    public LeaderboardWindow() {
        setTitle("Top 10 best players");
        setLayout(new BorderLayout());
        setSize(500, 429);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.setPreferredSize(new Dimension(getWidth(), getHeight()/9));
        topPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.black));

        JLabel title = new JLabel("BEST RESULTS");
        title.setFont(new Font("Times New Roman", Font.BOLD, 35));
        title.setHorizontalAlignment(JLabel.CENTER);
        topPanel.add(title);
        add(topPanel, BorderLayout.NORTH);


        JPanel eastPanel = new JPanel();
        eastPanel.setLayout(new BorderLayout());
        eastPanel.setPreferredSize(new Dimension(getWidth()/2 -8, (getHeight()/9)*8));
        eastPanel.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.black));


        JPanel southPanel = new JPanel();
        southPanel.setLayout(new BorderLayout());
        southPanel.setPreferredSize(new Dimension(getWidth()/2 -8, (getHeight()/9)*8));
        southPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.black));

        loadData(southPanel, eastPanel);

        add(eastPanel, BorderLayout.EAST);
        add(southPanel, BorderLayout.WEST);

        setVisible(true);
    }

    public void loadData(JPanel name, JPanel speed){
        String filename = "res/LeaderBoard.csv";
        int rank = 1;

        name.setLayout(new BoxLayout(name, BoxLayout.Y_AXIS));
        speed.setLayout(new BoxLayout(speed, BoxLayout.Y_AXIS));

        CreateTitle(name, "Name");
        CreateTitle(speed, "Time");

        try (BufferedReader br = new BufferedReader(new FileReader(filename))){
            String line;

            while ((line = br.readLine()) != null) {
                String[] pole = line.split(";");
                String[] time = pole[1].split(":");

                JLabel adress = new JLabel(rank + ") " + pole[0]);
                adress.setFont(new Font("Times New Roman", Font.PLAIN, 25));

                JLabel tempo = new JLabel("                " +String.format("%02d:%02d", Integer.parseInt(time[0]), Integer.parseInt(time[1])));
                tempo.setFont(new Font("Times New Roman", Font.PLAIN, 25));

                name.add(adress);
                speed.add(tempo);
                rank++;
            }
        } catch (IOException e) {
            System.out.println("Tabulka výsledků zatím neexistuje");
        }
    }

    public void CreateTitle(JPanel name, String word){
        JPanel title = new JPanel(new FlowLayout(FlowLayout.LEFT));
        title.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        title.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.black));

        JLabel l1 = new JLabel("             " + word);
        l1.setFont(new Font("Times New Roman", Font.BOLD, 27));
        title.add(l1);

        name.add(title, BorderLayout.NORTH);
    }
}