package pt.fjss.TextViewWithLinks;

import java.io.Serializable;

import android.widget.TextView;

public class MessageSpan implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7514309961937880617L;
	Object obj;
	private TextView view;

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	public TextView getView() {
		return view;
	}

	public void setView(TextView view) {
		this.view = view;
	}
}
