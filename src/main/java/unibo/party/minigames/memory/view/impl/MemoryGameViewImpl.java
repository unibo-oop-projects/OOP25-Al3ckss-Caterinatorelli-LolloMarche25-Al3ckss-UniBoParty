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
 * Implementazione JavaFX della View del Memory.
 * Mostra la griglia di carte, il timer e i messaggi di stato.
 * Dipende solo dalle API (controller/model).
 */
public class MemoryGameViewImpl extends BorderPane implements MemoryGameView {

    private static final int ROWS = 4;
    private static final int COLS = 4;

    // dimensione grafica dell'immagine dentro la carta
    private static final double CARD_IMG_SIZE = 60.0;

    private final GridPane grid;
    private final Label statusLabel;
    private final Label infoLabel;
    private final Button[][] buttons;
    private final Image questionImage;

    private MemoryGameController controller;

    public MemoryGameViewImpl() {

        // Creo la griglia che conterrà le carte
        this.grid = new GridPane();
        this.grid.setHgap(10); // Spazio orizzontale tra le celle
        this.grid.setVgap(10); // Spazio verticale tra le celle
        this.grid.setPadding(new Insets(20)); // margine interno della griglia
        this.grid.setAlignment(Pos.CENTER); // la griglia sta centrata

        // Creo una label di stato in basso (per messaggi tipo "Benvenuto")
        this.statusLabel = new Label("Welcome! Let's play!");
        this.statusLabel.setPadding(new Insets(10));
        this.statusLabel.setAlignment(Pos.CENTER);

        // label sopra (timer e mosse)
        this.infoLabel = new Label("Time: 0s | Moves: 0");
        this.infoLabel.setPadding(new Insets(5));
        this.infoLabel.setAlignment(Pos.CENTER);

        // contenitore verticale: info sopra, griglia sotto
        VBox centerBox = new VBox(10);
        centerBox.setAlignment(Pos.CENTER);
        centerBox.getChildren().addAll(this.infoLabel, this.grid);

        this.setCenter(centerBox);
        this.setBottom(this.statusLabel);
        this.setPadding(new Insets(20));

        // carte
        this.buttons = new Button[ROWS][COLS];
        this.questionImage = new Image(getClass().getResourceAsStream("/images/memory/question.png"));

        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                Button cardButton = new Button("?");
                cardButton.setPrefSize(80, 80);
                cardButton.setMinSize(80, 80);
                cardButton.setMaxSize(80, 80);
                cardButton.setAlignment(Pos.CENTER);

                // Salvo r e c in due variabili final per usarle nel listener
                final int rr = r;
                final int cc = c;

                // quando clicco la carta, avviso il controller
                cardButton.setOnAction(e -> {
                    if (this.controller != null) {
                        this.controller.onCardClicked(rr, cc);
                    }  
                });

                this.buttons[r][c] = cardButton;
                this.grid.add(cardButton, c, r); // Aggiungo il bottone nella posizione giusta della griglia
            }
        }
    }

    @Override
    public void setController(final MemoryGameController controller) {
        this.controller = controller;
    }

    @Override
    public void render(MemoryGameReadOnlyState state) {
        setStatusMessage(state.getMessage());
        
        final var cards = state.getCards(); // List<CardReadOnly>

        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                final int index = r * COLS + c;
                final CardReadOnly card = cards.get(index);

                if (card.isRevealed()) {
                    showCardWithImage(r, c, card.getSymbol().toString());
                } else {
                    hideCard(r, c);
                }
            }
        }

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
     * Mostra il simbolo vero della carta, caricando l'immagine del classpath.
     */
    public void showCardWithImage(final int r, final int c, final String imageName) {
        Button b = this.buttons[r][c];

        // carica l'immagine dalla cartella resources/images
        Image img = new Image(
            getClass().getResourceAsStream("/images/memory/" + imageName + ".png")
        );

        ImageView imgView = new ImageView(img);
        imgView.setFitWidth(CARD_IMG_SIZE);
        imgView.setFitHeight(CARD_IMG_SIZE);
        imgView.setPreserveRatio(true);

        b.setGraphic(imgView);
        b.setText(""); // niente testo, solo immagine
    }

    /**
     * Nasconde la carta (rimette "?").
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
     * Aggiorna il messaggio in basso.
     */
    public void setStatusMessage(final String msg) {
        this.statusLabel.setText(msg);
    }
}
