package com.dynalektric.view.modals;

import com.dynalektric.constants.DisplayConstant;
import com.dynalektric.constants.StyleConstants;
import com.dynalektric.constants.ViewMessages;
import com.dynalektric.view.ChildFrameListener;
import com.dynalektric.view.ViewMessage;
import com.dynalektric.view.workViews.AbstractWorkView;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.util.List;
import java.util.stream.Collectors;

public class OpenProjectModal extends AbstractModal implements ChildFrameListener {
    private final List<String> projectNames;
    private JPanel contentPanel;
    private JTextField searchField;
    private JScrollPane projectListScrollPane;
    private JPanel projectListPanel;
    private JPopupMenu searchSuggestionsMenu;

    public OpenProjectModal(AbstractWorkView parentView, List<String> projectNames) {
        super(parentView, DisplayConstant.OPEN_PROJECT);
        this.projectNames = projectNames;
    }

    @Override
    public void init() {
        SwingUtilities.invokeLater(this::initializeUI);
    }

    private void initializeUI() {
        setSize(650, 600); // Adjusted width to 650 for more space
        setLocationRelativeTo(getOwner());
        setResizable(false);

        contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout(0, 10));
        contentPanel.setBackground(StyleConstants.MODAL_BACKGROUND);
        contentPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel headerLabel = new JLabel(DisplayConstant.OPEN_PROJECT);
        headerLabel.setFont(StyleConstants.HEADING_SUB1);
        headerLabel.setForeground(StyleConstants.FOREGROUND_PRIMARY);
        headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        contentPanel.add(headerLabel, BorderLayout.NORTH);

        searchField = new JTextField() {
            @Override
            protected void paintComponent(Graphics g) {
                if (!isOpaque() && getBorder() instanceof RoundedCornerBorder) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(getBackground());
                    g2.fill(((RoundedCornerBorder) getBorder()).getBorderShape(0, 0, getWidth() - 1, getHeight() - 1));
                    g2.dispose();
                }
                super.paintComponent(g);
            }

            @Override
            public void updateUI() {
                super.updateUI();
                setOpaque(false);
                setBorder(new RoundedCornerBorder());
            }
        };
        searchField.setPreferredSize(new Dimension(contentPanel.getWidth() - 40, 35));
        searchField.setBackground(StyleConstants.TEXT_FIELD_BACKGROUND);
        searchField.setForeground(StyleConstants.FOREGROUND_PRIMARY);
        searchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                filterProjects();
                showSearchSuggestions();
            }
        });

        searchField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (searchField.getText().equals("Search projects...")) {
                    searchField.setText("");
                    searchField.setForeground(StyleConstants.FOREGROUND_PRIMARY);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (searchField.getText().isEmpty()) {
                    searchField.setForeground(Color.GRAY);
                    searchField.setText("Search projects...");
                }
            }
        });
        searchField.setForeground(Color.GRAY);
        searchField.setText("Search projects...");

        JButton clearSearchButton = new JButton("Clear");
        clearSearchButton.addActionListener(e -> {
            searchField.setText("");
            searchField.setForeground(Color.GRAY);
            searchField.setText("Search projects...");
            updateProjectList(projectNames);
        });

        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.setBackground(StyleConstants.MODAL_BACKGROUND);
        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(clearSearchButton, BorderLayout.EAST);

        projectListPanel = new JPanel();
        projectListPanel.setLayout(new BoxLayout(projectListPanel, BoxLayout.Y_AXIS));
        projectListPanel.setBackground(StyleConstants.BACKGROUND_PRIMARY);

        updateProjectList(projectNames);

        projectListScrollPane = new JScrollPane(projectListPanel);
        projectListScrollPane.setBorder(null);
        projectListScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // Disable horizontal scroll bar
        projectListScrollPane.getVerticalScrollBar().setUnitIncrement(16);

        projectListScrollPane.getVerticalScrollBar().setUI(new CustomScrollBarUI());

        searchSuggestionsMenu = new JPopupMenu();

        JPanel centerPanel = new JPanel(new BorderLayout(0, 10));
        centerPanel.setBackground(StyleConstants.MODAL_BACKGROUND);
        centerPanel.add(searchPanel, BorderLayout.NORTH);
        centerPanel.add(projectListScrollPane, BorderLayout.CENTER);

        contentPanel.add(centerPanel, BorderLayout.CENTER);

        setContentPane(contentPanel);
        setVisible(true);
    }

    private JPanel createProjectCard(String project) {
        JPanel cardContent = new JPanel(new FlowLayout(FlowLayout.LEADING));
        cardContent.setBackground(StyleConstants.BACKGROUND_SECONDARY);

        JButton openProjectButton = new JButton(project);
        openProjectButton.setFont(StyleConstants.NEW_PROJECT);
        openProjectButton.setBackground(StyleConstants.BACKGROUND_SECONDARY);
        openProjectButton.setPreferredSize(new Dimension(600, openProjectButton.getPreferredSize().height)); // Adjusted width
        openProjectButton.setHorizontalAlignment(SwingConstants.LEFT);
        openProjectButton.setToolTipText("Open " + project);

        openProjectButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    dispose();
                    parentView.captureEventFromChildSubFrame(new ViewMessage(ViewMessages.OPEN_SELECTED_PROJECT, project));
                } else {
                    ProjectPopUpMenu popMenu = new ProjectPopUpMenu(project, OpenProjectModal.this);
                    popMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                openProjectButton.setBackground(StyleConstants.HOVER_BACKGROUND);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                openProjectButton.setBackground(StyleConstants.BACKGROUND_SECONDARY);
            }
        });

        cardContent.add(openProjectButton);
        cardContent.setMaximumSize(new Dimension(610, cardContent.getPreferredSize().height)); // Adjusted width
        cardContent.setAlignmentX(LEFT_ALIGNMENT);

        return cardContent;
    }

    private void filterProjects() {
        String searchText = searchField.getText().toLowerCase();
        if (searchText.equals("search projects...")) {
            searchText = "";
        }
        String finalSearchText = searchText;
        List<String> filteredProjects = projectNames.stream()
                .filter(project -> project.toLowerCase().contains(finalSearchText))
                .collect(Collectors.toList());
        updateProjectList(filteredProjects);
    }

    private void updateProjectList(List<String> projects) {
        projectListPanel.removeAll();
        for (String project : projects) {
            projectListPanel.add(Box.createVerticalStrut(10));
            projectListPanel.add(createProjectCard(project));
        }
        projectListPanel.revalidate();
        projectListPanel.repaint();
    }

    private void showSearchSuggestions() {
        searchSuggestionsMenu.removeAll();
        String searchText = searchField.getText().toLowerCase();
        if (!searchText.equals("search projects...")) {
            List<String> suggestions = projectNames.stream()
                    .filter(project -> project.toLowerCase().contains(searchText))
                    .collect(Collectors.toList());
            for (String suggestion : suggestions) {
                JMenuItem menuItem = new JMenuItem(suggestion);
                menuItem.addActionListener(e -> {
                    searchField.setText(suggestion);
                    filterProjects();
                    searchSuggestionsMenu.setVisible(false);
                });
                searchSuggestionsMenu.add(menuItem);
            }
        }
        if (searchSuggestionsMenu.getComponentCount() > 0) {
            searchSuggestionsMenu.show(searchField, 0, searchField.getHeight());
        }
    }

    @Override
    public void captureEventFromChildSubFrame(ViewMessage message) {
        switch (message.getMsgType()) {
            case ViewMessages.DELETE_SELECTED_PROJECT:
                dispose();
                parentView.captureEventFromChildSubFrame(message);
                break;
            case ViewMessages.OPEN_SELECTED_PROJECT:
                dispose();
                parentView.captureEventFromChildSubFrame(message);
                break;
        }
    }

    private static class RoundedCornerBorder extends AbstractBorder {
        private static final int ARC = 15;

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(StyleConstants.TEXT_FIELD_BORDER);
            g2.drawRoundRect(x, y, width - 1, height - 1, ARC, ARC);
            g2.dispose();
        }

        public Shape getBorderShape(int x, int y, int width, int height) {
            return new RoundRectangle2D.Float(x, y, width - 1, height - 1, ARC, ARC);
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(4, 8, 4, 8);
        }
    }

    private static class CustomScrollBarUI extends BasicScrollBarUI {
        @Override
        protected void configureScrollBarColors() {
            this.thumbColor = StyleConstants.BUTTON_COLOR;
            this.trackColor = StyleConstants.BACKGROUND_SECONDARY;
        }

        @Override
        protected JButton createDecreaseButton(int orientation) {
            return createZeroButton();
        }

        @Override
        protected JButton createIncreaseButton(int orientation) {
            return createZeroButton();
        }

        private JButton createZeroButton() {
            JButton button = new JButton();
            Dimension zeroDim = new Dimension(0, 0);
            button.setPreferredSize(zeroDim);
            button.setMinimumSize(zeroDim);
            button.setMaximumSize(zeroDim);
            return button;
        }

        @Override
        protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
            if (thumbBounds.isEmpty() || !scrollbar.isEnabled()) {
                return;
            }

            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setPaint(thumbColor);
            g2.fillRoundRect(thumbBounds.x, thumbBounds.y, thumbBounds.width, thumbBounds.height, 10, 10);
            g2.dispose();
        }

        @Override
        protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setPaint(trackColor);
            g2.fillRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height);
            g2.dispose();
        }
    }
}
