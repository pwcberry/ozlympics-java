package org.pwcberry.Ozlympics;

import java.util.*;
import java.awt.event.*;

import javax.swing.JOptionPane;

/**
 * This class provide controller actions for the panel that displays Official
 * data. The actions initialize, set the model and validate the Official panel
 * (which utilizes the ParticipantPanel).
 */
public class OfficialsController implements OzlympicsManagerListener,
		ActionListener {

	// Instance variables
	private OzlympicsManager manager;
	private OzlympicsActionListener serviceBus;
	private ParticipantPanel panel;

	/**
	 * Instantiate OfficialsController by injecting references to the data
	 * manager and service bus.
	 */
	public OfficialsController(OzlympicsManager manager,
			OzlympicsActionListener serviceBus) {
		this.manager = manager;
		this.serviceBus = serviceBus;

		initializePanel();
	}

	private void initializePanel() {
		panel = new ParticipantPanel("OFFICIAL_PANEL", this);

		OzlympicsActionEvent event = new OzlympicsActionEvent(this,
				"INITIALIZE");
		event.setPanelForAction(panel);
		serviceBus.panelInitialized(event);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		if (command.equals("CREATE")) {
			createOfficial();
		} else if (command.equals("CANCEL")) {
			closePanel();
		}
	}

	@Override
	public void managerUpdated(OzlympicsManager manager) {
		this.manager = manager;
	}

	public void add() {
		panel.setFormHeading("Add new official");
		OzlympicsActionEvent event = new OzlympicsActionEvent(this, "SWITCH");
		event.setPanelForAction(panel);
		serviceBus.panelReadyToSwitch(event);
	}

	private void closePanel() {
		OzlympicsActionEvent event = new OzlympicsActionEvent(this,
				"SHOW_MAIN_PANEL");
		serviceBus.actionPerformed(event);
	}

	/*
	 * Validate the form and create an official
	 */
	private void createOfficial() {
		Map<String, String> official = panel.getModelMap();

		try {
			String name = official.get("NAME").trim();
			if (name.length() == 0) {
				panel.setValidationError("NAME", "Please enter a name");
			}

			Integer age = 0;
			if (OzlympicsManager.isAgeValid(official.get("AGE"))) {
				age = Integer.parseInt(official.get("AGE"));
			} else {
				panel.setValidationError("AGE", "Valid age between 18 and 60");
			}

			String state = official.get("STATE");
			if (!OzlympicsManager.isStateValid(state)) {
				panel.setValidationError("STATE", "Please select a state");
			}

			if (panel.isModelValid()) {
				Official theOfficial = manager.addOfficial(name, age, state);

				JOptionPane.showMessageDialog(panel, "Official: '"
						+ theOfficial + "' added to Ozlympics");

			}

		} catch (OzlympicsIsFullException ozlympicsException) {

			JOptionPane.showMessageDialog(panel,
					ozlympicsException.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);

		} catch (NumberFormatException numberException) {

			panel.setValidationError("AGE", "Invalid age specified");

		} finally {
			if (panel.isModelValid()) {
				closePanel();
			}
		}
	}
}
