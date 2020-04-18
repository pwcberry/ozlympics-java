package org.pwcberry.Ozlympics;

import java.awt.event.*;
import java.util.*;
import javax.swing.*;

/**
 * This class provide controller actions for the panel that displays Game data.
 * The actions initialize, set the model and validate the Game panel.
 */
public class GamesController implements OzlympicsManagerListener, ActionListener {

	// Instance variables
	private OzlympicsManager manager;
	private OzlympicsActionListener serviceBus;
	private GamePanel panel;

	/**
	 * Instantiate GamesController by injecting references to the Ozlympics Data
	 * Manager.
	 */
	public GamesController(OzlympicsManager manager,
			OzlympicsActionListener serviceBus) {
		this.manager = manager;
		this.serviceBus = serviceBus;

		initializePanel();
	}

	private void initializePanel() {
		panel = new GamePanel("GAME_PANEL", this);

		OzlympicsActionEvent event = new OzlympicsActionEvent(this,
				"INITIALIZE");
		event.setPanelForAction(panel);
		serviceBus.panelInitialized(event);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		if (command.equals("CREATE")) {
			createGame();
		} else if (command.equals("CANCEL")) {
			closePanel();
		} else if (command.equals("UPDATE")) {
			updateGame();
		}
	}

	@Override
	public void managerUpdated(OzlympicsManager manager) {
		this.manager = manager;
	}

	/**
	 * Add a game to Ozlympics.
	 */
	public void add() {
		panel.setFormHeading("Add new game");
		panel.setGamesModel(null);
		OzlympicsListModel<Official> officialsModel = new OzlympicsListModel<Official>(
				manager.getOfficials());
		panel.setOfficialsModel(officialsModel);

		panel.setAthletesModel(manager.getAthletes());

		OzlympicsActionEvent event = new OzlympicsActionEvent(this, "SWITCH");
		event.setPanelForAction(panel);
		serviceBus.panelReadyToSwitch(event);
	}

	/**
	 * Add participants to existing game
	 */
	public void addParticipants() {
		panel.setFormHeading("Modify a game");
		panel.setGamesModel(manager.getGames());

		OzlympicsListModel<Official> officialsModel = new OzlympicsListModel<Official>(
				manager.getOfficials());
		panel.setOfficialsModel(officialsModel);

		panel.setAthletesModel(manager.getAthletes());

		OzlympicsActionEvent event = new OzlympicsActionEvent(this, "SWITCH");
		event.setPanelForAction(panel);
		serviceBus.panelReadyToSwitch(event);
	}

	/**
	 * Run the unstarted games.
	 */
	public void run() {
		Game[] games = manager.getGames();

		for (int i = 0; i < games.length; i++) {
			if (games[i].getGameState() == GameState.NOT_STARTED) {
				games[i].play();
			}
		}

		OzlympicsActionEvent event = new OzlympicsActionEvent(this,
				"GAMES_FINISHED");
		serviceBus.actionPerformed(event);
	}

	private void createGame() {
		Map<String, String> game = panel.getModelMap();

		// Update the game details, and close the panel when finished.

		try {
			String sport = game.get("SPORT");
			if (!OzlympicsManager.isSportValid(sport)) {
				panel.setValidationError("SPORT", "Please select a sport");
			}

			// If the game has a specified sport,
			// add officials and athletes if they are valid too
			if (panel.isModelValid()) {
				Game theGame = manager.addGame(Sport.valueOf(sport));

				String officialId = game.get("OFFICIAL");
				if (OzlympicsManager.isIdValid(officialId, "Official")) {
					Official theOfficial = manager.findOfficial(officialId);
					theGame.appointOfficial(theOfficial);
				}

				String[] athleteIds = game.get("ATHLETES").split("\\|");
				for (String id : athleteIds) {
					if (OzlympicsManager.isIdValid(id, "Athlete")) {
						Athlete theAthlete = manager.findAthlete(id);
						theGame.addAthlete(theAthlete);
					}
				}

				JOptionPane.showMessageDialog(panel, "Game: " + theGame.getId()
						+ " added to Ozlympics");
			}
		} catch (OzlympicsIsFullException ozlympicsException) {

			JOptionPane.showMessageDialog(panel,
					ozlympicsException.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);

		} catch (GameIsFullException gameException) {

			JOptionPane.showMessageDialog(panel, gameException.getMessage(),
					"Warning", JOptionPane.WARNING_MESSAGE);
		} finally {
			if (panel.isModelValid()) {
				closePanel();
			}
		}
	}

	private void updateGame() {
		Map<String, String> game = panel.getModelMap();

		// Update the game details, and close the panel when finished.

		try {
			String gameId = game.get("GAME");
			if (!OzlympicsManager.isIdValid(gameId, "Game")) {
				JOptionPane.showMessageDialog(panel, "Invalid Game selection",
						"Error", JOptionPane.ERROR_MESSAGE);
				return;
			}

			Game theGame = manager.findGame(gameId);

			String officialId = game.get("OFFICIAL");
			if (OzlympicsManager.isIdValid(officialId, "Official")) {
				Official theOfficial = manager.findOfficial(officialId);
				theGame.appointOfficial(theOfficial);
			}

			String[] athleteIds = game.get("ATHLETES").split("\\|");
			for (String id : athleteIds) {
				if (OzlympicsManager.isIdValid(id, "Athlete")) {
					Athlete theAthlete = manager.findAthlete(id);
					theGame.addAthlete(theAthlete);
				}
			}

		} catch (GameIsFullException gameException) {

			JOptionPane.showMessageDialog(panel, gameException.getMessage(),
					"Warning", JOptionPane.WARNING_MESSAGE);
		} finally {

			closePanel();
		}
	}

	private void closePanel() {
		OzlympicsActionEvent event = new OzlympicsActionEvent(this,
				"SHOW_MAIN_PANEL");
		serviceBus.actionPerformed(event);
	}
}
