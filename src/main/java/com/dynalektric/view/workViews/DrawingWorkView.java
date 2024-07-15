package com.dynalektric.view.workViews;

import com.dynalektric.constants.StyleConstants;
import com.dynalektric.constants.ViewMessages;
import com.dynalektric.control.Control;
import com.dynalektric.model.Model;
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
    private JComboBox<String> imageSelector;
    private JTable table;
    private DefaultTableModel tableModel;
    private JScrollPane scrollPane;
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
            image1 = ImageIO.read(new File("src/main/resources/com/dynalektric/view/workViews/Oil-cooled Transformer Design.jpg"));
            image2 = ImageIO.read(new File("src/main/resources/com/dynalektric/view/workViews/Dry Type Transformer.jpg"));
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
                    updateTableModelForImage1();
                } else if (selectedOption.equals("Dry Type Transformer")) {
                    currentImage = image2;
                    updateTableModelForImage2();
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

        String[] columnNames = {"No", "Description", "Qty", "Quantity"};
        Object[][] initialData = new Object[15][4];
        tableModel = new DefaultTableModel(initialData, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);

        table.setFocusable(false);
        table.setRowSelectionAllowed(false);

        scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(320, 860));
        mainPanel.add(scrollPane, BorderLayout.EAST);
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
        clearTableModel();
        addDataToTableModel("Part", 1, 100, 1);
    }

    private void updateTableModelForImage2() {
        clearTableModel();
        addDataToTableModel("Component", 2, 50, 2);
    }

    private void clearTableModel() {
        for (int i = tableModel.getRowCount() - 1; i >= 0; i--) {
            tableModel.removeRow(i);
        }
    }

    private void addDataToTableModel(String descriptionPrefix, int qty, int quantity, int rowCount) {
        for (int i = 0; i < rowCount; i++) {
            tableModel.addRow(new Object[]{
                    i + 1,
                    descriptionPrefix + " " + (i + 1),
                    qty,
                    quantity
            });
        }
    }
}