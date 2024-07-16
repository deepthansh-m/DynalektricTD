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
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;

public class CoreWorkView extends AbstractWorkView {
    private final Control mainController = new Control();
    public final static String VIEW_NAME = "CoreWorkView";
    private final JPanel mainPanel = new JPanel();
    private final JPanel corePanel = new JPanel();
    private final JTable CORE_TABLE = new JTable(14, 3) {
        @Override
        public boolean isCellEditable(int row, int col) {
            return false;
        }
    };
    private final JTable CORE_VALUE_TABLE = new JTable(4, 2) {
        @Override
        public boolean isCellEditable(int row, int col) {
            return false;
        }
    };

    public CoreWorkView(Model model) {
        super(model);
        SwingUtilities.invokeLater(this::initializeUI);
    }

    private void initializeUI() {
        this.setLayout(new BorderLayout());
        this.add(new MenuBar(this), BorderLayout.NORTH);
        this.initializeMainPanel();
        this.add(mainPanel, BorderLayout.CENTER);
        this.add(initializeNavigationPanel(), BorderLayout.SOUTH);
    }

    @Override
    public void update(String message) {
        if(Objects.equals(message, "MODEL_UPDATED")) {
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
            case ViewMessages.OPEN_WINDING_VIEW:
                mainController.openWindingView();
                break;
            case ViewMessages.OPEN_CORE_VIEW:
                mainController.openCoreView();
                break;
            case ViewMessages.OPEN_DIMENSION_VIEW:
                mainController.openDimensionView();
                break;
            case ViewMessages.OPEN_B_O_M_VIEW:
                mainController.openBOMView();
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
        return 4;
    }

    private void initializeMainPanel() {
        this.mainPanel.setLayout(new GridLayout());
        this.initializeCorePanel();
        this.mainPanel.add(corePanel);
    }

    private void initializeCorePanel() {
        BoxLayout layout = new BoxLayout(this.corePanel, BoxLayout.Y_AXIS);
        this.corePanel.setLayout(layout);
        if(model.getLoadedProject() != null)
            setCorePanelValues();
        JLabel Core_Table_Heading = new JLabel("Transformer Design Wizard [Core]");
        Core_Table_Heading.setFont(StyleConstants.HEADING_SUB1);
        Core_Table_Heading.setAlignmentX(CENTER_ALIGNMENT);
        CORE_TABLE.setAlignmentX(CENTER_ALIGNMENT);
        this.corePanel.add(Core_Table_Heading);
        this.corePanel.add(Box.createVerticalStrut(10));
        this.corePanel.add(CORE_TABLE);
        this.corePanel.add(CORE_VALUE_TABLE);
        JScrollPane scrollPane = new JScrollPane(corePanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        this.corePanel.setBackground(StyleConstants.BACKGROUND);
        this.corePanel.setBorder(BorderFactory.createEmptyBorder(20, 375, 20, 375));
        CORE_TABLE.setShowGrid(true);
        CORE_TABLE.setGridColor(Color.BLACK);
        CORE_TABLE.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        CORE_TABLE.getColumnModel().getColumn(0).setPreferredWidth(120);
        CORE_TABLE.getColumnModel().getColumn(1).setPreferredWidth(40);
        CORE_TABLE.getColumnModel().getColumn(2).setPreferredWidth(40);
        CORE_TABLE.setRowHeight(30);
        CORE_TABLE.setSelectionBackground(StyleConstants.BACKGROUND_PRIMARY);
        CORE_TABLE.setSelectionForeground(StyleConstants.FOREGROUND_PRIMARY);
        CORE_VALUE_TABLE.setShowGrid(true);
        CORE_VALUE_TABLE.setGridColor(Color.BLACK);
        CORE_VALUE_TABLE.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        CORE_VALUE_TABLE.getColumnModel().getColumn(0).setPreferredWidth(50);
        CORE_VALUE_TABLE.getColumnModel().getColumn(1).setPreferredWidth(150);
        CORE_VALUE_TABLE.setRowHeight(30);
        CORE_VALUE_TABLE.setSelectionBackground(StyleConstants.BACKGROUND_PRIMARY);
        CORE_VALUE_TABLE.setSelectionForeground(StyleConstants.FOREGROUND_PRIMARY);
        DefaultTableCellRenderer customRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (row == 0) {
                    c.setBackground(StyleConstants.CARD_BORDER);
                    setHorizontalAlignment(JLabel.CENTER);
                } else {
                    c.setBackground(table.getBackground());
                    setHorizontalAlignment(column == 0 ? JLabel.LEFT : JLabel.CENTER);
                }
                return c;
            }
        };
        for (int i = 0; i < CORE_TABLE.getColumnCount(); i++) {
            CORE_TABLE.getColumnModel().getColumn(i).setCellRenderer(customRenderer);
        }
        CORE_TABLE.setIntercellSpacing(new Dimension(10, 5));

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        CORE_VALUE_TABLE.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        CORE_VALUE_TABLE.setBackground(StyleConstants.CARD_BACKGROUND);
    }

    private JPanel initializeNavigationPanel() {
        JPanel navigationPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        // Load the image and scale it down for the previous button
        ImageIcon previousIcon = new ImageIcon("src/main/resources/com/dynalektric/view/workViews/previous_icon.png");
        Image previousImage = previousIcon.getImage();
        Image scaledPreviousImage = previousImage.getScaledInstance(32, 32, Image.SCALE_SMOOTH); // scale to 32x32 pixels
        ImageIcon scaledPreviousIcon = new ImageIcon(scaledPreviousImage);

        // Create the Previous button and set the icon
        JButton previousBtn = new JButton("Previous", scaledPreviousIcon);

        // Set text position relative to the icon for the Previous button
        previousBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
        previousBtn.setHorizontalTextPosition(SwingConstants.CENTER);

        // Load the image and scale it down for the next button
        ImageIcon nextIcon = new ImageIcon("src/main/resources/com/dynalektric/view/workViews/next_icon.jpeg");
        Image nextImage = nextIcon.getImage();
        Image scaledNextImage = nextImage.getScaledInstance(32, 32, Image.SCALE_SMOOTH); // scale to 32x32 pixels
        ImageIcon scaledNextIcon = new ImageIcon(scaledNextImage);

        // Create the Next button and set the icon
        JButton nextBtn = new JButton("Next", scaledNextIcon);

        // Set text position relative to the icon for the Next button
        nextBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
        nextBtn.setHorizontalTextPosition(SwingConstants.CENTER);

        // Load the image and scale it down for the print button
        ImageIcon printIcon = new ImageIcon("src/main/resources/com/dynalektric/view/workViews/print_icon.png");
        Image printImage = printIcon.getImage();
        Image scaledPrintImage = printImage.getScaledInstance(32, 32, Image.SCALE_SMOOTH); // scale to 32x32 pixels
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
                View.getSingleton().setView(WindingWorkView.VIEW_NAME);
            }
        });
        nextBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                View.getSingleton().setView(DimensionsWorkView.VIEW_NAME);
            }
        });
        navigationPanel.add(nextBtn);
        printBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                View.getSingleton().setView(PrintWorkView.VIEW_NAME);
            }
        });
        navigationPanel.add(printBtn);
        return navigationPanel;
    }

    private void setCorePanelValues() {
        OutputData outputData = Model.getSingleton().getOutputData();
        InputData inputData = Model.getSingleton().getLoadedProjectInput();

        CORE_TABLE.setValueAt("Parameter", 0, 0);
        CORE_TABLE.setValueAt("W", 0, 1);
        CORE_TABLE.setValueAt("D", 0, 2);

        CORE_TABLE.setValueAt("Core", 1, 0);
        CORE_TABLE.setValueAt("Limb Plate", 2, 0);
        CORE_TABLE.setValueAt("CORE''", 3, 0);
        CORE_TABLE.setValueAt("gap/bobin", 4, 0);
        CORE_TABLE.setValueAt("ID(1)", 5, 0);
        CORE_TABLE.setValueAt("LV wdg", 6, 0);
        CORE_TABLE.setValueAt("OD(1)", 7, 0);
        CORE_TABLE.setValueAt("δ", 8, 0);
        CORE_TABLE.setValueAt("ID(2)", 9, 0);
        CORE_TABLE.setValueAt("HV wdg", 10, 0);
        CORE_TABLE.setValueAt("OD(2)", 11, 0);
        CORE_TABLE.setValueAt("am", 12, 0);

        CORE_TABLE.setValueAt(inputData.CORE_W, 1, 1);
        CORE_TABLE.setValueAt(outputData.CORE_D, 1, 2);

        CORE_TABLE.setValueAt(inputData.LIMB_PLATE_W, 2, 1);
        CORE_TABLE.setValueAt(inputData.LIMB_PLATE_D, 2, 2);

        CORE_TABLE.setValueAt(outputData.TOTAL_CORE_W, 3, 1);
        CORE_TABLE.setValueAt(outputData.TOTAL_CORE_D, 3, 2);

        CORE_TABLE.setValueAt(inputData.GAP_W, 4, 1);
        CORE_TABLE.setValueAt(inputData.GAP_D, 4, 2);

        CORE_TABLE.setValueAt(outputData.ID_W, 5, 1);
        CORE_TABLE.setValueAt(outputData.ID_D, 5, 2);

        CORE_TABLE.setValueAt(outputData.LV_WDG, 6, 1);
        CORE_TABLE.setValueAt(outputData.LV_WDG, 6, 2);

        CORE_TABLE.setValueAt(outputData.OD_W, 7, 1);
        CORE_TABLE.setValueAt(outputData.OD_D, 7, 2);

        CORE_TABLE.setValueAt(inputData.DELTA_W, 8, 1);
        CORE_TABLE.setValueAt(inputData.DELTA_D, 8, 2);

        CORE_TABLE.setValueAt(outputData.TOTAL_ID_W, 9, 1);
        CORE_TABLE.setValueAt(outputData.TOTAL_ID_D, 9, 2);

        CORE_TABLE.setValueAt(outputData.HV_WDG, 10, 1);
        CORE_TABLE.setValueAt(outputData.HV_WDG, 10, 2);

        CORE_TABLE.setValueAt(outputData.TOTAL_OD_W, 11, 1);
        CORE_TABLE.setValueAt(outputData.TOTAL_OD_D, 11, 2);

        CORE_TABLE.setValueAt(inputData.AM_W, 12, 1);
        CORE_TABLE.setValueAt(inputData.AM_D, 12, 2);

        CORE_VALUE_TABLE.setValueAt("Core Distance",0,0);
        CORE_VALUE_TABLE.setValueAt("Yoke Length",1,0);
        CORE_VALUE_TABLE.setValueAt("Leads",2,0);
        CORE_VALUE_TABLE.setValueAt("Core",3,0);

        CORE_VALUE_TABLE.setValueAt(outputData.C_DIST,0,1);
        CORE_VALUE_TABLE.setValueAt(outputData.YOKE_L,1,1);
        CORE_VALUE_TABLE.setValueAt(outputData.LEADS,2,1);
        CORE_VALUE_TABLE.setValueAt(outputData.CORE,3,1);
    }
}