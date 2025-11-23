package unibo.party.minigames.memory.view.impl;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import unibo.party.minigames.memory.controller.api.MemoryGameController;
import unibo.party.minigames.memory.model.api.CardReadOnly;
import unibo.party.minigames.memory.model.api.MemoryGameReadOnlyState;
import unibo.party.minigames.memory.view.api.MemoryGameView;

/**
 * Implementation of the Memory game view.
 * It shows the card grid, the timer and small status messages.
 * It only depends on the public APIs (controller/model).
 */
public class MemoryGameViewImpl extends BorderPane implements MemoryGameView {

    private static final int ROWS = 4;
    private static final int COLS = 4;

    // Graphic size of the card image
    private static final double CARD_IMG_SIZE = 60.0;

    private final GridPane grid;
    private final Label statusLabel;
    private final Label infoLabel;
    private final Button[][] buttons;
    private final Image questionImage;

    private MemoryGameController controller;

    public MemoryGameViewImpl() {

        // Create the grid that will contain the cards
        this.grid = new GridPane();
        this.grid.setHgap(10); // horizontal space between cells
        this.grid.setVgap(10); // vertical space between cells
        this.grid.setPadding(new Insets(20)); 
        this.grid.setAlignment(Pos.CENTER); // center the grid

        // Status label at the bottom (for messages like "Welcome", etc.)
        this.statusLabel = new Label("Welcome! Let's play!");
        this.statusLabel.setPadding(new Insets(10));
        this.statusLabel.setAlignment(Pos.CENTER);

        // Info label at the top (timer and moves)
        this.infoLabel = new Label("Time: 0s | Moves: 0");
        this.infoLabel.setPadding(new Insets(5));
        this.infoLabel.setAlignment(Pos.CENTER);

        // Vertical box: info label on top, grid below
        VBox centerBox = new VBox(10);
        centerBox.setAlignment(Pos.CENTER);
        centerBox.getChildren().addAll(this.infoLabel, this.grid);

        this.setCenter(centerBox);
        this.setBottom(this.statusLabel);
        this.setPadding(new Insets(20));

        // Create all buttons for the cards
        this.buttons = new Button[ROWS][COLS];

        // Default "question mark" image for hidden cards
        this.questionImage = new Image(getClass().getResourceAsStream("/images/memory/question.png"));

        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                Button cardButton = new Button("?");
                cardButton.setPrefSize(80, 80);
                cardButton.setMinSize(80, 80);
                cardButton.setMaxSize(80, 80);
                cardButton.setAlignment(Pos.CENTER);

                // Save row and column in final variables for the click handler
                final int rr = r;
                final int cc = c;

                // When the button is clicked, notify the controller
                cardButton.setOnAction(e -> {
                    if (this.controller != null) {
                        this.controller.onCardClicked(rr, cc);
                    }  
                });

                this.buttons[r][c] = cardButton;
                // Add the button to the grid at the correct position
                this.grid.add(cardButton, c, r);
            }
        }
    }

    @Override
    public void setController(final MemoryGameController controller) {
        this.controller = controller;
    }

    /**
     * Redraws the UI according to the given game state.
     */
    @Override
    public void render(MemoryGameReadOnlyState state) {
        setStatusMessage(state.getMessage());
        
        final var cards = state.getCards(); // List<CardReadOnly>

        // Update every button according to the card at the same position
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                final int index = r * COLS + c;
                final CardReadOnly card = cards.get(index);

                if (card.isRevealed()) {
                    // Show the real symbol image
                    showCardWithImage(r, c, card.getSymbol().toString());
                } else {
                    // Show the hidden card
                    hideCard(r, c);
                }
            }
        }

        // If the game is over, disable further input
        if (state.isGameOver()) {
            setAllButtonsDisabled(true);
        }
    }

    @Override
    public void updateInfoPanel(final int seconds, final int moves) {
        this.infoLabel.setText("Time: " + seconds + "s | Moves: " + moves);
    }

    @Override
    public void setAllButtonsDisabled(final boolean disabled) {
        for (int r = 0; r < this.buttons.length; r++) {
            for (int c = 0; c < this.buttons[r].length; c++) {
                this.buttons[r][c].setDisable(disabled);
            }
        }
    }

    /**
     * Shows the real symbol of the card by loading the corresponding image
     * @param r row of the card
     * @param c column of the card
     * @param imageName name of the image file
     */
    public void showCardWithImage(final int r, final int c, final String imageName) {
        Button b = this.buttons[r][c];

        // Load the image from resources/images/memory
        Image img = new Image(
            getClass().getResourceAsStream("/images/memory/" + imageName + ".png")
        );

        ImageView imgView = new ImageView(img);
        imgView.setFitWidth(CARD_IMG_SIZE);
        imgView.setFitHeight(CARD_IMG_SIZE);
        imgView.setPreserveRatio(true);

        b.setGraphic(imgView);
        b.setText(""); // no text, only image
    }

    /**
     * Hides the card and shows the default "question mark" image
     */
    public void hideCard(final int r, final int c) {
        Button b = this.buttons[r][c];
        ImageView imgView = new ImageView(this.questionImage);
        imgView.setFitWidth(CARD_IMG_SIZE);
        imgView.setFitHeight(CARD_IMG_SIZE);
        imgView.setPreserveRatio(true);
        b.setGraphic(imgView); // nessuna immagine
        b.setText("");
    }

    /**
     * Updates the status message at the bottom of the window.
     */
    public void setStatusMessage(final String msg) {
        this.statusLabel.setText(msg);
    }
}
