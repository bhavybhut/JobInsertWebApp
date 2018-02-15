
public class Main {

	/**
	 * @param args
	 */
	public void runAllThread() {
		// TODO Auto-generated method stub
		Thread t = new Thread(){
				public void run() {
					EntryLevel.main(null);
					Internship.main(null);
					Part.main(null);
					Summer.main(null);
				}
		};
		t.start();
	}
}
