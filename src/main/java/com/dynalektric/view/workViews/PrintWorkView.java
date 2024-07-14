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
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Objects;

public class PrintWorkView extends AbstractWorkView {
    private final Control mainController = new Control();
    private final PrintWorkView thisReference = this;
    public final static String VIEW_NAME = "PrintWorkView";
    private final JPanel mainPanel = new JPanel();
    private final JTable combinedTable;
    private final DefaultTableModel tableModel;

    private int sectionCounter = 0;

    public PrintWorkView(Model model) {
        super(model);
        this.setLayout(new BorderLayout());

        // Initialize the combined table
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Parameter");
        tableModel.addColumn("");
        tableModel.addColumn("");
        tableModel.addColumn("");
        tableModel.addColumn("");
        combinedTable = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }

            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                if (c instanceof JComponent) {
                    ((JComponent) c).setBorder(null);
                }
                return c;
            }
        };

        combinedTable.setShowGrid(false);

        BorderlessTableCellRenderer borderlessRenderer = new BorderlessTableCellRenderer();
        for (int i = 0; i < combinedTable.getColumnCount(); i++) {
            combinedTable.getColumnModel().getColumn(i).setCellRenderer(borderlessRenderer);
        }

        // Adjust column widths
        combinedTable.getColumnModel().getColumn(0).setPreferredWidth(30);
        combinedTable.getColumnModel().getColumn(1).setPreferredWidth(40);
        combinedTable.getColumnModel().getColumn(2).setPreferredWidth(40);
        combinedTable.getColumnModel().getColumn(3).setPreferredWidth(40);
        combinedTable.getColumnModel().getColumn(4).setPreferredWidth(40);

        combinedTable.setRowHeight(30);
        combinedTable.setIntercellSpacing(new Dimension(10, 5));
        combinedTable.setFont(StyleConstants.PRINT_FONT);

        SwingUtilities.invokeLater(this::initializeUI);
    }
    class BorderlessTableCellRenderer extends DefaultTableCellRenderer {
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus,
                                                       int row, int column) {
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            setBorder(noFocusBorder);
            return this;
        }
    }

    private void initializeUI() {
        this.setLayout(new BorderLayout());
        this.add(new MenuBar(this), BorderLayout.NORTH);
        this.initializeMainPanel();
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        this.add(scrollPane, BorderLayout.CENTER);
        this.add(initializeNavigationPanel(), BorderLayout.SOUTH);
    }

    @Override
    public void update(String message) {
        if (Objects.equals(message, "MODEL_UPDATED")) {
            this.populateCombinedTable();
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
            case ViewMessages.OPEN_WINDING_VIEW:
                mainController.openWindingView();
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
        return 5;
    }

    private void initializeMainPanel() {
        this.mainPanel.setLayout(new BorderLayout());
        this.mainPanel.add(combinedTable, BorderLayout.CENTER);
    }

    private JPanel initializeNavigationPanel() {
        JPanel navigationPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        navigationPanel.setBackground(StyleConstants.BACKGROUND);
        JButton previousBtn = new JButton("Cancel");
        navigationPanel.add(previousBtn);
        previousBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                View.getSingleton().setView(OutputTwoWorkView.VIEW_NAME);
            }
        });
        JButton printButton = new JButton("Print");
        printButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PrintManager.printComponent(mainPanel);
            }
        });
        navigationPanel.add(printButton);
        return navigationPanel;
    }

    private void populateCombinedTable() {
        tableModel.setRowCount(0);
        sectionCounter = 0;

        OutputData outputData = Model.getSingleton().getOutputData();
        InputData inputData = Model.getSingleton().getLoadedProjectInput();

        // LV HV data
        addSectionHeader("HV LV Data");
        addDataRow(" ", "HV"," ", "LV");
        addDataRow("   Rated Voltage", outputData.VPH_HV," ", outputData.VPH_LV);
        addDataRow("   Rated Current", outputData.IPH_HV," ", outputData.IPH_LV);
        addDataRow("   Cross Section sqmm", outputData.CROSS_SECTION_HV," ", outputData.CROSS_SECTION_LV);
        addDataRow("   Current density", outputData.CURRENT_DENSITY_HV," ", outputData.CURRENT_DENSITY_LV);
        addDataRow("   Turns/Limb", outputData.TURN_LIMB_HV," ", outputData.TURN_LIMB_LV," ");
        addDataRow("   Turns/Layer", outputData.TURN_LAYER_HV," ", outputData.TURN_LAYER_LV," ");
        addDataRow("   wdg lg-imp calc", outputData.WDG_LG_IMP_CALCU_HV," ", outputData.WDG_LG_IMP_CALCU_LV);
        addDataRow("   Wind Length(AXL)", outputData.WIND_LENGTH_HV," ", outputData.WIND_LENGTH_LV);
        addDataRow("   Limb length", outputData.LIMB_LENGTH_HV," ", outputData.LIMB_LENGTH_LV);
        addDataRow("   Wind-radial depth", outputData.WIND_RADIAL_DEPTH_HV," ", outputData.WIND_RADIAL_DEPTH_LV);
        addDataRow("   Turn length", outputData.TURN_LENGTH_HV," ", outputData.TURN_LENGTH_LV);
        addDataRow("   Wire Length", outputData.WIRE_LENGTH_HV," ", outputData.WIRE_LENGTH_LV);
        addDataRow("   Resistance (ohms)", outputData.RESISTANCE_HV," ", outputData.RESISTANCE_LV);
        addDataRow("   Stray Loss (%)", outputData.STRAY_LOSS_HV," ", outputData.STRAY_LOSS_LV);
        addDataRow("   Load Loss (Watts)", outputData.LOAD_LOSS_HV," ", outputData.LOAD_LOSS_LV);
        addDataRow("   S.a-m(wdg)", outputData.S_AM2_WDG_HV," ", outputData.S_AM2_WDG_LV);
        addDataRow("   W/m^2", outputData.W_M2_HV," ", outputData.W_M2_LV);
        addDataRow("   wdg temp rise ", outputData.WDG_TEMP_RISE_HV," ", outputData.WDG_TEMP_RISE_LV);
        addDataRow("   core", outputData.CORE);
        addDataRow("");

        addDataRow("   V/T", outputData.V_T);
        addDataRow("");

        // Wire Details
        addSectionHeader("Wire Details");
        addDataRow("", "HV 1", "HV 2", "LV 1", "LV 2");
        addDataRow("   Wire bare", inputData.WIREBAREHV1, inputData.WIREBAREHV2, inputData.WIREBARELV1, inputData.WIREBARELV2);
        addDataRow("   Wire insulated", outputData.WIRE_INSULATED_HV1, outputData.WIRE_INSULATED_HV2, outputData.WIRE_INSULATED_LV1, outputData.WIRE_INSULATED_LV2);
        addDataRow("");

        // Core Weight
        addSectionHeader("Core Weight");
        addDataRow("   Conductor in KG", outputData.CONDUCTOR_LV1, outputData.CONDUCTOR_LV2, outputData.CONDUCTOR_HV1, outputData.CONDUCTOR_HV2);
        addDataRow("");

        // Core Details
        addSectionHeader("Core Details");
        addDataRow("", "W", "D");
        addDataRow("   Core", inputData.CORE_W, outputData.CORE_D);
        addDataRow("   Limb Plate", inputData.LIMB_PLATE_W, inputData.LIMB_PLATE_D);
        addDataRow("   CORE''", outputData.TOTAL_CORE_W, outputData.TOTAL_CORE_D);
        addDataRow("   gap/bobin", inputData.GAP_W, inputData.GAP_D);
        addDataRow("   ID(1)", outputData.ID_W, outputData.ID_D);
        addDataRow("   LV wdg", outputData.LV_WDG, outputData.LV_WDG);
        addDataRow("   OD(1)", outputData.OD_W, outputData.OD_D);
        addDataRow("   δ", inputData.DELTA_W, inputData.DELTA_D);
        addDataRow("   ID(2)", outputData.TOTAL_ID_W, outputData.TOTAL_ID_D);
        addDataRow("   HV wdg", outputData.HV_WDG, outputData.HV_WDG);
        addDataRow("   OD(2)", outputData.TOTAL_OD_W, outputData.TOTAL_OD_D);
        addDataRow("   am", inputData.AM_W, inputData.AM_D);
        addDataRow("");
        addDataRow("   C Dist", outputData.C_DIST);
        addDataRow("   Yoke L", outputData.YOKE_L);
        addDataRow("   Leads", outputData.LEADS);
        addDataRow("");
        addDataRow("   R1", outputData.R1);
        addDataRow("   R2", outputData.R2);
        addDataRow("   R3", outputData.R3);
        addDataRow("   R4", outputData.R4);
        addDataRow("   perimeter 1", outputData.PERIMETER1);
        addDataRow("   perimeter 2", outputData.PERIMETER2);
        addDataRow("   perimeter 3", outputData.PERIMETER3);
        addDataRow("   perimeter 4", outputData.PERIMETER4);
        addDataRow("   Mean LG LV", outputData.MEAN_LG_LV);
        addDataRow("   Mean LG DELTA", outputData.MEAN_LG_DELTA);
        addDataRow("   Mean LG HV", outputData.MEAN_LG_HV);
        addDataRow("");

        // Tank Dimensions
        addSectionHeader("Tank Dimensions");
        addDataRow("   Active L", outputData.L_ACTIVE);
        addDataRow("   Active H", outputData.H_ACTIVE);
        addDataRow("   Active B", outputData.B_ACTIVE);
        addDataRow("   Overall L", outputData.L_MECHANICAL);
        addDataRow("   Overall H", outputData.H_MECHANICAL);
        addDataRow("   Overall B", outputData.B_MECHANICAL);
        addDataRow("");

        // Impedance
        addSectionHeader("Impedance");
        addDataRow("   h", outputData.H);
        addDataRow("   b", outputData.B);
        addDataRow("   kr", outputData.KR);
        addDataRow("   Ls", outputData.LS);
        addDataRow("   δ`", outputData.DELTA_DASH);
        addDataRow("   ex", outputData.EX);
        addDataRow("   Er", outputData.ER);
        addDataRow("   Ek", outputData.EK);
        addDataRow("");

        // Losses
        addSectionHeader("Losses");
        addDataRow("   Mass of the Conductor", outputData.MASS_OF_CONDUCTOR);
        addDataRow("   Load Loss LV Watts", outputData.LOAD_LOSS_LV);
        addDataRow("   Load Loss HV Watts", outputData.LOAD_LOSS_HV);
        addDataRow("   Tank Watts", outputData.TANK);
        addDataRow("   Total Obtained Watts", outputData.OBTAINED_LOSS);
        addDataRow("   Total Core Mass", outputData.TOTAL_CORE_MASS);
        addDataRow("   Net Cross Section", outputData.NET_CROSS_SECTION);
        addDataRow("   Spec Losses", outputData.SPEC_LOSSES);
        addDataRow("   Calc Loss watts", outputData.CALC_LOSS);
        addDataRow("");


        // Surface Area
        addSectionHeader("Surface Area");
        addDataRow("   Core s-a", outputData.CORE_SA);
        addDataRow("   Wdg s-a", outputData.WDG_SA);
        addDataRow("   Σ s-a", outputData.SUM_SA);
        addDataRow("   Σ Loss", outputData.SUM_LOSS);
        addDataRow("   θ(k)", outputData.THETA_K);
        addDataRow("");

        // VA Table
        addSectionHeader("VA Table");
        addDataRow("   Mass Limb", outputData.MASS_LIMB, outputData.MASS_LIMB_DASH);
        addDataRow("   Mass Yoke", outputData.MASS_YOKE, outputData.MASS_YOKE_DASH);
        addDataRow("   Mass Corner", outputData.MASS_CORNER, outputData.MASS_CORNER_DASH);
        addDataRow("   Gap VA", "", outputData.GAP_VA);
        addDataRow("   Σ VA", "", outputData.SUM_VA);
        addDataRow("   %N.L.Current", "", outputData.NL_CURRENT_PERCENTAGE);
        addDataRow("   Extra-N.L.Loss", "", outputData.EXTRA_NL_LOSS);
        addDataRow("");

        // Bill of Material
        addSectionHeader("Bill of Material");
        addDataRow("   Core", outputData.BOM_CORE);
        addDataRow("   Core-Steel + SS", outputData.BOM_CORE_STEEL);
        addDataRow("   " + inputData.CONDUCTOR, outputData.BOM_CONDUCTOR_WT);
        addDataRow("   Leads", outputData.BOM_LEADS);
        addDataRow("   Insulation-FG", outputData.BOM_INSULATION_FG);
        addDataRow("   Connection-FG", outputData.BOM_CONNECTION_FG);
        addDataRow("   Insulation-CL-H", outputData.BOM_INSULATION_CL_H);
        addDataRow("   RESIN-VT50", outputData.BOM_RESIN_VT50);
        addDataRow("   MISC", outputData.BOM_MISC);
        addDataRow("   CRCA ENCL", inputData.BOM_CRCA_ENCL);
        addDataRow("   Total Mass", outputData.BOM_TOTAL_MASS);
        addDataRow("");
    }

    private void addSectionHeader(String headerText) {
        sectionCounter++;
        tableModel.addRow(new Object[]{sectionCounter + ". " + headerText, "", "", "", ""});
    }

    private void addDataRow(String parameter, Object... values) {
        Object[] rowData = new Object[values.length + 1];
        rowData[0] = parameter;
        System.arraycopy(values, 0, rowData, 1, values.length);
        tableModel.addRow(rowData);
    }
}