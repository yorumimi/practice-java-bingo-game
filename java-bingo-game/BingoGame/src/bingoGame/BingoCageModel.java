package bingoGame;

import java.util.ArrayList;
import java.util.Collections;

public final class BingoCageModel {

  // 最大の数字
  // FIXME: BingoCardModelと重複
  private static final int MAX_NUM = 75;

  // NOTE: 同じ値が入らないことを明示するためにSetを使いたかったがCollections.shuffleが使えず変更
  // Set<Integer> markedNumbers = new HashSet<Integer>();
  private ArrayList<Integer> numbers = new ArrayList<Integer>();

  BingoCageModel() {

    // 初期値格納
    for (int i = 1; i < MAX_NUM + 1; i++) {
      numbers.add(i);
      Collections.shuffle(numbers);
    }
  }

  /**
   * @return 抽選結果の数値
   * @throws InvalidResultException 
   */
  public int raffle() throws InvalidResultException {

    // まだ残っているなら
    if (numbers.size() > 0) {

      // 末尾の要素を返すために取得
      final int lastIndex = numbers.size() - 1;
      final int result = numbers.get(lastIndex);

      // 一度使われた数値は二度と出ないので削除
      numbers.remove(lastIndex);

      // NOTE:なんとなくシャッフル。（現実でもかき混ぜるので）
      Collections.shuffle(numbers);
      return result;
    } else {
      throw new InvalidResultException("不正な計算結果です。");
    }
  }
}
