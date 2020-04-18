package org.pwcberry.Ozlympics;

import java.awt.Dimension;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

/**
 * This class provides the UI for an Athlete.
 */

@SuppressWarnings("serial")
public class AthletePanel extends ParticipantPanel {

	private JComboBox sportList;

	public AthletePanel(String name, ActionListener controller) {
		super(name, controller);
	}

	protected void initializeComponent() {
		super.initializeComponent();

		Sport[] sports = Sport.values();
		sportList = new JComboBox(sports);
		sportList.setName("Athlete_Sport_List");
		sportList.setMaximumSize(new Dimension(200, 20));

		add(createFieldPanel("Sport Played:", sportList, "SPORT"));
		add(Box.createVerticalStrut(10));
	}

	public Map<String, String> getModelMap() {
		Map<String, String> model = super.getModelMap();

		model.put("SPORT", sportList.getSelectedItem().toString());
		return model;
	}
}
