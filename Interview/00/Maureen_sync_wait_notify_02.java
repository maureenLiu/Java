import java.util.concurrent.CountDownLatch;

//使用CountDownLatch限制线程运行顺序，以下是限制线程2先运行
public class Maureen_sync_wait_notify_02 {

	private static CountDownLatch latch = new CountDownLatch(1);

	public static void main(String[] args) {
		final Object o = new Object();

		char[] aI = "1234567".toCharArray();
		char[] aC = "ABCDEFG".toCharArray();

		new Thread(() -> {
			try {
				latch.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			synchronized (o) {
				for (char c : aI) {
					System.out.print(c);
					try {
						o.notify();
						o.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				o.notify();
			}
		}, "t1").start();

		new Thread(() -> {
			synchronized (o) {
				for (char c : aC) {
					System.out.print(c);
					latch.countDown();
					try {
						o.notify();
						o.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				o.notify();
			}
		}, "t2").start();

	}

}

/**
 * 线程调用start()函数，并不意味着线程立即占用CPU运行，而是进入CPU的等待队列中，即进入Ready状态。根据操作系统的调度，决定选择哪个线程运行
 */
