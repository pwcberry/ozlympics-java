package org.pwcberry.Ozlympics;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * This class provides the window for Ozlympics.
 */

@SuppressWarnings("serial")
public class OzlympicsFrame extends JFrame {

	ActionListener controller;
	JMenuBar menuBar;

	public OzlympicsFrame(ActionListener controller) {
		super("Ozlympics Frame");

		this.controller = controller;

		// Using "CardLayout" so the Swing framework
		// handles the switching of panels (views).
		getContentPane().setLayout(new CardLayout());

		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolkit.getScreenSize();
		int windowHeight = (int) ((double) screenSize.height * 0.75);
		int windowWidth = (int) ((double) screenSize.width * 0.75);
		setMinimumSize(new Dimension(windowWidth, windowHeight));

		setupMenu();
	}

	/**
	 * Switch to the specified panel.
	 */
	public void show(JPanel panel) {
		CardLayout layout = (CardLayout) getContentPane().getLayout();
		layout.show(getContentPane(), panel.getName());
	}

	/*
	 * Create the menu structure for the window.
	 */
	private void setupMenu() {
		menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);

		// Set the File menu
		JMenu fileMenu = new JMenu("File");
		fileMenu.add(addMenuItem("New", "11"));
		fileMenu.add(addMenuItem("Open", "12"));
		fileMenu.addSeparator();
		fileMenu.add(addMenuItem("Save", "13"));
		fileMenu.add(addMenuItem("Save and Exit", "14"));
		fileMenu.addSeparator();
		fileMenu.add(addMenuItem("Exit", "15"));
		menuBar.add(fileMenu);

		// Set the Athletes menu
		JMenu athletesMenu = new JMenu("Athletes");
		athletesMenu.add(addMenuItem("Add...", "21"));
		athletesMenu.add(addMenuItem("Display Points", "22"));
		athletesMenu.addSeparator();
		athletesMenu.add(addMenuItem("Add to game...","23"));
		menuBar.add(athletesMenu);

		// Set the Games menu
		JMenu gamesMenu = new JMenu("Games");
		gamesMenu.add(addMenuItem("Add...", "31"));
		gamesMenu.addSeparator();
		gamesMenu.add(addMenuItem("Run...", "32"));
		gamesMenu.add(addMenuItem("Display Results", "33"));
		menuBar.add(gamesMenu);

		// Set the Officials menu
		JMenu officialsMenu = new JMenu("Officials");
		officialsMenu.add(addMenuItem("Add...", "41"));
		officialsMenu.addSeparator();
		officialsMenu.add(addMenuItem("Add to game...","42"));
		menuBar.add(officialsMenu);
	}

	/*
	 * Helper method to create menu items and add action listeners to them.
	 */
	private JMenuItem addMenuItem(String itemText, String commandName) {
		JMenuItem menuItem = new JMenuItem(itemText);
		menuItem.setActionCommand(commandName);
		menuItem.addActionListener(controller);
		menuItem.setPreferredSize(new Dimension(200, 24));
		return menuItem;
	}
}
