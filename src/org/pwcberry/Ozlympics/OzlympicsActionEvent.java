package org.pwcberry.Ozlympics;

import java.util.*;
import javax.swing.*;

/**
 * This class is the event object expected by OzlympicsActionListener.
 */
@SuppressWarnings("serial")
public class OzlympicsActionEvent extends EventObject {

	private JPanel panel;
	private String actionCommand;

	public OzlympicsActionEvent(Object source) {
		this(source, "switchPanel");
	}
	
	public OzlympicsActionEvent(Object source, String actionCommand) {
		super(source);
		this.actionCommand = actionCommand;
	}

	public void setPanelForAction(JPanel panel) {
		this.panel = panel;
	}

	public JPanel getPanelForAction() {
		return panel;
	}
	
	public String getActionCommand() {
		return actionCommand;
	}
}
