import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Maureen_Lock_Condition_01 {

	public static void main(String[] args) {
		char[] aI = "1234567".toCharArray();
		char[] aC = "ABCDEFG".toCharArray();
		
		Lock lock = new ReentrantLock(); //用Lock锁定
		Condition condition1 = lock.newCondition();
		Condition condition2 = lock.newCondition();
		
		new Thread(()->{
			try {
				lock.lock(); //要用Condition，必须先lock()
				for(char c: aI) {
					System.out.print(c);
					condition2.signal();
					condition1.await();
				}
				condition2.signal();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				lock.unlock();
			}
		},"t1").start();
		
		new Thread(()->{
			try {
				lock.lock();
				for(char c: aC) {
					System.out.print(c);
					condition1.signal();
					condition2.await();
				}
				condition1.signal();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				lock.unlock();
			}
		},"t2").start();

	}

}
