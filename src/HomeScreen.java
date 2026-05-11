import javax.swing.*;
import java.awt.*;

public class HomeScreen {

    private JFrame frame;
    private Color grey = new Color(182, 181, 181);

    public void StartHomeScreen() {
        frame = new JFrame("HomeScreen");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton play = new JButton("Play");
        ChangeLook.changeGreen(play);
        play.setCursor(new Cursor(Cursor.HAND_CURSOR));

        play.setPreferredSize(new Dimension(200, 100));

        JPanel bottomPanel = new JPanel();
        bottomPanel.setPreferredSize(new Dimension(1000, 250));
        bottomPanel.setOpaque(false);
        bottomPanel.add(play);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        play.addActionListener(e -> {
            frame.dispose();
            new GameWindow().StartGame();
        });

        JPanel topPanel = new JPanel(new GridLayout(1, 2));
        topPanel.setPreferredSize(new Dimension(frame.getWidth(), 150));

        //Pravy horni panel
        JPanel topRightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topRightPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 25, 15));
        topRightPanel.setBackground(grey);

        JButton settings = new JButton("");
        ChangeLook.SetSettingsIcon(settings, 100, 100);
        settings.setCursor(new Cursor(Cursor.HAND_CURSOR));

        topRightPanel.add(settings, BorderLayout.WEST);

        //Levy horni panel
        JPanel topLeftPanel = new JPanel(new  FlowLayout(FlowLayout.LEFT));
        topLeftPanel.setBorder(BorderFactory.createEmptyBorder(22, 20, 25, 0));
        topLeftPanel.setBackground(grey);

        JButton leaderboardBut = new JButton("Leaderboard");
        leaderboardBut.setFont(new Font("Arial", Font.BOLD, 30));
        leaderboardBut.setPreferredSize(new Dimension(300, 100));
        leaderboardBut.setCursor(new Cursor(Cursor.HAND_CURSOR));
        leaderboardBut.setFocusPainted(false);

        topLeftPanel.add(leaderboardBut, BorderLayout.CENTER);

        topPanel.add(topLeftPanel, BorderLayout.EAST);
        topPanel.add(topRightPanel, BorderLayout.WEST);


        frame.add(topPanel, BorderLayout.NORTH);

        settings.addActionListener(e -> {
            this.frame.dispose();
        });

        leaderboardBut.addActionListener(e -> {
            new  LeaderboardWindow();
        });

        frame.setVisible(true);
    }
}
