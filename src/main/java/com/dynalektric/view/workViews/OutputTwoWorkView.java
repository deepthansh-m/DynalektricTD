package com.dynalektric.view.workViews;

import com.dynalektric.constants.StyleConstants;
import com.dynalektric.constants.ViewMessages;
import com.dynalektric.control.Control;
import com.dynalektric.model.Model;
import com.dynalektric.model.repositories.project.InputData;
import com.dynalektric.model.repositories.project.OutputData;
import com.dynalektric.print.PrintManager;
import com.dynalektric.view.View;
import com.dynalektric.view.ViewMessage;
import com.dynalektric.view.components.MenuBar;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import static com.dynalektric.view.workViews.PrintWorkView.*;

public class OutputTwoWorkView extends AbstractWorkView{

    private final OutputTwoWorkView thisReference = this;
    private final Control mainController = new Control();
    public final static String VIEW_NAME = "OutputWorkViewTwo";

    private final JPanel mainPanel = new JPanel();
    private final JPanel contentPanel = new JPanel();
    private final JTable tankDimensionTable = new JTable(7  , 2){
        @Override
        public boolean isCellEditable(int row , int col){
            return false;
        }
    };;
    private final JTable impedanceVoltageTable = new JTable(8 , 2){
        @Override
        public boolean isCellEditable(int row , int col){
            return false;
        }
    };;
    private final JTable lossesTable = new JTable(9 , 2){
        @Override
        public boolean isCellEditable(int row , int col){
            return false;
        }
    };;
    private final JTable billTable = new JTable(11, 2){
        @Override
        public boolean isCellEditable(int row , int col){
            return false;
        }
    };;
    private final JTable surfaceTable = new JTable(5,2){
        @Override
        public boolean isCellEditable(int row , int col){ return false; }
    };
    private final JTable vaTable = new JTable(7,3){
        @Override
        public boolean isCellEditable(int row , int col){ return false; }
    };
    private final JPanel dimensionPanel = new JPanel();
    private final JPanel impedancePanel = new JPanel();
    private final JPanel lossesPanel = new JPanel();
    private final JPanel billPanel = new JPanel();
    private final JPanel surfacePanel = new JPanel();
    private final JPanel vaPanel = new JPanel();
    public OutputTwoWorkView(Model model) {
        super(model);
        this.setLayout(new BorderLayout());
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                initializeUI();
            }
        });
    }

    @Override
    public void update(String msg){
        if(msg.equals("MODEL_UPDATED")){
            this.initializeTankDimensionTable();
            this.initializeBillTable();
            this.initializeImpedanceTable();
            this.initializeLossesTable();
            this.initializeSurfaceTable();
            this.initializeVATable();
        }
    }

    private void initializeUI() {
        this.mainPanel.setLayout(new BorderLayout());
        this.contentPanel.setLayout(new GridLayout(1 , 3));
        BoxLayout dimensionLayout = new BoxLayout(this.dimensionPanel , BoxLayout.Y_AXIS);
        this.dimensionPanel.setLayout(dimensionLayout);
        this.dimensionPanel.setBackground(StyleConstants.BACKGROUND);
        BoxLayout impedanceLayout = new BoxLayout(this.impedancePanel , BoxLayout.Y_AXIS);
        this.impedancePanel.setLayout(impedanceLayout);
        this.impedancePanel.setBackground(StyleConstants.BACKGROUND);
        BoxLayout lossesLayout = new BoxLayout(this.lossesPanel , BoxLayout.Y_AXIS);
        this.lossesPanel.setLayout(lossesLayout);
        this.lossesPanel.setBackground(StyleConstants.BACKGROUND);
        BoxLayout billLayout = new BoxLayout(this.billPanel, BoxLayout.Y_AXIS);
        this.billPanel.setLayout(billLayout);
        this.billPanel.setBackground(StyleConstants.BACKGROUND);
        BoxLayout surfaceLayout = new BoxLayout(this.surfacePanel, BoxLayout.Y_AXIS);
        this.surfacePanel.setLayout(surfaceLayout);
        this.surfacePanel.setBackground(StyleConstants.BACKGROUND);
        BoxLayout vaLayout = new BoxLayout(this.vaPanel, BoxLayout.Y_AXIS);
        this.vaPanel.setLayout(vaLayout);
        this.vaPanel.setBackground(StyleConstants.BACKGROUND);
        JLabel dimensionLabel = new JLabel("Tank Dimensions");
        dimensionLabel.setFont(StyleConstants.HEADING_SUB1);
        this.dimensionPanel.add(dimensionLabel);
        this.dimensionPanel.add(Box.createVerticalStrut(30));
        if(model.getLoadedProject() != null)
            this.initializeTankDimensionTable();
        this.dimensionPanel.add(tankDimensionTable);
        JLabel impedanceHeading = new JLabel("Impedance");
        impedanceHeading.setFont(StyleConstants.HEADING_SUB1);
        this.impedancePanel.add(impedanceHeading);
        if(model.getLoadedProject() != null)
            this.initializeImpedanceTable();
        this.impedancePanel.add(Box.createVerticalStrut(30));
        this.impedancePanel.add(impedanceVoltageTable);
        JLabel lossesHeading = new JLabel("Losses");
        lossesHeading.setFont(StyleConstants.HEADING_SUB1);
        this.lossesPanel.add(lossesHeading);
        if(model.getLoadedProject() != null)
            this.initializeLossesTable();
        this.lossesPanel.add(Box.createVerticalStrut(30));
        this.lossesPanel.add(lossesTable);
        this.dimensionPanel.add(Box.createVerticalStrut(15));
        this.lossesPanel.add(Box.createVerticalStrut(15));
        this.impedancePanel.add(Box.createVerticalStrut(15));
        JLabel billHeading = new JLabel("Bill of material");
        billHeading.setFont(StyleConstants.HEADING_SUB1);
        this.dimensionPanel.add(billHeading);
        if(model.getLoadedProject() != null)
            this.initializeBillTable();
        this.dimensionPanel.add(Box.createVerticalStrut(30));
        this.dimensionPanel.add(billTable);
        JLabel surfaceHeading = new JLabel("Surface Area:");
        surfaceHeading.setFont(StyleConstants.HEADING_SUB1);
        this.impedancePanel.add(surfaceHeading);
        if(model.getLoadedProject() != null)
            this.initializeSurfaceTable();
        this.impedancePanel.add(Box.createVerticalStrut(30));
        this.impedancePanel.add(surfaceTable);
        JLabel vaHeading = new JLabel("VA Table");
        vaHeading.setFont(StyleConstants.HEADING_SUB1);
        this.lossesPanel.add(vaHeading);
        if(model.getLoadedProject() != null)
            this.initializeVATable();
        this.lossesPanel.add(Box.createVerticalStrut(30));
        this.lossesPanel.add(vaTable);
        this.impedancePanel.setBorder(BorderFactory.createEmptyBorder(20 , 20 , 20 ,20));
        this.lossesPanel.setBorder(BorderFactory.createEmptyBorder(20 , 20 , 20 , 20));
        this.dimensionPanel.setBorder(BorderFactory.createEmptyBorder(20 , 20 ,20 ,20));
        this.contentPanel.add(dimensionPanel);
        this.contentPanel.add(impedancePanel);
        this.contentPanel.add(lossesPanel);
        this.mainPanel.add(contentPanel , BorderLayout.CENTER);
        this.mainPanel.add(new MenuBar(thisReference) , BorderLayout.NORTH);
        this.mainPanel.add(initializeNavigationPanel() , BorderLayout.SOUTH);
        this.add(mainPanel , BorderLayout.CENTER);
        tankDimensionTable.setShowGrid(true);
        tankDimensionTable.setGridColor(Color.BLACK);
        tankDimensionTable.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        impedanceVoltageTable.setShowGrid(true);
        impedanceVoltageTable.setGridColor(Color.BLACK);
        impedanceVoltageTable.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        lossesTable.setShowGrid(true);
        lossesTable.setGridColor(Color.BLACK);
        lossesTable.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        billTable.setShowGrid(true);
        billTable.setGridColor(Color.BLACK);
        billTable.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        surfaceTable.setShowGrid(true);
        surfaceTable.setGridColor(Color.BLACK);
        surfaceTable.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        vaTable.setShowGrid(true);
        vaTable.setGridColor(Color.BLACK);
        vaTable.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }

    public void initializeBillTable() {
        OutputData outputData = Model.getSingleton().getOutputData();
        InputData inputData = Model.getSingleton().getLoadedProjectInput();


        // setting parameter name
        this.billTable.setValueAt("Core", 0, 0);
        this.billTable.setValueAt("Core-Steel + SS", 1, 0);
        this.billTable.setValueAt(inputData.CONDUCTOR, 2, 0);
        this.billTable.setValueAt("Leads", 3, 0);
        this.billTable.setValueAt("Insulation-FG", 4, 0);
        this.billTable.setValueAt("Connection-FG", 5, 0);
        this.billTable.setValueAt("Insulation-CL-H", 6, 0);
        this.billTable.setValueAt("RESIN-VT50", 7, 0);
        this.billTable.setValueAt("MISC", 8, 0);
        this.billTable.setValueAt("CRCA ENCL", 9, 0);
        this.billTable.setValueAt("Total Mass", 10, 0);

        // setting values
        this.billTable.setValueAt(outputData.BOM_CORE, 0, 1);
        this.billTable.setValueAt(outputData.BOM_CORE_STEEL, 1, 1);
        this.billTable.setValueAt(outputData.BOM_CONDUCTOR_WT, 2, 1);
        this.billTable.setValueAt(outputData.BOM_LEADS, 3, 1);
        this.billTable.setValueAt(outputData.BOM_INSULATION_FG, 4, 1);
        this.billTable.setValueAt(outputData.BOM_CONNECTION_FG, 5, 1);
        this.billTable.setValueAt(outputData.BOM_INSULATION_CL_H, 6, 1);
        this.billTable.setValueAt(outputData.BOM_RESIN_VT50, 7, 1);
        this.billTable.setValueAt(outputData.BOM_MISC, 8, 1);
        this.billTable.setValueAt(inputData.BOM_CRCA_ENCL, 9, 1);
        this.billTable.setValueAt(outputData.BOM_TOTAL_MASS, 10, 1);

    }

    private void initializeLossesTable() {

        OutputData outputData = Model.getSingleton().getOutputData();

        this.lossesTable.setValueAt("Mass of the Conductor", 0, 0);
        this.lossesTable.setValueAt("Load Loss LV Watts",1,0);
        this.lossesTable.setValueAt("Load Loss HV Watts",2,0);
        this.lossesTable.setValueAt("Tank Watts",3,0);
        this.lossesTable.setValueAt("Total Obtained Watts",4,0);
        this.lossesTable.setValueAt("Total Core Mass",5,0);
        this.lossesTable.setValueAt("Net Cross Section",6,0);
        this.lossesTable.setValueAt("Spec Losses",7,0);
        this.lossesTable.setValueAt("Calc Loss watts",8,0);

        this.lossesTable.setValueAt(outputData.MASS_OF_CONDUCTOR,0,1);
        this.lossesTable.setValueAt(outputData.LOAD_LOSS_LV,1,1);
        this.lossesTable.setValueAt(outputData.LOAD_LOSS_HV,2,1);
        this.lossesTable.setValueAt(outputData.TANK, 3, 1);
        this.lossesTable.setValueAt(outputData.OBTAINED_LOSS, 4, 1);
        this.lossesTable.setValueAt(outputData.TOTAL_CORE_MASS, 5, 1);
        this.lossesTable.setValueAt(outputData.NET_CROSS_SECTION, 6, 1);
        this.lossesTable.setValueAt(outputData.SPEC_LOSSES, 7, 1);
        this.lossesTable.setValueAt(outputData.CALC_LOSS, 8, 1);

    }

    private void initializeImpedanceTable() {

        OutputData outputData = Model.getSingleton().getOutputData();

        this.impedanceVoltageTable.setValueAt("h" , 0 , 0);
        this.impedanceVoltageTable.setValueAt("b" , 1 , 0);
        this.impedanceVoltageTable.setValueAt( "kr",2 , 0);
        this.impedanceVoltageTable.setValueAt( "Ls",3 , 0);
        this.impedanceVoltageTable.setValueAt( "δ`",4 , 0);
        this.impedanceVoltageTable.setValueAt( "ex",5 , 0);
        this.impedanceVoltageTable.setValueAt( "Er",6 , 0);
        this.impedanceVoltageTable.setValueAt( "Ek",7 , 0);

        // setting values
        this.impedanceVoltageTable.setValueAt(outputData.H, 0, 1);
        this.impedanceVoltageTable.setValueAt(outputData.B, 1, 1);
        this.impedanceVoltageTable.setValueAt(outputData.KR, 2, 1);
        this.impedanceVoltageTable.setValueAt(outputData.LS, 3, 1);
        this.impedanceVoltageTable.setValueAt(outputData.DELTA_DASH, 4, 1);
        this.impedanceVoltageTable.setValueAt(outputData.EX, 5, 1);
        this.impedanceVoltageTable.setValueAt(outputData.ER, 6, 1);
        this.impedanceVoltageTable.setValueAt(outputData.EK, 7, 1);
    }

    private void initializeTankDimensionTable() {

        OutputData outputData = Model.getSingleton().getOutputData();

        this.tankDimensionTable.setValueAt( "Parameter",0 , 0);
        this.tankDimensionTable.setValueAt( "mtr",0 , 1);
        this.tankDimensionTable.setValueAt( "Active L",1 , 0);
        this.tankDimensionTable.setValueAt("Active H" , 2 , 0);
        this.tankDimensionTable.setValueAt("Active B" , 3 , 0);
        this.tankDimensionTable.setValueAt( "Overall L",4 , 0);
        this.tankDimensionTable.setValueAt("Overall H" , 5 , 0);
        this.tankDimensionTable.setValueAt("Overall B" , 6 , 0);

        // setting values
        this.tankDimensionTable.setValueAt(outputData.L_ACTIVE, 1, 1);
        this.tankDimensionTable.setValueAt(outputData.H_ACTIVE, 2, 1);
        this.tankDimensionTable.setValueAt(outputData.B_ACTIVE, 3, 1);
        this.tankDimensionTable.setValueAt(outputData.L_MECHANICAL, 4, 1);
        this.tankDimensionTable.setValueAt(outputData.H_MECHANICAL, 5, 1);
        this.tankDimensionTable.setValueAt(outputData.B_MECHANICAL, 6, 1);

    }
    public void initializeSurfaceTable() {
        OutputData outputData = Model.getSingleton().getOutputData();


        // setting parameter name
        this.surfaceTable.setValueAt("Core s-a", 0, 0);
        this.surfaceTable.setValueAt("Wdg s-a", 1, 0);
        this.surfaceTable.setValueAt("Σ s-a", 2, 0);
        this.surfaceTable.setValueAt("Σ Loss", 3, 0);
        this.surfaceTable.setValueAt(" θ(k)", 4, 0);

        // setting values
        this.surfaceTable.setValueAt(outputData.CORE_SA, 0, 1);
        this.surfaceTable.setValueAt(outputData.WDG_SA, 1, 1);
        this.surfaceTable.setValueAt(outputData.SUM_SA, 2, 1);
        this.surfaceTable.setValueAt(outputData.SUM_LOSS, 3, 1);
        this.surfaceTable.setValueAt(outputData.THETA_K, 4, 1);

    }

    public void initializeVATable() {
        OutputData outputData = Model.getSingleton().getOutputData();


        // setting parameter name
        this.vaTable.setValueAt("Mass Limb", 0, 0);
        this.vaTable.setValueAt("Mass Yoke", 1, 0);
        this.vaTable.setValueAt("Mass Corner", 2, 0);
        this.vaTable.setValueAt("Gap VA", 3, 0);
        this.vaTable.setValueAt("Σ VA", 4, 0);
        this.vaTable.setValueAt("%N.L.Current", 5, 0);
        this.vaTable.setValueAt("Extra-N.L.Loss", 6, 0);

        // setting values
        this.vaTable.setValueAt(outputData.MASS_LIMB, 0, 1);
        this.vaTable.setValueAt(outputData.MASS_LIMB_DASH, 0, 2);
        this.vaTable.setValueAt(outputData.MASS_YOKE, 1, 1);
        this.vaTable.setValueAt(outputData.MASS_YOKE_DASH, 1, 2);
        this.vaTable.setValueAt(outputData.MASS_CORNER, 2, 1);
        this.vaTable.setValueAt(outputData.MASS_CORNER_DASH, 2, 2);
        this.vaTable.setValueAt(outputData.GAP_VA, 3, 2);
        this.vaTable.setValueAt(outputData.SUM_VA, 4, 2);
        this.vaTable.setValueAt(outputData.NL_CURRENT_PERCENTAGE, 5, 2);
        this.vaTable.setValueAt(outputData.EXTRA_NL_LOSS, 6, 2);

    }

    private JPanel initializeNavigationPanel(){
        JPanel navigationPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        // Load the image and scale it down for the previous button
        ImageIcon previousIcon = null;
        try {
            previousIcon = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("previous_icon.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Image previousImage = previousIcon.getImage();
        Image scaledPreviousImage = previousImage.getScaledInstance(25, 25, Image.SCALE_SMOOTH); // scale to 32x32 pixels
        ImageIcon scaledPreviousIcon = new ImageIcon(scaledPreviousImage);

        // Create the Previous button and set the icon
        JButton previousBtn = new JButton("Previous", scaledPreviousIcon);

        // Set text position relative to the icon for the Previous button
        previousBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
        previousBtn.setHorizontalTextPosition(SwingConstants.CENTER);

        // Load the image and scale it down for the next button
        ImageIcon nextIcon = null;
        try {
            nextIcon = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("next_icon.jpeg")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Image nextImage = nextIcon.getImage();
        Image scaledNextImage = nextImage.getScaledInstance(25, 25, Image.SCALE_SMOOTH); // scale to 32x32 pixels
        ImageIcon scaledNextIcon = new ImageIcon(scaledNextImage);

        // Create the Next button and set the icon
        JButton nextBtn = new JButton("Next", scaledNextIcon);

        // Set text position relative to the icon for the Next button
        nextBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
        nextBtn.setHorizontalTextPosition(SwingConstants.CENTER);

        // Load the image and scale it down for the print button
        ImageIcon printIcon = null;
        try {
            printIcon = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("print_icon.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Image printImage = printIcon.getImage();
        Image scaledPrintImage = printImage.getScaledInstance(25, 25, Image.SCALE_SMOOTH); // scale to 32x32 pixels
        ImageIcon scaledPrintIcon = new ImageIcon(scaledPrintImage);

        // Create the Print button and set the icon
        JButton printBtn = new JButton("Print", scaledPrintIcon);

        // Set text position relative to the icon for the Print button
        printBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
        printBtn.setHorizontalTextPosition(SwingConstants.CENTER);

        // Set the same size for all buttons
        Dimension buttonSize = new Dimension(100, 50); // Adjust the size as needed
        previousBtn.setPreferredSize(buttonSize);
        nextBtn.setPreferredSize(buttonSize);
        printBtn.setPreferredSize(buttonSize);

        navigationPanel.setBackground(StyleConstants.BACKGROUND);
        navigationPanel.add(previousBtn);
        previousBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                View.getSingleton().setView(OutputOneWorkView.VIEW_NAME);
            }
        });
        printBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                View.getSingleton().setView(PrintWorkView.VIEW_NAME);
            }
        });
        navigationPanel.add(printBtn);
        return navigationPanel;
    }

    @Override
    public void captureEventFromChildSubFrame(ViewMessage message) {
        switch (message.getMsgType()) {
            case ViewMessages.CLOSE_OPENED_PROJECT:
                mainController.closeOpenedProject();
                break;
            case ViewMessages.SAVE_PROJECT:
                mainController.saveProject();
                break;
            case ViewMessages.OPEN_INPUT_VIEW:
                mainController.openInputView();
                break;
            case ViewMessages.OPEN_DRAWINGS:
                mainController.openDrawingsView();
                break;
        }
    }

    @Override
    public String getViewName() {
        return VIEW_NAME;
    }

    @Override
    public Integer getViewId() {
        return 9;
    }

}
