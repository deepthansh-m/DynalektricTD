package com.dynalektric.view.components;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class InputSpinner extends JPanel {

    private final SpinnerNumberModel model;
    private final JLabel label;
    private final JSpinner spinner;

    public InputSpinner(double initialValue, double minValue, double maxValue, double stepValue, String labelName) {
        this.model = new SpinnerNumberModel(initialValue, minValue, maxValue, stepValue);
        this.label = new JLabel(labelName);
        this.spinner = new JSpinner(model);
        initSpinner();
    }

    public void setValue(double value) {
        this.spinner.setValue(value);
    }

    public double getValue() {
        return (Double) this.spinner.getValue();
    }

    public void addChangeListener(ChangeListener listener) {
        this.spinner.addChangeListener(listener);
    }

    private void initSpinner() {
        this.setLayout(new FlowLayout(FlowLayout.CENTER));
        this.add(label);
        this.add(spinner);
        this.setMaximumSize(new Dimension(300, 50));
    }
}
