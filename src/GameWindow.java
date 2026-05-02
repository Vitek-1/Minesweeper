import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameWindow extends JFrame {
    private int[][] miny = new int[9][9];
    private JButton[][] buttons;
    private JPanel topPanel;
    private JButton backButton;
    private int numberOfMins = 15;
    private boolean lost = false;
    private int seconsPassed = 0;
    private int minutesPassed = 0;
    private Timer timer;
    private JLabel timeLabel;
    private final Color grey = new Color(192, 192, 192);
    private GameMechanic mechanic;

    public void StartGame() {
        new JFrame("Minesweeper");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        topPanel = new JPanel(new BorderLayout());
        topPanel.setPreferredSize(new Dimension(getWidth(), 100));

        Border bottomLine = BorderFactory.createMatteBorder(0, 0, 2, 0, Color.black);
        Border padding = BorderFactory.createEmptyBorder(10, 10, 10, 10);
        topPanel.setBorder(BorderFactory.createCompoundBorder(bottomLine, padding));

        timeLabel = new JLabel("00:00", SwingConstants.CENTER);
        timeLabel.setFont(new Font("Monospaced", Font.BOLD, 50));
        topPanel.add(timeLabel, BorderLayout.CENTER);

        backButton = new JButton("Back to menu");
        backButton.setFont(new Font("Times new Remains", Font.BOLD, 50));
        backButton.setBackground(grey);
        backButton.setFocusPainted(false);
        backButton.setContentAreaFilled(true);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backButton.setVisible(false);
        topPanel.add(backButton, BorderLayout.EAST);

        backButton.addActionListener(e -> {
            dispose();
            new HomeScreen().StartHomeScreen();
        });

        add(topPanel, BorderLayout.NORTH);

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

                        if (lost) {
                            return;
                        }

                        if (SwingUtilities.isLeftMouseButton(e)) {
                            mechanic.SetPicture(buttons[finalX][finalY], finalX, finalY);
                            lost = mechanic.isLost();

                            if (lost) {
                                mechanic.ShowAllMines(buttons[finalX][finalY]);
                                mechanic.timer.stop();
                            }

                        } else if (SwingUtilities.isRightMouseButton(e)) {
                            if (miny[finalX][finalY] != 10) {
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
        add(buttonPanel, BorderLayout.CENTER);
        mechanic = new GameMechanic(miny, buttons, grey, lost, backButton, topPanel, numberOfMins, seconsPassed, minutesPassed, timer, timeLabel);

        mechanic.StartTimer();
        mechanic.MineGenerator();
        mechanic.CalculateMines();

        setVisible(true);
    }
}
