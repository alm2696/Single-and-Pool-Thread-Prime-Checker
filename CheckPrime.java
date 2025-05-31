package mod08_03;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;

/**
 * The CheckPrime class provides methods to check whether
 * a number is prime. It supports checking with a single
 * thread, thread pool, and an alternative pool method.
 * 
 * @author angel
 */
public class CheckPrime {

	// Stores the result of the prime check
	private boolean result;

	// Lock object to synchronize updates to the result variable
	private String lockResult = "result lock";

	/**
	 * Returns the result of the prime number check.
	 *
	 * @return true if the number is prime, false otherwise
	 */
	public boolean getResult() {
		return this.result;
	}

	/**
	 * Checks if a number is prime using a single-threaded approach.
	 *
	 * @param number      the number to check for primality
	 * @param threadCount the number of threads to simulate
	 */
	public void checkSingle(int number, int threadCount) {
		this.result = true;  // Assume the number is prime initially

		// Create a single-threaded executor service
		ExecutorService single = Executors.newSingleThreadScheduledExecutor();

		// Divide the work between "threadCount" portions, though a single thread is used
		for (int i = 0; i < threadCount; i++) {
			long start = (long) i * number / threadCount;
			long end = (long) (i + 1) * number / threadCount;

			// Execute the prime-checking task
			single.execute(() -> {
				isPrimeSync(number, start, end);
			});
		}

		// Close the executor service
		single.close();
	}

	/**
	 * Checks if a number is prime using a thread pool.
	 *
	 * @param number      the number to check for primality
	 * @param threadCount the number of threads to divide the work across
	 * @param poolSize    the size of the thread pool to use
	 */
	public void checkPool(int number, int threadCount, int poolSize) {
		this.result = true;  // Assume the number is prime initially

		// Create a fixed thread pool with the specified size
		ExecutorService pool = Executors.newFixedThreadPool(poolSize);

		// Divide the work among the threads
		for (int i = 0; i < threadCount; i++) {
			long start = (long) i * number / threadCount;
			long end = (long) (i + 1) * number / threadCount;

			// Execute the prime-checking task
			pool.execute(() -> {
				isPrimeSync(number, start, end);
			});
		}

		// Close the executor service
		pool.close();
	}

	/**
	 * A synchronized method to check if a number is prime within a specific range.
	 * This method is used to ensure thread-safe updates to the result variable.
	 *
	 * @param number the number to check for primality
	 * @param start  the starting point of the range
	 * @param end    the end point of the range
	 */
	private void isPrimeSync(int number, long start, long end) {
		System.out.println("   range: " + start + ":" + end);

		// Handle special cases
		if ((number == 0) || (number == 1) || (number < 0)) {
			synchronized (lockResult) {
				this.result = false;
			}
			return;
		}

		// Check for factors in the specified range
		for (long i = Math.max(2, start); i < end; i++) {
			if (number % i == 0) {
				synchronized (lockResult) {
					this.result = false;
				}
				return;
			}
		}
	}

	/**
	 * Checks if a number is prime using a thread pool and a Callable approach.
	 * This method uses Future objects to gather the results from multiple threads.
	 *
	 * @param number      the number to check for primality
	 * @param threadCount the number of threads to divide the work across
	 * @param poolSize    the size of the thread pool to use
	 */
	public void checkPool2(int number, int threadCount, int poolSize) {
		this.result = true;  // Assume the number is prime initially

		// Create a fixed thread pool with the specified size
		ExecutorService pool = Executors.newFixedThreadPool(poolSize);

		// List to store the futures from each thread
		List<Future<?>> futures = new ArrayList<>();

		// Divide the work among the threads
		for (int i = 0; i < threadCount; i++) {
			long start = (long) i * number / threadCount;
			long end = (long) (i + 1) * number / threadCount;

			// Create a Callable task to check primality in the specified range
			Callable<Boolean> callable = () -> isPrime(number, start, end);

			// Submit the task to the pool and store the future result
			Future<Boolean> future = pool.submit(callable);
			futures.add(future);
		}

		// Process each future result
		for (Future<?> future : futures) {
			try {
				// Combine the result from each thread
				Boolean threadResult = (Boolean) future.get();
				this.result = this.result && threadResult;

			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}

		// Close the executor service
		pool.close();
	}

	/**
	 * Checks if a number is prime within a specific range.
	 *
	 * @param number the number to check for primality
	 * @param start  the starting point of the range
	 * @param end    the end point of the range
	 * @return true  if the number is prime in the range, false otherwise
	 */
	private Boolean isPrime(int number, long start, long end) {
		System.out.println("   range: " + start + ":" + end);

		// Handle special cases (0, 1, and negative numbers are not prime)
		if ((number == 0) || (number == 1) || (number < 0)) {
			return false;
		}

		// Check for factors in the specified range
		for (long i = Math.max(2, start); i < end; i++) {
			if (number % i == 0) {
				return false;
			}
		}

		return true;  // No factors found, so the number is prime in the range
	}
}
