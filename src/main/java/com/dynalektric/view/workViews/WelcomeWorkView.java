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
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                initializeUI();
            }
        });
    }

    @Override
    public void update(String msg) {
        super.update(msg);
    }

    @Override
    public void update(String msg, Object data) {
        super.update(msg , data);
    }

    @Override
    public String getViewName() {
        return VIEW_NAME;
    }

    @Override
    public Integer getViewId() {
        return 1;
    }

    private void initializeUI(){
        this.setLayout(new BorderLayout());
        this.initializeLogoPanel();
        JPanel welcomeWorkViewMainPanel = new JPanel();
        welcomeWorkViewMainPanel.setLayout(new GridLayout(0, 2));

        leftPanel.setLayout(new BoxLayout(leftPanel , BoxLayout.Y_AXIS));
        leftPanel.setBackground(new Color(63, 102, 252));
        rightPanel.setLayout(new BoxLayout(rightPanel , BoxLayout.Y_AXIS));
        rightPanel.setBackground(new Color(63, 102, 252));
        JLabel heading = new JLabel(DisplayConstant.APP_TITLE);
        JButton newProjectButton = new JButton(DisplayConstant.NEW_PROJECT);
        JButton openProjectButton = new JButton(DisplayConstant.OPEN_PROJECT);

        heading.setFont(StyleConstants.HEADING_MAIN);
        heading.setAlignmentX(CENTER_ALIGNMENT);
        newProjectButton.setFont(StyleConstants.NEW_PROJECT);
        newProjectButton.setAlignmentX(CENTER_ALIGNMENT);
        openProjectButton.setFont(StyleConstants.NEW_PROJECT);
        openProjectButton.setAlignmentX(CENTER_ALIGNMENT);


        rightPanel.add(Box.createVerticalStrut(50));
        rightPanel.add(heading);
        rightPanel.add(Box.createVerticalStrut(30));
        rightPanel.add(newProjectButton);
        rightPanel.add(Box.createVerticalStrut(10));
        rightPanel.add(openProjectButton);

        this.initializeLeftPanel();

        welcomeWorkViewMainPanel.add(leftPanel);
        welcomeWorkViewMainPanel.add(rightPanel);

        this.add(welcomeWorkViewMainPanel ,BorderLayout.CENTER);

        newProjectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {

                AbstractModal newProject = new NewProjectModal(thisReference);
                newProject.init();
            }
        });

        openProjectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                List<String> projects = model.getGeneralRepo().getNamesOfAllProjectsCreated();
                AbstractModal openProject = new OpenProjectModal(thisReference, projects);
                openProject.init();
            }
        });
    }

    private JPanel createCardForProject(String project){
        JPanel cardContent = new JPanel(new FlowLayout(FlowLayout.LEADING));
        cardContent.setBackground(new Color(63, 102, 252));
        JButton openProjectButton = new JButton(project);
        openProjectButton.setFont(StyleConstants.NEW_PROJECT);
        openProjectButton.setBackground(StyleConstants.BACKGROUND_SECONDARY);
        openProjectButton.setPreferredSize(new Dimension(540 , openProjectButton.getPreferredSize().height));
        openProjectButton.setHorizontalAlignment(SwingConstants.LEFT);
        openProjectButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(e.getButton() == MouseEvent.BUTTON1) {
                    LOGGER.info("Opening project {}" , project);
                    controller.openProjectWithName(project);
                }else {
                    ProjectPopUpMenu popUp = new ProjectPopUpMenu(project , thisReference);
                    popUp.show(e.getComponent() , e.getX() , e.getY());
                }
            }
        });
        cardContent.add(openProjectButton);
        cardContent.setMaximumSize(new Dimension(550 , cardContent.getPreferredSize().height));
        cardContent.setAlignmentX(LEFT_ALIGNMENT);
        return cardContent;
    }

    private void initializeLeftPanel(){
        JPanel resultPanel = new JPanel();
        BoxLayout layout = new BoxLayout(resultPanel,BoxLayout.Y_AXIS);
        resultPanel.setLayout(layout);
        resultPanel.setBackground(new Color(63, 102, 252));

        JLabel recentProjects = new JLabel(DisplayConstant.RECENT_PROJECTS);
        recentProjects.setFont(StyleConstants.RECENT_PROJECTS);
        recentProjects.setAlignmentX(CENTER_ALIGNMENT);
        leftPanel.add(Box.createVerticalStrut(50));
        leftPanel.add(recentProjects);
        List<String> projects = model.getGeneralRepo().getNamesOfAllProjectsCreated();
        for(String project : projects){
            resultPanel.add(Box.createVerticalStrut(15));
            resultPanel.add(createCardForProject(project));
            resultPanel.add(Box.createVerticalStrut(15));
        }
        JScrollPane scrollPane = new JScrollPane(resultPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10 , 10 , 10 , 10));
        leftPanel.add(Box.createVerticalStrut(15));
        leftPanel.add(scrollPane);
        scrollPane.setBackground(new Color(63, 102, 252));
    }
    private void initializeLogoPanel(){
        logoPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
       
        logoPanel.setPreferredSize(new Dimension(
                (int)View.SCREEN_DIMENSION.getWidth(),
                (int)(View.SCREEN_DIMENSION.getHeight()*0.1)));
        logoPanel.setBackground(new Color(63, 102, 252));
        this.add(logoPanel,BorderLayout.NORTH);
        try{
            BufferedImage nhceLogoImage = ImageIO.read(this.getClass().getResource("NHCE.jpg"));
            ImageIcon nhceLogoIcon = new ImageIcon(nhceLogoImage);
            Image nhceScaledLogoImage = nhceLogoIcon.getImage().getScaledInstance((int)(this.getPreferredSize().getWidth()*0.15), -1, Image.SCALE_SMOOTH);
            ImageIcon nhceScaledLogoIcon = new ImageIcon(nhceScaledLogoImage);
            JLabel nhceIconLabel = new JLabel(nhceScaledLogoIcon);

            BufferedImage dynaLogoImage = ImageIO.read(this.getClass().getResource("DYNA.jpg"));
            ImageIcon dynaLogoIcon = new ImageIcon(dynaLogoImage);
            Image dynaScaledLogoImage = dynaLogoIcon.getImage().getScaledInstance((int)(this.getPreferredSize().getWidth()*0.04), -1, Image.SCALE_SMOOTH);
            ImageIcon dynaScaledLogoIcon = new ImageIcon(dynaScaledLogoImage);
            JLabel dynaIconLabel = new JLabel(dynaScaledLogoIcon);

            logoPanel.add(Box.createHorizontalStrut((int)(this.getPreferredSize().getWidth()*0.1)));
            logoPanel.add(nhceIconLabel);
            logoPanel.add(Box.createHorizontalStrut((int)(this.getPreferredSize().getWidth()*0.6)));
            logoPanel.add(dynaIconLabel);
            logoPanel.add(Box.createHorizontalStrut((int)(this.getPreferredSize().getWidth()*0.02)));

        }
        catch(IOException e){
            LOGGER.error(e.getMessage() ,e);
        }

    }

    @Override
    public void captureEventFromChildSubFrame(ViewMessage message){
        switch(message.getMsgType()) {
            case ViewMessages.NEW_PROJECT_NAME:
                LOGGER.info("Creating new project {} ", message.getMsgData());
                controller.createNewProject((String) message.getMsgData());
                leftPanel.removeAll();
                leftPanel.revalidate();
                initializeLeftPanel();
                leftPanel.repaint();
                break;
            case ViewMessages.OPEN_SELECTED_PROJECT:
                LOGGER.info("Opening project {}" , message.getMsgData());
                controller.openProjectWithName((String)message.getMsgData());
                break;
            case ViewMessages.DELETE_SELECTED_PROJECT:
                LOGGER.info("Deleting project {}" , message.getMsgData());
                controller.deleteProjectByName((String)message.getMsgData());
                JOptionPane.showMessageDialog(new JFrame(), "Deleted Project", "Dialog",
                        JOptionPane.INFORMATION_MESSAGE);
                leftPanel.removeAll();
                leftPanel.revalidate();
                this.initializeLeftPanel();
                leftPanel.repaint();
                break;
        }
    }
}
