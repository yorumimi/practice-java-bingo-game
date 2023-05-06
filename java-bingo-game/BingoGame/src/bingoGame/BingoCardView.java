package bingoGame;

public final class BingoCardView {

  /**
   * @param bingoCard
   * @param reachCount
   */
  public void displayBingoCard(final int[][] bingoCard, final int reachCount) {

    if ((bingoCard.length % 2 == 0) || (bingoCard[0].length % 2 == 0)) {
      throw new IllegalArgumentException("[Error] 不正なビンゴカードデータ。奇数x奇数のものを用意してください");
    }

    System.out.println("-------------------");
    System.out.println(" B | I | N | G | O |");
    System.out.println("-------------------");

    for (int i = 0; i < bingoCard.length; i++) {
      for (int j = 0; j < bingoCard[0].length; j++) {

        // 穴が空いているところは0
        if (bingoCard[i][j] == 0) {
          System.out.print("■");
        } else {
          System.out.printf("%2d", bingoCard[i][j]);
        }
        System.out.print("｜");
      }

      System.out.print("\n");
      System.out.println("-------------------");

    }

    System.out.println("リーチ数：" + reachCount);
  }

  /**
   * @param bingo ビンゴしたかどうか
   */
  public void displayConfratulations(final boolean bingo) {
    if (bingo) {
      System.out.println("Confratulations!!");
    }
  }
}

