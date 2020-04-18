package org.pwcberry.Ozlympics;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

/**
 * This class provides the basic UI for a Participant.
 */

@SuppressWarnings("serial")
public class ParticipantPanel extends OzlympicsFormPanel {

	// UI Components
	private JTextField nameField;
	private JTextField ageField;
	private JComboBox stateList;

	public ParticipantPanel(String name, ActionListener controller) {
		super(name, controller);
		this.initializeComponent();
		addButtons();
	}

	protected void initializeComponent() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		// Add the name field
		nameField = new JTextField(30);
		nameField.setMaximumSize(new Dimension(200, 20));
		add(createFieldPanel("Name:", nameField, "NAME"));
		add(Box.createVerticalStrut(10));
		
		// Add the age field
		ageField = new JTextField(10);
		ageField.setMaximumSize(new Dimension(100, 20));
		add(createFieldPanel("Age:", ageField, "AGE"));
		add(Box.createVerticalStrut(10));

		// Add the states combo-box / drop-down list
		String[] states = { "Please select...", "ACT", "NSW", "NT", "QLD",
				"SA", "TAS", "VIC", "WA" };
		stateList = new JComboBox(states);
		stateList.setName("Participant_State_List");
		stateList.setMaximumSize(new Dimension(200, 20));
		
		add(createFieldPanel("State:", stateList, "STATE"));
		add(Box.createVerticalStrut(10));
	}

	public Map<String, String> getModelMap() {
		resetValidationMessages();

		Map<String, String> model = new HashMap<String, String>();

		model.put("NAME", nameField.getText());
		model.put("AGE", ageField.getText());
		model.put("STATE", stateList.getSelectedItem().toString());

		return model;
	}
}
