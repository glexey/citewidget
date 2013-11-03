/**
 * 
 */
package org.glexey.citewidget;

import java.util.ArrayList;
import java.util.Locale;

import org.glexey.citewidget.Cite;
import org.glexey.citewidget.LangDB.LangDBException;

import android.content.Context;
import android.content.res.Resources;

/**
 * @author aagaidiu
 *
 */
public class LangDBManager {

	public static Cite stubCite = new Cite("No quotes left");
	public static final int historySize = 20; 
	
	private String[] languageList;
	private Context ctx;
	
	/**
	 * @param ctx 
	 * 
	 */
	public LangDBManager(Context ctx, int resID) {
		this.ctx = ctx;

		// read the list of languages from the resources
		Resources res = this.ctx.getResources();
		languageList = res.getStringArray(resID);
	}

	/**
	 * @return
	 */
	public Cite getNextQuote() {
		// TODO Auto-generated method stub
		return stubCite;
	}

	/** Get quote from specified language
	 * @param string
	 * @return
	 */
	public Cite getNextQuote(String lang) {
		Cite cite = getNextQuote(lang, "web");
		if (cite != null) return cite;
		return getNextQuote(lang, "fix");
	}

	/** Get next quote from DB specified by "lang.src".
	 *  If dictionary is empty, returns null.
	 * @param lang
	 * @param src
	 * @return
	 */
	public Cite getNextQuote(String lang, String src) {
		LangDB db = new LangDB(ctx, lang + "." + src);
		LangDB hist = new LangDB(ctx, "history");
		Cite cite;
		try {
			cite = db.shift();
			if (cite != null)
				hist.append(cite);
			return cite;
		} catch (LangDBException e) {
			e.printStackTrace();
			return null;
		}
	}

	/** Add a quote of given language to "web" dictionary
	 * @param string
	 * @param cite1
	 */
	public void addQuote(String lang, Cite cite) {
		LangDB db = new LangDB(ctx, lang + ".web");
		try {
			db.append(cite);
		} catch (LangDBException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return
	 */
	public String[] getlanguageList() {
		return languageList;
	}

	/**
	 * Delete existing dictionaries and then create new ones - for each language:
	 * 	1. "<lang>.fix" - populated from the resource string array
	 *  2. "<lang>.web" - empty
	 * In addition, create a language-independent, initially empty "history"
	 * dictionary, which will be appended on each getNextQuote() call.
	 */
	public void initFromScratch() {
		for (int i=0; i<languageList.length; i++) {
			String lang = languageList[i];
			LangDB fix_db = new LangDB(ctx, lang + ".fix");
			LangDB web_db = new LangDB(ctx, lang + ".web");
			int fixResId = ctx.getResources().getIdentifier(
					"CiteArr" + lang.toUpperCase(Locale.US), "id", ctx.getPackageName());
			try {
				fix_db.deleteDB();
				fix_db.createDB();
				web_db.deleteDB();
				web_db.createDB();
				if (fixResId != 0) fix_db.updateFromResource(fixResId);
			} catch (LangDBException e) {
				e.printStackTrace(); // STUB
			}
		}
		LangDB hist_db = new LangDB(ctx, "history");
		try {
			hist_db.deleteDB();
			hist_db.createDB();
		} catch (LangDBException e) {
			e.printStackTrace(); // STUB
		}
	}

	/**
	 * @return
	 */
	public ArrayList<Cite> getQuoteHistory() {
		// TODO Auto-generated method stub
		return null;
	}

}
