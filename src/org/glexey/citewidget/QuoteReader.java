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
public class QuoteReader {

	private String[] languageList;
	//private Context ctx;
	
	/**
	 * @param ctx 
	 * 
	 */
	public QuoteReader(Context ctx, int resID) {
		//this.ctx = ctx;

		// read the list of languages from the resources
		Resources res = ctx.getResources();
		languageList = res.getStringArray(resID);

	}

	/**
	 * @return
	 */
	public Cite GetNextQuote() {
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
	 * 
	 */
	public void ClearState() {
		// TODO Auto-generated method stub
		
	}

}
