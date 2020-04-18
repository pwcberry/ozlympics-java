package org.pwcberry.Ozlympics;

import java.awt.*;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;

import org.pwcberry.Ozlympics.Game.GameResult;

@SuppressWarnings("serial")
public class MainPanel extends JPanel {

	JTextArea athleteTextArea;
	JTextArea officialTextArea;
	JTextArea gameResultsTextArea;
	JTextArea gamesNotStartedTextArea;
	JTable athleteResultsTable;

	public MainPanel() {
		super(new GridLayout(2, 2, 20, 20));

		this.setName("MAIN_PANEL");

		initializeComponent();
	}

	private void initializeComponent() {
		createAthletesPointsPane();
		createOfficialsPane();
		createGameResultsPane();
		createGamesNotStartedPane();
	}

	private void createAthletesPointsPane() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setMinimumSize(new Dimension(200, 200));
		panel.setMaximumSize(new Dimension(400, 300));

		JLabel label = new JLabel("Athletes Points Table");
		panel.add(label);

		athleteResultsTable = new JTable();
		athleteResultsTable.setName("Athletes_Points_Table");
		athleteResultsTable.setColumnSelectionAllowed(false);
		athleteResultsTable.setRowSelectionAllowed(false);
		athleteResultsTable.setAutoscrolls(true);

		JScrollPane scrollPane = new JScrollPane(athleteResultsTable,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		panel.add(scrollPane);

		add(panel, BorderLayout.NORTH);
	}

	private void createOfficialsPane() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setMinimumSize(new Dimension(200, 200));
		panel.setMaximumSize(new Dimension(400, 300));

		JLabel label = new JLabel("Registered Officials");
		panel.add(label);

		officialTextArea = new JTextArea(20, 60);
		officialTextArea.setName("Registered_Officials_officialTextArea");
		officialTextArea.setAutoscrolls(true);
		officialTextArea.setAlignmentX(LEFT_ALIGNMENT);
		officialTextArea.setAlignmentY(TOP_ALIGNMENT);
		officialTextArea.setEditable(false);

		JScrollPane scrollPane = new JScrollPane(officialTextArea,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		panel.add(scrollPane);

		add(panel, BorderLayout.EAST);
	}

	private void createGameResultsPane() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setMinimumSize(new Dimension(200, 200));
		panel.setMaximumSize(new Dimension(400, 300));

		JLabel label = new JLabel("Game Results");
		panel.add(label);

		gameResultsTextArea = new JTextArea(20, 60);
		gameResultsTextArea.setName("Game_Results_gameResultsTextArea");
		gameResultsTextArea.setAutoscrolls(true);
		gameResultsTextArea.setAlignmentX(LEFT_ALIGNMENT);
		gameResultsTextArea.setAlignmentY(TOP_ALIGNMENT);
		gameResultsTextArea.setEditable(false);

		JScrollPane scrollPane = new JScrollPane(gameResultsTextArea,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		panel.add(scrollPane);

		add(panel, BorderLayout.WEST);
	}

	private void createGamesNotStartedPane() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setMinimumSize(new Dimension(200, 200));
		panel.setMaximumSize(new Dimension(400, 300));

		JLabel label = new JLabel("Games Not Started");
		panel.add(label);

		gamesNotStartedTextArea = new JTextArea(20, 60);
		gamesNotStartedTextArea
				.setName("Games_Not_Started_gamesNotStartedTextArea");
		gamesNotStartedTextArea.setAutoscrolls(true);
		gamesNotStartedTextArea.setAlignmentX(LEFT_ALIGNMENT);
		gamesNotStartedTextArea.setAlignmentY(TOP_ALIGNMENT);
		gamesNotStartedTextArea.setEditable(false);

		JScrollPane scrollPane = new JScrollPane(gamesNotStartedTextArea,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		panel.add(scrollPane);

		add(panel, BorderLayout.SOUTH);
	}

	public void setAthleteModel(Athlete[] model) {
		AthleteResultsTableModel tableModel = new AthleteResultsTableModel(model);
		athleteResultsTable.setModel(tableModel);
	}

	public void setOfficialsModel(Official[] model) {
		if (model.length > 0) {
			StringBuilder builder = new StringBuilder();

			for (Official official : model) {
				builder.append(official).append("\n");

				officialTextArea.setText(builder.toString());
			}
		} else {
			officialTextArea.setText("No officials recorded.");
		}
	}

	public void setGameResultsModel(Game[] model) {
		if (model.length > 0) {
			StringBuilder builder = new StringBuilder();

			for (Game game : model) {
				builder.append(game.getSportPlayed().toString()).append(" ")
						.append(game.getId()).append("\n")
						.append("-------------------------------------\n");

				if (game.getGameState() == GameState.FINISHED) {
					if (game.getOfficial() != null) {
						builder.append("Official:\n")
								.append(game.getOfficial()).append("\n\n");
					}

					builder.append("Results:\n");
					GameResult[] results = game.getResults();
					for (int i = 0; i < results.length; i++) {
						builder.append(i + 1).append(": ")
								.append(results[i].getTime()).append("s - ")
								.append(results[i].getAthlete()).append("\n");
					}
				} else {
					builder.append("GAME CANCELLED, due to:\n");
					if (game.getOfficial() == null) {
						builder.append("No official was appointed.\n");
					}

					int athleteCount = game.getAthletes().length;
					if (athleteCount < Game.MAX_ATHLETES_PER_GAME) {
						builder.append("Not enough athletes (" + athleteCount
								+ " were registered).\n");
					}
				}

				builder.append("\n");
			}

			gameResultsTextArea.setText(builder.toString());
		} else {
			gameResultsTextArea.setText("No games started.");
		}
	}

	public void setGamesNotStartedModel(Game[] model) {
		if (model.length > 0) {
			StringBuilder builder = new StringBuilder();

			for (Game game : model) {
				builder.append(game.getSportPlayed().toString()).append(" ")
						.append(game.getId()).append("\n")
						.append("-------------------------------------\n");

				if (game.getOfficial() != null) {
					builder.append("Official:\n").append(game.getOfficial())
							.append("\n\n");
				} else {
					builder.append("No official registered.\n");
				}

				Athlete[] athletes = game.getAthletes();
				if (athletes.length > 0) {
					builder.append("Athletes:\n");
					for (Athlete a : athletes) {
						builder.append(a).append("\n");
					}
				} else {
					builder.append("No athletes registered.\n");
				}

				builder.append("\n");
			}
			gamesNotStartedTextArea.setText(builder.toString());
		} else {
			gamesNotStartedTextArea.setText("No games registered.");
		}
	}

	private class AthleteResultsTableModel extends AbstractTableModel {

		private String[] columnNames = { "Position", "Score", "Athlete" };

		Athlete[] data;

		public AthleteResultsTableModel(Athlete[] data) {
			this.data = data;
		}

		public String getColumnName(int column){
			return columnNames[column];
		}
		
		@Override
		public int getColumnCount() {
			return columnNames.length;
		}

		@Override
		public int getRowCount() {
			return data.length;
		}

		@Override
		public Object getValueAt(int row, int column) {
			switch (column) {
			case 0:
				return (row + 1);
			case 1:
				return data[row].getScore();
			default:
				return data[row].toString();
			}
		}
	}
}
