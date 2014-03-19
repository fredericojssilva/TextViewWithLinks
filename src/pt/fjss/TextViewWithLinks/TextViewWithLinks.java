package pt.fjss.TextViewWithLinks;

import android.content.Context;
import android.graphics.Color;
import android.text.Selection;
import android.text.Spannable;
import android.text.style.BackgroundColorSpan;
import android.text.style.URLSpan;
import android.text.util.Linkify;
import android.util.AttributeSet;
import android.widget.TextView;

public class TextViewWithLinks extends TextView {

	private BackgroundColorSpan color = new BackgroundColorSpan(Color.GREEN);

	OnClickLinksListener listener;

	public TextViewWithLinks(Context context) {
		super(context);
		
	}

	public TextViewWithLinks(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public TextViewWithLinks(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public void linkify(final OnClickLinksListener listener) {
		this.listener = listener;

		Linkify.addLinks(this, Linkify.WEB_URLS);

		setMovementMethod(LinkMovementMethodExt.getInstance(new LinkMovementMethodExt.LinkMovementListener() {

			@Override
			public void onKeyUpOnLink(MessageSpan msg) {
				MessageSpan ms = msg;
				TextView view = ms.getView();
				Spannable _span = (Spannable) view.getText();
				_span.removeSpan(color);
				view.setText(_span);
			}

			@Override
			public void onKeyDownOnTextView() {
				listener.onTextViewClick();
			}

			@Override
			public void onKeyDownOnLink(MessageSpan msg) {
				MessageSpan ms = msg;
				Object[] spans = (Object[]) ms.getObj();
				TextView view = ms.getView();

				for (Object span : spans) {
					if (span instanceof URLSpan) {
						int start = Selection.getSelectionStart(view.getText());
						int end = Selection.getSelectionEnd(view.getText());

						Spannable _span = (Spannable) view.getText();
						
						_span.setSpan(color, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
						view.setText(_span);

						listener.onLinkClick(((URLSpan) span).getURL());
					}
				}
			}
		}, URLSpan.class));
	}
	
	public void setLinkColors(int linkColor,int backgroundLinkColor)
	{
		color = new BackgroundColorSpan(backgroundLinkColor);
		this.setLinkTextColor(linkColor);
	}

	public interface OnClickLinksListener {
		public void onLinkClick(String url);

		public void onTextViewClick();
	}
}
