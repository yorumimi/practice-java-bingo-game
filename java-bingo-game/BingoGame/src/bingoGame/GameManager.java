package bingoGame;

public final class GameManager {

  private final BingoGameController controller;

  private final BingoCardModel cardModel;
  private final BingoCardView cardView;

  private final BingoCageModel cageModel;
  private final BingoCageView cageView;


  GameManager() {

    cardModel = new BingoCardModel();
    cardView = new BingoCardView();

    cageModel = new BingoCageModel();
    cageView = new BingoCageView();

    controller = new BingoGameController(cageModel, cageView, cardModel, cardView);

  }

  private void setup() {
    controller.setup();
  }

  private boolean update() {
    return controller.update();
  }

  public void loop() {
    setup();
    while (!
        update()) {
    }
  }
}

