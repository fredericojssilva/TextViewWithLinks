TextViewWithLinks
=================

TextView With Link handler

##Usage
###JAVA
```
    String text = "bananas on www.bananas.ba and  <a href=\"http://google.com/\">Google</a>";
		
		TextViewWithLinks textview = new TextViewWithLinks(this);
		
		textview.setText(Html.fromHtml(text));
	
		textview.linkify(new TextViewWithLinks.OnClickLinksListener() {
			
			@Override
			public void onTextViewClick() {
				// Do whatever you want
			}
			
			@Override
			public void onLinkClick(String url) {
				// Do whatever you want
			}
		});
		
		//SET Colors
		textview.setLinkColors(Color.RED, Color.BLACK);
		
		setContentView(textview);
```
