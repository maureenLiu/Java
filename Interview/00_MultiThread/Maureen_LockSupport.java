import java.util.concurrent.locks.LockSupport;

public class Maureen_LockSupport {
	static Thread t1 = null, t2 = null;

	public static void main(String[] args) throws Exception {
		char[] aI = "1234567".toCharArray();
		char[] aC = "ABCDEFG".toCharArray();

		t1 = new Thread(() -> {
			for (char c : aI) {
				System.out.print(c);
				LockSupport.unpark(t2); // t2线程继续运行
				LockSupport.park(); // 当前线程暂停
			}
		}, "t1");

		t2 = new Thread(() -> {
			for (char c : aC) {
				LockSupport.park();
				System.out.print(c);
				LockSupport.unpark(t1);
			}
		}, "t2");

		t1.start();
		t2.start();
	}
}