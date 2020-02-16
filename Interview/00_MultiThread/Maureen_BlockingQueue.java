import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Maureen_BlockingQueue {

	static BlockingQueue<String> q1 = new ArrayBlockingQueue(1);
	static BlockingQueue<String> q2 = new ArrayBlockingQueue(1);

	public static void main(String[] args) throws Exception {
		char[] aI = "1234567".toCharArray();
		char[] aC = "ABCDEFG".toCharArray();

		new Thread(() -> {
			for (char c : aI) {
				System.out.print(c);
				try {
					q1.put("ok");
					q2.take();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}, "t1").start();

		new Thread(() -> {
			for (char c : aC) {
				try {
					q1.take(); //等着q1中有内容，取出内容再继续运行；如果没有内容，就阻塞
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.print(c);
				try {
					q2.put("ok");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}, "t2").start();
	}

}
