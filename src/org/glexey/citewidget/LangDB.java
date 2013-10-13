package org.glexey.citewidget;

import android.content.Context;

/**
 * @author aagaidiu
 *
 */
/**
 * @author aagaidiu
 *
 */
public class LangDB {

	// Language identifier, for tracking purposes. E.g.: "ru"
	public String language;
	
	public LangDB(String language) {
		this.language = language;
	}

	/**
	 * Create a new empty database file. If database file exists already, first remove it.
	 */
	public void createDB() {
		// TODO Auto-generated method stub
		deleteDB();
	}

	public void deleteDB() {
		// TODO Auto-generated method stub
		// This method deletes the database file, if it exists
		if (!dbExists()) return;
	}
	
	public int length() throws LangDBException {
		if (!dbExists()) throw new LangDBException("DB does not exist");
		// TODO stub
		return(0);
	}

	public boolean dbExists() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Read string array specified by resID, convert them to Cite objects and 
	 * insert them into the database.  
	 * @param ctx    - Context to get the resources from
	 * @param resid  - String array resource ID
	 */
	public void updateFromResource(Context ctx, int resID) {
		// TODO Auto-generated method stub
		
	}

	
	/**
     * Thrown when there were problems accessing the database
     * 	- not initialized
     *  - no space left to append to the DB
     */
    public static class LangDBException extends Exception {
		private static final long serialVersionUID = 1L;

		public LangDBException(String detailMessage, Throwable throwable) {
            super(detailMessage, throwable);
        }

        public LangDBException(String detailMessage) {
            super(detailMessage);
        }
    }


	/**
	 * @param cite
	 */
	public void append(Cite cite) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @param i
	 * @return
	 */
	public Cite get(int i) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @return
	 */
	public Cite shift() {
		// TODO Auto-generated method stub
		return get(0);
	}

}
