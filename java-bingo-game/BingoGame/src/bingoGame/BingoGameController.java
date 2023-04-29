package bingoGame;

import java.util.Scanner;

public final class BingoGameController {

  private BingoCageModel bingoCageModel;
  private BingoCageView bingoCageView;
  private BingoCardModel bingoCardModel;
  private BingoCardView bingoCardView;

  private Scanner scanner;

  BingoGameController(final BingoCageModel bingoCageModel, final BingoCageView bingoCageView,
      final BingoCardModel bingoCardModel, final BingoCardView bingoCardView) {

    this.bingoCageModel = bingoCageModel;
    this.bingoCageView = bingoCageView;

    this.bingoCardModel = bingoCardModel;
    this.bingoCardView = bingoCardView;

    scanner = new Scanner(System.in);
  }

  public void setup() {
    bingoCardModel.initialize();
  }

  public boolean update() {
    displayCard();
    scanner.nextLine();
    raffle();
    displayCongratulations();
    return exit();
  }

  private boolean exit() {
    return bingoCardModel.getBingo() ? true : false;
  }

  private void displayCard() {
    bingoCardView.displayBingoCard(bingoCardModel.getBingoCard(), bingoCardModel.getReachCount());
  }

  // NOTE: 表示と操作を同じメソッドに書いてもいいの？
  private void raffle() {
    final int num;
    try {
      num = bingoCageModel.raffle();
    } catch (InvalidResultException e) {
      System.err.println(e.getMessage());
      // FIXME: どうするのが正解かわからないので一旦こうしてる
      return;
    }

    bingoCageView.displayRaffleResult(num);
    bingoCardModel.mark(num);

  }

  private void displayCongratulations() {
    bingoCardView.displayConfratulations(bingoCardModel.getBingo());
  }
}

