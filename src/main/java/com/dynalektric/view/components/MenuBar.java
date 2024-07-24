package com.dynalektric.view.components;

import com.dynalektric.constants.DisplayConstant;
import com.dynalektric.constants.ViewMessages;
import com.dynalektric.view.ChildFrameListener;
import com.dynalektric.view.ViewMessage;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class MenuBar extends JMenuBar{
    private ChildFrameListener parent;
    public MenuBar(ChildFrameListener parent) {
        super();
        this.parent = parent;
        this.createMenus();
        this.setStyling();
    }

    private void createMenus() {
        JMenu projectMenu = new JMenu(DisplayConstant.MENU_BAR_PROJECT);
        JMenu viewMenu = new JMenu(DisplayConstant.MENU_BAR_VIEW);
        JMenu windowMenu = new JMenu(DisplayConstant.MENU_BAR_WINDOW);
        JMenu helpMenu = new JMenu(DisplayConstant.MENU_BAR_HELP);

        //project Menu
        MenuItem closeProject = new MenuItem(DisplayConstant.MENU_ITEM_CLOSE_PROJECT);
        MenuItem saveProject =new MenuItem(DisplayConstant.MENU_ITEM_SAVE_PROJECT);
        closeProject.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent event) {
                parent.captureEventFromChildSubFrame(new ViewMessage(ViewMessages.CLOSE_OPENED_PROJECT , null));
            }
        });
        saveProject.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                parent.captureEventFromChildSubFrame(new ViewMessage(ViewMessages.SAVE_PROJECT , null));
            }
        });
        projectMenu.add(closeProject);
        projectMenu.add(saveProject);
        this.add(projectMenu);
        MenuItem basicinfo = new MenuItem(DisplayConstant.MENU_ITEM_BASICINFO);
        basicinfo.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent event) {
                parent.captureEventFromChildSubFrame(new ViewMessage(ViewMessages.OPEN_INPUT_VIEW , null));
            }
        });
        MenuItem winding = new MenuItem(DisplayConstant.MENU_ITEM_WINDING);
        winding.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent event) {
                parent.captureEventFromChildSubFrame(new ViewMessage(ViewMessages.OPEN_WINDING_VIEW , null));
            }
        });
        MenuItem core = new MenuItem(DisplayConstant.MENU_ITEM_CORE);
        core.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent event) {
                parent.captureEventFromChildSubFrame(new ViewMessage(ViewMessages.OPEN_CORE_VIEW , null));
            }
        });
        MenuItem insuarr = new MenuItem(DisplayConstant.MENU_ITEM_INSULATION_ARRANGEMENT);
        insuarr.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent event) {
                parent.captureEventFromChildSubFrame(new ViewMessage(ViewMessages.OPEN_INSULATION_ARRANGEMENT_VIEW , null));
            }
        });
        MenuItem tank = new MenuItem(DisplayConstant.MENU_ITEM_DIMENSION);
        tank.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent event) {
                parent.captureEventFromChildSubFrame(new ViewMessage(ViewMessages.OPEN_DIMENSION_VIEW , null));
            }
        });
        MenuItem short_circuit = new MenuItem(DisplayConstant.MENU_ITEM_SHORT_CIRCUIT);
        short_circuit.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent event) {
                parent.captureEventFromChildSubFrame(new ViewMessage(ViewMessages.OPEN_SHORT_CIRCUIT_VIEW , null));
            }
        });
        MenuItem bom = new MenuItem(DisplayConstant.MENU_ITEM_B_O_M);
        bom.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent event) {
                parent.captureEventFromChildSubFrame(new ViewMessage(ViewMessages.OPEN_B_O_M_VIEW , null));
            }
        });
        MenuItem drawing = new MenuItem(DisplayConstant.MENU_ITEM_DRAWING);
        drawing.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent event) {
                parent.captureEventFromChildSubFrame(new ViewMessage(ViewMessages.OPEN_DRAWINGS , null));
            }
        });
        viewMenu.add(basicinfo);
        viewMenu.add(winding);
        viewMenu.add(core);
        viewMenu.add(insuarr);
        viewMenu.add(tank);
        viewMenu.add(short_circuit);
        viewMenu.add(bom);
        viewMenu.add(drawing);
        this.add(viewMenu);
        this.add(windowMenu);
        MenuItem usermanual = new MenuItem(DisplayConstant.MENU_ITEM_USER_MANUAL);
        usermanual.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent event) {
                downloadUserManual();
            }
        });
        helpMenu.add(usermanual);
        this.add(helpMenu);

    }

    private void setStyling() {
//        this.setBackground(StyleConstants.BACKGROUND_TERTIARY);
//        this.setForeground(StyleConstants.FOREGROUND_SECONDARY);
        this.setBorder(BorderFactory.createEmptyBorder(3 , 10 , 3 , 10));
    }

    private void downloadUserManual() {
        String fileName = "UserManual.pdf";
        String resourcePath = "/com/dynalektric/view/workViews/" + fileName;

        try {
            // Get the input stream for the resource
            InputStream inputStream = getClass().getResourceAsStream(resourcePath);
            if (inputStream == null) {
                JOptionPane.showMessageDialog(this, "User manual not found.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Create a file chooser
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setSelectedFile(new File(fileName));

            // Show save dialog
            int userSelection = fileChooser.showSaveDialog(this);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();

                // Copy the resource to the selected file
                Files.copy(inputStream, fileToSave.toPath(), StandardCopyOption.REPLACE_EXISTING);

                JOptionPane.showMessageDialog(this, "User manual downloaded successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error downloading user manual: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
