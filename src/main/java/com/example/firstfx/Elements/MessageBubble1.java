package com.example.firstfx.Elements;

import javafx.animation.PauseTransition;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.util.Duration;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageBubble1 extends AnchorPane {

    private static final String CODE_FONT_FAMILY = "Consolas"; // Example of a typical code font
    private static final double FONT_SIZE = 12.0;

    public MessageBubble1(String messageText, boolean isSentByMe) {
        setPadding(new Insets(10));

        Background messageBackground = new Background(
                new BackgroundFill(isSentByMe ? Color.LIGHTGRAY : Color.WHITE, new CornerRadii(10), Insets.EMPTY)
        );

        String[] parts = messageText.split("```");
        boolean isCodeBlock = false;

        VBox messageBox = new VBox();
        messageBox.setSpacing(5);
        messageBox.setPadding(new Insets(10));
        messageBox.setBackground(messageBackground);

        for (String part : parts) {
            if (isCodeBlock) {
                BorderPane codeFlow = createCodeBlock(part, isSentByMe);
                messageBox.getChildren().add(codeFlow);
            } else {
                TextFlow textFlow = createFormattedText(part);
                textFlow.setMaxWidth(400);
                textFlow.setPadding(new Insets(10));
                messageBox.getChildren().add(textFlow);
            }
            isCodeBlock = !isCodeBlock;
        }

        getChildren().add(messageBox);

        if (isSentByMe) {
            AnchorPane.setRightAnchor(messageBox, 10.0);
            setStyle("-fx-alignment: CENTER;");
        } else {
            AnchorPane.setLeftAnchor(messageBox, 10.0);
            setStyle("-fx-alignment: CENTER;");
        }
    }

    private BorderPane createCodeBlock(String code, boolean isSentByMe) {
        String language = "";
        int newlineIndex = code.indexOf('\n');
        if (newlineIndex != -1) {
            language = code.substring(0, newlineIndex).trim();
            code = code.substring(newlineIndex + 1).trim();
        }

        Text languageText = new Text(language);
        languageText.setFont(Font.font(CODE_FONT_FAMILY, FONT_SIZE));
        languageText.setFill(Color.WHITE);
        TextFlow languageFlow = new TextFlow(languageText);
        languageFlow.setPadding(new Insets(5));
        languageFlow.setStyle("-fx-background-color:#3f3f3f; ");

        TextFlow codeFlow = highlightSyntax(code);

        Image copyImage = new Image(getClass().getResourceAsStream("/assets/copy.png"));
        Image copiedImage = new Image(getClass().getResourceAsStream("/assets/copied.png"));
        ImageView copyImageView = new ImageView(copyImage);
        copyImageView.setFitHeight(20);
        copyImageView.setFitWidth(20);
        String finalCode = code;
        copyImageView.setOnMouseClicked(e -> {
            Clipboard clipboard = Clipboard.getSystemClipboard();
            ClipboardContent content = new ClipboardContent();
            content.putString(finalCode);
            clipboard.setContent(content);
            copyImageView.setImage(copiedImage);

            PauseTransition pause = new PauseTransition(Duration.seconds(2));
            pause.setOnFinished(event -> copyImageView.setImage(copyImage));
            pause.play();
        });
        HBox topBox = new HBox(languageFlow, copyImageView);
        topBox.setAlignment(Pos.BOTTOM_LEFT);
        topBox.setSpacing(500);
        topBox.setPadding(new Insets(5));
        topBox.setStyle("-fx-background-color:#3f3f3f; -fx-background-radius:10 10 0 0");


        BorderPane codeBlock = new BorderPane();
        codeBlock.setTop(topBox);
        codeBlock.setCenter(codeFlow);
        codeBlock.setStyle("-fx-background-color: black; -fx-font-family:Consolas; -fx-background-radius:20");

        return codeBlock;
    }

    // Define patterns for different types of tokens
    // Define patterns for different types of tokens
    private static final Pattern KEYWORD_PATTERN = Pattern.compile(
            "\\b(" +
                    "abstract|assert|boolean|break|byte|case|catch|char|class|const|continue|" +
                    "default|do|double|else|enum|extends|final|finally|float|for|goto|if|implements|" +
                    "import|instanceof|int|interface|long|native|new|null|package|private|protected|" +
                    "public|return|short|static|strictfp|super|switch|synchronized|this|throw|throws|" +
                    "transient|try|void|volatile|while|var" +
                    ")\\b");

    private static final Pattern TYPE_PATTERN = Pattern.compile(
            "\\b(" +
                    "void|boolean|byte|char|short|int|long|float|double" +
                    ")\\b");

    private static final Pattern STRING_PATTERN = Pattern.compile("\"([^\"]*)\"");
    private static final Pattern COMMENT_PATTERN = Pattern.compile("//.*|/\\*(?:.|[\\n\\r])*?\\*/");
    private static final Pattern ANNOTATION_PATTERN = Pattern.compile("@\\w+");

    // Regular expression combining all patterns
    private static final Pattern COMBINED_PATTERN = Pattern.compile(
            "(?<KEYWORD>" + KEYWORD_PATTERN + ")"
                    + "|(?<TYPE>" + TYPE_PATTERN + ")"
                    + "|(?<STRING>" + STRING_PATTERN + ")"
                    + "|(?<COMMENT>" + COMMENT_PATTERN + ")"
                    + "|(?<ANNOTATION>" + ANNOTATION_PATTERN + ")");

//    private static final double FONT_SIZE = 14.0;

    private TextFlow highlightSyntax(String code) {
        TextFlow codeFlow = new TextFlow();
        codeFlow.setPadding(new Insets(10));
        codeFlow.setStyle("-fx-background-color: black; -fx-background-radius: 10;");

        int lastEnd = 0;
        Matcher matcher = COMBINED_PATTERN.matcher(code);

        while (matcher.find()) {
            if (matcher.start() > lastEnd) {
                Text normalText = new Text(code.substring(lastEnd, matcher.start()));
                normalText.setFill(Color.WHITE);
                codeFlow.getChildren().add(normalText);
            }

            String group = null;

            // Check each named group individually
            if (matcher.group("KEYWORD") != null) {
                group = matcher.group("KEYWORD");
                Text highlightedText = new Text(group);
                highlightedText.setFill(Color.CYAN); // Keyword color
                codeFlow.getChildren().add(highlightedText);
            } else if (matcher.group("TYPE") != null) {
                group = matcher.group("TYPE");
                Text highlightedText = new Text(group);
                highlightedText.setFill(Color.YELLOW); // Type color
                codeFlow.getChildren().add(highlightedText);
            } else if (matcher.group("STRING") != null) {
                group = matcher.group("STRING");
                Text highlightedText = new Text(group);
                highlightedText.setFill(Color.ORANGE); // String color
                codeFlow.getChildren().add(highlightedText);
            } else if (matcher.group("COMMENT") != null) {
                group = matcher.group("COMMENT");
                Text highlightedText = new Text(group);
                highlightedText.setFill(Color.LIGHTGREEN); // Comment color
                codeFlow.getChildren().add(highlightedText);
            } else if (matcher.group("ANNOTATION") != null) {
                group = matcher.group("ANNOTATION");
                Text highlightedText = new Text(group);
                highlightedText.setFill(Color.YELLOW); // Annotation color
                codeFlow.getChildren().add(highlightedText);
            }

            lastEnd = matcher.end();
        }

        if (lastEnd < code.length()) {
            Text normalText = new Text(code.substring(lastEnd));
            normalText.setFill(Color.WHITE);
            codeFlow.getChildren().add(normalText);
        }

        return codeFlow;
    }

    private Matcher combineMatchers(String code) {
        Pattern combinedPattern = Pattern.compile(
                String.format("%s|%s|%s|%s",
                        KEYWORD_PATTERN.pattern(),
                        STRING_PATTERN.pattern(),
                        COMMENT_PATTERN.pattern(),
                        ANNOTATION_PATTERN.pattern())
        );
        return combinedPattern.matcher(code);
    }

    private TextFlow createFormattedText(String text) {
        TextFlow textFlow = new TextFlow();

        String[] lines = text.split("\n");
        for (String line : lines) {
            if (line.startsWith("##")) {
                Text header = new Text(line.substring(2).trim() + "\n");
                header.setFont(Font.font(FONT_SIZE * 1.5));
                header.setStyle("-fx-font-weight: bold;");
                textFlow.getChildren().add(header);
            } else {
                while (!line.isEmpty()) {
                    if (line.startsWith("**")) {
                        int endIndex = line.indexOf("**", 2);
                        if (endIndex != -1) {
                            Text boldText = new Text(line.substring(2, endIndex));
                            boldText.setFont(Font.font("System", FontWeight.BOLD, FONT_SIZE));
                            textFlow.getChildren().add(boldText);
                            line = line.substring(endIndex + 2);
                        } else {
                            Text remainingText = new Text(line);
                            textFlow.getChildren().add(remainingText);
                            break;
                        }
                    } else if (line.startsWith("*")) {
                        int endIndex = line.indexOf("*", 1);
                        if (endIndex != -1) {
                            Text italicText = new Text(line.substring(1, endIndex));
                            italicText.setFont(Font.font("System", FontPosture.ITALIC, FONT_SIZE));
                            textFlow.getChildren().add(italicText);
                            line = line.substring(endIndex + 1);
                        } else {
                            Text remainingText = new Text(line);
                            textFlow.getChildren().add(remainingText);
                            break;
                        }
                    } else {
                        int nextBold = line.indexOf("**");
                        int nextItalic = line.indexOf("*");
                        int nextSpecial = nextBold == -1 ? nextItalic : (nextItalic == -1 ? nextBold : Math.min(nextBold, nextItalic));
                        if (nextSpecial == -1) {
                            Text remainingText = new Text(line);
                            textFlow.getChildren().add(remainingText);
                            break;
                        } else {
                            Text normalText = new Text(line.substring(0, nextSpecial));
                            textFlow.getChildren().add(normalText);
                            line = line.substring(nextSpecial);
                        }
                    }
                }
                textFlow.getChildren().add(new Text("\n"));
            }
        }

        return textFlow;
    }
}
