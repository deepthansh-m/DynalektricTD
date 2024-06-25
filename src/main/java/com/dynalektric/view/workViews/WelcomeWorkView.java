package com.dynalektric.view.workViews;

import com.dynalektric.constants.DisplayConstant;
import com.dynalektric.constants.StyleConstants;
import com.dynalektric.constants.ViewMessages;
import com.dynalektric.control.WelcomeWorkViewController;
import com.dynalektric.model.Model;
import com.dynalektric.view.ChildFrameListener;
import com.dynalektric.view.View;
import com.dynalektric.view.ViewMessage;
import com.dynalektric.view.modals.AbstractModal;
import com.dynalektric.view.modals.NewProjectModal;
import com.dynalektric.view.modals.OpenProjectModal;
import com.dynalektric.view.modals.ProjectPopUpMenu;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

public class WelcomeWorkView extends AbstractWorkView implements ChildFrameListener {

    private final JPanel logoPanel = new JPanel();
    private final JPanel rightPanel = new JPanel();
    private final JPanel leftPanel = new JPanel();
    private final WelcomeWorkViewController controller = new WelcomeWorkViewController();
    private final WelcomeWorkView thisReference = this;
    private final static Logger LOGGER = LogManager.getLogger(WelcomeWorkView.class);
    public final static String VIEW_NAME = "Welcome work view";

    public WelcomeWorkView(Model model) {
        super(model);
        SwingUtilities.invokeLater(this::initializeUI);
    }

    @Override
    public void update(String msg) {
        super.update(msg);
    }

    @Override
    public void update(String msg, Object data) {
        super.update(msg, data);
    }

    @Override
    public String getViewName() {
        return VIEW_NAME;
    }

    @Override
    public Integer getViewId() {
        return 1;
    }

    private void initializeUI() {
        this.setLayout(new BorderLayout());
        this.initializeLogoPanel();
        JPanel welcomeWorkViewMainPanel = new JPanel(new GridBagLayout());
        welcomeWorkViewMainPanel.setBackground(StyleConstants.BACKGROUND);

        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(StyleConstants.BACKGROUND);
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBackground(StyleConstants.BACKGROUND);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        gbc.gridx = 0;
        gbc.gridy = 0;
        welcomeWorkViewMainPanel.add(leftPanel, gbc);

        gbc.gridx = 1;
        welcomeWorkViewMainPanel.add(rightPanel, gbc);

        this.add(welcomeWorkViewMainPanel, BorderLayout.CENTER);

        initializeLeftPanel();
        initializeRightPanel();
    }

    private void initializeLogoPanel() {
        logoPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        logoPanel.setPreferredSize(new Dimension(
                (int)View.SCREEN_DIMENSION.getWidth(),
                (int)(View.SCREEN_DIMENSION.getHeight()*0.20)));
        logoPanel.setBackground(StyleConstants.BACKGROUND);
        this.add(logoPanel,BorderLayout.NORTH);
        try{

            BufferedImage dynaLogoImage = ImageIO.read(this.getClass().getResource("DYNA.jpg"));
            ImageIcon dynaLogoIcon = new ImageIcon(dynaLogoImage);
            Image dynaScaledLogoImage = dynaLogoIcon.getImage().getScaledInstance((int)(this.getPreferredSize().getWidth()*0.16), -1, Image.SCALE_SMOOTH);
            ImageIcon dynaScaledLogoIcon = new ImageIcon(dynaScaledLogoImage);
            JLabel dynaIconLabel = new JLabel(dynaScaledLogoIcon);
            BufferedImage nhceLogoImage = ImageIO.read(this.getClass().getResource("NHCE.jpg"));
            ImageIcon nhceLogoIcon = new ImageIcon(nhceLogoImage);
            Image nhceScaledLogoImage = nhceLogoIcon.getImage().getScaledInstance((int)(this.getPreferredSize().getWidth()*0.25), -1, Image.SCALE_SMOOTH);
            ImageIcon nhceScaledLogoIcon = new ImageIcon(nhceScaledLogoImage);
            JLabel nhceIconLabel = new JLabel(nhceScaledLogoIcon);

            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.insets = new Insets(0, (int) (this.getPreferredSize().getWidth() * 0.1), 0, 0);
            gbc.anchor = GridBagConstraints.WEST;
            logoPanel.add(dynaIconLabel, gbc);

            gbc.gridx = 1;
            gbc.weightx = 1.0;
            gbc.insets = new Insets(0, 0, 0, 0);
            gbc.anchor = GridBagConstraints.CENTER;
            logoPanel.add(Box.createHorizontalGlue(), gbc);

            gbc.gridx = 2;
            gbc.weightx = 0;
            gbc.insets = new Insets(0, 0, 0, (int) (this.getPreferredSize().getWidth() * 0.02));
            gbc.anchor = GridBagConstraints.EAST;
            logoPanel.add(nhceIconLabel, gbc);

        }
        catch(IOException e){
            LOGGER.error(e.getMessage() ,e);

        }

        this.add(logoPanel, BorderLayout.NORTH);
    }

    private void initializeLeftPanel() {
        JLabel recentProjects = new JLabel(DisplayConstant.RECENT_PROJECTS);
        recentProjects.setFont(StyleConstants.RECENT_PROJECTS);
        recentProjects.setAlignmentX(Component.CENTER_ALIGNMENT);
        leftPanel.add(Box.createVerticalStrut(50));
        leftPanel.add(recentProjects);

        JPanel resultPanel = new JPanel();
        BoxLayout layout = new BoxLayout(resultPanel, BoxLayout.Y_AXIS);
        resultPanel.setLayout(layout);
        resultPanel.setBackground(StyleConstants.BACKGROUND);

        List<String> projects = model.getGeneralRepo().getNamesOfAllProjectsCreated();
        for (String project : projects) {
            resultPanel.add(Box.createVerticalStrut(5));
            resultPanel.add(createCardForProject(project));
            resultPanel.add(Box.createVerticalStrut(5));
        }

        JScrollPane scrollPane = new JScrollPane(resultPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        scrollPane.setBackground(StyleConstants.BACKGROUND);
        leftPanel.add(Box.createVerticalStrut(15));
        leftPanel.add(scrollPane);
    }

    private JPanel createCardForProject(String project) {
        JPanel cardContent = new JPanel(new BorderLayout());
        cardContent.setBackground(StyleConstants.CARD_BACKGROUND);
        cardContent.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(StyleConstants.CARD_BORDER, 1),
                new EmptyBorder(5, 10, 5, 10)
        ));

        JLabel projectIcon = new JLabel(UIManager.getIcon("FileView.fileIcon"));
        projectIcon.setBorder(new EmptyBorder(0, 0, 0, 10));

        JLabel projectName = new JLabel(project);
        projectName.setFont(StyleConstants.PROJECT_NAME_FONT.deriveFont(12f));
        projectName.setForeground(StyleConstants.PROJECT_NAME_COLOR);

        JPanel leftContent = new JPanel(new BorderLayout());
        leftContent.setOpaque(false);
        leftContent.add(projectIcon, BorderLayout.WEST);
        leftContent.add(projectName, BorderLayout.CENTER);

        JLabel openLabel = new JLabel("Open");
        openLabel.setFont(StyleConstants.PROJECT_NAME_FONT.deriveFont(10f));
        openLabel.setForeground(StyleConstants.BUTTON_GRADIENT_START);
        openLabel.setBorder(new EmptyBorder(0, 0, 0, 5));

        cardContent.add(leftContent, BorderLayout.WEST);
        cardContent.add(openLabel, BorderLayout.EAST);

        cardContent.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40)); // Allows horizontal expansion
        cardContent.setPreferredSize(new Dimension((int) (leftPanel.getPreferredSize().getWidth() * 0.5), 40)); // Set width to half of left panel

        cardContent.setAlignmentX(Component.LEFT_ALIGNMENT);

        cardContent.addMouseListener(new ProjectCardMouseListener(project));

        return cardContent;
    }

    private void initializeRightPanel() {
        JLabel heading = new JLabel(DisplayConstant.APP_TITLE);
        JButton newProjectButton = createStyledButton(DisplayConstant.NEW_PROJECT);
        JButton openProjectButton = createStyledButton(DisplayConstant.OPEN_PROJECT);

        heading.setFont(StyleConstants.HEADING_MAIN);
        heading.setAlignmentX(Component.CENTER_ALIGNMENT);

        rightPanel.add(Box.createVerticalStrut(50));
        rightPanel.add(heading);
        rightPanel.add(Box.createVerticalStrut(30));
        rightPanel.add(newProjectButton);
        rightPanel.add(Box.createVerticalStrut(10));
        rightPanel.add(openProjectButton);

        newProjectButton.addActionListener(e -> {
            AbstractModal newProject = new NewProjectModal(thisReference);
            newProject.init();
        });

        openProjectButton.addActionListener(e -> {
            List<String> projects = model.getGeneralRepo().getNamesOfAllProjectsCreated();
            AbstractModal openProject = new OpenProjectModal(thisReference, projects);
            openProject.init();
        });
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
        button.setPreferredSize(new Dimension(200, 40));
        button.setMaximumSize(new Dimension(200, 40));


        button.setIcon(UIManager.getIcon("FileView.directoryIcon"));
        button.setIconTextGap(10);

        button.addMouseListener(new ButtonHoverListener());
        return button;
    }

    @Override
    public void captureEventFromChildSubFrame(ViewMessage message) {
        switch (message.getMsgType()) {
            case ViewMessages.NEW_PROJECT_NAME:
                LOGGER.info("Creating new project {}", message.getMsgData());
                controller.createNewProject((String) message.getMsgData());
                refreshLeftPanel();
                break;
            case ViewMessages.OPEN_SELECTED_PROJECT:
                LOGGER.info("Opening project {}", message.getMsgData());
                controller.openProjectWithName((String) message.getMsgData());
                break;
            case ViewMessages.DELETE_SELECTED_PROJECT:
                LOGGER.info("Deleting project {}", message.getMsgData());
                controller.deleteProjectByName((String) message.getMsgData());
                JOptionPane.showMessageDialog(new JFrame(), "Deleted Project", "Dialog",
                        JOptionPane.INFORMATION_MESSAGE);
                refreshLeftPanel();
                break;
        }
    }

    private void refreshLeftPanel() {
        leftPanel.removeAll();
        initializeLeftPanel();
        leftPanel.revalidate();
        leftPanel.repaint();
    }

    private class ProjectCardMouseListener extends MouseAdapter {
        private final String project;

        ProjectCardMouseListener(String project) {
            this.project = project;
        }

        @Override
        public void mousePressed(MouseEvent e) {
            if (e.getButton() == MouseEvent.BUTTON1) {
                LOGGER.info("Opening project {}", project);
                controller.openProjectWithName(project);
            } else {
                ProjectPopUpMenu popUp = new ProjectPopUpMenu(project, thisReference);
                popUp.show(e.getComponent(), e.getX(), e.getY());
            }
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            JPanel card = (JPanel) e.getComponent();
            card.setBackground(StyleConstants.CARD_HOVER_BACKGROUND);
            card.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(StyleConstants.CARD_HOVER_BORDER, 1),
                    new EmptyBorder(5, 10, 5, 10)
            ));
        }

        @Override
        public void mouseExited(MouseEvent e) {
            JPanel card = (JPanel) e.getComponent();
            card.setBackground(StyleConstants.CARD_BACKGROUND);
            card.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(StyleConstants.CARD_BORDER, 1),
                    new EmptyBorder(5, 10, 5, 10)
            ));
        }
    }

    private class ButtonHoverListener extends MouseAdapter {
        @Override
        public void mouseEntered(MouseEvent e) {
            JButton button = (JButton) e.getComponent();
            button.setForeground(StyleConstants.BUTTON_HOVER_TEXT);
        }

        @Override
        public void mouseExited(MouseEvent e) {
            JButton button = (JButton) e.getComponent();
            button.setForeground(Color.WHITE);
        }
    }
}