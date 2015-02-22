package wyq.appengine2.di.utils;

import java.util.Iterator;

/**
 * This abstract class provides the retryable lookup ability. Override the
 * tryLookup(T) method to implement the lookup action. Provide the constructor
 * with the things to be searched and the filter to accept the result.
 * 
 * @author dewafer
 * @since 2
 * @version 1.0
 *
 * @param <R>
 *            type of the expected result
 * @param <T>
 *            type of things to be searched
 */
public abstract class RetryLookup<R, T> implements Iterable<T> {

	private T[] lookups;
	private LookupFilter<R> filter;

	public RetryLookup(T[] lookups, LookupFilter<R> filter) {
		this.lookups = lookups;
		this.filter = filter;
	}

	public final R lookup() throws NotFound {

		for (T retry : this) {
			R found = tryLookup(retry);
			if (filter.accept(found)) {
				return found;
			}
		}

		throw new NotFound();
	}

	public abstract R tryLookup(T param);

	@Override
	public final Iterator<T> iterator() {
		return new Iterator<T>() {

			int i = 0;

			@Override
			public boolean hasNext() {
				return lookups != null && i != lookups.length;
			}

			@Override
			public T next() {
				return lookups != null ? lookups[i++] : null;
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}

	public static interface LookupFilter<S> {
		public boolean accept(S found);
	}

	public static class NotNullFilter<S> implements LookupFilter<S> {

		@Override
		public boolean accept(S found) {
			return found != null;
		}

	}

	public static class NotFound extends Exception {

		/**
		 * 
		 */
		private static final long serialVersionUID = 3405379826105291437L;

	}

}
