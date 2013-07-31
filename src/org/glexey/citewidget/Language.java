package org.glexey.citewidget;

import java.util.ArrayList;

import java.util.List;

import android.content.res.Resources;
import android.util.Log;

public class Language {
	public List<Cite> citeList = new ArrayList<Cite>();
	private int cite_arr_id = -1;
	private int last_cite_id = -1;
	private Resources res;
	private Vars vars;
	public static final String TAG = org.glexey.citewidget.CiteAppWidgetProvider.TAG;

	public Language(Vars vars, Resources res, int cite_arr_id) {
		super();
		this.res = res;
        this.vars = vars;
		this.cite_arr_id = cite_arr_id;
        last_cite_id = vars.get("last_cite_id_" + cite_arr_id, -1);
        Log.d(TAG, "constructor :: Language(cite_arr_id="+cite_arr_id+"); last_cite_id="+last_cite_id);
	}

	public Cite getNextCite() {
		if (citeList.size() == 0)
			loadCiteList();
		if (++last_cite_id >= citeList.size())
			last_cite_id = 0;
		vars.put("last_cite_id_" + cite_arr_id, last_cite_id);
		if (citeList.size() > 0)
			return citeList.get(last_cite_id);
		else
			return new Cite("CiteList.size() == 0|Error");
	}

	private void loadCiteList() {
		try {
			String[] quotes = res.getStringArray(cite_arr_id);
			for (int i = 0; i < quotes.length; i++)
				citeList.add(new Cite(quotes[i]));
		} catch(Exception e) {
				citeList.add(new Cite(e.getLocalizedMessage() + "\ncite_arr_id=" + cite_arr_id + "|loadCiteList()"));
		}
	}
}
