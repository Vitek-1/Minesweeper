import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

public class GameWindow {
    JFrame frame;
    private int[][] Miny = new int[9][9];
    private JButton[][] buttons;
    private JPanel topPanel;
    private JButton backButton;
    private int numberOfMins = 10;
    private boolean Lost = false;
    private int seconsPassed = 0;
    private int minutesPassed = 0;
    private Timer timer;
    private JLabel timeLabel;
    private Color grey = new Color(192,192,192);

    public void StartGame() {
        frame = new JFrame("Minesweeper");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        topPanel = new JPanel(new BorderLayout());
        topPanel.setPreferredSize(new Dimension(frame.getWidth(), 100));

        Border bottomLine =  BorderFactory.createMatteBorder(0, 0, 2, 0, Color.black);
        Border padding = BorderFactory.createEmptyBorder(10, 10, 10, 10);
        topPanel.setBorder(BorderFactory.createCompoundBorder(bottomLine, padding));

        timeLabel = new JLabel("00:00", SwingConstants.CENTER);
        timeLabel.setFont(new Font("Monospaced", Font.BOLD, 50));
        topPanel.add(timeLabel, BorderLayout.CENTER);
        StartTimer();

        backButton = new JButton("Back to menu");
        backButton.setFont(new Font("Times new Remains", Font.BOLD, 50));
        backButton.setBackground(grey);
        backButton.setFocusPainted(false);
        backButton.setContentAreaFilled(true);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backButton.setVisible(false);
        topPanel.add(backButton, BorderLayout.EAST);

        backButton.addActionListener(e -> {
            frame.dispose();
            new HomeScreen().StartHomeScreen();
        });

        frame.add(topPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setLayout(new GridLayout(9, 9, 2, 2));
        buttonPanel.setBackground(Color.BLACK);
        buttonPanel.setOpaque(true);

        buttons = new JButton[9][9];

        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                buttons[x][y] = new JButton();

                final int finalX = x;
                final int finalY = y;

                    buttons[x][y].addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {

                            if (Lost){
                                return;
                            }

                            if (SwingUtilities.isLeftMouseButton(e)) {
                                SetPicture(buttons[finalX][finalY], finalX, finalY);

                                if (Lost){
                                    ShowAllMines(buttons[finalX][finalY]);
                                    timer.stop();
                                }

                            } else if (SwingUtilities.isRightMouseButton(e)) {
                                if (Miny[finalX][finalY] != 10) {
                                    buttons[finalX][finalY].setText("");
                                    ChangeLook.setFlagIcon(buttons[finalX][finalY], 100, 100);
                                }
                            }
                        }
                    });
                buttons[x][y].setCursor(new Cursor(Cursor.HAND_CURSOR));
                buttons[x][y].setBorderPainted(false);
                buttons[x][y].setFocusPainted(false);
                buttonPanel.add(buttons[x][y]);
            }
        }
        frame.add(buttonPanel, BorderLayout.CENTER);

        MineGenerator();
        CalculateMines();

        frame.setVisible(true);
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
                timeLabel.setFont(new Font("Monospaced", Font.BOLD, 50));
            }
        });
        timer.start();
    }

    public void MineGenerator() {
        Random rnd = new Random();
        int laidMines = 0;

        while (laidMines < numberOfMins) {
            int x = rnd.nextInt(9);
            int y = rnd.nextInt(9);

            if (Miny[x][y] != 9) {
                Miny[x][y] = 9;
                laidMines++;
            }
        }
    }

    public void CalculateMines() {
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                if (Miny[x][y] != 9) {

                    int neighbour = 0;

                    for (int i = -1; i <= 1; i++) {
                        for (int j = -1; j <= 1; j++) {
                            int neighbourX = x + i;
                            int neighbourY = y + j;

                            if (neighbourX >= 0 && neighbourY >= 0 && neighbourX < 9 && neighbourY < 9) {
                                if (Miny[neighbourX][neighbourY] == 9) {
                                    neighbour++;
                                }
                            }
                        }
                    }
                    Miny[x][y] = neighbour;
                }
            }
        }
    }

    public void SetPicture(JButton button, int x, int y) {
        if (Miny[x][y] != 9 && Miny[x][y] != 10) {
            int number = Miny[x][y];

            button.setIcon(null);
            button.setText(String.valueOf(Miny[x][y]));
            button.setFont(new Font("Times New Roman", Font.BOLD, 45));
            Miny[x][y] = 10;

            button.setBackground(grey);
            button.setContentAreaFilled(false);
            button.setOpaque(true);
            button.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

            button.setBorderPainted(false);
            button.setFocusPainted(false);

            if (number == 0) {
                ShowVicinity(x,y);
            }
        } else if (Miny[x][y] == 9) {
            ChangeLook.setMinaIcon(button, 100, 100);
            Lost = true;
        }
    }

    public void ShowAllMines(JButton button){
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                if (Miny[x][y] == 9) {
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
        backButton.setVisible(true);
        JLabel spacer = new JLabel();
        spacer.setPreferredSize(backButton.getPreferredSize());
        topPanel.add(spacer, BorderLayout.WEST);
    }

    public void ShowVicinity(int x, int y){
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int neighbourX = x + i;
                int neighbourY = y + j;

                if (neighbourX >= 0 && neighbourY >= 0 && neighbourX < 9 && neighbourY < 9){
                    if (Miny[neighbourX][neighbourY] != 10) {
                        SetPicture(buttons[neighbourX][neighbourY], neighbourX, neighbourY);
                        if (Miny[neighbourX][neighbourY] == 0) {
                            ShowVicinity(neighbourX, neighbourY);
                        }
                    }
                }
            }
        }
    }
}
