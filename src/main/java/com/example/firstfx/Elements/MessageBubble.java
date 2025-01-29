package com.example.firstfx.Elements;

import javafx.animation.PauseTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.util.Duration;

import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageBubble extends AnchorPane {
//
//    private static final String CODE_FONT_FAMILY = "Roboto Mono";
//    private static final double FONT_SIZE = 14.0;
//
//    public MessageBubble(String messageText, boolean isSentByMe) {
//        setPadding(new Insets(10));
//
//        Background messageBackground = new Background(
//                new BackgroundFill(isSentByMe ? Color.LIGHTBLUE : Color.LIGHTGRAY, new CornerRadii(10), Insets.EMPTY)
//        );
//
//        VBox messageBox = new VBox();
//        messageBox.setSpacing(5);
//        messageBox.setPadding(new Insets(10));
//        messageBox.setBackground(messageBackground);
//
//        // Split the text for code blocks and normal text
//        String[] parts = messageText.split("```");
//        boolean isCodeBlock = false;
//
//        for (String part : parts) {
//            if (isCodeBlock) {
//                VBox codeBlockWithCopy = createCodeBlockWithCopy(part);
//                messageBox.getChildren().add(codeBlockWithCopy);
//            } else {
//                TextFlow formattedTextFlow = parseFormattedText(part);
//                messageBox.getChildren().add(formattedTextFlow);
//            }
//            isCodeBlock = !isCodeBlock;
//        }
//
//        // Add the copy icon only to received messages
//        if (!isSentByMe) {
//            ImageView copyIcon = createCopyIcon(messageText);
//            messageBox.getChildren().add(copyIcon);
//        }
//
//        getChildren().add(messageBox);
//
//        if (isSentByMe) {
//            AnchorPane.setRightAnchor(messageBox, 10.0);
//            setStyle("-fx-alignment: CENTER_RIGHT;");
//        } else {
//            AnchorPane.setLeftAnchor(messageBox, 10.0);
//            setStyle("-fx-alignment: CENTER_LEFT;");
//        }
//    }
//
//    private TextFlow parseFormattedText(String messageText) {
//        TextFlow textFlow = new TextFlow();
//        textFlow.setMaxWidth(400);
//        textFlow.setPadding(new Insets(10));
//
//        // Define regex patterns for bold, italic, and headings
//        Pattern boldPattern = Pattern.compile("\\*\\*(.*?)\\*\\*");
//        Pattern italicPattern = Pattern.compile("\\*(.*?)\\*");
//        Pattern headingPattern = Pattern.compile("## (.*?)\\n");
//
//        // Replace headings
//        Matcher matcher = headingPattern.matcher(messageText);
//        while (matcher.find()) {
//            String headingText = matcher.group(1);
//            Text heading = new Text(headingText + "\n");
//            heading.setFont(Font.font(FONT_SIZE + 4));
//            heading.setStyle("-fx-font-weight: bold;");
//            textFlow.getChildren().add(heading);
//        }
//        messageText = matcher.replaceAll("");
//
//        // Replace bold text
//        matcher = boldPattern.matcher(messageText);
//        while (matcher.find()) {
//            String beforeText = messageText.substring(0, matcher.start());
//            String boldText = matcher.group(1);
//            Text before = new Text(beforeText);
//            Text bold = new Text(boldText);
//            bold.setStyle("-fx-font-weight: bold;");
//            textFlow.getChildren().addAll(before, bold);
//            messageText = messageText.substring(matcher.end());
//            matcher = boldPattern.matcher(messageText);
//        }
//
//        // Replace italic text
//        matcher = italicPattern.matcher(messageText);
//        while (matcher.find()) {
//            String beforeText = messageText.substring(0, matcher.start());
//            String italicText = matcher.group(1);
//            Text before = new Text(beforeText);
//            Text italic = new Text(italicText);
//            italic.setStyle("-fx-font-style: italic;");
//            textFlow.getChildren().addAll(before, italic);
//            messageText = messageText.substring(matcher.end());
//            matcher = italicPattern.matcher(messageText);
//        }
//
//        Text remainingText = new Text(messageText);
//        textFlow.getChildren().add(remainingText);
//
//        return textFlow;
//    }
//
//    private ImageView createCopyIcon(String textToCopy) {
//        InputStream iconStream = getClass().getResourceAsStream("/assets/copy.png");
//        Image copyIconImage = new Image(iconStream);
//        ImageView copyIcon = new ImageView(copyIconImage);
//        copyIcon.setFitHeight(20);
//        copyIcon.setFitWidth(20);
//
//        // Add dark background to the icon
//        StackPane iconContainer = new StackPane(copyIcon);
//        iconContainer.setBackground(new Background(new BackgroundFill(Color.DARKGRAY, new CornerRadii(5), Insets.EMPTY)));
//        iconContainer.setPadding(new Insets(5));
//
//        copyIcon.setOnMouseClicked(event -> {
//            Clipboard clipboard = Clipboard.getSystemClipboard();
//            ClipboardContent content = new ClipboardContent();
//            content.putString(textToCopy);
//            clipboard.setContent(content);
//
//            InputStream copiedIconStream = getClass().getResourceAsStream("/assets/copied.png");
//            Image copiedIconImage = new Image(copiedIconStream);
//            copyIcon.setImage(copiedIconImage);
//        });
//
//        return copyIcon;
//    }
//
//    private VBox createCodeBlockWithCopy(String code) {
//        TextFlow codeFlow = createCodeBlock(code);
//        String codeText = extractCodeText(code);
//        ImageView copyIcon = createCopyIcon(codeText);
//
//        VBox codeBlockWithCopy = new VBox(codeFlow, copyIcon);
//        codeBlockWithCopy.setSpacing(5);
//
//        return codeBlockWithCopy;
//    }
//
//    private String extractCodeText(String code) {
//        int newlineIndex = code.indexOf('\n');
//        if (newlineIndex != -1) {
//            return code.substring(newlineIndex + 1).trim();
//        }
//        return code.trim();
//    }
//
//    private TextFlow createCodeBlock(String code) {
//        String language = "";
//        int newlineIndex = code.indexOf('\n');
//        if (newlineIndex != -1) {
//            language = code.substring(0, newlineIndex).trim();
//            code = code.substring(newlineIndex + 1).trim();
//        }
//
//        Text languageText = new Text(language);
//        languageText.setFont(Font.font(CODE_FONT_FAMILY, FONT_SIZE));
//        languageText.setFill(Color.WHITE);
//        TextFlow languageFlow = new TextFlow(languageText);
//        languageFlow.setPadding(new Insets(5));
//        languageFlow.setStyle("-fx-background-color: #2D2D2D; -fx-border-color: #606060;");
//
//        TextFlow codeFlow = highlightSyntax(code);
//
//        VBox codeBlock = new VBox(languageFlow, codeFlow);
//        codeBlock.setSpacing(5);
//        codeBlock.setPadding(new Insets(10));
//        codeBlock.setStyle("-fx-background-color: #2D2D2D; -fx-border-color: #606060; -fx-border-radius: 5;");
//
//        return new TextFlow(codeBlock);
//    }
//
//    private TextFlow highlightSyntax(String code) {
//        TextFlow codeFlow = new TextFlow();
//        codeFlow.setPadding(new Insets(10));
//        codeFlow.setStyle("-fx-background-color: #2D2D2D; -fx-border-color: #606060; -fx-border-radius: 5;");
//
//        String[] lines = code.split("\n");
//        for (String line : lines) {
//            Text text = new Text(line + "\n");
//            text.setFont(Font.font(CODE_FONT_FAMILY, FONT_SIZE));
//
//            if (line.trim().startsWith("//")) {
//                text.setFill(Color.LIGHTGREEN); // Comment
//            } else if (line.contains("\"")) {
//                text.setFill(Color.ORANGE); // String
//            } else if (line.matches(".*\\b(public|private|protected|class|void|int|String|if|else|for|while|return|new|import|package)\\b.*")) {
//                text.setFill(Color.CYAN); // Keyword
//            } else {
//                text.setFill(Color.WHITE); // Default
//            }
//            codeFlow.getChildren().add(text);
//        }
//
//        return codeFlow;
//    }


    private static final String CODE_FONT_FAMILY = "Consolas"; // Example of a typical code font
    private static final double FONT_SIZE = 12.0;

    public MessageBubble(String messageText, boolean isSentByMe) {
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
