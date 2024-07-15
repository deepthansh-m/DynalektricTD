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

public class DimensionsWorkView extends AbstractWorkView {
    private final Control mainController = new Control();
    public final static String VIEW_NAME = "DimensionsWorkView";
    private final JPanel mainPanel = new JPanel();
    private final JPanel dimensionsPanel = new JPanel();
    private final JTable dimensionsTable = new JTable(18, 3) {
        @Override
        public boolean isCellEditable(int row, int col) {
            return false;
        }
    };

    public DimensionsWorkView(Model model) {
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
            this.setDimensionsPanelValues();
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
        return 3; // Adjust this as needed
    }

    private void initializeMainPanel() {
        this.mainPanel.setLayout(new GridLayout());
        this.initializeDimensionsPanel();
        this.mainPanel.add(dimensionsPanel);
    }

    private void initializeDimensionsPanel() {
        BoxLayout layout = new BoxLayout(this.dimensionsPanel, BoxLayout.Y_AXIS);
        this.dimensionsPanel.setLayout(layout);
        if(model.getLoadedProject() != null)
            setDimensionsPanelValues();

        JLabel dimensionsTableHeading = new JLabel("DIMENSIONS TABLE");
        dimensionsTableHeading.setFont(StyleConstants.HEADING_SUB1);
        dimensionsTableHeading.setAlignmentX(CENTER_ALIGNMENT);

        initializeTable(dimensionsTable);

        this.dimensionsPanel.add(dimensionsTableHeading);
        this.dimensionsPanel.add(Box.createVerticalStrut(10));
        this.dimensionsPanel.add(dimensionsTable);

        JScrollPane scrollPane = new JScrollPane(dimensionsPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        this.dimensionsPanel.setBackground(StyleConstants.BACKGROUND);
        this.dimensionsPanel.setBorder(BorderFactory.createEmptyBorder(20, 375, 20, 375));
    }

    private void initializeTable(JTable table) {
        table.setShowGrid(true);
        table.setGridColor(Color.BLACK);
        table.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        table.getColumnModel().getColumn(0).setPreferredWidth(150);
        table.getColumnModel().getColumn(1).setPreferredWidth(25);
        table.getColumnModel().getColumn(2).setPreferredWidth(100);
        table.setRowHeight(30);
        table.setSelectionBackground(StyleConstants.BACKGROUND_PRIMARY);
        table.setSelectionForeground(StyleConstants.FOREGROUND_PRIMARY);

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

        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(customRenderer);
        }

        table.setIntercellSpacing(new Dimension(10, 5));
    }

    private JPanel initializeNavigationPanel() {
        JPanel navigationPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        navigationPanel.setBackground(StyleConstants.BACKGROUND);
        JButton previousBtn = new JButton("Previous");
        JButton nextBtn = new JButton("Next");
        navigationPanel.add(previousBtn);
        previousBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                View.getSingleton().setView(CoreWorkView.VIEW_NAME);
            }
        });
        nextBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                View.getSingleton().setView(OutputOneWorkView.VIEW_NAME);
            }
        });
        navigationPanel.add(nextBtn);
        return navigationPanel;
    }

    private void setDimensionsPanelValues() {
        OutputData outputData = Model.getSingleton().getOutputData();

        dimensionsTable.setValueAt("PARAMETER", 0, 0);
        dimensionsTable.setValueAt("UNITS",0,1);
        dimensionsTable.setValueAt("Value", 0, 2);
        dimensionsTable.setValueAt("R1", 1, 0);
        dimensionsTable.setValueAt("R2", 2, 0);
        dimensionsTable.setValueAt("R3", 3, 0);
        dimensionsTable.setValueAt("R4", 4, 0);
        dimensionsTable.setValueAt("perimeter 1", 5, 0);
        dimensionsTable.setValueAt("perimeter 2", 6, 0);
        dimensionsTable.setValueAt("perimeter 3", 7, 0);
        dimensionsTable.setValueAt("perimeter 4", 8, 0);
        dimensionsTable.setValueAt("Mean LG LV", 9, 0);
        dimensionsTable.setValueAt("Mean LG DELTA", 10, 0);
        dimensionsTable.setValueAt("Mean LG HV", 11, 0);

        dimensionsTable.setValueAt("mm",1,1);
        dimensionsTable.setValueAt("mm",2,1);
        dimensionsTable.setValueAt("mm",3,1);
        dimensionsTable.setValueAt("mm",4,1);
        dimensionsTable.setValueAt("mm",5,1);
        dimensionsTable.setValueAt("mm",6,1);
        dimensionsTable.setValueAt("mm",7,1);
        dimensionsTable.setValueAt("mm",8,1);
        dimensionsTable.setValueAt("mm",9,1);
        dimensionsTable.setValueAt("mm",10,1);
        dimensionsTable.setValueAt("mm",11,1);
        dimensionsTable.setValueAt("mtr",12,1);
        dimensionsTable.setValueAt("mtr",13,1);
        dimensionsTable.setValueAt("mtr",14,1);
        dimensionsTable.setValueAt("mtr",15,1);
        dimensionsTable.setValueAt("mtr",16,1);
        dimensionsTable.setValueAt("mtr",17,1);

        dimensionsTable.setValueAt(outputData.R1, 1, 2);
        dimensionsTable.setValueAt(outputData.R2, 2, 2);
        dimensionsTable.setValueAt(outputData.R3, 3, 2);
        dimensionsTable.setValueAt(outputData.R4, 4, 2);
        dimensionsTable.setValueAt(outputData.PERIMETER1, 5, 2);
        dimensionsTable.setValueAt(outputData.PERIMETER2, 6, 2);
        dimensionsTable.setValueAt(outputData.PERIMETER3, 7, 2);
        dimensionsTable.setValueAt(outputData.PERIMETER4, 8, 2);
        dimensionsTable.setValueAt(outputData.MEAN_LG_LV, 9, 2);
        dimensionsTable.setValueAt(outputData.MEAN_LG_DELTA, 10, 2);
        dimensionsTable.setValueAt(outputData.MEAN_LG_HV, 11, 2);

        dimensionsTable.setValueAt("Active L", 12, 0);
        dimensionsTable.setValueAt("Active H", 13, 0);
        dimensionsTable.setValueAt("Active B", 14, 0);
        dimensionsTable.setValueAt("Overall L", 15, 0);
        dimensionsTable.setValueAt("Overall H", 16, 0);
        dimensionsTable.setValueAt("Overall B", 17, 0);

        dimensionsTable.setValueAt(outputData.L_ACTIVE, 12, 2);
        dimensionsTable.setValueAt(outputData.H_ACTIVE, 13, 2);
        dimensionsTable.setValueAt(outputData.B_ACTIVE, 14, 2);
        dimensionsTable.setValueAt(outputData.L_MECHANICAL, 15, 2);
        dimensionsTable.setValueAt(outputData.H_MECHANICAL, 16, 2);
        dimensionsTable.setValueAt(outputData.B_MECHANICAL, 17, 2);
    }
}