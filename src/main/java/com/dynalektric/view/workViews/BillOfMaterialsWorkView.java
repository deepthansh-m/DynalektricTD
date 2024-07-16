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

import static java.lang.Math.round;

public class BillOfMaterialsWorkView extends AbstractWorkView{
    private final Control mainController = new Control();
    public final static String VIEW_NAME = "BillOfMaterialsWorkView";
    private final JPanel mainPanel = new JPanel();
    private final JPanel BOMPanel = new JPanel();
    private final JTable BOM_TABLE = new JTable(12  , 10){
        @Override
        public boolean isCellEditable(int row , int col){
            return false;
        }
    };

    public BillOfMaterialsWorkView(Model model) {
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
            this.setWindingPanelValues();
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
        return 3;
    }

    private void initializeMainPanel(){
        this.mainPanel.setLayout(new GridLayout());
        this.initializeWindingPanel();
        this.mainPanel.add(BOMPanel);
    }
    private void initializeWindingPanel(){
        BoxLayout layout = new BoxLayout(this.BOMPanel , BoxLayout.Y_AXIS);
        this.BOMPanel.setLayout(layout);
        if(model.getLoadedProject() != null)
            setWindingPanelValues();
        JLabel Winding_Table_Heading = new JLabel("Transformer Design Wizard [Winding]");
        Winding_Table_Heading.setFont(StyleConstants.HEADING_SUB1);
        Winding_Table_Heading.setAlignmentX(CENTER_ALIGNMENT);
        BOM_TABLE.setAlignmentX(CENTER_ALIGNMENT);
        this.BOMPanel.add(Winding_Table_Heading);
        this.BOMPanel.add(Box.createVerticalStrut(10));
        this.BOMPanel.add(BOM_TABLE);
        JScrollPane scrollPane = new JScrollPane(BOMPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.getHorizontalScrollBar().setUnitIncrement(16);
        this.BOMPanel.setBackground(StyleConstants.BACKGROUND);
        this.BOMPanel.setBorder(BorderFactory.createEmptyBorder(20, 40 , 20 ,40));
        BOM_TABLE.setShowGrid(true);
        BOM_TABLE.setGridColor(Color.BLACK);
        BOM_TABLE.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        BOM_TABLE.getColumnModel().getColumn(0).setPreferredWidth(25);
        BOM_TABLE.getColumnModel().getColumn(1).setPreferredWidth(50);
        BOM_TABLE.getColumnModel().getColumn(2).setPreferredWidth(100);
        BOM_TABLE.getColumnModel().getColumn(3).setPreferredWidth(150);
        BOM_TABLE.getColumnModel().getColumn(4).setPreferredWidth(150);
        BOM_TABLE.getColumnModel().getColumn(5).setPreferredWidth(150);
        BOM_TABLE.getColumnModel().getColumn(6).setPreferredWidth(25);
        BOM_TABLE.getColumnModel().getColumn(7).setPreferredWidth(100);
        BOM_TABLE.getColumnModel().getColumn(8).setPreferredWidth(100);
        BOM_TABLE.getColumnModel().getColumn(9).setPreferredWidth(100);
        BOM_TABLE.setRowHeight(30);
        BOM_TABLE.setSelectionBackground(StyleConstants.BACKGROUND_PRIMARY);
        BOM_TABLE.setSelectionForeground(StyleConstants.FOREGROUND_PRIMARY);
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
        for (int i = 0; i < BOM_TABLE.getColumnCount(); i++) {
            BOM_TABLE.getColumnModel().getColumn(i).setCellRenderer(customRenderer);
        }
        BOM_TABLE.setIntercellSpacing(new Dimension(10, 5));
    }

    private JPanel initializeNavigationPanel(){
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
                View.getSingleton().setView(DimensionsWorkView.VIEW_NAME);
            }
        });
        nextBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                View.getSingleton().setView(DrawingWorkView.VIEW_NAME);
//                System.out.println(coreWeightTable.getModel().getValueAt(0 , 1));
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
    private void setWindingPanelValues(){
        OutputData outputData = Model.getSingleton().getOutputData();
        InputData inputData = Model.getSingleton().getLoadedProjectInput();

        BOM_TABLE.setValueAt("Sr.NO" , 0 , 0);
        BOM_TABLE.setValueAt("Item Code",0,1);
        BOM_TABLE.setValueAt("Material" , 0 , 2);
        BOM_TABLE.setValueAt("Description of Item" , 0 , 3);
        BOM_TABLE.setValueAt("Specification(Size)", 0, 4);
        BOM_TABLE.setValueAt("Quantity" , 0 , 5);
        BOM_TABLE.setValueAt("Unit" , 0 , 6);
        BOM_TABLE.setValueAt("Unit Cost RS" , 0 , 7);
        BOM_TABLE.setValueAt("Total Cost RS" , 0 , 8);
        BOM_TABLE.setValueAt("Remark" , 0 , 9);

        BOM_TABLE.setValueAt("1", 1, 0);
        BOM_TABLE.setValueAt("2", 2, 0);
        BOM_TABLE.setValueAt("3", 3, 0);
        BOM_TABLE.setValueAt("4", 4, 0);
        BOM_TABLE.setValueAt("5", 5, 0);
        BOM_TABLE.setValueAt("6", 6, 0);
        BOM_TABLE.setValueAt("7", 7, 0);
        BOM_TABLE.setValueAt("8", 8, 0);
        BOM_TABLE.setValueAt("9", 9, 0);
        BOM_TABLE.setValueAt("10", 10, 0);
        BOM_TABLE.setValueAt("11", 11, 0);

        BOM_TABLE.setValueAt("", 1, 1);
        BOM_TABLE.setValueAt("", 2, 1);
        BOM_TABLE.setValueAt("", 3, 1);
        BOM_TABLE.setValueAt("", 4, 1);
        BOM_TABLE.setValueAt("", 5, 1);
        BOM_TABLE.setValueAt("", 6, 1);
        BOM_TABLE.setValueAt("", 7, 1);
        BOM_TABLE.setValueAt("", 8, 1);
        BOM_TABLE.setValueAt("", 9, 1);
        BOM_TABLE.setValueAt("", 10, 1);
        BOM_TABLE.setValueAt("", 11, 1);

        BOM_TABLE.setValueAt("Core" , 1 , 2);
        BOM_TABLE.setValueAt("Core-Steel + SS" , 2 , 2);
        BOM_TABLE.setValueAt(inputData.CONDUCTOR , 3 , 2);
        BOM_TABLE.setValueAt("Leads" , 4 , 2);
        BOM_TABLE.setValueAt("Insulation-FG" , 5 , 2);
        BOM_TABLE.setValueAt("Connection-FG" , 6 , 2);
        BOM_TABLE.setValueAt("Insulation-CL-H" , 7 , 2);
        BOM_TABLE.setValueAt("RESIN-VT50" , 8 , 2);
        BOM_TABLE.setValueAt("MISC" , 9 , 2);
        BOM_TABLE.setValueAt("CRCA ENCL" , 10 , 2);
        BOM_TABLE.setValueAt("Total Mass" , 11 , 2);

        BOM_TABLE.setValueAt("", 1, 3);
        BOM_TABLE.setValueAt("", 2, 3);
        BOM_TABLE.setValueAt("", 3, 3);
        BOM_TABLE.setValueAt("", 4, 3);
        BOM_TABLE.setValueAt("", 5, 3);
        BOM_TABLE.setValueAt("", 6, 3);
        BOM_TABLE.setValueAt("", 7, 3);
        BOM_TABLE.setValueAt("", 8, 3);
        BOM_TABLE.setValueAt("", 9, 3);
        BOM_TABLE.setValueAt("", 10, 3);
        BOM_TABLE.setValueAt("", 11, 3);

        BOM_TABLE.setValueAt("", 1, 4);
        BOM_TABLE.setValueAt("", 2, 4);
        BOM_TABLE.setValueAt("", 3, 4);
        BOM_TABLE.setValueAt("", 4, 4);
        BOM_TABLE.setValueAt("", 5, 4);
        BOM_TABLE.setValueAt("", 6, 4);
        BOM_TABLE.setValueAt("", 7, 4);
        BOM_TABLE.setValueAt("", 8, 4);
        BOM_TABLE.setValueAt("", 9, 4);
        BOM_TABLE.setValueAt("", 10, 4);
        BOM_TABLE.setValueAt("", 11, 4);

        // setting values
        BOM_TABLE.setValueAt(Math.round(outputData.BOM_CORE), 1, 5);
        BOM_TABLE.setValueAt(outputData.BOM_CORE_STEEL, 2, 5);
        BOM_TABLE.setValueAt(outputData.BOM_CONDUCTOR_WT, 3, 5);
        BOM_TABLE.setValueAt(outputData.BOM_LEADS, 4, 5);
        BOM_TABLE.setValueAt(outputData.BOM_INSULATION_FG, 5, 5);
        BOM_TABLE.setValueAt(outputData.BOM_CONNECTION_FG, 6, 5);
        BOM_TABLE.setValueAt(outputData.BOM_INSULATION_CL_H, 7, 5);
        BOM_TABLE.setValueAt(outputData.BOM_RESIN_VT50, 8, 5);
        BOM_TABLE.setValueAt(outputData.BOM_MISC, 9, 5);
        BOM_TABLE.setValueAt(inputData.BOM_CRCA_ENCL, 10, 5);
        BOM_TABLE.setValueAt(outputData.BOM_TOTAL_MASS, 11, 5);

        BOM_TABLE.setValueAt("KG", 1, 6);
        BOM_TABLE.setValueAt("KG", 2, 6);
        BOM_TABLE.setValueAt("KG", 3, 6);
        BOM_TABLE.setValueAt("KG", 4, 6);
        BOM_TABLE.setValueAt("KG", 5, 6);
        BOM_TABLE.setValueAt("KG", 6, 6);
        BOM_TABLE.setValueAt("KG", 7, 6);
        BOM_TABLE.setValueAt("KG", 8, 6);
        BOM_TABLE.setValueAt("KG", 9, 6);
        BOM_TABLE.setValueAt("KG", 10, 6);
        BOM_TABLE.setValueAt("KG", 11, 6);

        BOM_TABLE.setValueAt("", 1, 7);
        BOM_TABLE.setValueAt("", 2, 7);
        BOM_TABLE.setValueAt("", 3, 7);
        BOM_TABLE.setValueAt("", 4, 7);
        BOM_TABLE.setValueAt("", 5, 7);
        BOM_TABLE.setValueAt("", 6, 7);
        BOM_TABLE.setValueAt("", 7, 7);
        BOM_TABLE.setValueAt("", 8, 7);
        BOM_TABLE.setValueAt("", 9, 7);
        BOM_TABLE.setValueAt("", 10, 7);
        BOM_TABLE.setValueAt("", 11, 7);

        BOM_TABLE.setValueAt("", 1, 8);
        BOM_TABLE.setValueAt("", 2, 8);
        BOM_TABLE.setValueAt("", 3, 8);
        BOM_TABLE.setValueAt("", 4, 8);
        BOM_TABLE.setValueAt("", 5, 8);
        BOM_TABLE.setValueAt("", 6, 8);
        BOM_TABLE.setValueAt("", 7, 8);
        BOM_TABLE.setValueAt("", 8, 8);
        BOM_TABLE.setValueAt("", 9, 8);
        BOM_TABLE.setValueAt("", 10, 8);
        BOM_TABLE.setValueAt("", 11, 8);
    }
}