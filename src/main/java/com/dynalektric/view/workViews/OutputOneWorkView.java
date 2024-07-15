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
import java.util.Objects;

public class OutputOneWorkView extends AbstractWorkView{
    private final Control mainController = new Control();
    public final static String VIEW_NAME = "OutputViewOne";
    private final JPanel mainPanel = new JPanel();
    private final JPanel LV_HVPanel = new JPanel();
    private final JPanel coreDetailsPanel = new JPanel();
    private final JTable LV_HV_Table = new JTable(20  , 3){
        @Override
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
    };;
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
    public OutputOneWorkView(Model model) {
        super(model);
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
        this.add(mainPanel , BorderLayout.CENTER);
        this.add(initializeNavigationPanel() , BorderLayout.SOUTH);
    }
    @Override
    public void update(String message){
        if(Objects.equals(message, "MODEL_UPDATED")){
            this.setLVHVPanelValues();
            this.setCorePanelValues();

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
        return 3;
    }

    private void initializeMainPanel(){
        this.mainPanel.setLayout(new GridLayout(0 , 2));
        this.initializeLV_HVPanel();
        this.initializeCoreDetailsPanel();
        this.mainPanel.add(LV_HVPanel);
        this.mainPanel.add(coreDetailsPanel);
        LV_HV_Table.setShowGrid(true);
        LV_HV_Table.setGridColor(Color.BLACK);
        LV_HV_Table.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        wireDetailTable.setShowGrid(true);
        wireDetailTable.setGridColor(Color.BLACK);
        wireDetailTable.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        coreWdgTable.setShowGrid(true);
        coreWdgTable.setGridColor(Color.BLACK);
        coreWdgTable.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        coreWeightTable.setShowGrid(true);
        coreWeightTable.setGridColor(Color.BLACK);
        coreWeightTable.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        standardTable.setShowGrid(true);
        standardTable.setGridColor(Color.BLACK);
        standardTable.setBorder(BorderFactory.createLineBorder(Color.BLACK));

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
        JPanel navigationPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        navigationPanel.setBackground(StyleConstants.BACKGROUND);
        JButton previousBtn = new JButton("Previous");
        JButton nextBtn = new JButton("Next");
        navigationPanel.add(previousBtn);
        previousBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                View.getSingleton().setView(DrawingWorkView.VIEW_NAME);
            }
        });
        nextBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                View.getSingleton().setView(OutputTwoWorkView.VIEW_NAME);
//                System.out.println(coreWeightTable.getModel().getValueAt(0 , 1));
            }
        });
        navigationPanel.add(nextBtn);
        return navigationPanel;
    }
    private void setLVHVPanelValues(){
        OutputData outputData = Model.getSingleton().getOutputData();
        InputData inputData = Model.getSingleton().getLoadedProjectInput();

        LV_HV_Table.setValueAt("Parameter" , 0 , 0);
        LV_HV_Table.setValueAt("LV" , 0 , 2);
        LV_HV_Table.setValueAt("HV" , 0 , 1);
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
        LV_HV_Table.setValueAt("W/m^2" , 17 , 0);
        LV_HV_Table.setValueAt("wdg temp rise " , 18 , 0);
        LV_HV_Table.setValueAt("core" , 19 , 0);

        // setting values
        LV_HV_Table.setValueAt(outputData.VPH_LV, 1, 2);
        LV_HV_Table.setValueAt(outputData.VPH_HV, 1, 1);

        LV_HV_Table.setValueAt(outputData.IPH_LV, 2, 2);
        LV_HV_Table.setValueAt(outputData.IPH_HV, 2, 1);

        LV_HV_Table.setValueAt(outputData.CROSS_SECTION_LV, 3, 2);
        LV_HV_Table.setValueAt(outputData.CROSS_SECTION_HV, 3, 1);

        LV_HV_Table.setValueAt(outputData.CURRENT_DENSITY_LV, 4, 2);
        LV_HV_Table.setValueAt(outputData.CURRENT_DENSITY_HV, 4, 1);

        LV_HV_Table.setValueAt(outputData.TURN_LIMB_LV, 5, 2);
        LV_HV_Table.setValueAt(outputData.TURN_LIMB_HV, 5, 1);

        LV_HV_Table.setValueAt(outputData.TURN_LAYER_LV, 6, 2);
        LV_HV_Table.setValueAt(outputData.TURN_LAYER_HV, 6, 1);

        LV_HV_Table.setValueAt(outputData.WDG_LG_IMP_CALCU_LV, 7, 2);
        LV_HV_Table.setValueAt(outputData.WDG_LG_IMP_CALCU_HV, 7, 1);

        LV_HV_Table.setValueAt(outputData.WIND_LENGTH_LV, 8, 2);
        LV_HV_Table.setValueAt(outputData.WIND_LENGTH_HV, 8, 1);

        LV_HV_Table.setValueAt(outputData.LIMB_LENGTH_LV, 9, 2);
        LV_HV_Table.setValueAt(outputData.LIMB_LENGTH_HV, 9, 1);

        LV_HV_Table.setValueAt(outputData.WIND_RADIAL_DEPTH_LV, 10, 2);
        LV_HV_Table.setValueAt(outputData.WIND_RADIAL_DEPTH_HV, 10, 1);

        LV_HV_Table.setValueAt(outputData.TURN_LENGTH_LV, 11, 2);
        LV_HV_Table.setValueAt(outputData.TURN_LENGTH_HV, 11, 1);

        LV_HV_Table.setValueAt(outputData.WIRE_LENGTH_LV, 12, 2);
        LV_HV_Table.setValueAt(outputData.WIRE_LENGTH_HV, 12, 1);

        LV_HV_Table.setValueAt(outputData.RESISTANCE_LV ,13,2);
        LV_HV_Table.setValueAt(outputData.RESISTANCE_HV,13,1);

        LV_HV_Table.setValueAt(outputData.STRAY_LOSS_LV,14,2);
        LV_HV_Table.setValueAt(outputData.STRAY_LOSS_HV,14,1);

        LV_HV_Table.setValueAt(outputData.LOAD_LOSS_LV,15,2);
        LV_HV_Table.setValueAt(outputData.LOAD_LOSS_HV,15,1);

        LV_HV_Table.setValueAt(outputData.S_AM2_WDG_LV,16,2);
        LV_HV_Table.setValueAt(outputData.S_AM2_WDG_HV,16,1);

        LV_HV_Table.setValueAt(outputData.W_M2_LV,17,2);
        LV_HV_Table.setValueAt(outputData.W_M2_HV,17,1);

        LV_HV_Table.setValueAt(outputData.WDG_TEMP_RISE_LV,18,2);
        LV_HV_Table.setValueAt(outputData.WDG_TEMP_RISE_HV,18,1);

        LV_HV_Table.setValueAt(outputData.CORE,19,1);


        wireDetailTable.setValueAt("Parameter" , 0 ,0);
        wireDetailTable.setValueAt("LV 1" , 0 ,3);
        wireDetailTable.setValueAt("LV 2" , 0 ,4);
        wireDetailTable.setValueAt("HV 1" , 0 ,1);
        wireDetailTable.setValueAt("HV 2" , 0 ,2);

        // setting parameter names
        wireDetailTable.setValueAt("Wire bare" , 1 , 0);
        wireDetailTable.setValueAt("Wire insulated" , 2 , 0);
        coreWeightTable.setValueAt("Conductor in KG" , 0 , 0);

        // setting values
        wireDetailTable.setValueAt(inputData.WIREBARELV1,1,3);
        wireDetailTable.setValueAt(inputData.WIREBARELV2,1,4);
        wireDetailTable.setValueAt(inputData.WIREBAREHV1,1,1);
        wireDetailTable.setValueAt(inputData.WIREBAREHV2,1,2);
        wireDetailTable.setValueAt(outputData.WIRE_INSULATED_LV1,2,3);
        wireDetailTable.setValueAt(outputData.WIRE_INSULATED_LV2,2,4);
        wireDetailTable.setValueAt(outputData.WIRE_INSULATED_HV1,2,1);
        wireDetailTable.setValueAt(outputData.WIRE_INSULATED_HV2,2,2);

        coreWeightTable.setValueAt(outputData.CONDUCTOR_LV1,0,3);
        coreWeightTable.setValueAt(outputData.CONDUCTOR_LV2,0,4);
        coreWeightTable.setValueAt(outputData.CONDUCTOR_HV1,0,1);
        coreWeightTable.setValueAt(outputData.CONDUCTOR_HV2,0,2);

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
}