package gov.iti.jets.client.presentation.util;

import java.util.StringTokenizer;

import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public enum HTMLMessageParser {
    INSTANCE;

    public TextFlow formatMessage(String htmlMessage) {
        //bold didn't work with font-size
		TextFlow parentTextFlow = new TextFlow();
		if (!htmlMessage.contains("span"))
			return null;
		for (String currentSpanToken: htmlMessage.split("span")) {
			if(!currentSpanToken.contains("style")) continue;
			String messageStyle = currentSpanToken.substring(currentSpanToken.indexOf("style=") + 7, currentSpanToken.indexOf("\">"));
			String messageContent = currentSpanToken.substring(currentSpanToken.indexOf(">")+1, currentSpanToken.indexOf("<"));
			Text textMessage = new Text(messageContent);
			if (messageContent.isBlank())
				return null;
			TextFlow messageTextFlow = new TextFlow(textMessage);
			StringTokenizer st = new StringTokenizer(messageStyle, ";");
			int index = 0;
			String style = "";
			while (st.hasMoreTokens()) {
				String currentToken = st.nextToken();
				if (!(currentToken.equals(" font-family: &quot") || currentToken.equals("font-family: &quot") || currentToken.equals("&quot"))) {
					if (index == 0) {
						style = "-fx-" + currentToken;
					} else {
						style = "-fx-" + currentToken.substring(1);
					}
					if (style.contains("-fx-color:")) {
						textMessage.setFill(colorParser(style));
					} else if (style.contains("underline")) {
						textMessage.setStyle("-fx-underline: true");
					} else
						messageTextFlow.setStyle(style);
				}
				index++;
			}
			parentTextFlow.getChildren().add(messageTextFlow);
		}
		return parentTextFlow;

	}

    private Color colorParser(String htmlStyle){
        String red = htmlStyle.substring("-fx-color: rgb(".length(), htmlStyle.indexOf(","));
        String green = htmlStyle.substring(htmlStyle.indexOf(",") + 2, htmlStyle.lastIndexOf(","));
        String blue = htmlStyle.substring(htmlStyle.lastIndexOf(",") + 2, htmlStyle.lastIndexOf(")"));
        return Color.rgb(Integer.valueOf(red), Integer.valueOf(green), Integer.valueOf(blue));

    }

}
