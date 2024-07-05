package com.dynalektric.view;
import com.dynalektric.constants.StyleConstants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SplashScreen {

    public static void runSplash(Runnable mainAppInit) {
        JFrame splashScreen1 = new JFrame("Splash Screen 1");
        splashScreen1.setUndecorated(true);

        JPanel panel1 = new JPanel(new GridBagLayout());
        panel1.setBackground(StyleConstants.BACKGROUND);

        ImageIcon splashImage1 = new ImageIcon("src/main/resources/com/dynalektric/view/workViews/DYNAXNHCE.png");
        Image scaledImage1 = splashImage1.getImage().getScaledInstance(800, 800, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon1 = new ImageIcon(scaledImage1);

        JLabel splashLabel1 = new JLabel(scaledIcon1);
        panel1.add(splashLabel1);

        splashScreen1.setContentPane(panel1);
        splashScreen1.pack();
        splashScreen1.setSize(new Dimension(
                (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth(),
                (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight()
        ));
        splashScreen1.setLocationRelativeTo(null);
        splashScreen1.setVisible(true);

        Timer timer1 = new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame splashScreen2 = new JFrame("Splash Screen 2");
                splashScreen2.setUndecorated(true);
                splashScreen2.getContentPane().setBackground(StyleConstants.BACKGROUND);

                JPanel panel2 = new JPanel(new GridBagLayout());
                panel2.setBackground(StyleConstants.BACKGROUND);

                JLabel splashLabel = new JLabel("Welcome!!!", SwingConstants.CENTER);
                splashLabel.setFont(new Font("Algerian", Font.BOLD, 40));
                panel2.add(splashLabel);

                splashScreen2.setContentPane(panel2);
                splashScreen2.pack();
                splashScreen2.setSize(new Dimension(
                        (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth(),
                        (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight()
                ));
                splashScreen2.setLocationRelativeTo(null);
                splashScreen2.setVisible(true);

                Timer timer2 = new Timer(1000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        SwingUtilities.invokeLater(mainAppInit);
                        Timer timer3 = new Timer(200, new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e){
                                splashScreen2.dispose();
                                splashScreen1.dispose();
                            }
                        });

                        timer3.setRepeats(false);
                        timer3.start();

                        // Initialize and start the main application
                    }
                });

                timer2.setRepeats(false);
                timer2.start();
            }
        });

        timer1.setRepeats(false);
        timer1.start();
    }
}