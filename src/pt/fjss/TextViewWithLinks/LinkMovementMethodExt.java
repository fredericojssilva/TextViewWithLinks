package pt.fjss.TextViewWithLinks;

import java.io.Serializable;

import android.text.Layout;
import android.text.Selection;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;

public class LinkMovementMethodExt extends LinkMovementMethod{

	private static LinkMovementMethod sInstance;

	private Class spanClass = null;

	static LinkMovementListener listener;

	public static MovementMethod getInstance(LinkMovementListener _listener, Class _spanClass) {
		if (sInstance == null) {
			sInstance = new LinkMovementMethodExt();
			listener = _listener;
			((LinkMovementMethodExt) sInstance).spanClass = _spanClass;
		}

		return sInstance;
	}

	@Override
	public boolean onTouchEvent(TextView widget, Spannable buffer, MotionEvent event) {
		int action = event.getAction();
		
		int mDownX = 0, mDownY = 0;

		if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_DOWN) {
			int x = (int) event.getX();
			int y = (int) event.getY();
			
			mDownX = x;
			mDownY = y;

			x -= widget.getTotalPaddingLeft();
			y -= widget.getTotalPaddingTop();

			x += widget.getScrollX();
			y += widget.getScrollY();

			Layout layout = widget.getLayout();
			int line = layout.getLineForVertical(y);
			int off = layout.getOffsetForHorizontal(line, x);

			// get spans
			Object[] spans = buffer.getSpans(off, off, spanClass);
			if (spans.length != 0) {
				if (action == MotionEvent.ACTION_DOWN) {
					Selection
							.setSelection(buffer, buffer.getSpanStart(spans[0]), buffer.getSpanEnd(spans[0]));
					MessageSpan obj = new MessageSpan();
					obj.setObj(spans);
					obj.setView(widget);

					listener.onKeyDownOnLink(obj);

					return true;
				} else if (action == MotionEvent.ACTION_UP) {
					MessageSpan obj = new MessageSpan();
					obj.setView(widget);
					obj.setObj(spans);
					Log.d("FRED","ActionUp");
					listener.onKeyUpOnLink(obj);
					return true;
				}
				
				
			} else {
				if (action == MotionEvent.ACTION_UP) {

					listener.onKeyDownOnTextView();
				}
				
			}
		}
		else
		{
			if (Math.abs(mDownY - event.getY()) > 100)
			listener.onScroll(widget);
		}

		return super.onTouchEvent(widget, buffer, event);
	}

	public interface LinkMovementListener {
		void onKeyDownOnLink(MessageSpan msg);

		void onKeyUpOnLink(MessageSpan msg);

		void onKeyDownOnTextView();
		
		void onScroll(TextView widget);
	}
}
