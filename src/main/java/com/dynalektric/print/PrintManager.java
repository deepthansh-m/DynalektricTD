package com.dynalektric.print;

import com.dynalektric.constants.StyleConstants;
import com.dynalektric.model.Model;
import com.dynalektric.view.workViews.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.print.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PrintManager {

    private static final String COMPANY_INFO = "Dynalektric Equipment Pvt. Ltd.\n" +
            "No-49/2,Vaderamanchanahalli Village, Kallubalu, Anekal Taluk,Jigani Hobli,\n" +
            " Bangalore- 560105\n" +
            "Mobile no: +91-767-648-9086\n" +
            "Email: cs@dynalektric.com\n";

    public static void printComponent(JComponent component) {
        PrinterJob pj = PrinterJob.getPrinterJob();
        pj.setJobName("Print Component");

        PageFormat pf = pj.defaultPage();
        pf.setOrientation(PageFormat.PORTRAIT);

        pj.setPrintable(new ComponentPrintable(component), pf);

        if (pj.printDialog()) {
            try {
                pj.print();
            } catch (PrinterException ex) {
                JOptionPane.showMessageDialog(null, "Printing failed: " + ex.getMessage(), "Print Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private static class ComponentPrintable implements Printable {
        private final JComponent component;
        private int HEADER_HEIGHT = 100;
        private final int LOGO_SIZE = 80;
        private List<Integer> pageBreaks;
        String os = System.getProperty("os.name").toLowerCase();
        boolean isWindows = os.contains("win");


        public ComponentPrintable(JComponent component) {
            this.component = component;
            this.pageBreaks = new ArrayList<>();
        }

        @Override
        public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
            Graphics2D g2d = (Graphics2D) graphics;
            g2d.setColor(Color.BLACK);

            if (isWindows) {
                // Adjust for Windows
                HEADER_HEIGHT = 150;  // This moves everything down by 50 pixels
            }

            int availableWidth = (int) pageFormat.getImageableWidth();
            int availableHeight = (int) (pageFormat.getImageableHeight()*2.35) - HEADER_HEIGHT;

            if (pageBreaks.isEmpty()) {
                calculatePageBreaks(availableHeight);
            }

            if (pageIndex >= pageBreaks.size()) {
                return NO_SUCH_PAGE;
            }

            // Draw header
            drawHeader(g2d, availableWidth, (int) pageFormat.getImageableX());

            // Set clip to printable area
            g2d.setClip((int) pageFormat.getImageableX(), (int) pageFormat.getImageableY(),
                    availableWidth, (int) pageFormat.getImageableHeight());

            // Translate to the printable area
            g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY() + HEADER_HEIGHT);

            // Scale only if the component is wider than the available width
            double scale = Math.min(1.0, (double) availableWidth / component.getWidth());
            g2d.scale(scale, scale);

            // Print the component
            int y = pageIndex > 0 ? pageBreaks.get(pageIndex - 1) : 0;
            g2d.translate(0, -y);
            g2d.setClip(0, y, component.getWidth(), availableHeight);
            component.print(g2d);

            return PAGE_EXISTS;
        }

        private void calculatePageBreaks(int availableHeight) {
            int componentHeight = component.getHeight();
            int y = 0;

            while (y < componentHeight) {
                int rowHeight = findNextRowBreak(y, availableHeight);
                y += rowHeight;
                pageBreaks.add(y);
            }
        }

        private int findNextRowBreak(int startY, int maxHeight) {
            if (component instanceof JTable) {
                JTable table = (JTable) component;
                int rowCount = table.getRowCount();
                int currentHeight = 0;
                int rowIndex = table.rowAtPoint(new Point(0, startY));

                while (rowIndex < rowCount) {
                    int rowHeight = table.getRowHeight(rowIndex);
                    if (currentHeight + rowHeight > maxHeight) {
                        break;
                    }
                    currentHeight += rowHeight;
                    rowIndex++;
                }

                if (currentHeight == 0 && rowIndex < rowCount) {
                    currentHeight = table.getRowHeight(rowIndex);
                }

                return currentHeight;
            } else {
                int componentHeight = component.getHeight();
                int remainingHeight = componentHeight - startY;
                return Math.min(maxHeight, remainingHeight);
            }
        }

        private void drawHeader(Graphics2D g2d, int pageWidth, int pageX) {
            int startY = 30;

            if (isWindows) {
                // Adjust for Windows
                startY = 70;  // This moves everything down by 50 pixels
            }

            try {
                BufferedImage logo = ImageIO.read(getClass().getResourceAsStream("/com/dynalektric/view/workViews/DYNA.jpg"));
                if (logo != null) {
                    g2d.drawImage(logo, pageX + 5, startY, LOGO_SIZE + 40, LOGO_SIZE - 30, null);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            g2d.setFont(StyleConstants.PRINT_HEADER);
            FontMetrics fm = g2d.getFontMetrics();

            String[] lines = COMPANY_INFO.split("\n");
            int y = startY;
            int textWidth = pageWidth - LOGO_SIZE - 30;

            for (String line : lines) {
                int x = pageX + LOGO_SIZE + 20 + (textWidth / 2) - (fm.stringWidth(line) / 2);
                g2d.drawString(line, x, y + fm.getAscent());  // Use getAscent() to align text properly
                y += fm.getHeight();
            }

            g2d.drawLine(pageX, HEADER_HEIGHT - 5, pageX + pageWidth, HEADER_HEIGHT - 5);
        }
    }
}
