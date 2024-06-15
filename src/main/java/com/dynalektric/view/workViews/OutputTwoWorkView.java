package com.dynalektric.view.workViews;

import com.dynalektric.constants.StyleConstants;
import com.dynalektric.constants.ViewMessages;
import com.dynalektric.control.Control;
import com.dynalektric.model.Model;
import com.dynalektric.model.repositories.project.InputData;
import com.dynalektric.model.repositories.project.OutputData;
import com.dynalektric.view.View;
import com.dynalektric.view.ViewMessage;
import com.dynalektric.view.components.MenuBar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
    private final JPanel dimensionPanel = new JPanel();
    private final JPanel impedancePanel = new JPanel();
    private final JPanel lossesPanel = new JPanel();
    private final JPanel billPanel = new JPanel();
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
        }
    }

    private void initializeUI() {
        this.mainPanel.setLayout(new BorderLayout());
        this.contentPanel.setLayout(new GridLayout(0 , 3));
        BoxLayout dimensionLayout = new BoxLayout(this.dimensionPanel , BoxLayout.Y_AXIS);
        this.dimensionPanel.setLayout(dimensionLayout);
        BoxLayout impedanceLayout = new BoxLayout(this.impedancePanel , BoxLayout.Y_AXIS);
        this.impedancePanel.setLayout(impedanceLayout);
        BoxLayout lossesLayout = new BoxLayout(this.lossesPanel , BoxLayout.Y_AXIS);
        this.lossesPanel.setLayout(lossesLayout);
        BoxLayout billLayout = new BoxLayout(this.billPanel, BoxLayout.Y_AXIS);
        this.billPanel.setLayout(billLayout);
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
        JLabel billHeading = new JLabel("Bill of material");
        billHeading.setFont(StyleConstants.HEADING_SUB1);
        this.dimensionPanel.add(billHeading);
        if(model.getLoadedProject() != null)
            this.initializeBillTable();
        this.dimensionPanel.add(Box.createVerticalStrut(30));
        this.dimensionPanel.add(billTable);
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

    private JPanel initializeNavigationPanel(){
        JPanel navigationPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
        JButton previousBtn = new JButton("Previous");
        navigationPanel.add(previousBtn);
        previousBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                View.getSingleton().setView(OutputOneWorkView.VIEW_NAME);
            }
        });
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
        }
    }

    @Override
    public String getViewName() {
        return VIEW_NAME;
    }

    @Override
    public Integer getViewId() {
        return 4;
    }

}
