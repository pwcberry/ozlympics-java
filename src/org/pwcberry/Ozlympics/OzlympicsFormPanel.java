package org.pwcberry.Ozlympics;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

/**
 * This class provides the basic UI and functionality for form in the Ozlympics
 * application.
 */

@SuppressWarnings("serial")
public abstract class OzlympicsFormPanel extends JPanel {

	// Instance variables
	private ActionListener controller;
	private boolean isValid;

	// Common components
	protected final JButton saveButton;

	// This field keeps a record of labels used to display
	// validation messages.
	// This field is protected so it may be accessible to subclasses
	protected final Map<String, JLabel> validationMessages;

	/**
	 * Default constructor that all subclasses must call, initializing the form
	 * with the specified name and controller.
	 */
	protected OzlympicsFormPanel(String name, ActionListener controller) {
		this.controller = controller;
		this.setName(name);
		validationMessages = new HashMap<String, JLabel>();
		saveButton = new JButton("Save");
	}

	/**
	 * Add components to the form panel.
	 */
	protected abstract void initializeComponent();

	/**
	 * Returns the values of the form as a hash table, where the keys of the
	 * hash table are the field names and the values are the field values
	 */
	public abstract Map<String, String> getModelMap();

	/**
	 * Set the heading text for the form.
	 */
	public void setFormHeading(String text) {
		JComponent component = (JComponent) getComponent(0);
		String componentName = component.getName();
		if ((componentName == null)
				|| (componentName != null && !componentName
						.equals("FORM_HEADING"))) {
			JLabel label = new JLabel(text);
			label.setHorizontalAlignment(JLabel.LEFT);
			label.setName("FORM_HEADING");
			Font font = label.getFont();

			label.setFont(new Font(font.getName(), Font.BOLD, 24));
			add(label, 0);
		} else {
			((JLabel) component).setText(text);
		}
	}

	/**
	 * Add "Save" and "Cancel" buttons to the form.
	 */
	protected void addButtons() {
		Box panel = Box.createHorizontalBox();
		add(panel);

		saveButton.setActionCommand("CREATE");
		saveButton.addActionListener(controller);
		saveButton.setMaximumSize(new Dimension(100, 20));
		saveButton.setAlignmentX(Component.LEFT_ALIGNMENT);
		panel.add(saveButton);

		panel.add(Box.createRigidArea(new Dimension(20, 20)));

		JButton cancelButton = new JButton("Cancel");
		cancelButton.setActionCommand("CANCEL");
		cancelButton.addActionListener(controller);
		cancelButton.setMaximumSize(new Dimension(100, 20));
		saveButton.setAlignmentX(Component.LEFT_ALIGNMENT);
		panel.add(cancelButton);
	}

	/**
	 * Create a validation message label with the specified name and attached it
	 * to the specified component, placing a spacer before the label.
	 */
	protected void setValidationLabel(JComponent component, String name) {
		JLabel messageLabel = createLabel("");
		messageLabel.setVisible(false);
		messageLabel.setHorizontalAlignment(JLabel.LEFT);
		messageLabel.setForeground(new Color(0xcc0000));
		messageLabel.setPreferredSize(new Dimension(240, 20));
		component.add(messageLabel);
		validationMessages.put(name, messageLabel);
	}

	/**
	 * Returns the valid state of the form's model.
	 */
	public boolean isModelValid() {
		return isValid;
	}

	/**
	 * Clear validation messages and reset the form's validity to true.
	 */
	protected void resetValidationMessages() {
		for (JLabel label : validationMessages.values()) {
			label.setVisible(false);
		}
		isValid = true;
	}

	/**
	 * Set the validation message for the specified field.
	 */
	public void setValidationError(String fieldName, String message) {
		if (validationMessages.containsKey(fieldName)) {
			JLabel messageLabel = validationMessages.get(fieldName);
			messageLabel.setText(message);
			messageLabel.revalidate();
			messageLabel.setVisible(true);
			isValid = false;
		}
	}

	/**
	 * Helper method to create a field label.
	 */
	protected JLabel createLabel(String text) {
		JLabel label = new JLabel();
		label.setText(text);
		label.setHorizontalAlignment(JLabel.RIGHT);
		label.setPreferredSize(new Dimension(200, 20));

		return label;
	}

	/**
	 * Helper method to create a panel that contains a label, the input
	 * component and a name to specify for validation.
	 * 
	 * Returns the created JPanel component.
	 */
	protected JPanel createFieldPanel(String labelText, JComponent field,
			String validationLabelName) {
		JPanel panel = new JPanel();
		SpringLayout layout = new SpringLayout();
		panel.setLayout(layout);

		JLabel label = createLabel(labelText);
		panel.add(label);

		layout.putConstraint(SpringLayout.WEST, field, 20, SpringLayout.EAST,
				label);
		panel.add(field);

		setValidationLabel(panel, validationLabelName);

		JComponent validationLabel = (JComponent) panel.getComponent(2);
		layout.putConstraint(SpringLayout.WEST, validationLabel, 20,
				SpringLayout.EAST, field);

		return panel;
	}
}
