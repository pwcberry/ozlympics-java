package org.pwcberry.Ozlympics;

import java.io.*;

/**
 * This class provides functionality to read from a text file saved Ozlympics
 * data.
 */
public class OzlympicsTextFileWriter extends OzlympicsFile implements
		OzlympicsFileWriter {

	// Instance variables
	boolean success;

	/**
	 * Default constructor.
	 */
	public OzlympicsTextFileWriter() {
		success = false;
		intializeErrorWriter("writer_errors.txt");
	}

	@Override
	public void save(String location, OzlympicsManager manager) {
		try {
			PrintWriter writer = new PrintWriter(new File(location));

			writeManagerToDocument(manager, writer);

			writer.flush();
			writer.close();

			success = true;

			closeErrorWriter();

		} catch (IOException ioException) {
			writeErrorMessage(ErrorSeverity.FATAL, ioException);
		}
	}

	@Override
	public boolean getSuccess() {
		return success;
	}

	private void writeManagerToDocument(OzlympicsManager manager,
			PrintWriter writer) {

		Athlete[] athletes = manager.getAthletes();
		if (athletes.length > 0) {
			writeAthletesToDocument(athletes, writer);
		}

		Official[] officials = manager.getOfficials();
		if (officials.length > 0) {
			writeOfficialsToDocument(officials, writer);
		}

		Game[] games = manager.getGames();
		if (games.length > 0) {
			writeGamesToDocument(games, writer);
		}
	}

	private void writeGamesToDocument(Game[] games, PrintWriter writer) {
		for (Game theGame : games) {
			writer.print(theGame.getId());
			writer.print("|");
			writer.print(theGame.getSportPlayed());

			Official theOfficial = theGame.getOfficial();
			if (theOfficial != null) {
				writer.print("|");
				writer.print(theOfficial.getId());
			}

			Athlete[] athletes = theGame.getAthletes();
			for (Athlete theAthlete : athletes) {
				writer.print("|");
				writer.print(((Participant) theAthlete).getId());
			}

			writer.println();
		}
	}

	private void writeOfficialsToDocument(Official[] officials,
			PrintWriter writer) {
		for (Official theOfficial : officials) {
			writer.print(theOfficial.getId());
			writer.print("|");
			writer.print(theOfficial.getName());
			writer.print("|");
			writer.print(theOfficial.getAge());
			writer.print("|");
			writer.print(theOfficial.getState().toUpperCase());
			writer.println();
		}
	}

	private void writeAthletesToDocument(Athlete[] athletes, PrintWriter writer) {
		for (Athlete theAthlete : athletes) {
			Participant participant = (Participant) theAthlete;
			writer.print(participant.getId());
			writer.print("|");
			writer.print(participant.getName());
			writer.print("|");
			writer.print(participant.getAge());
			writer.print("|");
			writer.print(participant.getState().toUpperCase());
			writer.print("|");
			writer.print(theAthlete.getSportPlayed().toString());
			writer.println();
		}
	}
}
