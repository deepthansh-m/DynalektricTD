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
    //private JTable bomTable;
    private final JTable BOM_TABLE = new JTable(22, 3) {
        @Override
        public boolean isCellEditable(int row, int col) {
            return false;
        }
    };
    //private JTable weightDetailsTable;
    private final JTable WEIGHT_DETAILS_TABLE = new JTable(6, 4) {
        @Override
        public boolean isCellEditable(int row, int col) {
            return false;
        }
    };
    //private JTable overallDimensionsTable;
    private final JTable OVERALL_DIMENSION_TABLE = new JTable(2, 5) {
        @Override
        public boolean isCellEditable(int row, int col) {
            return false;
        }
    };
    //private DefaultTableModel bomTableModel;
    //private DefaultTableModel weightDetailsTableModel;
    //private DefaultTableModel overallDimensionsTableModel;
    private JPanel imagePanel;
    private JLabel titleLabel;
    private final JPanel tablePanel = new JPanel();
    private final JPanel bomTablePanel = new JPanel();
    //private final JPanel overallTablePanel = new JPanel();
    //private final JPanel weightTablePanel = new JPanel();

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
            image1 = ImageIO.read(getClass().getResourceAsStream("Oil-cooled Transformer Design.png"));
            image2 = ImageIO.read(getClass().getResourceAsStream("Dry Type Transformer.png"));
            currentImage = image1; // Default to image1
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        titleLabel = new JLabel("DRAWINGS", SwingConstants.CENTER);
        titleLabel.setFont(StyleConstants.HEADING_SUB1);
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel topPanel = new JPanel(new BorderLayout());

        JPanel dropdownPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel dropdownLabel = new JLabel("Select Transformer Type:");
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
                    //updateTableModelForImage1();
                    updateTableForImage1();
                } else if (i==2) {
                    //updateTableModelForImage2();
                    updateTableForImage2();
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

        // BOM table
        BOM_TABLE.setAlignmentX(CENTER_ALIGNMENT);
        BoxLayout layout1 = new BoxLayout(this.bomTablePanel, BoxLayout.Y_AXIS);
        this.bomTablePanel.setLayout(layout1);
        this.bomTablePanel.setBackground(StyleConstants.BACKGROUND);
        this.bomTablePanel.add(BOM_TABLE);
        JScrollPane bomScrollPane = new JScrollPane(bomTablePanel);
        bomScrollPane.setPreferredSize(new Dimension(450, 430));
        bomScrollPane.setBackground(StyleConstants.BACKGROUND);
        bomScrollPane.setForeground(StyleConstants.BACKGROUND);
        bomScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        bomScrollPane.getHorizontalScrollBar().setUnitIncrement(16);
        BOM_TABLE.setShowGrid(true);
        BOM_TABLE.setGridColor(Color.BLACK);
        BOM_TABLE.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        BOM_TABLE.getColumnModel().getColumn(0).setPreferredWidth(50);
        BOM_TABLE.getColumnModel().getColumn(1).setPreferredWidth(300);
        BOM_TABLE.getColumnModel().getColumn(2).setPreferredWidth(75);
        BOM_TABLE.setRowHeight(30);
        BOM_TABLE.setSelectionBackground(StyleConstants.BACKGROUND_PRIMARY);
        BOM_TABLE.setSelectionForeground(StyleConstants.FOREGROUND_PRIMARY);
        for (int i = 0; i < BOM_TABLE.getColumnCount(); i++) {
            BOM_TABLE.getColumnModel().getColumn(i).setCellRenderer(customRenderer);
        }
        BOM_TABLE.setIntercellSpacing(new Dimension(10, 5));

        /*String[] bomColumnNames = {"No", "Description", "Qty"};
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
        bomTable.setShowGrid(true);
        bomTable.setGridColor(Color.BLACK);
        bomTable.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        bomTable.getColumnModel().getColumn(0).setPreferredWidth(25);
        bomTable.getColumnModel().getColumn(1).setPreferredWidth(300);
        bomTable.getColumnModel().getColumn(2).setPreferredWidth(100);
        for (int i = 0; i < bomTable.getColumnCount(); i++) {
            bomTable.getColumnModel().getColumn(i).setCellRenderer(customRenderer);
        }*/

        // Overall dimensions table
        OVERALL_DIMENSION_TABLE.setAlignmentX(CENTER_ALIGNMENT);
        //BoxLayout layout2 = new BoxLayout(this.overallTablePanel, BoxLayout.Y_AXIS);
        //this.overallTablePanel.setLayout(layout2);
        //this.overallTablePanel.setBackground(StyleConstants.BACKGROUND);
        //this.overallTablePanel.add(OVERALL_DIMENSION_TABLE);
        //JScrollPane overallDimensionsScrollPane = new JScrollPane(overallTablePanel);
        //overallDimensionsScrollPane.setPreferredSize(new Dimension(450, 100));
        //overallDimensionsScrollPane.setBackground(StyleConstants.BACKGROUND);
        //overallDimensionsScrollPane.setForeground(StyleConstants.BACKGROUND);
        OVERALL_DIMENSION_TABLE.setPreferredSize(new Dimension(450, 55));
        OVERALL_DIMENSION_TABLE.setShowGrid(true);
        OVERALL_DIMENSION_TABLE.setGridColor(Color.BLACK);
        OVERALL_DIMENSION_TABLE.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        OVERALL_DIMENSION_TABLE.getColumnModel().getColumn(0).setPreferredWidth(50);
        OVERALL_DIMENSION_TABLE.getColumnModel().getColumn(1).setPreferredWidth(150);
        OVERALL_DIMENSION_TABLE.getColumnModel().getColumn(2).setPreferredWidth(70);
        OVERALL_DIMENSION_TABLE.getColumnModel().getColumn(3).setPreferredWidth(70);
        OVERALL_DIMENSION_TABLE.getColumnModel().getColumn(4).setPreferredWidth(70);
        OVERALL_DIMENSION_TABLE.setRowHeight(30);
        OVERALL_DIMENSION_TABLE.setSelectionBackground(StyleConstants.BACKGROUND_PRIMARY);
        OVERALL_DIMENSION_TABLE.setSelectionForeground(StyleConstants.FOREGROUND_PRIMARY);
        for (int i = 0; i < OVERALL_DIMENSION_TABLE.getColumnCount(); i++) {
            OVERALL_DIMENSION_TABLE.getColumnModel().getColumn(i).setCellRenderer(customRenderer);
        }
        OVERALL_DIMENSION_TABLE.setIntercellSpacing(new Dimension(10, 5));

        /*String[] overallDimensionsColumnNames = {"KVA", "V/R", "L", "W", "H"};
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
        overallDimensionsTable.setShowGrid(true);
        overallDimensionsTable.setGridColor(Color.BLACK);
        overallDimensionsTable.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        overallDimensionsTable.getColumnModel().getColumn(0).setPreferredWidth(50);
        overallDimensionsTable.getColumnModel().getColumn(1).setPreferredWidth(200);
        overallDimensionsTable.getColumnModel().getColumn(2).setPreferredWidth(75);
        overallDimensionsTable.getColumnModel().getColumn(3).setPreferredWidth(75);
        overallDimensionsTable.getColumnModel().getColumn(4).setPreferredWidth(75);
        for (int i = 0; i < overallDimensionsTable.getColumnCount(); i++) {
            overallDimensionsTable.getColumnModel().getColumn(i).setCellRenderer(customRenderer);
        } */

        // Weight details table
        WEIGHT_DETAILS_TABLE.setAlignmentX(CENTER_ALIGNMENT);
        //BoxLayout layout3 = new BoxLayout(this.weightTablePanel, BoxLayout.Y_AXIS);
        //this.weightTablePanel.setLayout(layout3);
        //this.weightTablePanel.setBackground(StyleConstants.BACKGROUND);
        //this.weightTablePanel.add(WEIGHT_DETAILS_TABLE);
        //JScrollPane weightDetailsScrollPane = new JScrollPane(weightTablePanel);
        //weightDetailsScrollPane.setPreferredSize(new Dimension(450, 430));
        //weightDetailsScrollPane.setBackground(StyleConstants.BACKGROUND);
        //weightDetailsScrollPane.setForeground(StyleConstants.BACKGROUND);
        WEIGHT_DETAILS_TABLE.setPreferredSize(new Dimension(450, 175));
        WEIGHT_DETAILS_TABLE.setShowGrid(true);
        WEIGHT_DETAILS_TABLE.setGridColor(Color.BLACK);
        WEIGHT_DETAILS_TABLE.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        WEIGHT_DETAILS_TABLE.getColumnModel().getColumn(0).setPreferredWidth(50);
        WEIGHT_DETAILS_TABLE.getColumnModel().getColumn(1).setPreferredWidth(275);
        WEIGHT_DETAILS_TABLE.getColumnModel().getColumn(2).setPreferredWidth(50);
        WEIGHT_DETAILS_TABLE.getColumnModel().getColumn(3).setPreferredWidth(50);
        WEIGHT_DETAILS_TABLE.setRowHeight(30);
        WEIGHT_DETAILS_TABLE.setSelectionBackground(StyleConstants.BACKGROUND_PRIMARY);
        WEIGHT_DETAILS_TABLE.setSelectionForeground(StyleConstants.FOREGROUND_PRIMARY);
        for (int i = 0; i < WEIGHT_DETAILS_TABLE.getColumnCount(); i++) {
            WEIGHT_DETAILS_TABLE.getColumnModel().getColumn(i).setCellRenderer(customRenderer);
        }
        WEIGHT_DETAILS_TABLE.setIntercellSpacing(new Dimension(10, 5));

        /*String[] weightDetailsColumnNames = {"No", "Description", "Unit", "Value"};
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
        weightDetailsTable.setShowGrid(true);
        weightDetailsTable.setGridColor(Color.BLACK);
        weightDetailsTable.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        weightDetailsTable.getColumnModel().getColumn(0).setPreferredWidth(25);
        weightDetailsTable.getColumnModel().getColumn(1).setPreferredWidth(300);
        weightDetailsTable.getColumnModel().getColumn(2).setPreferredWidth(50);
        weightDetailsTable.getColumnModel().getColumn(3).setPreferredWidth(50);
        for (int i = 0; i < weightDetailsTable.getColumnCount(); i++) {
            weightDetailsTable.getColumnModel().getColumn(i).setCellRenderer(customRenderer);
        } */

        // Add scroll panes
        /*bomScrollPane = new JScrollPane(bomTable);
        bomScrollPane.setPreferredSize(new Dimension(425, 430)); // Adjusted for height
        overallDimensionsScrollPane = new JScrollPane(overallDimensionsTable);
        overallDimensionsScrollPane.setPreferredSize(new Dimension(425, 50)); // Adjusted for height
        weightDetailsScrollPane = new JScrollPane(weightDetailsTable);
        weightDetailsScrollPane.setPreferredSize(new Dimension(425, 430)); // Adjusted for height */

        // Create headings
        JLabel bomTableHeading = new JLabel("Bill of Material", SwingConstants.CENTER);
        bomTableHeading.setFont(StyleConstants.HEADING_SUB2);
        JLabel overallDimensionsTableHeading = new JLabel("Overall Dimensions", SwingConstants.CENTER);
        overallDimensionsTableHeading.setFont(StyleConstants.HEADING_SUB2);
        JLabel weightDetailsTableHeading = new JLabel("Weight of Materials", SwingConstants.CENTER);
        weightDetailsTableHeading.setFont(StyleConstants.HEADING_SUB2);

        BoxLayout layout = new BoxLayout(this.tablePanel, BoxLayout.Y_AXIS);
        this.tablePanel.setLayout(layout);
        this.tablePanel.add(bomTableHeading);
        this.tablePanel.add(bomScrollPane);
        this.tablePanel.add(Box.createVerticalStrut(20));
        this.tablePanel.add(overallDimensionsTableHeading);
        this.tablePanel.add(OVERALL_DIMENSION_TABLE);
        this.tablePanel.add(Box.createVerticalStrut(20));
        this.tablePanel.add(weightDetailsTableHeading);
        this.tablePanel.add(WEIGHT_DETAILS_TABLE);
        this.tablePanel.setBackground(StyleConstants.BACKGROUND);

        // Combine tables and headings in a vertical box layout
        /*Box tableBox = Box.createVerticalBox();
        tableBox.add(bomTableHeading);
        tableBox.add(bomScrollPane);
        tableBox.add(overallDimensionsTableHeading);
        tableBox.add(overallDimensionsScrollPane);
        tableBox.add(weightDetailsTableHeading);
        tableBox.add(weightDetailsScrollPane); */

        mainPanel.add(tablePanel, BorderLayout.EAST);

    }

    private JPanel initializeNavigationPanel() {
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
        return 7;
    }

    private void updateTableForImage1() {
        OutputData outputData = Model.getSingleton().getOutputData();
        InputData inputData = Model.getSingleton().getLoadedProjectInput();

        clearTable(BOM_TABLE);

        BOM_TABLE.setValueAt("No", 0, 0);
        BOM_TABLE.setValueAt("Description", 0, 1);
        BOM_TABLE.setValueAt( "Qty", 0, 2);

        BOM_TABLE.setValueAt("1.", 1, 0);
        BOM_TABLE.setValueAt("2.", 2, 0);
        BOM_TABLE.setValueAt("3.", 3, 0);
        BOM_TABLE.setValueAt("4.", 4, 0);
        BOM_TABLE.setValueAt("5.", 5, 0);
        BOM_TABLE.setValueAt("6.", 6, 0);
        BOM_TABLE.setValueAt("7.", 7, 0);
        BOM_TABLE.setValueAt("8.", 8, 0);
        BOM_TABLE.setValueAt("9.", 9, 0);
        BOM_TABLE.setValueAt("10.", 10, 0);
        BOM_TABLE.setValueAt("11.", 11, 0);
        BOM_TABLE.setValueAt("12.", 12, 0);
        BOM_TABLE.setValueAt("13.", 13, 0);
        BOM_TABLE.setValueAt("14.", 14, 0);
        BOM_TABLE.setValueAt("15.", 15, 0);
        BOM_TABLE.setValueAt("16.", 16, 0);
        BOM_TABLE.setValueAt("17.", 17, 0);
        BOM_TABLE.setValueAt("18.", 18, 0);
        BOM_TABLE.setValueAt("19.", 19, 0);
        BOM_TABLE.setValueAt("20.", 20, 0);
        BOM_TABLE.setValueAt("21.", 21, 0);

        BOM_TABLE.setValueAt("HV BUSHING",  1, 1);
        BOM_TABLE.setValueAt("3", 1, 2);

        BOM_TABLE.setValueAt("LV BUSHING & NEUTRAL BUSHING",  2, 1);
        BOM_TABLE.setValueAt("4", 2, 2);

        BOM_TABLE.setValueAt("JAKING PAD",  3, 1);
        BOM_TABLE.setValueAt("4", 3, 2);

        BOM_TABLE.setValueAt("CONSERVATOR ASSLY WITH OIL FILLING CAP",  4, 1);
        BOM_TABLE.setValueAt("1", 4, 2);

        BOM_TABLE.setValueAt("SILICA JEL BREATHER",  5, 1);
        BOM_TABLE.setValueAt("1", 5, 2);

        BOM_TABLE.setValueAt("EARTHING TERMINAL", 6, 1);
        BOM_TABLE.setValueAt("2", 6, 2);

        BOM_TABLE.setValueAt("TANK LIFTING LUG",  7, 1);
        BOM_TABLE.setValueAt("4", 7, 2);

        BOM_TABLE.setValueAt("TOP FILTER VALVE",  8, 1);
        BOM_TABLE.setValueAt("1", 8, 2);

        BOM_TABLE.setValueAt("BOTTOM SAMPLING VALVE",  9, 1);
        BOM_TABLE.setValueAt("1", 9, 2);

        BOM_TABLE.setValueAt("R & D PLATE",  10, 1);
        BOM_TABLE.setValueAt("1", 10, 2);

        BOM_TABLE.setValueAt("ROLLER",  11, 1);
        BOM_TABLE.setValueAt("4", 11, 2);

        BOM_TABLE.setValueAt("BUCHHOZ RELAY",  12, 1);
        BOM_TABLE.setValueAt("1", 12, 2);

        BOM_TABLE.setValueAt("FLANGED RADIATORS",  13, 1);
        BOM_TABLE.setValueAt("1", 13, 2);

        BOM_TABLE.setValueAt("TRANSFORMER TANK",  14, 1);
        BOM_TABLE.setValueAt("1", 14, 2);

        BOM_TABLE.setValueAt("RADIATOR SHUT OFF VALVE",  15, 1);
        BOM_TABLE.setValueAt("1", 15, 2);

        BOM_TABLE.setValueAt("OIL FILLING HOLE (CONSERVATOR)",  16, 1);
        BOM_TABLE.setValueAt("1", 16, 2);

        BOM_TABLE.setValueAt("DRAIN VALVE (CONSERVATOR)",  17, 1);
        BOM_TABLE.setValueAt("1", 17, 2);

        BOM_TABLE.setValueAt("EXPLOSION VENT PIPE",  18, 1);
        BOM_TABLE.setValueAt("1", 18, 2);

        BOM_TABLE.setValueAt("THERMOMETER POCKET",  19, 1);
        BOM_TABLE.setValueAt("2", 19, 2);

        BOM_TABLE.setValueAt("BASE CHANNEL",  20, 1);
        BOM_TABLE.setValueAt("2", 20, 2);

        BOM_TABLE.setValueAt("STIFFENERS",  21, 1);
        BOM_TABLE.setValueAt("4", 21, 2);

        clearTable(OVERALL_DIMENSION_TABLE);

        OVERALL_DIMENSION_TABLE.setValueAt("KVA", 0,0);
        OVERALL_DIMENSION_TABLE.setValueAt("V/R", 0,1);
        OVERALL_DIMENSION_TABLE.setValueAt("L", 0,2);
        OVERALL_DIMENSION_TABLE.setValueAt("W", 0,3);
        OVERALL_DIMENSION_TABLE.setValueAt("H",0,4);

        OVERALL_DIMENSION_TABLE.setValueAt(inputData.KVA,1,0);
        OVERALL_DIMENSION_TABLE.setValueAt(inputData.LINEVOLTSHV+"/"+inputData.LINEVOLTSLV+" kV",1,1);
        OVERALL_DIMENSION_TABLE.setValueAt(outputData.L_MECHANICAL,1,2);
        OVERALL_DIMENSION_TABLE.setValueAt(outputData.B_MECHANICAL,1,3);
        OVERALL_DIMENSION_TABLE.setValueAt(outputData.H_MECHANICAL,1,4);

        clearTable(WEIGHT_DETAILS_TABLE);

        WEIGHT_DETAILS_TABLE.setValueAt("No", 0,0);
        WEIGHT_DETAILS_TABLE.setValueAt("Description", 0,1);
        WEIGHT_DETAILS_TABLE.setValueAt("Unit", 0,2);
        WEIGHT_DETAILS_TABLE.setValueAt("Value",0,3);

        WEIGHT_DETAILS_TABLE.setValueAt("1", 1, 0);
        WEIGHT_DETAILS_TABLE.setValueAt("2", 2, 0);
        WEIGHT_DETAILS_TABLE.setValueAt("3", 3, 0);
        WEIGHT_DETAILS_TABLE.setValueAt("4", 4, 0);
        WEIGHT_DETAILS_TABLE.setValueAt("5", 5, 0);

        WEIGHT_DETAILS_TABLE.setValueAt("CORE AND WINDING", 1,1);
        WEIGHT_DETAILS_TABLE.setValueAt("Kg", 1,2);
        WEIGHT_DETAILS_TABLE.setValueAt("1749",1,3);

        WEIGHT_DETAILS_TABLE.setValueAt("TANK WITH FITTINGS", 2,1);
        WEIGHT_DETAILS_TABLE.setValueAt("Kg",  2, 2);
        WEIGHT_DETAILS_TABLE.setValueAt("654", 2, 3);

        WEIGHT_DETAILS_TABLE.setValueAt("OIL", 3,1);
        WEIGHT_DETAILS_TABLE.setValueAt("Kg",  3, 2);
        WEIGHT_DETAILS_TABLE.setValueAt("658", 3, 3);

        WEIGHT_DETAILS_TABLE.setValueAt("TOTAL FOR TRANSFORMER",  4, 1);
        WEIGHT_DETAILS_TABLE.setValueAt("Kg",  4, 2);
        WEIGHT_DETAILS_TABLE.setValueAt("3061", 4, 3);

        WEIGHT_DETAILS_TABLE.setValueAt("VOLUME OF OIL",   5, 1);
        WEIGHT_DETAILS_TABLE.setValueAt("Ltr",   5, 2);
        WEIGHT_DETAILS_TABLE.setValueAt("784", 5, 3);
    }

    private void updateTableForImage2() {
        OutputData outputData = Model.getSingleton().getOutputData();
        InputData inputData = Model.getSingleton().getLoadedProjectInput();

        clearTable(BOM_TABLE);

        BOM_TABLE.setValueAt("No", 0, 0);
        BOM_TABLE.setValueAt("Description", 0, 1);
        BOM_TABLE.setValueAt( "Qty", 0, 2);

        BOM_TABLE.setValueAt("1.", 1, 0);
        BOM_TABLE.setValueAt("2.", 2, 0);
        BOM_TABLE.setValueAt("3.", 3, 0);
        BOM_TABLE.setValueAt("4.", 4, 0);
        BOM_TABLE.setValueAt("5.", 5, 0);
        BOM_TABLE.setValueAt("6.", 6, 0);
        BOM_TABLE.setValueAt("7.", 7, 0);

        BOM_TABLE.setValueAt("HV BUSHING",  1, 1);
        BOM_TABLE.setValueAt("3", 1, 2);

        BOM_TABLE.setValueAt("LV BUSHING & NEUTRAL BUSHING",  2, 1);
        BOM_TABLE.setValueAt("4", 2, 2);

        BOM_TABLE.setValueAt("JAKING PAD",  3, 1);
        BOM_TABLE.setValueAt("4", 3, 2);

        BOM_TABLE.setValueAt("CONSERVATOR ASSLY WITH OIL FILLING CAP",  4, 1);
        BOM_TABLE.setValueAt("1", 4, 2);

        BOM_TABLE.setValueAt("SILICA JEL BREATHER",  5, 1);
        BOM_TABLE.setValueAt("1", 5, 2);

        BOM_TABLE.setValueAt("EARTHING TERMINAL", 6, 1);
        BOM_TABLE.setValueAt("2", 6, 2);

        BOM_TABLE.setValueAt("TANK LIFTING LUG",  7, 1);
        BOM_TABLE.setValueAt("4", 7, 2);

        clearTable(OVERALL_DIMENSION_TABLE);

        OVERALL_DIMENSION_TABLE.setValueAt("KVA", 0,0);
        OVERALL_DIMENSION_TABLE.setValueAt("V/R", 0,1);
        OVERALL_DIMENSION_TABLE.setValueAt("L", 0,2);
        OVERALL_DIMENSION_TABLE.setValueAt("W", 0,3);
        OVERALL_DIMENSION_TABLE.setValueAt("H",0,4);

        OVERALL_DIMENSION_TABLE.setValueAt(inputData.KVA,1,0);
        OVERALL_DIMENSION_TABLE.setValueAt(inputData.LINEVOLTSHV+"/"+inputData.LINEVOLTSLV+" kV",1,1);
        OVERALL_DIMENSION_TABLE.setValueAt(outputData.L_MECHANICAL,1,2);
        OVERALL_DIMENSION_TABLE.setValueAt(outputData.B_MECHANICAL,1,3);
        OVERALL_DIMENSION_TABLE.setValueAt(outputData.H_MECHANICAL,1,4);

        clearTable(WEIGHT_DETAILS_TABLE);

        WEIGHT_DETAILS_TABLE.setValueAt("No", 0,0);
        WEIGHT_DETAILS_TABLE.setValueAt("Description", 0,1);
        WEIGHT_DETAILS_TABLE.setValueAt("Unit", 0,2);
        WEIGHT_DETAILS_TABLE.setValueAt("Value",0,3);

        WEIGHT_DETAILS_TABLE.setValueAt("1", 1, 0);
        WEIGHT_DETAILS_TABLE.setValueAt("2", 2, 0);
        WEIGHT_DETAILS_TABLE.setValueAt("3", 3, 0);
        WEIGHT_DETAILS_TABLE.setValueAt("4", 4, 0);
        WEIGHT_DETAILS_TABLE.setValueAt("5", 5, 0);

        WEIGHT_DETAILS_TABLE.setValueAt("CORE AND WINDING", 1,1);
        WEIGHT_DETAILS_TABLE.setValueAt("Kg", 1,2);
        WEIGHT_DETAILS_TABLE.setValueAt("1749",1,3);

        WEIGHT_DETAILS_TABLE.setValueAt("TANK WITH FITTINGS", 2,1);
        WEIGHT_DETAILS_TABLE.setValueAt("Kg",  2, 2);
        WEIGHT_DETAILS_TABLE.setValueAt("654", 2, 3);

        WEIGHT_DETAILS_TABLE.setValueAt("OIL", 3,1);
        WEIGHT_DETAILS_TABLE.setValueAt("Kg",  3, 2);
        WEIGHT_DETAILS_TABLE.setValueAt("658", 3, 3);

        WEIGHT_DETAILS_TABLE.setValueAt("TOTAL FOR TRANSFORMER",  4, 1);
        WEIGHT_DETAILS_TABLE.setValueAt("Kg",  4, 2);
        WEIGHT_DETAILS_TABLE.setValueAt("3061", 4, 3);

        WEIGHT_DETAILS_TABLE.setValueAt("VOLUME OF OIL",   5, 1);
        WEIGHT_DETAILS_TABLE.setValueAt("Ltr",   5, 2);
        WEIGHT_DETAILS_TABLE.setValueAt("784", 5, 3);
    }

    /*private void updateTableModelForImage1() {
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
     */
    private void clearTable(JTable table) {
        for (int i = 0; i < table.getRowCount(); i++) {
            for (int j = 0; j < table.getColumnCount(); j++) {
                table.setValueAt(" ",i,j );
            }
        }
    }

    /*private void addDataToTableModel(DefaultTableModel tableModel, Object[][] data) {
        for (Object[] row : data) {
            tableModel.addRow(row);
        }
    }
     */
}