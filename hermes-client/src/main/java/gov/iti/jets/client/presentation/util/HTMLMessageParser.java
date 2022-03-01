package gov.iti.jets.client.presentation.util;

import java.sql.Timestamp;
import java.util.StringTokenizer;

import javafx.geometry.Insets;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public enum HTMLMessageParser {
	INSTANCE;

	public VBox formatMessage(String htmlMessage, Timestamp messageTimestamp) {
		String textColor = "";
		String bgColor = "";
		VBox messageVBox = new VBox();
		Text messageTime = new Text(messageTimestamp.toString());
		messageTime.setStyle("-fx-font-size: 8;");
		System.out.println(htmlMessage);

		TextFlow parentTextFlow = new TextFlow();
		if (!htmlMessage.contains("span")) // add html field handling bonus
			return null;
		if (htmlMessage.contains("background-color"))
			bgColor = "#" + htmlMessage.substring(
					htmlMessage.indexOf("background-color: 0x") + "background-color: 0x".length(),
					htmlMessage.indexOf(";"));
		if (htmlMessage.contains(" color"))
			textColor = "#" + htmlMessage.substring(htmlMessage.indexOf(" color: 0x") + " color: 0x".length(),
					htmlMessage.indexOf("; \">"));
		for (String currentSpanToken : htmlMessage.split("span")) {
			if (!currentSpanToken.contains("style") || currentSpanToken.contains("p"))
				continue;
			String messageStyle = currentSpanToken.substring(currentSpanToken.indexOf("style=") + 7,
					currentSpanToken.indexOf("\">"));
			String messageContent = currentSpanToken.substring(currentSpanToken.indexOf(">") + 1,
					currentSpanToken.indexOf("<"));
			Text textMessage = new Text(messageContent);
			if (messageContent.isBlank())
				return null;
			TextFlow messageTextFlow = new TextFlow(textMessage);
			StringTokenizer st = new StringTokenizer(messageStyle, ";");
			int index = 0;
			String style = "";
			while (st.hasMoreTokens()) {
				String currentToken = st.nextToken();
				if (!currentToken.contains("&quot")) {
					if (index == 0) {
						style = "-fx-" + currentToken;
					} else {
						style = "-fx-" + currentToken.substring(1);
					}
					if (style.contains("font-family")) {
						textMessage.setStyle("-fx-font-weight: bold");
					} else if (style.contains("size")) {
						if (style.contains("x-small")) {
							textMessage.setStyle("-fx-font-size: 8;");
						} else if (style.contains("small")) {
							textMessage.setStyle("-fx-font-size: 10;");
						} else if (style.contains("xxx-large")) {
							textMessage.setStyle("-fx-font-size: 36;");
						} else if (style.contains("xx-large")) {
							textMessage.setStyle("-fx-font-size: 24;");
						} else if (style.contains("x-large")) {
							textMessage.setStyle("-fx-font-size: 18;");
						} else if (style.contains("large")) {
							textMessage.setStyle("-fx-font-size: 14;");
						} else {
							textMessage.setStyle("-fx-font-size: 12;");
						}
					} else if (style.contains("underline")) {
						textMessage.setStyle("-fx-underline: true");
					} else {
						messageTextFlow.setStyle(style);
					}
				}
				if (!textColor.equals(""))
					textMessage.setFill(Paint.valueOf(textColor));
				index++;
			}
			parentTextFlow.getChildren().add(messageTextFlow);
		}
		// parentTextFlow.setPadding(new Insets(5, 10, 5, 10));
		messageVBox.getChildren().add(parentTextFlow);
		messageVBox.getChildren().add(messageTime);
		if (!bgColor.equals("")){
			messageVBox.setStyle("-fx-background-radius: 10;-fx-padding: 5 ;-fx-background-color: " + bgColor);
			// messageVBox.setStyle("");
		}
		return messageVBox;

	}

	private Color colorParser(String htmlStyle) {
		String red = htmlStyle.substring("-fx-color: rgb(".length(), htmlStyle.indexOf(","));
		String green = htmlStyle.substring(htmlStyle.indexOf(",") + 2, htmlStyle.lastIndexOf(","));
		String blue = htmlStyle.substring(htmlStyle.lastIndexOf(",") + 2, htmlStyle.lastIndexOf(")"));
		return Color.rgb(Integer.valueOf(red), Integer.valueOf(green), Integer.valueOf(blue));
	}

	private String toRGBCode(Color color) {
		return String.format("#%02X%02X%02X",
				(int) (color.getRed() * 255),
				(int) (color.getGreen() * 255),
				(int) (color.getBlue() * 255));
	}

}
