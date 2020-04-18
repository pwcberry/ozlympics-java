package org.pwcberry.Ozlympics;

import java.io.*;
import java.util.*;

/**
 * This class provides functionality to read from a text file saved Ozlympics
 * data.
 */
public class OzlympicsTextFileReader extends OzlympicsFile implements
		OzlympicsFileReader {

	// Instance variables
	boolean success;
	OzlympicsManager manager;
	HashMap<String, String> idMapper;
	int lineNumber;

	/**
	 * Default constructor.
	 */
	public OzlympicsTextFileReader() {
		success = false;
		idMapper = new HashMap<String, String>();
		lineNumber = 0;
		intializeErrorWriter("reader_errors.txt");
	}

	@Override
	public OzlympicsManager load(String location) {
		manager = new OzlympicsManager();

		try {
			BufferedReader reader = new BufferedReader(new FileReader(new File(
					location)));
			parseDocument(reader);

			reader.close();

			success = true;

			closeErrorWriter();

		} catch (IOException ioException) {
			writeErrorMessage(ErrorSeverity.FATAL, ioException);
		}

		return manager;
	}

	@Override
	public boolean getSuccess() {
		return success;
	}

	private void parseDocument(BufferedReader reader) {
		try {
			String line = reader.readLine();
			while (line != null) {
				lineNumber += 1;

				if (line.length() > 0) {
					parseLine(line);
				}
				line = reader.readLine();
			}
		} catch (IOException ioException) {
			writeErrorMessage(ErrorSeverity.ERROR, lineNumber, ioException);
		}
	}

	private void parseLine(String line) {
		String[] segments = line.split("\\|");
		if (segments.length > 0) {
			char entityCode = segments[0].charAt(0);
			switch (entityCode) {
			case 'A':
				parseAthlete(segments);
				break;
			case 'O':
				parseOfficial(segments);
				break;
			case 'C':
			case 'R':
			case 'S':
				parseGame(segments);
				break;
			default:
				writeErrorMessage(ErrorSeverity.WARNING, lineNumber,
						"Unknown code: '" + segments[0] + "'");
				break;
			}
		}
	}

	/*
	 * Add athletes to the Ozlympics Manager.
	 */
	private void parseAthlete(String[] segments) {
		String id = segments[0].trim();
		if (!OzlympicsManager.isIdValid(id, "Athlete")) {
			writeErrorMessage(ErrorSeverity.WARNING, lineNumber,
					"Athlete's 'id' is incorrect or not specified.");
		}

		String name = segments[1].trim();
		if (name == null || (name != null && name.length() == 0)) {
			writeErrorMessage(ErrorSeverity.WARNING, lineNumber,
					"Athlete's 'name' is not specified.");
		}

		String age = segments[2];
		if (!OzlympicsManager.isAgeValid(age)) {
			writeErrorMessage(ErrorSeverity.WARNING, lineNumber,
					"Athlete's 'age' is invalid or not specified");
		}

		String state = segments[3].trim();
		if (!OzlympicsManager.isStateValid(state)) {
			writeErrorMessage(ErrorSeverity.WARNING, lineNumber,
					"Athlete's 'state' is invalid or not specified");
		}

		String sportPlayed = segments[4].trim();
		if (!OzlympicsManager.isSportValid(sportPlayed)) {
			writeErrorMessage(ErrorSeverity.WARNING, lineNumber,
					"Athlete's 'sport' is invalid or not specified");
		}

		try {
			Participant athlete = (Participant) manager.addAthlete(name,
					Integer.parseInt(age), state,
					Sport.valueOf(sportPlayed.toUpperCase()));

			if (!idMapper.containsKey(id)) {
				idMapper.put(id, athlete.getId());
			}

		} catch (OzlympicsIsFullException ex) {
			writeErrorMessage(ErrorSeverity.ERROR, ex);
		}
	}

	/*
	 * Add officials to the Ozlympics Manager.
	 */
	private void parseOfficial(String[] segments) {
		String id = segments[0].trim();
		if (!OzlympicsManager.isIdValid(id, "Official")) {
			writeErrorMessage(ErrorSeverity.WARNING, lineNumber,
					"Official's 'id' is incorrect or not specified.");
		}

		String name = segments[1].trim();
		if (name == null || (name != null && name.length() == 0)) {
			writeErrorMessage(ErrorSeverity.WARNING, lineNumber,
					"Official's 'name' is not specified.");
		}

		String age = segments[2].trim();
		if (!OzlympicsManager.isAgeValid(age)) {
			writeErrorMessage(ErrorSeverity.WARNING, lineNumber,
					"Official's 'age' is invalid or not specified");
		}

		String state = segments[3].trim();
		if (!OzlympicsManager.isStateValid(state)) {
			writeErrorMessage(ErrorSeverity.WARNING, lineNumber,
					"Official's 'state' is invalid or not specified");
		}

		try {
			Official official = manager.addOfficial(name,
					Integer.parseInt(age), state);

			if (!idMapper.containsKey(id)) {
				idMapper.put(id, official.getId());
			}

		} catch (OzlympicsIsFullException ex) {
			writeErrorMessage(ErrorSeverity.ERROR, ex);
		}
	}

	/*
	 * Add games to the Ozlympics Manager.
	 */
	private void parseGame(String[] segments) {
		// Determine if Game has valid ID
		String id = segments[0].trim();
		if (!OzlympicsManager.isIdValid(id, "Game")) {
			writeErrorMessage(ErrorSeverity.WARNING, lineNumber,
					"Game 'id' is incorrect or not specified.");
		}

		// Ensure the Sport is valid
		String sportPlayed = segments[1].trim();
		if (!OzlympicsManager.isSportValid(sportPlayed)) {
			writeErrorMessage(ErrorSeverity.WARNING, lineNumber,
					"Athlete 'sport' is invalid or not specified");
		}

		// Add game, appoint official and register athletes
		try {
			Game theGame = manager.addGame(Sport.valueOf(sportPlayed
					.toUpperCase()));

			if (!idMapper.containsKey(id)) {
				idMapper.put(id, theGame.getId());
			}

			// Appoint official
			String officialId = idMapper.get(segments[2].trim());
			Official theOfficial = manager.findOfficial(officialId);
			if (theOfficial != null) {
				theGame.appointOfficial(theOfficial);
			}

			// Register competitors
			for (int i = 3; i < segments.length; i++) {
				String athleteId = idMapper.get(segments[i].trim());
				Athlete theAthlete = manager.findAthlete(athleteId);
				if (theAthlete != null) {
					theGame.addAthlete(theAthlete);
				}
			}
		} catch (GameIsFullException gameException) {
			writeErrorMessage(ErrorSeverity.ERROR, gameException);
		} catch (OzlympicsIsFullException fullException) {
			writeErrorMessage(ErrorSeverity.ERROR, fullException);
		} catch (Exception ex) {
			writeErrorMessage(ErrorSeverity.WARNING, ex);
		}
	}
}
