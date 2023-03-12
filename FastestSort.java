package Package;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class FastestSort {

  /********************************************************************************
   * Jacob Yealy                                                                  * 
   * Common sorting algorithm time comparison Prompts user for input              *
   * integer array, checks all integer values, then runs the input array against  *
   * mergeSort, heapSort, and quickSort.                                          *
   *******************************************************************************/
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    System.out.println("Enter the number of elements in the array: ");
    int n = scanner.nextInt();
    int[] arr = new int[n];
    System.out.println("Enter the elements of the integer array: ");
    for (int i = 0; i < n; i++) {
      arr[i] = scanner.nextInt();
    }
    scanner.close();

    checkIntArray(arr);

    // Three copies of the input array so that each can be run at time 0.
    int[] arr1 = Arrays.copyOf(arr, n);
    int[] arr2 = Arrays.copyOf(arr, n);
    int[] arr3 = Arrays.copyOf(arr, n);

    long start = System.nanoTime();
    mergeSort(arr1, 0, n - 1);
    long end = System.nanoTime();
    System.out.println("Time taken by merge sort: " + (end - start) + " nanoseconds");

    start = System.nanoTime();
    heapSort(arr2);
    end = System.nanoTime();
    System.out.println("Time taken by heap sort: " + (end - start) + " nanoseconds");

    start = System.nanoTime();
    quickSort(arr3, 0, n - 1);
    end = System.nanoTime();
    System.out.println("Time taken by quick sort: " + (end - start) + " nanoseconds");

    int[] sortedArr = arr1;
    long timeTaken = end - start;
    if ((end - start) > (System.nanoTime() - start)) {
      sortedArr = arr2;
      timeTaken = System.nanoTime() - start;
    }
    if ((end - start) > (System.nanoTime() - start)) {
      sortedArr = arr3;
      timeTaken = System.nanoTime() - start;
    }
    System.out.println("Sorted array: " + Arrays.toString(sortedArr));
    System.out.println("Time taken by the fastest algorithm: " + timeTaken + " nanoseconds");
  }

  /**
   * Splits the array into sorted halves, then calls merge to re-merge the sorted
   * parts.
   * 
   * @param arr - The user input array of values.
   * @param l   - The left side of the split array.
   * @param r   - The right side of the split array.
   * 
   */
  public static void mergeSort(int[] arr, int l, int r) {
    if (l < r) {
      int m = (l + r) / 2;
      mergeSort(arr, l, m);
      mergeSort(arr, m + 1, r);
      merge(arr, l, m, r);
    }
  }

  /**
   * Method for recombination of the arrays sorted subsets.
   * 
   * @param arr - The user input array of values.
   * @param l   - The left side of the split array.
   * @param m   - The middle of the split array (placeholder)
   * @param r   - The right side of the split array.
   * 
   */
  public static void merge(int[] arr, int l, int m, int r) {
    int[] left = Arrays.copyOfRange(arr, l, m + 1);
    int[] right = Arrays.copyOfRange(arr, m + 1, r + 1);
    int i = 0, j = 0, k = l;
    while (i < left.length && j < right.length) {
      if (left[i] <= right[j]) {
        arr[k++] = left[i++];
      } else {
        arr[k++] = right[j++];
      }
    }
    while (i < left.length) {
      arr[k++] = left[i++];
    }
    while (j < right.length) {
      arr[k++] = right[j++];
    }
  }

  /**
   * Sorts the input array using a binary tree (heapify).
   * 
   * @param arr - The user input array of values.
   * 
   */
  public static void heapSort(int[] arr) {
    int n = arr.length;
    for (int i = n / 2 - 1; i >= 0; i--) {
      heapify(arr, n, i);
    }
    for (int i = n - 1; i >= 0; i--) {
      int temp = arr[0];
      arr[0] = arr[i];
      arr[i] = temp;
      heapify(arr, i, 0);
    }
  }

  /**
   * Swaps values of a node/child if a max/min value is found.
   * 
   * @param arr - The user input array of values.
   * @param n   - Size of heap
   * @param i   - Current node index (Which child to compare next).
   * 
   */
  public static void heapify(int[] arr, int n, int i) {
    int largest = i;
    int l = 2 * i + 1;
    int r = 2 * i + 2;
    if (l < n && arr[l] > arr[largest]) {
      largest = l;
    }
    if (r < n && arr[r] > arr[largest]) {
      largest = r;
    }
    if (largest != i) {
      int temp = arr[i];
      arr[i] = arr[largest];
      arr[largest] = temp;
      heapify(arr, n, largest);
    }
  }

  /**
   * Splits the array into sorted halves, then calls merge to re-merge the sorted
   * parts.
   * 
   * @param arr  - The user input array of values to be partitioned.
   * @param low  - Lower bound of the sub-array to be partitioned around pivot.
   * @param high - Higher bound of sub-array to be partitioned around pivot.
   * 
   */
  public static void quickSort(int[] arr, int low, int high) {
    if (low < high) {
      int pi = partition(arr, low, high);
      quickSort(arr, low, pi - 1);
      quickSort(arr, pi + 1, high);
    }
  }

  /**
   * Method for quickSort calculations around pivot.
   * 
   * @param arr  - The user input array of values to be partitioned.
   * @param low  - Lower bound of the sub-array to be partitioned around pivot.
   * @param high - Higher bound of sub-array to be partitioned around pivot.
   * 
   */
  public static int partition(int[] arr, int low, int high) {
    int pivot = arr[high];
    int i = low - 1;
    for (int j = low; j < high; j++) {
      if (arr[j] < pivot) {
        i++;
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
      }
    }
    int temp = arr[i + 1];
    arr[i + 1] = arr[high];
    arr[high] = temp;
    return i + 1;
  }

  /**
   * Makes sure that the input contains only integers.
   * 
   * @param arr - The user input array of values.
   * @exception InputMismatchException Throws if non-integer type found
   * 
   */
  public static void checkIntArray(int[] arr) throws InputMismatchException {
    for (int i = 0; i < arr.length; i++) {
      Integer val = Integer.valueOf(arr[i]);
      if (val == null || !(val instanceof Integer)) {
        throw new InputMismatchException("Array contains a non-integer value.");
      }
    }
  }

}
