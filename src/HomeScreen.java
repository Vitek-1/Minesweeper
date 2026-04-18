import javax.swing.*;
import java.awt.*;

public class HomeScreen {

    private JFrame frame;

    public void StartHomeScreen() {
        frame = new JFrame("HomeScreen");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(Color.BLACK);

        JButton play = new JButton("Play");
        ChangeLook.changeGreen(play);

        play.setPreferredSize(new Dimension(200,100));

        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(1000,250));
        panel.setOpaque(false);
        panel.add(play);
        frame.add(panel, BorderLayout.SOUTH);

        play.addActionListener(e -> {
            frame.dispose();
            new GameWindow().StartGame();
        });

        JButton settings = new JButton("");
        ChangeLook.setMinaIcon(settings,100,100);

        JPanel panel2 = new JPanel(new  FlowLayout(FlowLayout.RIGHT));
        panel2.setPreferredSize(new Dimension(1000,120));
        panel2.setOpaque(false);
        panel2.setBackground(Color.white);
        panel2.add(settings);
        frame.add(panel2, BorderLayout.NORTH);

        settings.addActionListener(e ->{
            this.frame.dispose();
        });

        frame.setVisible(true);
    }
}
