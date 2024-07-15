package com.dynalektric.view.components;

import com.dynalektric.constants.DisplayConstant;
import com.dynalektric.constants.ViewMessages;
import com.dynalektric.view.ChildFrameListener;
import com.dynalektric.view.ViewMessage;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
        this.add(helpMenu);

    }

    private void setStyling() {
//        this.setBackground(StyleConstants.BACKGROUND_TERTIARY);
//        this.setForeground(StyleConstants.FOREGROUND_SECONDARY);
        this.setBorder(BorderFactory.createEmptyBorder(3 , 10 , 3 , 10));
    }
}
