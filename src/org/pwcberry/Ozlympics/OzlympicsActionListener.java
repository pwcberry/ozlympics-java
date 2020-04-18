package org.pwcberry.Ozlympics;

/**
 * This class provides event handlers for events within the UI, usually passed
 * to a main controller object or service bus.
 */
public interface OzlympicsActionListener {
	void panelReadyToSwitch(OzlympicsActionEvent e);

	void panelInitialized(OzlympicsActionEvent e);

	void actionPerformed(OzlympicsActionEvent e);
}
