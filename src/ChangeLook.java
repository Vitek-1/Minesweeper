import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class ChangeLook {

    public static void changeGreen(JButton button) {
        button.setBackground(new Color(40, 255, 25));
        button.setForeground(Color.BLACK);

        button.setFont(new Font("Times new Roman", Font.BOLD, 50 )); // Font 50 je pro hlavní tlačítko ideální!

        button.setFocusPainted(false);
        button.setBorderPainted(false);

        button.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    }

    public static void setMinaIcon(JButton button, int width, int height) {
        URL imgURL = ChangeLook.class.getResource("/Settings.jpg");

        setButtonAsImg(button, imgURL, width, height);

        button.setVerticalTextPosition(SwingConstants.BOTTOM);
        button.setHorizontalTextPosition(SwingConstants.CENTER);
    }



    public static void setButtonAsImg(JButton button, URL imgURL, int width, int height) {
        if (imgURL != null) {
            ImageIcon icon = new ImageIcon(imgURL);

            Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            button.setIcon(new ImageIcon(img));

            button.setBorderPainted(false);
            button.setContentAreaFilled(false);
            button.setFocusPainted(false);
        } else {
            System.out.println("Image not found");
        }
    }



    public static void changeViolet(JButton button) {
        button.setBackground(new Color(205, 43, 216));
        button.setForeground(Color.BLACK);

        button.setFont(new Font("Times new Roman", Font.BOLD, 25));

        button.setFocusPainted(false);
        button.setBorderPainted(false);
    }

    public static void changeLabel(JLabel label) {
        label.setFont(new Font("Times new Roman", Font.BOLD, 25));
        label.setForeground(Color.BLACK);
        label.setBackground(new Color(25, 25, 255));
        label.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

    }

    public static void changeTextField(JTextField field) {
        field.setBackground(new Color(255, 90, 95));
        field.setForeground(Color.BLACK);
        field.setFont(new Font("Times new Roman", Font.BOLD, 25));
    }

    public static void changeMoneyLabel(JLabel label) {
        label.setFont(new Font("Arial", Font.BOLD, 40));
        label.setForeground(new Color(255, 215, 0));
    }
}
