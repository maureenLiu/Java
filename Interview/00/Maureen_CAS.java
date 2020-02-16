
public class Maureen_CAS {
	enum ReadyToRun {
		T1, T2
	}

	static volatile ReadyToRun r = ReadyToRun.T1;

	public static void main(String[] args) {
		char[] aI = "1234567".toCharArray();
		char[] aC = "ABCDEFG".toCharArray();

		new Thread(() -> {
			for (char c : aI) {
				while (r != ReadyToRun.T1) {
					//空转，一直占着CPU，也就是自旋（原地打转）
				}
				System.out.print(c);
				r = ReadyToRun.T2; //每输出一个字符，就将r标记进行修改 
			}
		}, "t1").start();

		new Thread(() -> {
			for (char c : aC) {
				while (r != ReadyToRun.T2) {
				}
				System.out.print(c);
				r = ReadyToRun.T1;
			}
		}, "t2").start();

	}

}
