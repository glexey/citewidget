/**
 * 
 */
package org.glexey.citewidget;

import org.glexey.citewidget.Cite;

import android.content.Context;
import android.content.res.Resources;

/**
 * @author aagaidiu
 *
 */
public class LangDBManager {

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
		return new Cite("No quotes left");
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

}
