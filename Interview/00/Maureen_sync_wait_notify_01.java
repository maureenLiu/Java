//sync_wait_notify程序无法限制哪个线程先运行，因此在这个程序里限制哪个线程先运行
public class Maureen_sync_wait_notify_01 {
	private static volatile boolean t2Started = false;

	public static void main(String[] args) {
		final Object o = new Object();

		char[] aI = "1234567".toCharArray();
		char[] aC = "ABCDEFG".toCharArray();

		new Thread(() -> {
			synchronized (o) {
				while (!t2Started) { //限制t2先运行，如果t2没有先运行，t1就先等待
					try {
						o.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

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
		},"t1").start();
		
		new Thread(()->{
			synchronized(o) {
				for(char c: aC) {
					System.out.print(c);
					t2Started = true;
					try {
						o.notify();
						o.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				o.notify();
			}
		},"t2").start();

	}

}
