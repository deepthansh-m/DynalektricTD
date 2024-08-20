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

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Objects;

public class WindingWorkView extends AbstractWorkView{
        private final Control mainController = new Control();
        public final static String VIEW_NAME = "WindingWorkView";
        private final JPanel mainPanel = new JPanel();
        private final JPanel WindingPanel = new JPanel();
        private final JPanel tablePanel = new JPanel();
        private final JTable WINDING_TABLE = new JTable(20  , 4){
            @Override
            public boolean isCellEditable(int row , int col){
                return false;
            }
        };

        public WindingWorkView(Model model) {
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
            this.mainPanel.add(WindingPanel);
        }
        private void initializeWindingPanel(){
            BoxLayout layout = new BoxLayout(this.WindingPanel , BoxLayout.Y_AXIS);
            this.WindingPanel.setLayout(layout);
            BoxLayout layout1 = new BoxLayout(this.tablePanel , BoxLayout.Y_AXIS);
            this.tablePanel.setLayout(layout1);
            if(model.getLoadedProject() != null)
                setWindingPanelValues();
            JLabel Winding_Table_Heading = new JLabel("Transformer Design Wizard [Winding]");
            Winding_Table_Heading.setFont(StyleConstants.HEADING_SUB1);
            Winding_Table_Heading.setAlignmentX(CENTER_ALIGNMENT);
            WINDING_TABLE.setAlignmentX(CENTER_ALIGNMENT);
            this.WindingPanel.add(Winding_Table_Heading);
            this.WindingPanel.add(Box.createVerticalStrut(10));
            this.tablePanel.add(WINDING_TABLE);
            this.tablePanel.setBackground(StyleConstants.BACKGROUND);
            JScrollPane scrollPane = new JScrollPane(tablePanel);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            scrollPane.getVerticalScrollBar().setUnitIncrement(16);
            scrollPane.getHorizontalScrollBar().setUnitIncrement(16);
            this.WindingPanel.add(scrollPane);
            this.WindingPanel.setBackground(StyleConstants.BACKGROUND);
            this.WindingPanel.setBorder(BorderFactory.createEmptyBorder(20, 325 , 70 ,325));
            WINDING_TABLE.setShowGrid(true);
            WINDING_TABLE.setGridColor(Color.BLACK);
            WINDING_TABLE.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            WINDING_TABLE.getColumnModel().getColumn(0).setPreferredWidth(300);
            WINDING_TABLE.getColumnModel().getColumn(1).setPreferredWidth(25);
            WINDING_TABLE.getColumnModel().getColumn(2).setPreferredWidth(100);
            WINDING_TABLE.getColumnModel().getColumn(3).setPreferredWidth(100);
            WINDING_TABLE.setRowHeight(30);
            WINDING_TABLE.setSelectionBackground(StyleConstants.BACKGROUND_PRIMARY);
            WINDING_TABLE.setSelectionForeground(StyleConstants.FOREGROUND_PRIMARY);
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
            for (int i = 0; i < WINDING_TABLE.getColumnCount(); i++) {
                WINDING_TABLE.getColumnModel().getColumn(i).setCellRenderer(customRenderer);
            }
            WINDING_TABLE.setIntercellSpacing(new Dimension(10, 5));
        }

        private JPanel initializeNavigationPanel(){
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
                    View.getSingleton().setView(InputWorkView.VIEW_NAME);
                }
            });
            nextBtn.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    View.getSingleton().setView(CoreWorkView.VIEW_NAME);
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

            WINDING_TABLE.setValueAt("Parameter" , 0 , 0);
            WINDING_TABLE.setValueAt("UNITS",0,1);
            WINDING_TABLE.setValueAt("LV" , 0 , 3);
            WINDING_TABLE.setValueAt("HV" , 0 , 2);
            WINDING_TABLE.setValueAt("Type of Winding", 1, 0);
            WINDING_TABLE.setValueAt("Rated Voltage" , 2 , 0);
            WINDING_TABLE.setValueAt("Rated Current" , 3 , 0);
            WINDING_TABLE.setValueAt("Cross Section sqmm" , 4 , 0);
            WINDING_TABLE.setValueAt("Current density" , 5 , 0);
            WINDING_TABLE.setValueAt("Turns/Limb" , 6 , 0);
            WINDING_TABLE.setValueAt("Turns/Layer" , 7 , 0);
            WINDING_TABLE.setValueAt("wdg lg-imp calc" , 8 , 0);
            WINDING_TABLE.setValueAt("Wind Length(AXL)" , 9 , 0);
            WINDING_TABLE.setValueAt("Limb length" , 10 , 0);
            WINDING_TABLE.setValueAt("Wind-radial depth"  , 11 , 0);
            WINDING_TABLE.setValueAt("Turn length" , 12 , 0);
            WINDING_TABLE.setValueAt("Wire Length" , 13 , 0);
            WINDING_TABLE.setValueAt("Resistance (ohms)" , 14 , 0);
            WINDING_TABLE.setValueAt("Stray Loss (%)" , 15 , 0);
            WINDING_TABLE.setValueAt("Load Loss (Watts)" , 16 , 0);
            WINDING_TABLE.setValueAt("S.a-m(wdg)" , 17 , 0);
            WINDING_TABLE.setValueAt("W/m^2" , 18 , 0);
            WINDING_TABLE.setValueAt("wdg temp rise " , 19 , 0);

            // setting values
            WINDING_TABLE.setValueAt(inputData.WINDINGTYPELV, 1, 3);
            WINDING_TABLE.setValueAt(inputData.WINDINGTYPEHV, 1, 2);
            WINDING_TABLE.setValueAt("", 1, 1);

            WINDING_TABLE.setValueAt(outputData.VPH_LV, 2, 3);
            WINDING_TABLE.setValueAt(outputData.VPH_HV, 2, 2);
            WINDING_TABLE.setValueAt("V", 2, 1);

            WINDING_TABLE.setValueAt(outputData.IPH_LV, 3, 3);
            WINDING_TABLE.setValueAt(outputData.IPH_HV, 3, 2);
            WINDING_TABLE.setValueAt("A", 3, 1);

            WINDING_TABLE.setValueAt(outputData.CROSS_SECTION_LV, 4, 3);
            WINDING_TABLE.setValueAt(outputData.CROSS_SECTION_HV, 4, 2);
            WINDING_TABLE.setValueAt("mm²", 4, 1);

            WINDING_TABLE.setValueAt(outputData.CURRENT_DENSITY_LV, 5, 3);
            WINDING_TABLE.setValueAt(outputData.CURRENT_DENSITY_HV, 5, 2);
            WINDING_TABLE.setValueAt("A/mm²", 5, 1);

            WINDING_TABLE.setValueAt(outputData.TURN_LIMB_LV, 6, 3);
            WINDING_TABLE.setValueAt(outputData.TURN_LIMB_HV, 6, 2);
            WINDING_TABLE.setValueAt("Nos", 6, 1);

            WINDING_TABLE.setValueAt(outputData.TURN_LAYER_LV, 7, 3);
            WINDING_TABLE.setValueAt(outputData.TURN_LAYER_HV, 7, 2);
            WINDING_TABLE.setValueAt("Nos", 7, 1);

            WINDING_TABLE.setValueAt(outputData.WDG_LG_IMP_CALCU_LV, 8, 3);
            WINDING_TABLE.setValueAt(outputData.WDG_LG_IMP_CALCU_HV, 8, 2);
            WINDING_TABLE.setValueAt("H", 8, 1);

            WINDING_TABLE.setValueAt(outputData.WIND_LENGTH_LV, 9, 3);
            WINDING_TABLE.setValueAt(outputData.WIND_LENGTH_HV, 9, 2);
            WINDING_TABLE.setValueAt("mm", 9, 1);

            WINDING_TABLE.setValueAt(outputData.LIMB_LENGTH_LV, 10, 3);
            WINDING_TABLE.setValueAt(outputData.LIMB_LENGTH_HV, 10, 2);
            WINDING_TABLE.setValueAt("mm", 10, 1);

            WINDING_TABLE.setValueAt(outputData.WIND_RADIAL_DEPTH_LV, 11, 3);
            WINDING_TABLE.setValueAt(outputData.WIND_RADIAL_DEPTH_HV, 11, 2);
            WINDING_TABLE.setValueAt("mm", 11, 1);

            WINDING_TABLE.setValueAt(outputData.TURN_LENGTH_LV, 12, 3);
            WINDING_TABLE.setValueAt(outputData.TURN_LENGTH_HV, 12, 2);
            WINDING_TABLE.setValueAt("M", 12, 1);

            WINDING_TABLE.setValueAt(outputData.WIRE_LENGTH_LV, 13, 3);
            WINDING_TABLE.setValueAt(outputData.WIRE_LENGTH_HV, 13, 2);
            WINDING_TABLE.setValueAt("", 13, 1);

            WINDING_TABLE.setValueAt(outputData.RESISTANCE_LV ,14,3);
            WINDING_TABLE.setValueAt(outputData.RESISTANCE_HV,14,2);
            WINDING_TABLE.setValueAt("Ω", 14, 1);

            WINDING_TABLE.setValueAt(outputData.STRAY_LOSS_LV,15,3);
            WINDING_TABLE.setValueAt(outputData.STRAY_LOSS_HV,15,2);
            WINDING_TABLE.setValueAt("%", 15, 1);

            WINDING_TABLE.setValueAt(outputData.LOAD_LOSS_LV,16,3);
            WINDING_TABLE.setValueAt(outputData.LOAD_LOSS_HV,16,2);
            WINDING_TABLE.setValueAt("Watts", 16, 1);

            WINDING_TABLE.setValueAt(outputData.S_AM2_WDG_LV,17,3);
            WINDING_TABLE.setValueAt(outputData.S_AM2_WDG_HV,17,2);
            WINDING_TABLE.setValueAt("Sqmm", 17, 1);

            WINDING_TABLE.setValueAt(outputData.W_M2_LV,18,3);
            WINDING_TABLE.setValueAt(outputData.W_M2_HV,18,2);
            WINDING_TABLE.setValueAt("W/M²", 18, 1);

            WINDING_TABLE.setValueAt(outputData.WDG_TEMP_RISE_LV,19,3);
            WINDING_TABLE.setValueAt(outputData.WDG_TEMP_RISE_HV,19,2);
            WINDING_TABLE.setValueAt("°C", 19, 1);
        }
}
