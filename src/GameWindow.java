import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

public class GameWindow {
    JFrame frame;
    private int[][] Miny = new int[9][9];
    private int numberOfMins = 10;

    public GameWindow() {
        MineGenerator();
        CalculateMines();
    }

    public void StartGame() {
        frame = new JFrame("Minesweeper");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout());

        JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayout(9, 9, 2, 2));
        panel2.setOpaque(false);

        JButton[][] buttons = new JButton[9][9];

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
                        } else if (SwingUtilities.isRightMouseButton(e)){
                            ChangeLook.setFlagIcon(buttons[finalX][finalY], 100,100);
                        }
                    }
                });

                panel2.add(buttons[x][y]);
            }
        }
        frame.add(panel2, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    public void MineGenerator() {
        Random rnd = new Random();
        int laidMines = 0;

        while (laidMines < numberOfMins) {
            int x  = rnd.nextInt(9);
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

                            if (neighbourX >= 0 && neighbourY >= 0 &&  neighbourX < 9 && neighbourY < 9) {
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

    public void SetPicture(JButton button, int x, int y){
        if (Miny[x][y] != 9) {
            button.setText(String.valueOf(Miny[x][y]));
            button.setFont(new Font("Times New Roman", Font.BOLD, 50));
        } else {
            ChangeLook.setMinaIcon(button, 100,100);
        }
    }


}
