/**
 * 
 */
package org.glexey.citewidget;

import java.util.ArrayList;

import org.glexey.citewidget.Cite;

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
	//private Context ctx;
	
	/**
	 * @param ctx 
	 * 
	 */
	public LangDBManager(Context ctx, int resID) {
		//this.ctx = ctx;

		// read the list of languages from the resources
		Resources res = ctx.getResources();
		languageList = res.getStringArray(resID);

	}

	/**
	 * @return
	 */
	public Cite getNextQuote() {
		// TODO Auto-generated method stub
		return stubCite;
	}

	/**
	 * @param string
	 * @return
	 */
	public Cite getNextQuote(String lang) {
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		
	}

	/**
	 * @param string
	 * @param cite1
	 */
	public void addQuote(String lang, Cite cite) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @return
	 */
	public ArrayList<Cite> getQuoteHistory() {
		// TODO Auto-generated method stub
		return null;
	}

}
