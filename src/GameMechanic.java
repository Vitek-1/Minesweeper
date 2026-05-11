import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

public class GameMechanic {

    private int[][] miny;
    private JButton[][] buttons;
    private Color grey;
    private boolean lost;
    private JButton backButton;
    private JPanel topPanel;
    private int numberOfMins;
    private int seconsPassed;
    private int minutesPassed;
    Timer timer;
    private JLabel timeLabel;

    public GameMechanic(int[][] miny, JButton[][] buttons, Color grey, boolean lost, JButton backButton, JPanel topPanel, int numberOfMins, int seconsPassed, int minutesPassed, Timer timer,  JLabel timeLabel) {
        this.miny = miny;
        this.buttons = buttons;
        this.grey = grey;
        this.lost = lost;
        this.backButton = backButton;
        this.topPanel = topPanel;
        this.numberOfMins = numberOfMins;
        this.seconsPassed = seconsPassed;
        this.minutesPassed = minutesPassed;
        this.timer = timer;
        this.timeLabel = timeLabel;
    }

    public GameMechanic() {
    }

    public void ShowVicinity(int x, int y) {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int neighbourX = x + i;
                int neighbourY = y + j;

                if (neighbourX >= 0 && neighbourY >= 0 && neighbourX < 9 && neighbourY < 9) {
                    if (miny[neighbourX][neighbourY] != 10) {
                        SetPicture(buttons[neighbourX][neighbourY], neighbourX, neighbourY);
                        if (miny[neighbourX][neighbourY] == 0) {
                            ShowVicinity(neighbourX, neighbourY);
                        }
                    }
                }
            }
        }
    }

    public void SetPicture(JButton button, int x, int y) {
        if (miny[x][y] != 9 && miny[x][y] != 10) {
            int number = miny[x][y];

            button.setIcon(null);
            button.setText(String.valueOf(miny[x][y]));
            button.setFont(new Font("Times New Roman", Font.BOLD, 45));
            miny[x][y] = 10;

            button.setBackground(grey);
            button.setContentAreaFilled(false);
            button.setOpaque(true);
            button.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

            button.setBorderPainted(false);
            button.setFocusPainted(false);

            if (number == 0) {
                ShowVicinity(x, y);
            }
        } else if (miny[x][y] == 9) {
            ChangeLook.setMinaIcon(button, 100, 100);
            lost = true;
        }
    }

    public boolean isLost() {
        return lost;
    }

    public void ShowAllMines(JButton button) {
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                if (miny[x][y] == 9) {
                    ChangeLook.setMinaIcon(buttons[x][y], 100, 100);
                    buttons[x][y].setBackground(new Color(237, 28, 36));
                } else {
                    buttons[x][y].setBackground(grey);
                }
                buttons[x][y].setContentAreaFilled(false);
                buttons[x][y].setOpaque(true);
                buttons[x][y].setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

                buttons[x][y].setBorderPainted(false);
                buttons[x][y].setFocusPainted(false);
            }
        }
        End();
    }

    public void CalculateMines() {
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                if (miny[x][y] != 9) {

                    int neighbour = 0;

                    for (int i = -1; i <= 1; i++) {
                        for (int j = -1; j <= 1; j++) {
                            int neighbourX = x + i;
                            int neighbourY = y + j;

                            if (neighbourX >= 0 && neighbourY >= 0 && neighbourX < 9 && neighbourY < 9) {
                                if (miny[neighbourX][neighbourY] == 9) {
                                    neighbour++;
                                }
                            }
                        }
                    }
                    miny[x][y] = neighbour;
                }
            }
        }
    }

    public void MineGenerator() {
        Random rnd = new Random();
        int laidMines = 0;

        while (laidMines < numberOfMins) {
            int x = rnd.nextInt(9);
            int y = rnd.nextInt(9);

            if (miny[x][y] != 9) {
                miny[x][y] = 9;
                laidMines++;
            }
        }
    }

    public void StartTimer() {
        timer = new Timer(1000, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                seconsPassed++;
                if (seconsPassed == 60) {
                    seconsPassed = 0;
                    minutesPassed++;
                }
                timeLabel.setText(String.format("%02d:%02d", minutesPassed, seconsPassed));
                timeLabel.setFont(new Font("Arial", Font.BOLD, 50));
            }
        });
        timer.start();
    }

    public boolean Victory(){
        for (int x = 0; x < miny.length; x++) {
            for (int y = 0; y < miny.length; y++) {
                int value = miny[x][y];
                JButton button = buttons[x][y];

                if (value != 9) {
                    if (value != 10) {
                        return false;
                    }
                }
            }

        }
        End();
        return true;
    }

    public boolean FastEnough(){
        String filename = "res/LeaderBoard.csv";
        int count = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(filename))){
            String line;

            while ((line = br.readLine()) != null) {
                String[] pole = line.split(";");
                String[] time = pole[1].split(":");

                if (Integer.parseInt(time[0])*60 + Integer.parseInt(time[1]) > getMinutesPassed()*60 + getSeconsPassed()){
                    return true;
                }
                count++;
            }

            if (count < 10){
                return true;
            }
        } catch (IOException e) {
            System.out.println("Tabulka výsledků zatím neexistuje");
        }
        return false;
    }

    public boolean NameExists(String name){
        String filename = "res/LeaderBoard.csv";
        try (BufferedReader br = new BufferedReader(new FileReader(filename))){
            String line;

            while ((line = br.readLine()) != null) {
                String[] pole = line.split(";");

                if (pole[0].equals(name)) {
                    return true;
                }
            }

        } catch (IOException e) {
            System.out.println("Tabulka výsledků zatím neexistuje");
        }
        return false;
    }

    public void End(){
        backButton.setVisible(true);
        JLabel spacer = new JLabel();
        spacer.setPreferredSize(backButton.getPreferredSize());
        topPanel.add(spacer, BorderLayout.WEST);
    }

    public int getSeconsPassed() {
        return seconsPassed;
    }

    public int getMinutesPassed() {
        return minutesPassed;
    }
}
