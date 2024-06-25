package com.dynalektric.view.modals;

import com.dynalektric.constants.DisplayConstant;
import com.dynalektric.constants.StyleConstants;
import com.dynalektric.constants.ViewMessages;
import com.dynalektric.view.ViewMessage;
import com.dynalektric.view.workViews.AbstractWorkView;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;

public class NewProjectModal extends AbstractModal {

    public NewProjectModal(AbstractWorkView parentView) {
        super(parentView, DisplayConstant.NEW_PROJECT);
    }

    @Override
    public void init() {
        SwingUtilities.invokeLater(this::initializeUI);
    }

    private void initializeUI() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(StyleConstants.MODAL_BACKGROUND);
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(StyleConstants.MODAL_BACKGROUND);

        JLabel heading = new JLabel(DisplayConstant.NEW_PROJECT);
        heading.setFont(StyleConstants.HEADING_SUB1);
        heading.setForeground(StyleConstants.FOREGROUND_PRIMARY);
        heading.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel projectNameLabel = new JLabel(DisplayConstant.NEW_PROJECT_NAME);
        projectNameLabel.setFont(StyleConstants.NORMAL_TEXT);
        projectNameLabel.setForeground(StyleConstants.FOREGROUND_PRIMARY);
        projectNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField projectNameTB = createStyledTextField();

        JButton submitButton = createStyledButton(DisplayConstant.CREATE_TEXT);

        contentPanel.add(Box.createVerticalGlue());
        contentPanel.add(heading);
        contentPanel.add(Box.createVerticalStrut(30));
        contentPanel.add(projectNameLabel);
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(projectNameTB);
        contentPanel.add(Box.createVerticalStrut(30));
        contentPanel.add(submitButton);
        contentPanel.add(Box.createVerticalGlue());

        mainPanel.add(contentPanel, BorderLayout.CENTER);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                String projectName = projectNameTB.getText();
                dispose();
                parentView.captureEventFromChildSubFrame(new ViewMessage(ViewMessages.NEW_PROJECT_NAME, projectName));
            }
        });

        this.setContentPane(mainPanel);
        this.setSize(400, 300);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private JTextField createStyledTextField() {
        JTextField textField = new JTextField() {
            @Override
            protected void paintComponent(Graphics g) {
                if (!isOpaque() && getBorder() instanceof RoundRectangle2D) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(getBackground());

                    RoundRectangle2D roundedBorder = (RoundRectangle2D) getBorder();
                    g2.fill(roundedBorder);

                    g2.dispose();
                }
                super.paintComponent(g);
            }
        };
        textField.setOpaque(false);
        textField.setBackground(StyleConstants.TEXT_FIELD_BACKGROUND);
        textField.setForeground(StyleConstants.FOREGROUND_PRIMARY);
        textField.setFont(StyleConstants.NORMAL_TEXT);
        textField.setBorder(BorderFactory.createCompoundBorder(
                new RoundBorder(StyleConstants.TEXT_FIELD_BORDER, 10),
                new EmptyBorder(5, 10, 5, 10)
        ));
        textField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        textField.setAlignmentX(Component.CENTER_ALIGNMENT);
        return textField;
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int width = getWidth();
                int height = getHeight();

                GradientPaint gp = new GradientPaint(0, 0, StyleConstants.BUTTON_GRADIENT_START,
                        0, height, StyleConstants.BUTTON_GRADIENT_END);
                g2.setPaint(gp);
                g2.fill(new RoundRectangle2D.Double(0, 0, width, height, 10, 10));

                g2.setColor(getForeground());
                g2.setFont(getFont());
                FontMetrics fm = g2.getFontMetrics();
                Rectangle stringBounds = fm.getStringBounds(this.getText(), g2).getBounds();
                int textX = (width - stringBounds.width) / 2;
                int textY = (height - stringBounds.height) / 2 + fm.getAscent();
                g2.drawString(getText(), textX, textY);

                g2.dispose();
            }
        };

        button.setFont(StyleConstants.BUTTON_FONT);
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(0, 0, 0, 0));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setPreferredSize(new Dimension(150, 40));
        button.setMaximumSize(new Dimension(150, 40));

        return button;
    }

    private static class RoundBorder extends AbstractBorder {
        private final Color color;
        private final int radius;

        RoundBorder(Color color, int radius) {
            this.color = color;
            this.radius = radius;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.draw(new RoundRectangle2D.Double(x, y, width - 1, height - 1, radius, radius));
            g2.dispose();
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(radius, radius, radius, radius);
        }
    }
}
