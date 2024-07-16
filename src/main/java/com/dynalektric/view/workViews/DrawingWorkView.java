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
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class DrawingWorkView extends AbstractWorkView {

    private final Control mainController = new Control();
    public final static String VIEW_NAME = "DrawingWorkView";
    private final JPanel mainPanel = new JPanel();

    private BufferedImage image1;
    private BufferedImage image2;
    private BufferedImage currentImage;
    private int i = 1;
    private JComboBox<String> imageSelector;
    private JTable bomTable;
    private JTable weightDetailsTable;
    private JTable overallDimensionsTable;
    private DefaultTableModel bomTableModel;
    private DefaultTableModel weightDetailsTableModel;
    private DefaultTableModel overallDimensionsTableModel;
    private JScrollPane bomScrollPane;
    private JScrollPane weightDetailsScrollPane;
    private JScrollPane overallDimensionsScrollPane;
    private JPanel imagePanel;
    private JLabel titleLabel;

    public DrawingWorkView(Model model) {
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
        this.add(new MenuBar(this), BorderLayout.NORTH);
        this.initializeMainPanel();
        this.add(mainPanel, BorderLayout.CENTER);
        this.add(initializeNavigationPanel(), BorderLayout.SOUTH);
    }

    private void initializeMainPanel() {
        mainPanel.setLayout(new BorderLayout());

        try {
            image1 = ImageIO.read(new File("src/main/resources/com/dynalektric/view/workViews/Oil-cooled Transformer Design.png"));
            image2 = ImageIO.read(new File("src/main/resources/com/dynalektric/view/workViews/Dry Type Transformer.png"));
            currentImage = image1; // Default to image1
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        titleLabel = new JLabel("DRAWINGS", SwingConstants.CENTER);
        titleLabel.setFont(StyleConstants.HEADING_SUB1);
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel topPanel = new JPanel(new BorderLayout());

        JPanel dropdownPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel dropdownLabel = new JLabel("Select Image:");
        String[] imageOptions = {"Oil-cooled Transformer", "Dry Type Transformer"};
        imageSelector = new JComboBox<>(imageOptions);
        imageSelector.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedOption = (String) imageSelector.getSelectedItem();
                if (selectedOption.equals("Oil-cooled Transformer")) {
                    currentImage = image1;
                    i = 1;
                } else if (selectedOption.equals("Dry Type Transformer")) {
                    currentImage = image2;
                    i = 2;
                }
                imagePanel.repaint();
            }
        });
        dropdownPanel.add(dropdownLabel);
        dropdownPanel.add(imageSelector);
        dropdownPanel.setBackground(StyleConstants.BACKGROUND);

        topPanel.add(dropdownPanel, BorderLayout.NORTH);

        imagePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                if(i==1) {
                    updateTableModelForImage1();
                } else if (i==2) {
                    updateTableModelForImage2();
                }
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;

                if (currentImage != null) {
                    int panelWidth = getWidth();
                    int panelHeight = getHeight();
                    int imageWidth = currentImage.getWidth();
                    int imageHeight = currentImage.getHeight();
                    double scaleFactor = 0.8 * Math.min(panelWidth / (double) imageWidth, panelHeight / (double) imageHeight);
                    int newWidth = (int) (imageWidth * scaleFactor);
                    int newHeight = (int) (imageHeight * scaleFactor);
                    int x = (panelWidth - newWidth) / 2;
                    int y = (panelHeight - newHeight) / 2;
                    g2d.drawImage(currentImage, x, y, newWidth, newHeight, this);
                }
            }
        };
        topPanel.add(imagePanel, BorderLayout.CENTER);

        mainPanel.add(topPanel, BorderLayout.CENTER);
        mainPanel.setBackground(StyleConstants.BACKGROUND);
        imagePanel.setBackground(StyleConstants.BACKGROUND);

        // BOM table
        String[] bomColumnNames = {"No", "Description", "Qty"};
        Object[][] bomInitialData = new Object[0][3];
        bomTableModel = new DefaultTableModel(bomInitialData, bomColumnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        bomTable = new JTable(bomTableModel);
        bomTable.setFocusable(false);
        bomTable.setRowSelectionAllowed(false);

        // Overall dimensions table
        String[] overallDimensionsColumnNames = {"KVA", "V/R", "L", "W", "H"};
        Object[][] overallDimensionsInitialData = new Object[0][5];
        overallDimensionsTableModel = new DefaultTableModel(overallDimensionsInitialData, overallDimensionsColumnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        overallDimensionsTable = new JTable(overallDimensionsTableModel);
        overallDimensionsTable.setFocusable(false);
        overallDimensionsTable.setRowSelectionAllowed(false);

        // Weight details table
        String[] weightDetailsColumnNames = {"No", "Description", "Unit", "Value"};
        Object[][] weightDetailsInitialData = new Object[0][4];
        weightDetailsTableModel = new DefaultTableModel(weightDetailsInitialData, weightDetailsColumnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        weightDetailsTable = new JTable(weightDetailsTableModel);
        weightDetailsTable.setFocusable(false);
        weightDetailsTable.setRowSelectionAllowed(false);

        // Add scroll panes
        bomScrollPane = new JScrollPane(bomTable);
        bomScrollPane.setPreferredSize(new Dimension(320, 430)); // Adjusted for height
        overallDimensionsScrollPane = new JScrollPane(overallDimensionsTable);
        overallDimensionsScrollPane.setPreferredSize(new Dimension(320, 50)); // Adjusted for height
        weightDetailsScrollPane = new JScrollPane(weightDetailsTable);
        weightDetailsScrollPane.setPreferredSize(new Dimension(320, 430)); // Adjusted for height

        // Create headings
        JLabel bomTableHeading = new JLabel("Bill of Material", SwingConstants.CENTER);
        bomTableHeading.setFont(StyleConstants.HEADING_SUB2);
        JLabel overallDimensionsTableHeading = new JLabel("Overall Dimensions", SwingConstants.CENTER);
        overallDimensionsTableHeading.setFont(StyleConstants.HEADING_SUB2);
        JLabel weightDetailsTableHeading = new JLabel("Weight of Materials", SwingConstants.CENTER);
        weightDetailsTableHeading.setFont(StyleConstants.HEADING_SUB2);

        // Combine tables and headings in a vertical box layout
        Box tableBox = Box.createVerticalBox();
        tableBox.add(bomTableHeading);
        tableBox.add(bomScrollPane);
        tableBox.add(overallDimensionsTableHeading);
        tableBox.add(overallDimensionsScrollPane);
        tableBox.add(weightDetailsTableHeading);
        tableBox.add(weightDetailsScrollPane);

        mainPanel.add(tableBox, BorderLayout.EAST);

        // Initialize with the data for the default image (image1)
        updateTableModelForImage1();
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
                View.getSingleton().setView(BillOfMaterialsWorkView.VIEW_NAME);
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

    @Override
    public void update(String message) {
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

    private void updateTableModelForImage1() {
        clearTableModel(bomTableModel);
        Object[][] bomData = {
                {"1", "HV BUSHING", "3"},
                {"2", "LV BUSHING & NEUTRAL BUSHING", "4"},
                {"3", "JAKING PAD", "4"},
                {"4", "CONSERVATOR ASSLY WITH OIL FILLING CAP", "1"},
                {"5", "SILICA JEL BREATHER", "1"},
                {"6", "EARTHING TERMINAL", "2"},
                {"7", "TANK LIFTING LUG", "4"},
                {"8", "TOP FILTER VALVE", "1"},
                {"9", "BOTTOM SAMPLING VALVE", "1"},
                {"10", "R & D PLATE", "1"},
                {"11", "ROLLER", "4"},
                {"12", "BUCHHOZ RELAY", "1"},
                {"13", "FLANGED RADIATORS", "1"},
                {"14", "TRANSFORMER TANK", "1"},
                {"15", "RADIATOR SHUT OFF VALVE", "1"},
                {"16", "OIL FILLING HOLE (CONSERVATOR)", "1"},
                {"17", "DRAIN VALVE (CONSERVATOR)", "1"},
                {"18", "EXPLOSION VENT PIPE", "1"},
                {"19", "THERMOMETER POCKET", "2"},
                {"20", "BASE CHANNEL", "2"},
                {"21", "STIFFENERS", "4"}
        };
        addDataToTableModel(bomTableModel, bomData);

        clearTableModel(overallDimensionsTableModel);

        OutputData outputData = Model.getSingleton().getOutputData();
        InputData inputData = Model.getSingleton().getLoadedProjectInput();
        Object[][] overallDimensionsData = {
                {inputData.KVA, inputData.LINEVOLTSHV+"/"+inputData.LINEVOLTSLV+"kV", outputData.L_MECHANICAL, outputData.B_MECHANICAL, outputData.H_MECHANICAL}
        };
        addDataToTableModel(overallDimensionsTableModel, overallDimensionsData);

        clearTableModel(weightDetailsTableModel);
        Object[][] weightDetailsData = {
                {"1", "CORE AND WINDING", "Kg", "1749"},
                {"2", "TANK WITH FITTINGS", "Kg", "654"},
                {"3", "OIL", "Kg", "658"},
                {"4", "TOTAL FOR TRANSFORMER", "Kg", "3061"},
                {"5", "VOLUME OF OIL", "Ltr", "784"}
        };
        addDataToTableModel(weightDetailsTableModel, weightDetailsData);
    }

    private void updateTableModelForImage2() {
        clearTableModel(bomTableModel);
        Object[][] bomData = {
                {"1", "HV BUSHING", "3"},
                {"2", "LV BUSHING & NEUTRAL BUSHING", "4"},
                {"3", "JAKING PAD", "4"},
                {"4", "CONSERVATOR ASSLY WITH OIL FILLING CAP", "1"},
                {"5", "SILICA JEL BREATHER", "1"},
                {"6", "EARTHING TERMINAL", "2"},
                {"7", "TANK LIFTING LUG", "4"},
                {"8", "TOP FILTER VALVE", "1"},
                {"9", "BOTTOM SAMPLING VALVE", "1"},
                {"10", "R & D PLATE", "1"},
                {"11", "ROLLER", "4"},
                {"12", "BUCHHOZ RELAY", "1"},
                {"13", "FLANGED RADIATORS", "1"},
                {"14", "TRANSFORMER TANK", "1"},
                {"15", "RADIATOR SHUT OFF VALVE", "1"},
                {"16", "OIL FILLING HOLE (CONSERVATOR)", "1"},
                {"17", "DRAIN VALVE (CONSERVATOR)", "1"},
                {"18", "EXPLOSION VENT PIPE", "1"},
                {"19", "THERMOMETER POCKET", "2"},
                {"20", "BASE CHANNEL", "2"},
                {"21", "STIFFENERS", "4"}

        };
        addDataToTableModel(bomTableModel, bomData);

        clearTableModel(overallDimensionsTableModel);

        OutputData outputData = Model.getSingleton().getOutputData();
        InputData inputData = Model.getSingleton().getLoadedProjectInput();
        Object[][] overallDimensionsData = {
                {inputData.KVA, inputData.LINEVOLTSHV+"/"+inputData.LINEVOLTSLV+"kV", outputData.L_MECHANICAL, outputData.B_MECHANICAL, outputData.H_MECHANICAL}
        };
        addDataToTableModel(overallDimensionsTableModel, overallDimensionsData);

        clearTableModel(weightDetailsTableModel);
        Object[][] weightDetailsData = {
                {"1", "CORE AND WINDING", "Kg", "1749"},
                {"2", "TANK WITH FITTINGS", "Kg", "654"},
                {"3", "OIL", "Kg", "658"},
                {"4", "TOTAL FOR TRANSFORMER", "Kg", "3061"},
                {"5", "VOLUME OF OIL", "Ltr", "784"}
        };
        addDataToTableModel(weightDetailsTableModel, weightDetailsData);
    }

    private void clearTableModel(DefaultTableModel tableModel) {
        tableModel.setRowCount(0);
    }

    private void addDataToTableModel(DefaultTableModel tableModel, Object[][] data) {
        for (Object[] row : data) {
            tableModel.addRow(row);
        }
    }
}