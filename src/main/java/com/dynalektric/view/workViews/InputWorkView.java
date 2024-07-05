package com.dynalektric.view.workViews;

import com.dynalektric.constants.StyleConstants;
import com.dynalektric.constants.ViewMessages;
import com.dynalektric.control.Control;
import com.dynalektric.model.Model;
import com.dynalektric.model.repositories.project.InputData;
import com.dynalektric.view.View;
import com.dynalektric.view.ViewMessage;
import com.dynalektric.view.components.InputDropDown;
import com.dynalektric.view.components.InputTextFieldWithLabel;
import com.dynalektric.view.components.MenuBar;
import com.dynalektric.view.components.InputSpinner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;



public class InputWorkView extends AbstractWorkView{
    private final JPanel logoPanel = new JPanel();
    private final JPanel mainPanel = new JPanel(new BorderLayout());
    private final static Logger LOGGER = LogManager.getLogger(WelcomeWorkView.class);
    public final static String VIEW_NAME = "Input Work view";

    String[] typesOfMaterialInputs = {"COPPER","ALUMINIUM"};
    String[] typesOfWindingInputs = {"STRIP", "FOIL"};
    String[] typesOfConnectionInputs = {"STAR","DELTA"};
    String[] windingTemperatureInputs = {"90","115"};
    String[] steelGradeInputs = {"CRNO-35","M4-27","MOH-23"};
    String[] oilDuctsLv1Inputs = {"0","1","2","3","4","5"};
    String[] oilDuctsLv2Inputs = {"0","6","8","10","12"};
    String[] oilDuctsHv1Inputs = {"0","1","2"};
    String[] oilDuctsHv2Inputs = {"0","6","8","10","12"};
    String[] coreBldgInputs = {"1.5","2"};
    String[] connectionInputs = {"Dyn11","Dyn5","Dd0","Yyn0","Yd1","Yd11","Dz0","Yz1","Yz11"};
    String[] coolingInputs = {"AN-CL-F","AF-CL","ONAN-CL","ONAF-CL","ONWF-CL","OFWF-CL"};
    String[] frequencyInputs = {"50","60"};

    //Input fields
    InputTextFieldWithLabel kvaIn = new InputTextFieldWithLabel();
    //InputTextFieldWithLabel kIn = new InputTextFieldWithLabel("");
    public InputSpinner kIn = new InputSpinner(97,0,1999999999,1,"K:");
    InputTextFieldWithLabel LVIn = new InputTextFieldWithLabel();
    InputTextFieldWithLabel HVIn = new InputTextFieldWithLabel();

    InputTextFieldWithLabel wireBareLv1In = new InputTextFieldWithLabel();
    InputTextFieldWithLabel wireBareLv2In = new InputTextFieldWithLabel();
    InputTextFieldWithLabel wireBareHv1In = new InputTextFieldWithLabel();
    InputTextFieldWithLabel wireBareHv2In = new InputTextFieldWithLabel();
    InputTextFieldWithLabel noInParallel_R_A_Lv1In = new InputTextFieldWithLabel();
    InputTextFieldWithLabel noInParallel_R_A_Lv2In = new InputTextFieldWithLabel();
    InputTextFieldWithLabel noInParallel_R_A_Hv1In = new InputTextFieldWithLabel();
    InputTextFieldWithLabel noInParallel_R_A_Hv2In = new InputTextFieldWithLabel();
    InputTextFieldWithLabel sectionIn = new InputTextFieldWithLabel("Sections");
    InputTextFieldWithLabel layersLvIn = new InputTextFieldWithLabel();
    InputTextFieldWithLabel layersHvIn = new InputTextFieldWithLabel();
    InputTextFieldWithLabel insulationBetweenLayersLvIn = new InputTextFieldWithLabel();
    InputTextFieldWithLabel insulationBetweenLayersHvIn = new InputTextFieldWithLabel();

    InputTextFieldWithLabel sectionLengthIn = new InputTextFieldWithLabel("Section Length");
    InputTextFieldWithLabel oilDuctsAxialIn = new InputTextFieldWithLabel();
    InputTextFieldWithLabel transpositionLvIn = new InputTextFieldWithLabel();
    InputTextFieldWithLabel transpositionHvIn = new InputTextFieldWithLabel();
    InputTextFieldWithLabel compGapIn = new InputTextFieldWithLabel();
    InputTextFieldWithLabel endClearanceLvIn = new InputTextFieldWithLabel();

    InputTextFieldWithLabel endClearanceHvIn = new InputTextFieldWithLabel();
    InputTextFieldWithLabel limbPlateIn = new InputTextFieldWithLabel();
    InputTextFieldWithLabel gapBobbinIn = new InputTextFieldWithLabel();
    InputTextFieldWithLabel deltaIn = new InputTextFieldWithLabel();
    InputTextFieldWithLabel amIn = new InputTextFieldWithLabel();
    InputTextFieldWithLabel leadsIn = new InputTextFieldWithLabel();
    InputTextFieldWithLabel stackingFactorIn = new InputTextFieldWithLabel();
    //InputTextFieldWithLabel fluxDensityIn = new InputTextFieldWithLabel();
    public InputSpinner flux__density = new InputSpinner(1.4,.60,1.75,.025,"");
    InputTextFieldWithLabel specLossIn = new InputTextFieldWithLabel("Spec Loses");
    InputTextFieldWithLabel coreWIn = new InputTextFieldWithLabel();
    InputTextFieldWithLabel ekPercentageGaurIn = new InputTextFieldWithLabel();
    InputTextFieldWithLabel ambienceAirTempIn = new InputTextFieldWithLabel();
    InputTextFieldWithLabel insulationLvIn = new InputTextFieldWithLabel();
    InputTextFieldWithLabel insulationHvIn = new InputTextFieldWithLabel();


    InputDropDown typesOfMaterialIn = new InputDropDown(typesOfMaterialInputs , "" , "COPPER");
    InputDropDown typesOfWindingLvIn = new InputDropDown(typesOfWindingInputs,"","STRIP");
    InputDropDown typesOfConnectionLvIn = new InputDropDown(typesOfConnectionInputs,"","STAR");
    InputDropDown typesOfWindingHvIn = new InputDropDown(typesOfWindingInputs,"","STRIP");
    InputDropDown typesOfConnectionHvIn = new InputDropDown(typesOfConnectionInputs,"","STAR");
    InputDropDown oilDuctsLv1In = new InputDropDown(oilDuctsLv1Inputs,"","0");
    InputDropDown oilDuctsLv2In = new InputDropDown(oilDuctsLv2Inputs,"","0");
    InputDropDown windingTemperatureIn = new InputDropDown(windingTemperatureInputs,"","90");
    InputDropDown steelGradeIn =new InputDropDown(steelGradeInputs,"","CRNO-35");
    InputDropDown coreBldgIn = new InputDropDown(coreBldgInputs,"","1.5");
    InputDropDown oilDuctsHv1In = new InputDropDown(oilDuctsHv1Inputs,"","0");
    InputDropDown oilDuctsHv2In = new InputDropDown(oilDuctsHv2Inputs,"","0");
    InputDropDown coolingIn = new InputDropDown(coolingInputs,"","AN-CL-F");
    InputDropDown connectionIn = new InputDropDown(connectionInputs,"","Dyn11");
    InputDropDown frequencyIn = new InputDropDown(frequencyInputs,"","50");

    Control controller = new Control();
    public InputWorkView(Model model) {
        super(model);
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                initializeUI();
            }
        });
    }

    private void initializeUI() {
        this.setLayout(new BorderLayout());
        this.initializeLogoPanel();
        mainPanel.add(new MenuBar(this), BorderLayout.NORTH);
        this.add(mainPanel, BorderLayout.CENTER);

        JPanel inputWorkViewPanel = new JPanel(new GridLayout(0, 2));
        JPanel leftPanel = new JPanel();
        JPanel rightPanel = new JPanel();
        JPanel mainLeftPanel = new JPanel();
        JPanel inputPanel = new JPanel();
        JPanel dropDownPanel = new JPanel();
        JPanel inputLeftPanel = new JPanel();
        JPanel dropDownLeftPanel = new JPanel();
        JPanel dropDownRightPanel = new JPanel();
        JPanel inputRightPanel = new JPanel();
        JPanel defaultPanel = new JPanel();
        JPanel defaultLeftPanel = new JPanel();
        JPanel defaultRightPanel = new JPanel();

        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        mainLeftPanel.setLayout(new GridLayout(2, 0));
        inputPanel.setLayout(new GridLayout(0, 2));
        dropDownPanel.setLayout(new GridLayout(0, 2));

        // Use GridBagLayout for better alignment
        inputLeftPanel.setLayout(new GridBagLayout());
        inputRightPanel.setLayout(new GridBagLayout());
        dropDownLeftPanel.setLayout(new GridBagLayout());
        dropDownRightPanel.setLayout(new GridBagLayout());
        defaultLeftPanel.setLayout(new GridBagLayout());
        defaultRightPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Add k
        leftPanel.add(kIn);
        kIn.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add components to inputLeftPanel
        addComponent(inputLeftPanel, new JLabel("KVA:"), kvaIn, gbc);
        addComponent(inputLeftPanel, new JLabel(),new JLabel(), gbc); // this is just to add a comp due to slight bugs
        addComponent(inputLeftPanel, new JLabel("Low Voltage:"), LVIn, gbc);
        addComponent(inputLeftPanel, new JLabel("High Voltage:"), HVIn, gbc);
        addComponent(inputLeftPanel, new JLabel("Type of Material:"), typesOfMaterialIn, gbc);
        addComponent(inputLeftPanel, new JLabel("Core Bldg:"), coreBldgIn, gbc);

        // Add components to inputRightPanel
        addComponent(inputRightPanel, new JLabel("Connection:"), connectionIn, gbc);
        addComponent(inputRightPanel, new JLabel("Cooling:"), coolingIn, gbc);
        addComponent(inputRightPanel, new JLabel("Frequency:"), frequencyIn, gbc);
        addComponent(inputRightPanel, new JLabel("Winding Temperature:"), windingTemperatureIn, gbc);
        addComponent(inputRightPanel, new JLabel("Steel Grade:"), steelGradeIn, gbc);

        // Add components to dropDownLeftPanel
        addComponent(dropDownLeftPanel, new JLabel("Type of Winding HV:"), typesOfWindingHvIn, gbc);
        addComponent(dropDownLeftPanel, new JLabel("Type of Connection HV:"), typesOfConnectionHvIn, gbc);
        addComponent(dropDownLeftPanel, new JLabel("Oil Ducts HV1:"), oilDuctsHv1In, gbc);
        addComponent(dropDownLeftPanel, new JLabel("Oil Ducts HV2:"), oilDuctsHv2In, gbc);

        // Add components to dropDownRightPanel
        addComponent(dropDownRightPanel, new JLabel("Type of Winding LV:"), typesOfWindingLvIn, gbc);
        addComponent(dropDownRightPanel, new JLabel("Type of Connection LV:"), typesOfConnectionLvIn, gbc);
        addComponent(dropDownRightPanel, new JLabel("Oil Ducts LV1:"), oilDuctsLv1In, gbc);
        addComponent(dropDownRightPanel, new JLabel("Oil Ducts LV2:"), oilDuctsLv2In, gbc);

        // Add components to defaultLeftPanel
        addComponent(defaultLeftPanel, new JLabel("Flux Density :"), flux__density, gbc);
        addComponent(defaultLeftPanel, new JLabel("Wire Bare HV1 :"), wireBareHv1In, gbc);
        addComponent(defaultLeftPanel, new JLabel("Wire Bare HV2 :"), wireBareHv2In, gbc);
        addComponent(defaultLeftPanel, new JLabel("No in Parallel R-A HV1 :"), noInParallel_R_A_Hv1In, gbc);
        addComponent(defaultLeftPanel, new JLabel("No in Parallel R-A HV2 :"), noInParallel_R_A_Hv2In, gbc);
        addComponent(defaultLeftPanel, new JLabel("Insulation Hv :"), insulationHvIn, gbc);
        addComponent(defaultLeftPanel, new JLabel("Layers Hv :"), layersHvIn, gbc);
        addComponent(defaultLeftPanel, new JLabel("Insulation B/W Layers Hv :"), insulationBetweenLayersHvIn, gbc);
        addComponent(defaultLeftPanel, new JLabel("Transposition Hv :"), transpositionHvIn, gbc);
        addComponent(defaultLeftPanel, new JLabel("End Clearances Hv :"), endClearanceHvIn, gbc);
        addComponent(defaultLeftPanel, new JLabel("Oil Ducts - Axial :"), oilDuctsAxialIn, gbc);
        addComponent(defaultLeftPanel, new JLabel("Comp-Gap :"), compGapIn, gbc);
        addComponent(defaultLeftPanel, new JLabel("Limb Plate :"), limbPlateIn, gbc);
        addComponent(defaultLeftPanel, new JLabel("δ :"), deltaIn, gbc);
        addComponent(defaultLeftPanel, new JLabel("Leads :"), leadsIn, gbc);

        // Add components to defaultRightPanel
        addComponent(defaultRightPanel, new JLabel("Core W :"), coreWIn, gbc);
        addComponent(defaultRightPanel, new JLabel("Wire Bare LV1 :"), wireBareLv1In, gbc);
        addComponent(defaultRightPanel, new JLabel("Wire Bare LV2 :"), wireBareLv2In, gbc);
        addComponent(defaultRightPanel, new JLabel("No in Parallel R-A LV1 :"), noInParallel_R_A_Lv1In, gbc);
        addComponent(defaultRightPanel, new JLabel("No in Parallel R-A LV2 :"), noInParallel_R_A_Lv2In, gbc);
        addComponent(defaultRightPanel, new JLabel("Insulation Lv :"), insulationLvIn, gbc);
        addComponent(defaultRightPanel, new JLabel("Layers Lv :"), layersLvIn, gbc);
        addComponent(defaultRightPanel, new JLabel("Insulation B/W Layers Lv :"), insulationBetweenLayersLvIn, gbc);
        addComponent(defaultRightPanel, new JLabel("Transposition Lv :"), transpositionLvIn, gbc);
        addComponent(defaultRightPanel, new JLabel("End Clearances Lv :"), endClearanceLvIn, gbc);
        addComponent(defaultRightPanel, new JLabel("Gap/Bobbin :"), gapBobbinIn, gbc);
        addComponent(defaultRightPanel, new JLabel("am :"), amIn, gbc);
        addComponent(defaultRightPanel, new JLabel("Stacking Factor :"), stackingFactorIn, gbc);
        //addComponent(defaultRightPanel, new JLabel("Spec Loses :"), specLossIn, gbc);
        addComponent(defaultRightPanel, new JLabel("ek % Gaur :"), ekPercentageGaurIn, gbc);
        addComponent(defaultRightPanel, new JLabel("Ambience Air Temp :"), ambienceAirTempIn, gbc);

        inputPanel.add(inputLeftPanel);
        inputPanel.add(inputRightPanel);
        inputPanel.setSize(inputPanel.getPreferredSize().width , inputPanel.getPreferredSize().height);

        dropDownPanel.add(dropDownLeftPanel);
        dropDownPanel.add(dropDownRightPanel);
        dropDownPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        defaultPanel.setLayout(new GridLayout(0, 2));
        defaultPanel.add(defaultLeftPanel);
        defaultPanel.add(defaultRightPanel);

        mainLeftPanel.add(inputPanel);
        mainLeftPanel.add(dropDownPanel);

        leftPanel.add(mainLeftPanel);
        JPanel navigationPanel = initializeNavigationPanel();
        navigationPanel.setAlignmentX(CENTER_ALIGNMENT);
        leftPanel.add(navigationPanel);

        rightPanel.add(defaultPanel);

        JScrollPane defaultScrollPane = new JScrollPane(rightPanel);
        inputWorkViewPanel.add(leftPanel);
        inputWorkViewPanel.add(defaultScrollPane);

        setBackgrounds(StyleConstants.BACKGROUND, inputLeftPanel, dropDownLeftPanel, dropDownRightPanel,
                inputRightPanel, defaultLeftPanel, defaultRightPanel, leftPanel);

        mainPanel.add(inputWorkViewPanel);

        if (Model.getSingleton().getLoadedProject() != null)
            this.refreshInputValues();
    }

    private void addComponent(JPanel panel, JComponent label, JComponent field, GridBagConstraints gbc) {
        gbc.gridx = 0;
        gbc.weightx = 0;
        panel.add(label, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        panel.add(field, gbc);

        gbc.gridy++;
    }

    private void setBackgrounds(Color color, JPanel... panels) {
        for (JPanel panel : panels) {
            panel.setBackground(color);
        }
    }


    private void initializeLogoPanel(){
        logoPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        logoPanel.setBackground(StyleConstants.BACKGROUND);
        logoPanel.setPreferredSize(new Dimension(
                (int) View.SCREEN_DIMENSION.getWidth(),
                (int)(View.SCREEN_DIMENSION.getHeight()*0.1)));
        this.add(logoPanel,BorderLayout.NORTH);
        try{
            BufferedImage nhceLogoImage = ImageIO.read(this.getClass().getResource("NHCE.jpg"));
            ImageIcon nhceLogoIcon = new ImageIcon(nhceLogoImage);
            Image nhceScaledLogoImage = nhceLogoIcon.getImage().getScaledInstance((int)(this.getPreferredSize().getWidth()*0.15), -1, Image.SCALE_SMOOTH);
            ImageIcon nhceScaledLogoIcon = new ImageIcon(nhceScaledLogoImage);
            JLabel nhceIconLabel = new JLabel(nhceScaledLogoIcon);

            BufferedImage dynaLogoImage = ImageIO.read(this.getClass().getResource("DYNA.jpg"));
            ImageIcon dynaLogoIcon = new ImageIcon(dynaLogoImage);
            Image dynaScaledLogoImage = dynaLogoIcon.getImage().getScaledInstance((int)(this.getPreferredSize().getWidth()*0.1), -1, Image.SCALE_SMOOTH);
            ImageIcon dynaScaledLogoIcon = new ImageIcon(dynaScaledLogoImage);
            JLabel dynaIconLabel = new JLabel(dynaScaledLogoIcon);

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

    }
    @Override
    public void captureEventFromChildSubFrame(ViewMessage message) {
        switch (message.getMsgType()) {
            case ViewMessages.CLOSE_OPENED_PROJECT:
                controller.closeOpenedProject();
                break;
            case ViewMessages.SAVE_PROJECT:
                controller.saveProject();
                break;
        }
    }

    @Override
    public String getViewName() {
        return VIEW_NAME;
    }

    @Override
    public Integer getViewId() {
        return 2;
    }

    private JPanel initializeNavigationPanel(){
        JPanel navigationPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton calculateBtn = new JButton("Calculate");
        JButton nextBtn = new JButton("Next");
        navigationPanel.setBackground(StyleConstants.BACKGROUND);
        calculateBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                storeEnteredValuesInModel();
                controller.beginCalculations();
                View.getSingleton().setView(OutputOneWorkView.VIEW_NAME);
            }
        });
        navigationPanel.add(calculateBtn);
        nextBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                View.getSingleton().setView(OutputOneWorkView.VIEW_NAME);
            }
        });
        navigationPanel.add(nextBtn);
        return navigationPanel;
    }

    @Override
    public void update(String msg){
        if(msg.equals("MODEL_UPDATED")){
            this.refreshInputValues();
        }
        if(msg.equals("STORE_INPUT_IN_MODEL")){
            System.out.println("Before storing in model" + model.getLoadedProjectInput().K);
            this.storeEnteredValuesInModel();
            System.out.println("After storing in model Before saving project" + model.getLoadedProjectInput().K);
        }
    }

    private void refreshInputValues(){
        InputData inputData = model.getLoadedProjectInput();
        this.kvaIn.setValueEntered(String.valueOf(inputData.KVA));
        this.kvaIn.setBackground(StyleConstants.BACKGROUND);
        //this.kIn.setValueEntered(String.valueOf(inputData.K));
        kIn.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                inputData.K = kIn.getValue();
            }
        });
        this.kIn.setBackground(StyleConstants.BACKGROUND);
        this.LVIn.setValueEntered(String.valueOf(inputData.LINEVOLTSLV));
        this.LVIn.setBackground(StyleConstants.BACKGROUND);
        this.HVIn.setValueEntered(String.valueOf(inputData.LINEVOLTSHV));
        this.HVIn.setBackground(StyleConstants.BACKGROUND);
        this.insulationLvIn.setValueEntered(String.valueOf(inputData.INSULATION_LV));
        this.insulationLvIn.setBackground(StyleConstants.BACKGROUND);
        this.insulationHvIn.setValueEntered(String.valueOf(inputData.INSULATION_HV));
        this.insulationHvIn.setBackground(StyleConstants.BACKGROUND);
        this.layersLvIn.setValueEntered(String.valueOf(inputData.LAYER_LV));
        this.layersLvIn.setBackground(StyleConstants.BACKGROUND);
        this.layersHvIn.setValueEntered(String.valueOf(inputData.LAYER_HV));
        this.layersHvIn.setBackground(StyleConstants.BACKGROUND);
        this.insulationBetweenLayersLvIn.setValueEntered(String.valueOf(inputData.INSULATION_BETWEEN_LAYERS_LV));
        this.insulationBetweenLayersLvIn.setBackground(StyleConstants.BACKGROUND);
        this.insulationBetweenLayersHvIn.setValueEntered(String.valueOf(inputData.INSULATION_BETWEEN_LAYERS_HV));
        this.insulationBetweenLayersHvIn.setBackground(StyleConstants.BACKGROUND);
        this.oilDuctsAxialIn.setValueEntered(String.valueOf(inputData.OIL_DUCTS_AXIAL_LV));
        this.oilDuctsAxialIn.setBackground(StyleConstants.BACKGROUND);
        this.transpositionLvIn.setValueEntered(String.valueOf(inputData.TRANSPOSITION_LV));
        this.transpositionLvIn.setBackground(StyleConstants.BACKGROUND);
        this.transpositionHvIn.setValueEntered(String.valueOf(inputData.TRANSPOSITION_HV));
        this.transpositionHvIn.setBackground(StyleConstants.BACKGROUND);
        this.compGapIn.setValueEntered(String.valueOf(inputData.COMP_GAP_LV));
        this.compGapIn.setBackground(StyleConstants.BACKGROUND);
        this.endClearanceLvIn.setValueEntered(String.valueOf(inputData.END_CLEARANCES_LV));
        this.endClearanceLvIn.setBackground(StyleConstants.BACKGROUND);
        this.endClearanceHvIn.setValueEntered(String.valueOf(inputData.END_CLEARANCES_HV));
        this.endClearanceHvIn.setBackground(StyleConstants.BACKGROUND);
        this.limbPlateIn.setValueEntered(String.valueOf(inputData.LIMB_PLATE_W));
        this.limbPlateIn.setBackground(StyleConstants.BACKGROUND);
        this.wireBareLv1In.setValueEntered(String.valueOf(inputData.WIREBARELV1));
        this.wireBareLv1In.setBackground(StyleConstants.BACKGROUND);
        this.wireBareLv2In.setValueEntered(String.valueOf(inputData.WIREBARELV2));
        this.wireBareLv2In.setBackground(StyleConstants.BACKGROUND);
        this.wireBareHv1In.setValueEntered(String.valueOf(inputData.WIREBAREHV1));
        this.wireBareHv1In.setBackground(StyleConstants.BACKGROUND);
        this.wireBareHv2In.setValueEntered(String.valueOf(inputData.WIREBAREHV2));
        this.wireBareHv2In.setBackground(StyleConstants.BACKGROUND);
        this.noInParallel_R_A_Lv1In.setValueEntered(String.valueOf(inputData.NO_IN_PARALLEL_RA_LV1));
        this.noInParallel_R_A_Lv1In.setBackground(StyleConstants.BACKGROUND);
        this.noInParallel_R_A_Lv2In.setValueEntered(String.valueOf(inputData.NO_IN_PARALLEL_RA_LV2));
        this.noInParallel_R_A_Lv2In.setBackground(StyleConstants.BACKGROUND);
        this.noInParallel_R_A_Hv1In.setValueEntered(String.valueOf(inputData.NO_IN_PARALLEL_RA_HV1));
        this.noInParallel_R_A_Hv1In.setBackground(StyleConstants.BACKGROUND);
        this.noInParallel_R_A_Hv2In.setValueEntered(String.valueOf(inputData.NO_IN_PARALLEL_RA_HV2));
        this.noInParallel_R_A_Hv2In.setBackground(StyleConstants.BACKGROUND);

        //
        this.gapBobbinIn.setValueEntered(String.valueOf(inputData.GAP_W));
        this.gapBobbinIn.setBackground(StyleConstants.BACKGROUND);
        this.deltaIn.setValueEntered(String.valueOf(inputData.DELTA_W));
        this.deltaIn.setBackground(StyleConstants.BACKGROUND);
        this.amIn.setValueEntered(String.valueOf(inputData.AM_W));
        this.amIn.setBackground(StyleConstants.BACKGROUND);
        this.leadsIn.setValueEntered("6");
        this.leadsIn.setBackground(StyleConstants.BACKGROUND);
        this.stackingFactorIn.setValueEntered(String.valueOf(inputData.STACKING_FACTOR));
        this.stackingFactorIn.setBackground(StyleConstants.BACKGROUND);
        //this.fluxDensityIn.setValueEntered(String.valueOf(inputData.FLUX_DENSITY));
        //this.fluxDensityIn.setBackground(StyleConstants.BACKGROUND);
        flux__density.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                inputData.FLUX_DENSITY = flux__density.getValue();
            }
        });
        this.flux__density.setBackground(StyleConstants.BACKGROUND);
        //this.specLossIn.setValueEntered(inputData.);
        //this.specLossIn..setBackground(StyleConstants.BACKGROUND);
        this.coreWIn.setValueEntered(String.valueOf(inputData.CORE_W));
        this.coreWIn.setBackground(StyleConstants.BACKGROUND);
        this.ambienceAirTempIn.setValueEntered("50");
        this.ambienceAirTempIn.setBackground(StyleConstants.BACKGROUND);
        this.ekPercentageGaurIn.setValueEntered("5");
        this.ekPercentageGaurIn.setBackground(StyleConstants.BACKGROUND);
        //this.ambienceAirTempIn.setValueEntered(inputData.AMB);

        this.typesOfMaterialIn.setValueSelected(inputData.CONDUCTOR);
        this.typesOfMaterialIn.setBackground(StyleConstants.BACKGROUND);
        this.coreBldgIn.setValueSelected(String.valueOf(inputData.CORE_BLDG_FACTOR));
        this.coreBldgIn.setBackground(StyleConstants.BACKGROUND);
        this.connectionIn.setValueSelected(inputData.CONNECTION);
        this.connectionIn.setBackground(StyleConstants.BACKGROUND);
        this.coolingIn.setValueSelected(inputData.COOLING);
        this.coolingIn.setBackground(StyleConstants.BACKGROUND);
        this.frequencyIn.setValueSelected(String.valueOf(inputData.FREQUENCY));
        this.frequencyIn.setBackground(StyleConstants.BACKGROUND);
        this.windingTemperatureIn.setValueSelected(String.valueOf(inputData.WIND_TEMP));
        this.windingTemperatureIn.setBackground(StyleConstants.BACKGROUND);
        this.steelGradeIn.setValueSelected(inputData.STEEL_GRADE);
        this.steelGradeIn.setBackground(StyleConstants.BACKGROUND);
        this.typesOfWindingLvIn.setValueSelected(inputData.WINDINGTYPELV);
        this.typesOfWindingLvIn.setBackground(StyleConstants.BACKGROUND);
        this.typesOfWindingHvIn.setValueSelected(inputData.WINDINGTYPEHV);
        this.typesOfWindingHvIn.setBackground(StyleConstants.BACKGROUND);
        this.typesOfConnectionLvIn.setValueSelected(inputData.CONNECTIONTYPELV);
        this.typesOfConnectionLvIn.setBackground(StyleConstants.BACKGROUND);
        this.typesOfConnectionHvIn.setValueSelected(inputData.CONNECTIONTYPEHV);
        this.typesOfConnectionHvIn.setBackground(StyleConstants.BACKGROUND);
        this.oilDuctsLv1In.setValueSelected(String.valueOf(inputData.OIL_DUCTS_RADIAL_LV1));
        this.oilDuctsLv1In.setBackground(StyleConstants.BACKGROUND);
        this.oilDuctsLv2In.setValueSelected(String.valueOf(inputData.OIL_DUCTS_RADIAL_LV2));
        this.oilDuctsLv2In.setBackground(StyleConstants.BACKGROUND);
        this.oilDuctsHv1In.setValueSelected(String.valueOf(inputData.OIL_DUCTS_RADIAL_HV1));
        this.oilDuctsHv1In.setBackground(StyleConstants.BACKGROUND);
        this.oilDuctsHv2In.setValueSelected(String.valueOf(inputData.OIL_DUCTS_RADIAL_HV2));
        this.oilDuctsHv2In.setBackground(StyleConstants.BACKGROUND);
    }

    public void storeEnteredValuesInModel(){
        System.out.println("Storing in model");
        InputData input = model.getLoadedProjectInput();
        input.KVA = Double.parseDouble(this.kvaIn.getValueEntered());
        //input.K = Double.parseDouble(this.kIn.getValueEntered());
        input.LINEVOLTSLV = Double.parseDouble(this.LVIn.getValueEntered());
        input.LINEVOLTSHV = Double.parseDouble(this.HVIn.getValueEntered());
        input.CONNECTION = this.connectionIn.getValueSelected();
        input.COOLING = this.coolingIn.getValueSelected();
        input.FREQUENCY = Double.parseDouble(this.frequencyIn.getValueSelected());
        input.WIND_TEMP = Double.parseDouble(this.windingTemperatureIn.getValueSelected());
        input.CONDUCTOR = this.typesOfMaterialIn.getValueSelected();
        input.CORE_BLDG_FACTOR = Double.parseDouble(this.coreBldgIn.getValueSelected());
        input.STEEL_GRADE = this.steelGradeIn.getValueSelected();

        input.WINDINGTYPELV = this.typesOfWindingLvIn.getValueSelected();
        input.WINDINGTYPEHV = this.typesOfWindingHvIn.getValueSelected();
        input.CONNECTIONTYPELV = this.typesOfConnectionLvIn.getValueSelected();
        input.CONNECTIONTYPEHV = this.typesOfConnectionHvIn.getValueSelected();
        input.OIL_DUCTS_RADIAL_LV1 = Integer.parseInt(this.oilDuctsLv1In.getValueSelected());
        input.OIL_DUCTS_RADIAL_HV1 = Integer.parseInt(this.oilDuctsHv1In.getValueSelected());
        input.OIL_DUCTS_RADIAL_LV2 = Integer.parseInt(this.oilDuctsLv2In.getValueSelected());
        input.OIL_DUCTS_RADIAL_HV2 = Integer.parseInt(this.oilDuctsHv2In.getValueSelected());

        //input.FLUX_DENSITY = Double.parseDouble(this.fluxDensityIn.getValueEntered());
        input.CORE_W = Double.parseDouble(this.coreWIn.getValueEntered());
        input.WIREBARELV1 = Double.parseDouble(this.wireBareLv1In.getValueEntered());
        input.WIREBARELV2 = Double.parseDouble(this.wireBareLv2In.getValueEntered());
        input.WIREBAREHV1 = Double.parseDouble(this.wireBareHv1In.getValueEntered());
        input.WIREBAREHV2 = Double.parseDouble(this.wireBareHv2In.getValueEntered());
        input.NO_IN_PARALLEL_RA_LV1 = Double.parseDouble(this.noInParallel_R_A_Lv1In.getValueEntered());
        input.NO_IN_PARALLEL_RA_LV2 = Double.parseDouble(this.noInParallel_R_A_Lv2In.getValueEntered());
        input.NO_IN_PARALLEL_RA_HV1 = Double.parseDouble(this.noInParallel_R_A_Hv1In.getValueEntered());
        input.NO_IN_PARALLEL_RA_HV2 = Double.parseDouble(this.noInParallel_R_A_Hv2In.getValueEntered());
        input.INSULATION_LV = Double.parseDouble(this.insulationLvIn.getValueEntered());
        input.INSULATION_HV = Double.parseDouble(this.insulationHvIn.getValueEntered());
        input.LAYER_LV = Integer.parseInt(this.layersLvIn.getValueEntered());
        input.LAYER_HV = Integer.parseInt(this.layersHvIn.getValueEntered());
        input.INSULATION_BETWEEN_LAYERS_LV = Double.parseDouble(this.insulationBetweenLayersLvIn.getValueEntered());
        input.INSULATION_BETWEEN_LAYERS_HV = Double.parseDouble(this.insulationBetweenLayersHvIn.getValueEntered());
        input.TRANSPOSITION_LV = Integer.parseInt(this.transpositionLvIn.getValueEntered());
        input.TRANSPOSITION_HV = Integer.parseInt(this.transpositionHvIn.getValueEntered());
        input.END_CLEARANCES_LV = Double.parseDouble(this.endClearanceLvIn.getValueEntered());
        input.END_CLEARANCES_HV = Double.parseDouble(this.endClearanceHvIn.getValueEntered());
        input.OIL_DUCTS_AXIAL_LV = Integer.parseInt(this.oilDuctsAxialIn.getValueEntered());
        input.GAP_W = Integer.parseInt(this.gapBobbinIn.getValueEntered());
        input.COMP_GAP_LV = Integer.parseInt(this.compGapIn.getValueEntered());
        input.AM_W = Integer.parseInt(this.amIn.getValueEntered());
        input.LIMB_PLATE_W = Integer.parseInt(this.limbPlateIn.getValueEntered());
        input.STACKING_FACTOR = Double.parseDouble(this.stackingFactorIn.getValueEntered());
        input.DELTA_W = Integer.parseInt(this.deltaIn.getValueEntered());

        model.setLoadedProjectInput(input);
    }
}