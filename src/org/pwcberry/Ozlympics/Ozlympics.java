package org.pwcberry.Ozlympics;

import java.util.*;
import java.awt.event.*;
import java.io.File;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;

/**
 * This class provides the entry point and the user interface for the Ozlympics
 * 2012 program.
 * 
 * Information on JFileChooser retrieved from
 * http://docs.oracle.com/javase/7/docs/api/javax/swing/JFileChooser.html.
 */
public class Ozlympics implements OzlympicsActionListener, ActionListener {

	/*
	 * Entry point for the application.
	 */
	public static void main(String[] args) {
		// Ignite the Ozlympics Flame!

		Ozlympics serviceBus;

		if (args.length > 0) {
			OzlympicsFileReader loader = new OzlympicsTextFileReader();
			OzlympicsManager manager = loader.load(args[0]);

			if (!loader.getSuccess()) {
				System.err.println("An error occurred while loading '" + args[0]
						+ "'. Please check the error log.");
				System.exit(1);
			}

			serviceBus = new Ozlympics(manager, new File(args[0]));
		} else {
			serviceBus = new Ozlympics();
		}

		serviceBus.launch();
	}

	// Instance variables
	private OzlympicsManager manager;
	private OzlympicsFrame mainFrame;
	private Map<String, ActionListener> controllers;
	private JPanel activePanel;
	private File managerFile;

	/**
	 * Default constructor.
	 */
	public Ozlympics() {
		this(new OzlympicsManager(), null);
	}

	public Ozlympics(OzlympicsManager manager, File file) {
		this.manager = manager;

		controllers = new HashMap<String, ActionListener>();

		managerFile = file;
	}

	public void launch() {
		mainFrame = new OzlympicsFrame(this);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		controllers.put("MAIN_PANEL", new MainPanelController(manager, this));
		controllers.put("ATHLETES", new AthletesController(manager, this));
		controllers.put("OFFICIALS", new OfficialsController(manager, this));
		controllers.put("GAMES", new GamesController(manager, this));

		mainFrame.setVisible(true);

		((MainPanelController) controllers.get("MAIN_PANEL")).showPanel();
	}

	/**
	 * A controller signals it is ready to switch to another panel. Only do so
	 * if the panel isn't already active.
	 */
	public void panelReadyToSwitch(OzlympicsActionEvent e) {
		JPanel panel = e.getPanelForAction();
		if (activePanel != panel) {
			activePanel = panel;
			mainFrame.show(panel);
		}
	}

	/**
	 * A controller signals that a panel has been initialized and requires
	 * addition to the main frame.
	 */
	public void panelInitialized(OzlympicsActionEvent e) {
		JPanel panel = e.getPanelForAction();
		mainFrame.add(panel, panel.getName());
	}

	/**
	 * Event handler for the Menu Items.
	 */
	public void actionPerformed(ActionEvent e) {
		MainPanelController mainController = (MainPanelController) controllers
				.get("MAIN_PANEL");
		AthletesController athletesController = (AthletesController) controllers
				.get("ATHLETES");
		OfficialsController officialsController = (OfficialsController) controllers
				.get("OFFICIALS");
		GamesController gamesController = (GamesController) controllers
				.get("GAMES");

		int actionCode = Integer.parseInt(e.getActionCommand());
		switch (actionCode) {
		case 11:
			createNewManager();
			break;
		case 12:
			openManager();
			break;
		case 13:
			saveManager();
			break;
		case 14:
			saveAndExit();
			break;
		case 15:
			System.exit(0);
			break;
		case 21:
			athletesController.add();
			break;
		case 22:
		case 33:
			mainController.showPanel();
			break;
		case 23:
		case 42:
			gamesController.addParticipants();
			break;
		case 31:
			gamesController.add();
			break;
		case 32:
			gamesController.run();
			break;
		case 41:
			officialsController.add();
			break;
		}
	}

	/**
	 * Event handler for controllers that raise events.
	 */
	public void actionPerformed(OzlympicsActionEvent e) {
		String command = e.getActionCommand();

		if (command.equals("GAMES_FINISHED")
				|| command.equals("SHOW_MAIN_PANEL")) {
			((MainPanelController) controllers.get("MAIN_PANEL")).showPanel();
		}
	}

	/*
	 * Start a new Ozlympics.
	 */
	private void createNewManager() {
		int result = JOptionPane.showConfirmDialog(mainFrame,
				"Are you sure you want to start a new Ozlympics?", "New",
				JOptionPane.YES_NO_OPTION);

		// Update controllers with new OzlympicsManager model.
		if (result == JOptionPane.YES_OPTION) {
			updateControllersWithNewManager(new OzlympicsManager());
		}
	}

	/*
	 * Replace the current OzlympicsManager with a saved file.
	 */
	private void openManager() {
		// Set up the File Choose to only recognize *.dat files,
		// starting with the last known file location.
		JFileChooser fileChooser = new JFileChooser(managerFile);
		fileChooser.addChoosableFileFilter(new DataFileFilter());
		fileChooser.setAcceptAllFileFilterUsed(false);

		// Load the manager, and display the data if successsful
		int result = fileChooser.showOpenDialog(mainFrame);
		if (result == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			OzlympicsFileReader loader = new OzlympicsTextFileReader();
			OzlympicsManager newManager = loader.load(file.getAbsolutePath());

			if (!loader.getSuccess()) {
				JOptionPane
						.showMessageDialog(
								mainFrame,
								"Unable to load Ozlympics file. Please see the error log file.",
								"File Load Error", JOptionPane.ERROR_MESSAGE);
			} else {
				manager = newManager;
				managerFile = file;

				updateControllersWithNewManager(manager);
			}
		}
	}

	/*
	 * Save the current OzlympicsManager to the previous file location.
	 */
	private void saveManager() {
		OzlympicsFileWriter writer = new OzlympicsTextFileWriter();
		writer.save(managerFile.getAbsolutePath(), manager);

		if (writer.getSuccess()) {
			JOptionPane.showMessageDialog(mainFrame, "Ozlympics saved.");
		} else {
			JOptionPane.showMessageDialog(mainFrame,
					"Unable to save Ozlympics. Please see the error log file.",
					"File Save Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	/*
	 * Save the current OzlympicsManager to the specified location, and exit the
	 * program.
	 */
	private void saveAndExit() {
		// Set up the File Choose to only recognize *.dat files,
		// starting with the last known file location.
		JFileChooser fileChooser = new JFileChooser(managerFile);
		fileChooser.addChoosableFileFilter(new DataFileFilter());
		fileChooser.setAcceptAllFileFilterUsed(false);
		fileChooser.setSelectedFile(managerFile);

		int result = fileChooser.showSaveDialog(mainFrame);

		// Save the manager and exit if successful
		if (result == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();

			OzlympicsFileWriter writer = new OzlympicsTextFileWriter();
			writer.save(file.getAbsolutePath(), manager);

			if (writer.getSuccess()) {
				System.exit(0);
			} else {
				JOptionPane
						.showMessageDialog(
								mainFrame,
								"Unable to save Ozlympics. Please see the error log file.",
								"File Save Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	/*
	 * Update the controllers with the specified OzlympicsManager, then display
	 * the main panel.
	 */
	private void updateControllersWithNewManager(OzlympicsManager manager) {
		((OzlympicsManagerListener) controllers.get("ATHLETES"))
				.managerUpdated(manager);
		((OzlympicsManagerListener) controllers.get("OFFICIALS"))
				.managerUpdated(manager);
		((OzlympicsManagerListener) controllers.get("GAMES"))
				.managerUpdated(manager);

		MainPanelController mainController = (MainPanelController) controllers
				.get("MAIN_PANEL");
		mainController.managerUpdated(manager);
		mainController.showPanel();
	}

	/*
	 * Helper class to filter only *.dat files for the FileChooser.
	 */
	private class DataFileFilter extends FileFilter {

		@Override
		public boolean accept(File f) {
			if (f == null) {
				return false;
			}

			String filename = f.getName();
			return filename.endsWith(".dat");
		}

		@Override
		public String getDescription() {
			return "DAT Text files";
		}
	}
}
