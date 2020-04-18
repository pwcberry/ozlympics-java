package org.pwcberry.Ozlympics;

import java.io.*;

/**
 * This class forms the basis for file operations within Ozlympcis.
 */

public abstract class OzlympicsFile {

	PrintWriter errorWriter;

	/**
	 * Initialize the error logger for the subclass.
	 * @param errorWriterPath The file path to log errors to.
	 */
	protected void intializeErrorWriter(String errorWriterPath) {
		try {
			errorWriter = new PrintWriter(new File(errorWriterPath));
		} catch (FileNotFoundException ex) {
			System.err
					.println("Unable to initialize OzlympicsXmlFileManager error writer");
		}
	}
	
	/*
	 * Allow the subclass to close the error writer when processing has finished.
	 */
	protected void closeErrorWriter() {
		errorWriter.flush();
		errorWriter.close();
	}
	
	/*
	 * Write the exception to the error log.
	 */
	protected void writeErrorMessage(ErrorSeverity severity, Exception ex) {
		errorWriter.println(severity + ": " + ex.getClass().getSimpleName()
				+ ": " + ex.getMessage());
	}
	
	/*
	 * Write the exception to the error log with the specified line number.
	 */
	protected void writeErrorMessage(ErrorSeverity severity, int lineNumber, Exception ex) {
		errorWriter.println(severity + ": at line " + lineNumber + ". " + ex.getClass().getSimpleName()
				+ ": " + ex.getMessage());
	}
	
	/*
	 * Write the error message to the log with the specified line number.
	 */
	protected void writeErrorMessage(ErrorSeverity severity, int lineNumber, String message) {
		errorWriter.println(severity + ": at line " + lineNumber + ". " + message);
	}
}
