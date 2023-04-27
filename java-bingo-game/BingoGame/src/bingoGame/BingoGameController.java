package bingoGame;

import java.util.Scanner;

public final class BingoGameController {

	private BingoCageModel bingoCageModel;
	private BingoCageView bingoCageView;
	private BingoCardModel bingoCardModel;
	private BingoCardView bingoCardView;
	
	Scanner scanner;

	BingoGameController(final BingoCageModel bingoCageModel, final BingoCageView bingoCageView,
			final BingoCardModel bingoCardModel, final BingoCardView bingoCardView) {

		this.bingoCageModel = bingoCageModel;
		this.bingoCageView = bingoCageView;

		this.bingoCardModel = bingoCardModel;
		this.bingoCardView = bingoCardView;
		
		scanner = new Scanner(System.in);
	}

	public void setup() {

		bingoCardModel = bingoCardModel.initialize(bingoCardModel);

	}

	public void update() {
		
		displayCard();
		scanner.nextLine();
		raffle();
		displayCongratulations();
	}

	private boolean exit() {
		return false;
	}

	private void displayCard() {

		bingoCardView.displayBingoCard(bingoCardModel.getBingoCard(), bingoCardModel.getReachCount(bingoCardModel));

	}

	// NOTE: 表示と操作を同じメソッドに書いてもいいの？
	private void raffle() {
		final int num = bingoCageModel.raffle();
		bingoCageView.displayRaffleResult(num);
		bingoCardModel = bingoCardModel.mark(bingoCardModel, num);
	}

	private void displayCongratulations() {
		bingoCardView.displayConfratulations(bingoCardModel.getBingo());
	}
}

