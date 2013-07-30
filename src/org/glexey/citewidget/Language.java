package org.glexey.citewidget;

import java.util.ArrayList;
import java.util.List;

import android.content.res.Resources;

public class Language {
	public List<Cite> citeList = new ArrayList<Cite>();
	private int cite_arr_id = -1;
	private int last_cite_id = -1;
	private Resources res;

	public Language(Resources res, int cite_arr_id) {
		super();
		this.res = res;
		this.cite_arr_id = cite_arr_id;
	}

	public Cite getNextCite() {
		if (citeList.size() == 0)
			loadCiteList();
		if (++last_cite_id >= citeList.size())
			last_cite_id = 0;
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
