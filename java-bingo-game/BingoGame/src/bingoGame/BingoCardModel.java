package bingoGame;

import java.util.ArrayList;
import java.util.Collections;

public final class BingoCardModel {

	private int[][] bingoCard;

	private static final int BINGO_SIZE = 5;

	// FIXME: BingoCageModelと重複
	private static final int MAX_NUM = 75;

	// NOTE: 空いたマスは0で表現
	private static final int MARKED_NUM = 0;

	// NOTE: これstaicにしていいものなの?
	private static boolean bingo;

	BingoCardModel() {
		// NOTE: デッドコードといわれる.
		// NOTE: 定数なんだから奇数に決まってんだろって話なんだろうけどプログラマによって書き換えられてしまう可能性があるときはどうしたらいい？
		if (BINGO_SIZE % 2 == 0) {
			System.out.println("[Error] ビンゴのサイズが偶数です");
		}

		bingoCard = new int[BINGO_SIZE][BINGO_SIZE];
	}

	/**
	 * @param bingoCardIn ビンゴカードモデル(this)
	 * @return 初期化後のビンゴカードデータ
	 */
	public BingoCardModel initialize(final BingoCardModel bingoCardIn) {

		// 簡単のため
		int temp[][] = new int[BINGO_SIZE][BINGO_SIZE];

		for (int i = 0; i < BINGO_SIZE; i++) {

			// ① 1～(MAX_NUM / BINGO_SIZE) の中からBINGO_SIZE個、一意な整数値をランダムに取得
			ArrayList<Integer> numbers = new ArrayList<Integer>();

			for (int k = 1; k < MAX_NUM / BINGO_SIZE; k++) {
				numbers.add(i * (MAX_NUM / BINGO_SIZE) + k);
			}

			Collections.shuffle(numbers);

			for (int j = 0; j < BINGO_SIZE; j++) {
				// ②まずは横に格納
				temp[i][j] = numbers.get(j);
				// ③配列を90度回転して格納
				bingoCardIn.bingoCard[j][i] = temp[i][j];
			}
		}
		
	/**
	 * ん？？？？？？？？？？？？？？？？？？？
	 * final BingoCardModel bingoCardIn でfinalで受け取っているのに
	 * bingoCardIn.bingoCard[j][i] = temp[i][j]って書けるの？？？？？？？？？？？？？？？？？？
	 * ってことは設計がだめってこと￥？？？？？？？？？？？？？？？？？
	 */

		// 真ん中は0なので(空いているので)
		bingoCardIn.bingoCard[BINGO_SIZE / 2][BINGO_SIZE / 2] = MARKED_NUM;

		//System.out.println("[Log] ビンゴカード初期化完了");

		return new BingoCardModel(bingoCardIn);
	}

	/**
	 * @param bingoCardIn: ビンゴカードモデル(this)、number: markする数値
	 * @return mark後のビンゴカードデータ
	 */
	public BingoCardModel mark(final BingoCardModel bingoCardIn, final int number) {

		// NOTE: number-1は,MAX_NUMだった場合範囲外アクセスをおこしてしまうから
		final int checkColumn = (number - 1) / (MAX_NUM / BINGO_SIZE);

		for (int j = 0; j < BINGO_SIZE; j++) {
			if (number == bingoCardIn.bingoCard[j][checkColumn]) {
				bingoCardIn.bingoCard[j][checkColumn] = 0;
				break;
			}
		}
		return new BingoCardModel(bingoCardIn);
	}

	/**
	 * @param ビンゴカードモデル(this)
	 * @return リーチ個数
	 */
	public int getReachCount(final BingoCardModel bingoCardIn) {

		final int reachCount = getRowReachCount(bingoCardIn.bingoCard) + getColumnReachCount(bingoCardIn.bingoCard)
				+ getDiagonalReachCount(bingoCardIn.bingoCard);
		
		return reachCount;
	}

	/**
	 * ビンゴしたかどうか
	 */
	public boolean getBingo() {
		return bingo;
	}

	// NOTE: ここで不変の配列を返したかったけど（view側で変更されたらいやなので）できないっぽい？
	/**
	 * ビンゴカードデータ
	 */
	public int[][] getBingoCard() {
		return bingoCard;
	}

	/**
	 * @param bingoCardIn ビンゴカードモデル(this)
	 */
	private BingoCardModel(final BingoCardModel bingoCardIn) {
		this.bingoCard = bingoCardIn.bingoCard;
	}

	/**
	 * @param num マークされているか否か
	 */
	private boolean marked(final int num) {
		return num == MARKED_NUM ? true : false;
	}

	/**
	 * @param markedCnt リーチかどうか
	 */
	private boolean reach(final int markedCnt) {
		return markedCnt == (BINGO_SIZE - 1) ? true : false;
	}

	/**
	 * 
	 * @param markedCnt
	 * @return ビンゴかどうか
	 */
	private boolean bingo(final int markedCnt) {
		return markedCnt == BINGO_SIZE ? true : false;
	}

	// HACK: ビンゴかどうかを判断するアルゴリズムも全く同じなので同じ場所に書いてしまったが気持ち悪い。
	/**
	 * @param bingoCard
	 * @return 横のリーチ数
	 */
	private int getRowReachCount(final int[][] bingoCard) {

		int rowReachCount = 0;

		for (int i = 0; i < bingoCard.length; i++) {

			int marked_cnt = 0;

			for (int j = 0; j < bingoCard[0].length; j++) {

				if (marked(bingoCard[i][j])) {
					marked_cnt++;
				}

				if (reach(marked_cnt)) {
					rowReachCount++;
				}

				// FIXME:bingoがstataicなの違和感
				if (bingo(marked_cnt)) {
					bingo = true;
				}
			}
		}
		return rowReachCount;
	}

	/**
	 * @param bingoCard
	 * @return 縦のリーチ数
	 */
	private int getColumnReachCount(final int[][] bingoCard) {

		int columnReachCount = 0;

		for (int i = 0; i < bingoCard.length; i++) {

			int markedCnt = 0;

			for (int j = 0; j < bingoCard[0].length; j++) {

				if (marked(bingoCard[j][i])) {
					markedCnt++;
				}

				if (reach(markedCnt)) {
					columnReachCount++;
				}

				// FIXME:bingoがstataicなの違和感
				if (bingo(markedCnt)) {
					bingo = true;
				}
			}
		}
		return columnReachCount;
	}

	/**
	 * 
	 * @param bingoCard
	 * @return 斜めのリーチ数
	 */
	private int getDiagonalReachCount(final int[][] bingoCard) {

		// 全体の斜めのリーチ数（最大値2）
		int diagonalReachCount = 0;

		// 右下がりの斜めリーチ検出用
		int markedCntDownwardRight = 0;

		// 右上がりの斜めリーチ検出用
		int markedCntUpRight = 0;

		for (int i = 0; i < BINGO_SIZE; i++) {

			// 右下がり
			if (marked(bingoCard[i][i])) {
				markedCntDownwardRight++;
			}

			// 右上がり
			if (marked(bingoCard[i][BINGO_SIZE - (i + 1)])) {
				markedCntUpRight++;
			}
		}

		if (reach(markedCntDownwardRight)) {
			diagonalReachCount++;
		}

		if (reach(markedCntUpRight)) {
			diagonalReachCount++;
		}

		// FIXME:bingoがstataicなの違和感
		if (bingo(markedCntUpRight) || bingo(markedCntDownwardRight)) {
			bingo = true;
		}

		return diagonalReachCount;

	}

}
