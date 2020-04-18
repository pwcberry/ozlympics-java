package org.pwcberry.Ozlympics;

import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * This class provides the UI for a Game.
 */

@SuppressWarnings("serial")
public class GamePanel extends OzlympicsFormPanel {

	// UI Components
	private JComboBox sportList;
	private JPanel gameAddPanel;
	private JPanel gameEditPanel;
	private JPanel gameDetailsPanel;
	private JList gamesList;
	private JList officialsList;
	private JList athletesList;

	// Model references
	Game[] theGames;
	Athlete[] theAthletes;
	OzlympicsListModel<Official> officialsListModel;
	OzlympicsListModel<Athlete> athletesListModel;
	OzlympicsListModel<Game> gamesListModel;

	public GamePanel(String name, ActionListener controller) {
		super(name, controller);

		initializeComponent();
		addButtons();
	}

	protected void initializeComponent() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		JPanel inputPanel = new JPanel();
		inputPanel.setLayout(new GridLayout(2, 2));
		add(inputPanel);

		gameDetailsPanel = new JPanel();
		gameDetailsPanel.setLayout(new CardLayout());
		inputPanel.add(gameDetailsPanel, BorderLayout.WEST);

		createGamePane(gameDetailsPanel);
		createGameListPanel(gameDetailsPanel);
		createOfficialPane(inputPanel);
		createAthletesPane(inputPanel);
	}

	private void createGamePane(JPanel container) {
		gameAddPanel = createPanel();

		// Pane title
		JLabel label = new JLabel("New Game Details:");
		gameAddPanel.add(label);
		
		// Add a combo box for sports

		Sport[] sports = Sport.values();
		sportList = new JComboBox(sports);
		sportList.setName("Games_Sport_List");
		sportList.setMaximumSize(new Dimension(200, 20));
		sportList.addActionListener(new SportListActionListener());
		gameAddPanel.add(createFieldPanel("Sport played:", sportList, "SPORT"));
		
		container.add(gameAddPanel, "Game_Add_Game_Panel");
	}

	private void createGameListPanel(JPanel container) {
		gameEditPanel = createPanel();

		// Pane title
		JLabel label = new JLabel("Select Game to edit:");
		gameEditPanel.add(label);

		gamesList = new JList();
		gamesList.setName("Game_Games_List");
		gamesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		gamesList.setLayoutOrientation(JList.VERTICAL);
		gamesList.setVisibleRowCount(10);
		gamesList.addListSelectionListener(new GamesListSelectionListener());

		JScrollPane scrollPane = new JScrollPane(gamesList,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		gameEditPanel.add(scrollPane);
		
		container.add(gameEditPanel, "Game_Games_List_Panel");
	}

	/*
	 * Create the pane that displays Officials to appoint to the Game.
	 */
	private void createOfficialPane(JPanel container) {
		JPanel panel = createPanel();

		// Pane title
		JLabel label = new JLabel("Select an official:");
		panel.add(label);

		officialsList = new JList();
		officialsList.setName("Game_Officials_List");
		officialsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		officialsList.setLayoutOrientation(JList.VERTICAL);
		officialsList.setVisibleRowCount(10);

		JScrollPane scrollPane = new JScrollPane(officialsList,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		panel.add(scrollPane);

		container.add(panel, BorderLayout.EAST);
	}

	/*
	 * Create the pane that displays Athletes to register with the Game.
	 */
	private void createAthletesPane(JPanel container) {
		JPanel panel = createPanel();

		// Pane title
		JLabel label = new JLabel("Select athletes:");
		panel.add(label);

		athletesList = new JList();
		athletesList.setName("Game_Athletes_List");
		athletesList
				.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		athletesList.setLayoutOrientation(JList.VERTICAL);
		athletesList.setVisibleRowCount(10);

		JScrollPane scrollPane = new JScrollPane(athletesList,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		panel.add(scrollPane);

		container.add(panel, BorderLayout.SOUTH);
	}

	/**
	 * Set the games model for the form.
	 * 
	 * @param model
	 *            when this is null, the form will be placed in the "Add Game"
	 *            state.
	 */
	public void setGamesModel(Game[] model) {
		theGames = model;
		CardLayout layout = (CardLayout) gameDetailsPanel.getLayout();
		
		if (theGames == null) {
			layout.show(gameDetailsPanel, "Game_Add_Game_Panel");
			saveButton.setActionCommand("CREATE");
		} else {
			layout.show(gameDetailsPanel, "Game_Games_List_Panel");
			gamesList.setModel(new OzlympicsListModel<Game>(theGames));
			saveButton.setActionCommand("UPDATE");
		}
	}

	public void setAthletesModel(Athlete[] model) {
		theAthletes = model;
		athletesListModel = new OzlympicsListModel<Athlete>(model);
		athletesList.setModel(athletesListModel);
	}

	public void setOfficialsModel(OzlympicsListModel<Official> model) {
		officialsListModel = model;
		officialsList.setModel(model);
	}

	/**
	 * Returns the values of the form as a hash table, where the keys of the
	 * hash table are the field names and the values are the field values
	 */
	public Map<String, String> getModelMap() {
		resetValidationMessages();

		Map<String, String> model = new HashMap<String, String>();
		int selectedIndex = -1;

		if (theGames != null) {
			selectedIndex = gamesList.getSelectedIndex();
			if (selectedIndex >= 0) {
				model.put("GAME", theGames[selectedIndex].getId());
			}
		} else {
			model.put("SPORT", sportList.getSelectedItem().toString());
		}

		selectedIndex = officialsList.getSelectedIndex();
		if (selectedIndex >= 0) {
			Official theOfficial = (Official) officialsListModel
					.getElementAt(selectedIndex);
			model.put("OFFICIAL", theOfficial.getId());
		}

		int[] selectedIndices = athletesList.getSelectedIndices();
		if (selectedIndices.length > 0) {
			// Create a string of Athlete IDs separated by pipe "|"
			StringBuilder ids = new StringBuilder();
			for (int index : selectedIndices) {
				if (ids.length() > 0) {
					ids.append("|");
				}
				ids.append(((Participant) athletesListModel.getElementAt(index))
						.getId());
			}

			model.put("ATHLETES", ids.toString());
		}

		return model;
	}

	/*
	 * Helper method to create panels.
	 */
	private JPanel createPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setMinimumSize(new Dimension(200, 200));
		panel.setMaximumSize(new Dimension(400, 300));
		return panel;
	}

	/*
	 * Helper method to filter athletes by sport.
	 */
	private OzlympicsListModel<Athlete> filterAthletesBySport(Sport sport) {
		List<Athlete> filteredList = new ArrayList<Athlete>();
		for (Athlete item : theAthletes) {
			if (item.getSportPlayed() == sport
					|| item.getSportPlayed() == Sport.TRIATHLON) {
				filteredList.add(item);
			}
		}

		return new OzlympicsListModel<Athlete>(filteredList);
	}

	/*
	 * Helper method to update the Athlete List by filtering according to the
	 * selected sport.
	 */
	private void updateAthletesListModel(Sport sport) {
		if (sport == Sport.NONE) {
			athletesListModel = new OzlympicsListModel<Athlete>(theAthletes);
			athletesList.setModel(athletesListModel);
		} else {
			athletesListModel = filterAthletesBySport(sport);
			athletesList.setModel(athletesListModel);
		}
	}

	private void highlightRegisteredOfficialsAndAthletes(Game theGame) {
		Official currentOfficial = theGame.getOfficial();
		if (currentOfficial != null) {
			officialsList.setSelectedValue(currentOfficial, true);
		} else {
			officialsList.setSelectedIndex(-1);
		}

		athletesList.setSelectedIndex(-1);
		Athlete[] currentAthletes = theGame.getAthletes();
		if (currentAthletes.length > 0) {
			int[] selectedIndices = new int[currentAthletes.length];
			for (int i = 0; i < currentAthletes.length; i++) {
				selectedIndices[i] = athletesListModel
						.indexOf(currentAthletes[i]);
			}
			athletesList.setSelectedIndices(selectedIndices);
		}
	}

	/*
	 * Class to listen to changes from components that involve sport, such as
	 * the SportList or GamesList (a Game specifies its sport). Then the
	 * ListModel for Athletes is updated with a filtered list by sport.
	 */
	private class SportListActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Sport sportToFilter = (Sport) sportList.getSelectedItem();

			updateAthletesListModel(sportToFilter);
		}
	}

	private class GamesListSelectionListener implements ListSelectionListener {

		@Override
		public void valueChanged(ListSelectionEvent e) {
			// When the JList for games is updated or the model is initialized
			// for the JList, it often fires this event. We need to ensure an
			// item has been selected.
			if (gamesList.getSelectedValue() != null) {
				Game theGame = (Game) gamesList.getSelectedValue();
				updateAthletesListModel(theGame.getSportPlayed());
				highlightRegisteredOfficialsAndAthletes(theGame);
			}
		}

	}
}
