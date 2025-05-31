package mod08_03;

/**
 * The App class contains the main method testing the
 * CheckPrime class. Performing prime number checks using
 * different methods, such as single-threaded and thread
 * pool-based approaches, and measures the execution time.
 * 
 * @author angel
 */
public class App {

	/**
	 * The main method tests the prime-checking
	 * functionality using different configurations.
	 *
	 * @param args command-line arguments
	 */
	public static void main(String[] args) {

		// Large prime number to test against
		int prime = 1000000007;

		// Test single-threaded prime checking with different thread counts
		testCheckSingle(prime, 1);
		testCheckSingle(prime, 2);
		testCheckSingle(prime, 3);

		// Test thread pool-based prime checking with different configurations
		testCheckPool(prime, 2, 2);
		testCheckPool(prime, 4, 2);
		testCheckPool(prime, 4, 4);

		// Test an alternative thread pool-based prime checking method
		testCheckPool2(prime, 2, 2);
	}

	/**
	 * Tests the single-threaded prime checking
	 * method and displays the result and elapsed time.
	 *
	 * @param number      the number to check for primality
	 * @param threadCount the number of threads to use
	 */
	private static void testCheckSingle(int number, int threadCount) {
		System.out.println("Check Single");

		// Create a CheckPrime instance to perform the prime check
		CheckPrime check = new CheckPrime();

		// Measure the time taken to perform the prime check
		long time = System.currentTimeMillis();
		check.checkSingle(number, threadCount);
		long elapsed = System.currentTimeMillis() - time;

		// Display the result and the elapsed time
		System.out.println("   Prime: " + check.getResult());
		System.out.println("   Elapsed: " + elapsed + "ms");
	}

	/**
	 * Tests the thread pool-based prime checking
	 * method and displays the result and elapsed time.
	 *
	 * @param number      the number to check for primality
	 * @param threadCount the number of threads to use
	 * @param poolSize    the size of the thread pool
	 */
	private static void testCheckPool(int number, int threadCount, int poolSize) {
		System.out.println("Check Pool");

		// Create a CheckPrime instance to perform the prime check
		CheckPrime check = new CheckPrime();

		// Measure the time taken to perform the prime check
		long time = System.currentTimeMillis();
		check.checkPool(number, threadCount, poolSize);
		long elapsed = System.currentTimeMillis() - time;

		// Display the result and the elapsed time
		System.out.println("   Prime: " + check.getResult());
		System.out.println("   Elapsed: " + elapsed + "ms");
	}

	/**
	 * Tests an alternative thread pool-based prime checking
	 * method and displays the result and elapsed time.
	 *
	 * @param number      the number to check for primality
	 * @param threadCount the number of threads to use
	 * @param poolSize    the size of the thread pool
	 */
	private static void testCheckPool2(int number, int threadCount, int poolSize) {
		System.out.println("Check Pool2");

		// Create a CheckPrime instance to perform the prime check
		CheckPrime check = new CheckPrime();

		// Measure the time taken to perform the prime check
		long time = System.currentTimeMillis();
		check.checkPool2(number, threadCount, poolSize);
		long elapsed = System.currentTimeMillis() - time;

		// Display the result and the elapsed time
		System.out.println("   Prime: " + check.getResult());
		System.out.println("   Elapsed: " + elapsed + "ms");
	}
}
