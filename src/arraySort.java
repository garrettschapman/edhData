// arraySort class created by Garrett Chapman
// created 05/13/2020
// last updated 05/13/2020
// sorts a 2d array given the array and which column to sort by

class arraySort {
	private static Object[][] values;
	private static int column;
	private static int number;
	
	/*
	 * function to sort a given array
	 */
	public static Object[][] sort(Object[][] array, int category) {
		values = array;
		number = values.length;
		column = category;
		
		quicksort(0, number - 1);
		
		return values;
	} // end of function sort
	
	/*
	 * recursive function to use quicksort algorithm
	 */
	private static void quicksort(int low, int high) {
		int i = low;
		int j = high;
		
		// get middle element to be the pivot point
		double pivot = Double.parseDouble(values[low + (high-low)/2][column].toString());
		
		// divide into two lists
		while (i <= j) {
			// if the left value is smaller than the pivot number, then get the next element from the left list
			while (Double.parseDouble(values[i][column].toString()) < pivot) {
				i++;
			} // end of left internal while loop
			
			// if the right value is larger than the pivot number, then get the next element from the right list
			while(Double.parseDouble(values[j][column].toString()) > pivot) {
				j--;
			} // end of right internal while loop
			
			// if there is a left value that is larger than the pivot number and there is a value in the right list that is smaller than the pivot element, then we exchange the values
			if (i <= j) {
				exchange(i, j);
				i++;
				j--;
			}
		} // end of external while loop
		
		// recursion
		if (low < j) {
			quicksort(low, j);
		}
		if (high > i) {
			quicksort(i, high);
		}
	} // end of function quicksort
	
	/*
	 * function to exchange two rows in the array
	 */
	private static void exchange(int i, int j) {
		Object[] temp = values[i];
		values[i] = values[j];
		values[j] = temp;
	} // end of function exchange
} // end of class arraySort