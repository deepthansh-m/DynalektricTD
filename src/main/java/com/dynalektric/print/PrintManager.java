package com.dynalektric.print;

import com.dynalektric.model.Model;
import com.dynalektric.view.workViews.*;

import javax.swing.*;
import java.awt.*;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

public class PrintManager {

    public static void printComponent(Component component) {
        PrinterJob pj = PrinterJob.getPrinterJob();
        pj.setJobName(" Print Component ");

        pj.setPrintable(new Printable() {
            @Override
            public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
                if (pageIndex > 0) {
                    return Printable.NO_SUCH_PAGE;
                }

                Graphics2D g2 = (Graphics2D) graphics;
                g2.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

                double scaleX = pageFormat.getImageableWidth() / component.getWidth();
                double scaleY = pageFormat.getImageableHeight() / component.getHeight();
                double scale = Math.min(scaleX, scaleY);

                g2.scale(scale, scale);
                component.paint(g2);

                return Printable.PAGE_EXISTS;
            }
        });

        if (pj.printDialog()) {
            try {
                pj.print();
            } catch (PrinterException ex) {
                JOptionPane.showMessageDialog(null, "Printing failed: " + ex.getMessage(), "Print Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void printOutput(Model model) {
        SwingUtilities.invokeLater(() -> {
            PrintWorkView view = new PrintWorkView(model);
            printComponent(view);
        });
    }
}