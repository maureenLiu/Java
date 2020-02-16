
//要使用wait和notify，要对一个对象加锁，才能在其中进行notify和wait的操作
public class Maureen_sync_wait_notify_00 {

	public static void main(String[] args) {
		final Object o = new Object();

		char[] aI = "1234567".toCharArray();
		char[] aC = "ABCDEFG".toCharArray();

		new Thread(() -> {
			synchronized (o) {// 不能使用this，因为代码块中是一个匿名内部类，如果用this，那么和下面代码块的this不是同一个对象，不能构成同步
				for (char c : aI) {
					System.out.print(c);
					try {
						o.notify(); // 叫醒等待队列里的任意一个，notifyAll是叫醒所有线程
						o.wait(); // 让出锁 运行中的线程进入等待队列中，与此同时将锁释放
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				o.notify(); // 必须，否则无法停止程序。因为无论哪个线程先运行完，总有个线程处于wait状态，只有叫醒该线程程序才会运行完
			}
		}, "t1").start();

		new Thread(() -> {
			synchronized (o) {// 拿不到锁的时候就在等待队列中
				for (char c : aC) {
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
		}, "t2").start();

	}

}
