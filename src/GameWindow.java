import javax.swing.*;
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
    private int numberOfMins = 10;
    private boolean Lost = false;
    private int seconsPassed = 0;
    private int minutesPassed = 0;
    private Timer timer;
    private JLabel timeLabel;

    public void StartGame() {
        frame = new JFrame("Minesweeper");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(frame.getWidth(), 100));

        timeLabel = new JLabel();
        StartTimer();

        timeLabel.setBackground(Color.white);
        timeLabel.setFont(new Font("Monospaced", Font.BOLD, 25));

        panel.add(timeLabel);
        panel.setBorder(BorderFactory.createMatteBorder(0, 0, 5, 0, Color.BLACK));
        frame.add(panel, BorderLayout.NORTH);

        JPanel panel2 = new JPanel(new GridBagLayout());
        panel2.setLayout(new GridLayout(9, 9, 2, 2));
        panel2.setBackground(Color.BLACK);
        panel2.setOpaque(true);

        buttons = new JButton[9][9];

        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                buttons[x][y] = new JButton();

                final int finalX = x;
                final int finalY = y;

                    buttons[x][y].addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {

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

                buttons[x][y].setBorderPainted(false);
                buttons[x][y].setFocusPainted(false);
                panel2.add(buttons[x][y]);
            }
        }
        frame.add(panel2, BorderLayout.CENTER);

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
            button.setIcon(null);
            button.setText(String.valueOf(Miny[x][y]));
            button.setFont(new Font("Times New Roman", Font.BOLD, 45));
            Miny[x][y] = 10;

            button.setBackground(new Color(194, 194, 194));
            button.setContentAreaFilled(false);
            button.setOpaque(true);

            button.setBorderPainted(false);
            button.setFocusPainted(false);
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
                }
                buttons[x][y].setBackground(new Color(194, 194, 194));
                buttons[x][y].setContentAreaFilled(false);
                buttons[x][y].setOpaque(true);

                buttons[x][y].setBorderPainted(false);
                buttons[x][y].setFocusPainted(false);
            }
        }
    }


}
