package item8;

import java.lang.ref.Cleaner;

public class Room implements AutoCloseable {

	private static final Cleaner cleaner = Cleaner.create();

	/**
	 * 정적 중첩 클래스로 둔 이유:
	 * 정적이 아닌 일반 중첩 클래스는 자동으로 바깥 객체의 참조를 갖게 되기 때문
	 */
	private static class State implements Runnable {
		int numJunkPiles;

		State(int numJunkPiles) {
			this.numJunkPiles = numJunkPiles;
		}

		@Override
		public void run() {
			System.out.println("Room clean");
			numJunkPiles = 0;
		}
	}

	private final State state;
	private final Cleaner.Cleanable cleanable;

	public Room(int numJunkPiles) {
		state = new State(numJunkPiles);
		cleanable = cleaner.register(this, state);
	}

	@Override
	public void close() {
		cleanable.clean();
	}
}
