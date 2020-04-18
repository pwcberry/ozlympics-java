package org.pwcberry.Ozlympics;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

/**
 * This class provide controller actions for the Main Panel. Actions such as
 * updating and initializing the UI component.
 */
public class MainPanelController implements OzlympicsManagerListener,
		ActionListener {

	// Instance variables
	private OzlympicsManager manager;
	private OzlympicsActionListener serviceBus;
	private MainPanel panel;

	/**
	 * Instantiate MainFrameController by injecting references to the Ozlympics
	 * Data Manager.
	 */
	public MainPanelController(OzlympicsManager manager,
			OzlympicsActionListener serviceBus) {
		this.manager = manager;
		this.serviceBus = serviceBus;

		initializePanel();
	}

	public void showPanel() {
		updateMainPanel();

		OzlympicsActionEvent event = new OzlympicsActionEvent(this, "SWITCH");
		event.setPanelForAction(panel);
		serviceBus.panelReadyToSwitch(event);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// 'ActionListener' is used as a marker for this class with
		// regard to the service bus implementation.
	}

	@Override
	public void managerUpdated(OzlympicsManager manager) {
		this.manager = manager;
	}

	private void initializePanel() {
		panel = new MainPanel();
		panel.setPreferredSize(new Dimension(600, 400));
		updateMainPanel();

		OzlympicsActionEvent event = new OzlympicsActionEvent(this,
				"INITIALIZE");
		event.setPanelForAction(panel);
		serviceBus.panelInitialized(event);
	}

	/*
	 * Update the data to display in the components on the main panel.
	 */
	private void updateMainPanel() {
		// Display the scores of Athletes in order of rank
		Athlete[] athletes = manager.getAthletes();
		Arrays.sort(athletes, new AthleteComparator());
		panel.setAthleteModel(athletes);

		// Display the Officials registered
		panel.setOfficialsModel(manager.getOfficials());

		// Display game results, sorted by ID
		Game[] games = manager.getGames();
		SortedSet<Game> gamesFinished = new TreeSet<Game>(new GameComparator());
		for (Game game : games) {
			if (game.getGameState() != GameState.NOT_STARTED) {
				gamesFinished.add(game);
			}
		}
		panel.setGameResultsModel(gamesFinished.toArray(new Game[gamesFinished
				.size()]));

		// Display the games not started, sorted by ID
		SortedSet<Game> gamesNotStarted = new TreeSet<Game>(
				new GameComparator());
		for (Game game : games) {
			if (game.getGameState() == GameState.NOT_STARTED) {
				gamesNotStarted.add(game);
			}
		}
		panel.setGamesNotStartedModel(gamesNotStarted
				.toArray(new Game[gamesNotStarted.size()]));
	}

	/*
	 * Helper class to sort games by ID.
	 */
	private class GameComparator implements Comparator<Game> {
		public int compare(Game g1, Game g2) {
			return g1.getId().substring(1).compareTo(g2.getId().substring(1));
		}
	}
}
