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

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;

public class PrintWorkView extends AbstractWorkView {
    private final Control mainController = new Control();
    private final PrintWorkView thisReference = this;
    public final static String VIEW_NAME = "PrintWorkView";
    private final JPanel mainPanel = new JPanel();
    private final JPanel LV_HVPanel = new JPanel();
    private final JPanel coreDetailsPanel = new JPanel();
    private final JPanel surfacePanel = new JPanel();
    private final JPanel vaPanel = new JPanel();
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
    private final JTable LV_HV_Table = new JTable(18  , 3){@Override
    public boolean isCellEditable(int row , int col){
        return false;
    }
    };
    private final JTable wireDetailTable = new JTable(3 , 5){
        @Override
        public boolean isCellEditable(int row , int col){
            return false;
        }
    };
    private final JTable coreWdgTable = new JTable(13 , 3){
        @Override
        public boolean isCellEditable(int row , int col){
            return false;
        }
    };
    private final JTable coreWeightTable = new JTable(1 , 5){
        @Override
        public boolean isCellEditable(int row , int col){
            return false;
        }
    };
    private final JTable standardTable = new JTable(12 , 2){
        @Override
        public boolean isCellEditable(int row , int col){
            return false;
        }
    };;
    private final JLabel cDistLabel = new JLabel("C Dist : ");
    private final JLabel yokeL = new JLabel("Yoke L : ");
    private final JLabel leads = new JLabel("Leads : ");

    JLabel VByTOutput = new JLabel();
    public PrintWorkView(Model model) {
        super(model);
        this.setLayout(new BorderLayout());
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                initializeUI();
            }
        });
    }
    private void initializeUI(){
        this.setLayout(new BorderLayout());
        this.add(new MenuBar(this) , BorderLayout.NORTH);
        this.initializeMainPanel();
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        this.add(scrollPane , BorderLayout.CENTER);
        this.add(initializeNavigationPanel() , BorderLayout.SOUTH);
    }
    @Override
    public void update(String message){
        if(Objects.equals(message, "MODEL_UPDATED")){
            this.setLVHVPanelValues();
            this.setCorePanelValues();
            this.initializeTankDimensionTable();
            this.initializeBillTable();
            this.initializeImpedanceTable();
            this.initializeLossesTable();
            this.initializeSurfaceTable();
            this.initializeVATable();
        }
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
        return 5;
    }

    private void initializeMainPanel(){
        this.mainPanel.setLayout(new GridLayout(2 , 1));
        this.initializeLV_HVPanel();
        this.initializeCoreDetailsPanel();
        this.mainPanel.add(LV_HVPanel);
        this.mainPanel.add(coreDetailsPanel);
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
        this.dimensionPanel.add(Box.createVerticalStrut(15));
        JLabel surfaceHeading = new JLabel("Surface Area:");
        surfaceHeading.setFont(StyleConstants.HEADING_SUB1);
        this.dimensionPanel.add(surfaceHeading);
        if(model.getLoadedProject() != null)
            this.initializeSurfaceTable();
        this.dimensionPanel.add(Box.createVerticalStrut(30));
        this.dimensionPanel.add(surfaceTable);
        this.impedancePanel.add(Box.createVerticalStrut(15));
        this.impedancePanel.add(lossesPanel);
        this.impedancePanel.add(Box.createVerticalStrut(15));
        JLabel vaHeading = new JLabel("VA Table");
        vaHeading.setFont(StyleConstants.HEADING_SUB1);
        this.impedancePanel.add(vaHeading);
        if(model.getLoadedProject() != null)
            this.initializeVATable();
        this.impedancePanel.add(Box.createVerticalStrut(30));
        this.impedancePanel.add(vaTable);
        this.impedancePanel.setBorder(BorderFactory.createEmptyBorder(20 , 20 , 20 ,20));
        this.lossesPanel.setBorder(BorderFactory.createEmptyBorder(20 , 20 , 20 , 20));
        this.dimensionPanel.setBorder(BorderFactory.createEmptyBorder(20 , 20 ,20 ,20));
        this.mainPanel.add(dimensionPanel);
        this.mainPanel.add(impedancePanel);

    }
    private void initializeLV_HVPanel(){
        BoxLayout layout = new BoxLayout(this.LV_HVPanel , BoxLayout.Y_AXIS);
        this.LV_HVPanel.setLayout(layout);
        if(model.getLoadedProject() != null)
            setLVHVPanelValues();
        JLabel LV_HVHeading = new JLabel("LV HV data");
        LV_HVHeading.setFont(StyleConstants.HEADING_SUB1);
        LV_HVHeading.setAlignmentX(CENTER_ALIGNMENT);
        LV_HV_Table.setAlignmentX(CENTER_ALIGNMENT);
        this.VByTOutput.setFont(StyleConstants.NORMAL_TEXT);
        JLabel VByTLabel = new JLabel("V/T");
        VByTLabel.setFont(StyleConstants.HEADING_SUB2);
        this.LV_HVPanel.add(LV_HVHeading);
        this.LV_HVPanel.add(Box.createVerticalStrut(10));
        this.LV_HVPanel.add(LV_HV_Table);
        this.LV_HVPanel.add(Box.createVerticalStrut(10));
        this.LV_HVPanel.add(VByTLabel);
        this.LV_HVPanel.add(Box.createVerticalStrut(5));
        this.LV_HVPanel.add(VByTOutput);
        this.LV_HVPanel.add(Box.createVerticalStrut(10));
        this.LV_HVPanel.add(this.wireDetailTable);
        this.LV_HVPanel.add(Box.createVerticalStrut(40));
        this.LV_HVPanel.add(this.coreWeightTable);
        this.LV_HVPanel.setBackground(StyleConstants.BACKGROUND);
        this.LV_HVPanel.setBorder(BorderFactory.createEmptyBorder(20, 20 , 20 ,20));
    }
    private void initializeCoreDetailsPanel(){
        BoxLayout layout = new BoxLayout(this.coreDetailsPanel , BoxLayout.Y_AXIS);
        this.coreDetailsPanel.setLayout(layout);
        JLabel coreDetailsHeading = new JLabel("Core");
        coreDetailsHeading.setFont(StyleConstants.HEADING_SUB1);
        coreDetailsHeading.setAlignmentX(CENTER_ALIGNMENT);
        if(model.getLoadedProject() != null)
            this.setCorePanelValues();
        this.coreDetailsPanel.add(coreDetailsHeading);
        this.coreDetailsPanel.add(Box.createVerticalStrut(10));
        this.coreDetailsPanel.add(coreWdgTable);
        this.coreDetailsPanel.setBackground(StyleConstants.BACKGROUND);
        this.coreDetailsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20 , 20 ,20));
        this.cDistLabel.setAlignmentX(LEFT_ALIGNMENT);
        this.coreDetailsPanel.add(cDistLabel);
        this.coreDetailsPanel.add(yokeL);
        this.coreDetailsPanel.add(leads);
        this.coreDetailsPanel.add(Box.createVerticalStrut(10));
        this.coreDetailsPanel.add(standardTable);
    }
    private JPanel initializeNavigationPanel(){
        JPanel navigationPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
        navigationPanel.setBackground(StyleConstants.BACKGROUND);
        JButton previousBtn = new JButton("Cancel");
        navigationPanel.add(previousBtn);
        previousBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                View.getSingleton().setView(OutputTwoWorkView.VIEW_NAME);
            }
        });
        JButton printButton = new JButton("Continue");
        printButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PrintManager.printComponent(mainPanel);
            }
        });
        navigationPanel.add(printButton);
        return navigationPanel;
    }
    private void setLVHVPanelValues(){
        OutputData outputData = Model.getSingleton().getOutputData();
        InputData inputData = Model.getSingleton().getLoadedProjectInput();

        LV_HV_Table.setValueAt("Parameter" , 0 , 0);
        LV_HV_Table.setValueAt("LV" , 0 , 1);
        LV_HV_Table.setValueAt("HV" , 0 , 2);
        LV_HV_Table.setValueAt("Rated Voltage" , 1 , 0);
        LV_HV_Table.setValueAt("Rated Current" , 2 , 0);
        LV_HV_Table.setValueAt("Cross Section sqmm" , 3 , 0);
        LV_HV_Table.setValueAt("Current density" , 4 , 0);
        LV_HV_Table.setValueAt("Turns/Limb" , 5 , 0);
        LV_HV_Table.setValueAt("Turns/Layer" , 6 , 0);
        LV_HV_Table.setValueAt("wdg lg-imp calc" , 7 , 0);
        LV_HV_Table.setValueAt("Wind Length(AXL)" , 8 , 0);
        LV_HV_Table.setValueAt("Limb length" , 9 , 0);
        LV_HV_Table.setValueAt("Wind-radial depth"  , 10 , 0);
        LV_HV_Table.setValueAt("Turn length" , 11 , 0);
        LV_HV_Table.setValueAt("Wire Length" , 12 , 0);
        LV_HV_Table.setValueAt("Resistance (ohms)" , 13 , 0);
        LV_HV_Table.setValueAt("Stray Loss (%)" , 14 , 0);
        LV_HV_Table.setValueAt("Load Loss (Watts)" , 15 , 0);
        LV_HV_Table.setValueAt("S.a-m(wdg)" , 16 , 0);

        // setting values
        LV_HV_Table.setValueAt(outputData.VPH_LV, 1, 1);
        LV_HV_Table.setValueAt(outputData.VPH_HV, 1, 2);

        LV_HV_Table.setValueAt(outputData.IPH_LV, 2, 1);
        LV_HV_Table.setValueAt(outputData.IPH_HV, 2, 2);

        LV_HV_Table.setValueAt(outputData.CROSS_SECTION_LV, 3, 1);
        LV_HV_Table.setValueAt(outputData.CROSS_SECTION_HV, 3, 2);

        LV_HV_Table.setValueAt(outputData.CURRENT_DENSITY_LV, 4, 1);
        LV_HV_Table.setValueAt(outputData.CURRENT_DENSITY_HV, 4, 2);

        LV_HV_Table.setValueAt(outputData.TURN_LIMB_LV, 5, 1);
        LV_HV_Table.setValueAt(outputData.TURN_LIMB_HV, 5, 2);

        LV_HV_Table.setValueAt(outputData.TURN_LAYER_LV, 6, 1);
        LV_HV_Table.setValueAt(outputData.TURN_LAYER_HV, 6, 2);

        LV_HV_Table.setValueAt(outputData.WDG_LG_IMP_CALCU_LV, 7, 1);
        LV_HV_Table.setValueAt(outputData.WDG_LG_IMP_CALCU_HV, 7, 2);

        LV_HV_Table.setValueAt(outputData.WIND_LENGTH_LV, 8, 1);
        LV_HV_Table.setValueAt(outputData.WIND_LENGTH_HV, 8, 2);

        LV_HV_Table.setValueAt(outputData.LIMB_LENGTH_LV, 9, 1);
        LV_HV_Table.setValueAt(outputData.LIMB_LENGTH_HV, 9, 2);

        LV_HV_Table.setValueAt(outputData.WIND_RADIAL_DEPTH_LV, 10, 1);
        LV_HV_Table.setValueAt(outputData.WIND_RADIAL_DEPTH_HV, 10, 2);

        LV_HV_Table.setValueAt(outputData.TURN_LENGTH_LV, 11, 1);
        LV_HV_Table.setValueAt(outputData.TURN_LENGTH_HV, 11, 2);

        LV_HV_Table.setValueAt(outputData.WIRE_LENGTH_LV, 12, 1);
        LV_HV_Table.setValueAt(outputData.WIRE_LENGTH_HV, 12, 2);

        LV_HV_Table.setValueAt(outputData.RESISTANCE_LV ,13,1);
        LV_HV_Table.setValueAt(outputData.RESISTANCE_HV,13,2);

        LV_HV_Table.setValueAt(outputData.STRAY_LOSS_LV,14,1);
        LV_HV_Table.setValueAt(outputData.STRAY_LOSS_HV,14,2);

        LV_HV_Table.setValueAt(outputData.LOAD_LOSS_LV,15,1);
        LV_HV_Table.setValueAt(outputData.LOAD_LOSS_HV,15,2);

        LV_HV_Table.setValueAt(outputData.S_AM2_WDG_LV,16,1);
        LV_HV_Table.setValueAt(outputData.S_AM2_WDG_HV,16,2);


        wireDetailTable.setValueAt("Parameter" , 0 ,0);
        wireDetailTable.setValueAt("LV 1" , 0 ,1);
        wireDetailTable.setValueAt("LV 2" , 0 ,2);
        wireDetailTable.setValueAt("HV 1" , 0 ,3);
        wireDetailTable.setValueAt("HV 2" , 0 ,4);

        // setting parameter names
        wireDetailTable.setValueAt("Wire bare" , 1 , 0);
        wireDetailTable.setValueAt("Wire insulated" , 2 , 0);
        coreWeightTable.setValueAt("Conductor in KG" , 0 , 0);

        // setting values
        wireDetailTable.setValueAt(inputData.WIREBARELV1,1,1);
        wireDetailTable.setValueAt(inputData.WIREBARELV2,1,2);
        wireDetailTable.setValueAt(inputData.WIREBAREHV1,1,3);
        wireDetailTable.setValueAt(inputData.WIREBAREHV2,1,4);
        wireDetailTable.setValueAt(outputData.WIRE_INSULATED_LV1,2,1);
        wireDetailTable.setValueAt(outputData.WIRE_INSULATED_LV2,2,2);
        wireDetailTable.setValueAt(outputData.WIRE_INSULATED_HV1,2,3);
        wireDetailTable.setValueAt(outputData.WIRE_INSULATED_HV2,2,4);

        coreWeightTable.setValueAt(outputData.CONDUCTOR_LV1,0,1);
        coreWeightTable.setValueAt(outputData.CONDUCTOR_LV2,0,2);
        coreWeightTable.setValueAt(outputData.CONDUCTOR_HV1,0,3);
        coreWeightTable.setValueAt(outputData.CONDUCTOR_HV2,0,4);

        VByTOutput.setText(String.valueOf(outputData.V_T));
    }

    private void setCorePanelValues(){

        OutputData outputData = Model.getSingleton().getOutputData();
        InputData inputData = Model.getSingleton().getLoadedProjectInput();

        coreWdgTable.setValueAt("Parameter" , 0 , 0);
        coreWdgTable.setValueAt("W" , 0 , 1);
        coreWdgTable.setValueAt("D" , 0 , 2);
        //parameter names
        coreWdgTable.setValueAt("Core" , 1 , 0);
        coreWdgTable.setValueAt("Limb Plate" , 2 , 0);
        coreWdgTable.setValueAt("CORE''" , 3 , 0);
        coreWdgTable.setValueAt("gap/bobin" , 4 , 0);
        coreWdgTable.setValueAt("ID(1)" , 5 , 0);
        coreWdgTable.setValueAt("LV wdg" , 6 , 0);
        coreWdgTable.setValueAt("OD(1)" , 7, 0);
        coreWdgTable.setValueAt("δ" , 8 , 0);
        coreWdgTable.setValueAt("ID(2)" , 9 , 0);
        coreWdgTable.setValueAt("HV wdg" , 10 , 0);
        coreWdgTable.setValueAt("OD(2)" , 11 , 0);
        coreWdgTable.setValueAt("am" , 12 , 0);
        coreWdgTable.setAlignmentX(CENTER_ALIGNMENT);

        // setting values
        coreWdgTable.setValueAt(inputData.CORE_W, 1, 1);
        coreWdgTable.setValueAt(outputData.CORE_D, 1, 2);

        coreWdgTable.setValueAt(inputData.LIMB_PLATE_W, 2, 1);
        coreWdgTable.setValueAt(inputData.LIMB_PLATE_D, 2, 2);

        coreWdgTable.setValueAt(outputData.TOTAL_CORE_W, 3, 1);
        coreWdgTable.setValueAt(outputData.TOTAL_CORE_D, 3, 2);

        coreWdgTable.setValueAt(inputData.GAP_W, 4, 1);
        coreWdgTable.setValueAt(inputData.GAP_D, 4, 2);

        coreWdgTable.setValueAt(outputData.ID_W, 5, 1);
        coreWdgTable.setValueAt(outputData.ID_D, 5, 2);

        coreWdgTable.setValueAt(outputData.LV_WDG, 6, 1);
        coreWdgTable.setValueAt(outputData.LV_WDG, 6, 2);

        coreWdgTable.setValueAt(outputData.OD_W, 7, 1);
        coreWdgTable.setValueAt(outputData.OD_D, 7, 2);

        coreWdgTable.setValueAt(inputData.DELTA_W, 8, 1);
        coreWdgTable.setValueAt(inputData.DELTA_D, 8, 2);

        coreWdgTable.setValueAt(outputData.TOTAL_ID_W, 9, 1);
        coreWdgTable.setValueAt(outputData.TOTAL_ID_D, 9, 2);

        coreWdgTable.setValueAt(outputData.HV_WDG, 10, 1);
        coreWdgTable.setValueAt(outputData.HV_WDG, 10, 2);

        coreWdgTable.setValueAt(outputData.TOTAL_OD_W, 11, 1);
        coreWdgTable.setValueAt(outputData.TOTAL_OD_D, 11, 2);

        coreWdgTable.setValueAt(inputData.AM_W, 12, 1);
        coreWdgTable.setValueAt(inputData.AM_D, 12, 2);

        String C_dist_data = cDistLabel.getText();
        C_dist_data = C_dist_data.substring(0 , 7);
        cDistLabel.setText(C_dist_data + String.valueOf(outputData.C_DIST));

        String yokeL_data = yokeL.getText();
        yokeL_data = yokeL_data.substring(0 , 7);
        yokeL.setText(yokeL_data + String.valueOf(outputData.YOKE_L));

        String leads_data = leads.getText();
        leads_data = leads_data.substring(0 , 7);
        leads.setText(leads_data + String.valueOf(outputData.LEADS));

        standardTable.setValueAt("parameter",0,0);
        standardTable.setValueAt("mm",0,1);
        standardTable.setValueAt("R1",1,0);
        standardTable.setValueAt("R2",2,0);
        standardTable.setValueAt("R3",3,0);
        standardTable.setValueAt("R4",4,0);
        standardTable.setValueAt("perimeter 1",5,0);
        standardTable.setValueAt("perimeter 2",6,0);
        standardTable.setValueAt("perimeter 3",7,0);
        standardTable.setValueAt("perimeter 4",8,0);
        standardTable.setValueAt("Mean LG LV",9,0);
        standardTable.setValueAt("Mean LG DELTA",10,0);
        standardTable.setValueAt("Mean LG HV",11,0);

        standardTable.setValueAt(outputData.R1,1,1);
        standardTable.setValueAt(outputData.R2,2,1);
        standardTable.setValueAt(outputData.R3,3,1);
        standardTable.setValueAt(outputData.R4,4,1);
        standardTable.setValueAt(outputData.PERIMETER1,5,1);
        standardTable.setValueAt(outputData.PERIMETER2,6,1);
        standardTable.setValueAt(outputData.PERIMETER3,7,1);
        standardTable.setValueAt(outputData.PERIMETER4,8,1);
        standardTable.setValueAt(outputData.MEAN_LG_LV,9,1);
        standardTable.setValueAt(outputData.MEAN_LG_DELTA,10,1);
        standardTable.setValueAt(outputData.MEAN_LG_HV,11,1);

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
}