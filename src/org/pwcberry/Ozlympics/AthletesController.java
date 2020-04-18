package org.pwcberry.Ozlympics;

import java.awt.event.*;
import java.util.Map;
import javax.swing.JOptionPane;

/**
 * This class provide controller actions for the panel that displays Athlete
 * data. The actions initialize, set the model and validate the Athlete panel.
 */
public class AthletesController implements OzlympicsManagerListener, ActionListener {

	// Instance variables
	private OzlympicsManager manager;
	private OzlympicsActionListener serviceBus;
	private AthletePanel panel;

	/**
	 * Instantiate AthletesController by injecting references to the Ozlympics
	 * Data Manager.
	 */
	public AthletesController(OzlympicsManager manager,
			OzlympicsActionListener serviceBus) {
		this.manager = manager;
		this.serviceBus = serviceBus;

		initializePanel();
	}

	private void initializePanel() {
		panel = new AthletePanel("ATHLETE_PANEL", this);

		OzlympicsActionEvent event = new OzlympicsActionEvent(this,
				"INITIALIZE");
		event.setPanelForAction(panel);
		serviceBus.panelInitialized(event);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		if (command.equals("CREATE")) {
			createAthlete();
		} else if (command.equals("CANCEL")) {
			closePanel();
		}
	}

	@Override
	public void managerUpdated(OzlympicsManager manager) {
		this.manager = manager;
	}

	public void add() {
		panel.setFormHeading("Add new athlete");
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
	 * Validate the form and create an athlete
	 */
	private void createAthlete() {
		Map<String, String> athlete = panel.getModelMap();

		try {
			String name = athlete.get("NAME").trim();
			if (name.length() == 0) {
				panel.setValidationError("NAME", "Please enter a name");
			}

			Integer age = 0;
			if (OzlympicsManager.isAgeValid(athlete.get("AGE"))) {
				age = Integer.parseInt(athlete.get("AGE"));
			} else {
				panel.setValidationError("AGE",
						"Valid age between 18 and 60");
			}

			String state = athlete.get("STATE");
			if (!OzlympicsManager.isStateValid(state)) {
				panel.setValidationError("STATE",
						"Please select a state");
			}

			String sport = athlete.get("SPORT");
			if (!OzlympicsManager.isSportValid(sport)) {
				panel.setValidationError("SPORT",
						"Please select a sport");
			}

			if (panel.isModelValid()) {
				Athlete theAthlete = manager.addAthlete(name, age, state,
						Sport.valueOf(sport));

				JOptionPane.showMessageDialog(panel, "Athlete: '" + theAthlete
						+ "' added to Ozlympics");
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
