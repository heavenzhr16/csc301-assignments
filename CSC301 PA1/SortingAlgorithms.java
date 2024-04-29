/*
 * 2023 Fall
 * CSC 301: Programming Assignment 1: Sorting Stuff
 * October 11, 2023
 * Author : Havin Lim
 */

package csc301.f23.lab1;
import java.util.Comparator;

public class SortingAlgorithms {

    // Method that takes a generic array of type T and any Comparator that can compare T types and performs an Insertion sort.
    public static <T> void InsertionSort(T[] arr, Comparator<? super T> comp) {
        InsertionSortHelper(arr, comp, 0, arr.length);
    }

    // Insertion sort that is performed on lo to hi (specific range) of the array.
    public static <T> void InsertionSortHelper(T[] arr, Comparator<? super T> comp, int lo, int hi) {
        for (int i = lo + 1; i < hi; i++) {

            T key = arr[i];
            int j = i - 1;

            while (j >= lo && comp.compare(arr[j], key) > 0) {
                arr[j + 1] = arr[j];
                j--;
            }

            arr[j + 1] = key;

        }
    }

    // Method that takes a generic array of type T and any Comparator that can compare T types and performs a Quick sort.
    // When performed on small subarrays, it performs an Insertion sort rather than Quick sort.
    public static <T> void QuickSort(T[] arr, Comparator<? super T> comp) {

        if (arr == null || arr.length == 0) {
            return;
        }

        QuickSortHelper(arr, 0, arr.length - 1, comp);

    }

    // Helper method for QuickSort.
    private static <T> void QuickSortHelper(T[] arr, int floor, int ceiling, Comparator<? super T> comp) {

        if (floor < ceiling) {

            // The base case number for the Insertion sort to run rather than QuickSort is 175.
            if (ceiling - floor < 175) {
                InsertionSortHelper(arr, comp, floor, ceiling + 1);
            }

            else {
                int index = partition(arr, floor, ceiling, comp);
                QuickSortHelper(arr, floor, index - 1, comp);
                QuickSortHelper(arr, index + 1, ceiling, comp);
            }

        }
    }

    // Helper method for QuickSort, partitions the array into two subarrays.
    private static <T> int partition(T[] arr, int floor, int ceiling, Comparator<? super T> comp) {

        // Pivot starts with the last element of the array.
        T pivot = arr[ceiling];
        int i = floor - 1;

        for (int j = floor; j < ceiling; j++) {

            // If the current element is smaller than the pivot, swap the current element with the element at index i.
            if (comp.compare(arr[j], pivot) <= 0) {
                i++;
                T temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        // Swap the pivot with the element at index i + 1.
        T temp = arr[i + 1];
        arr[i + 1] = arr[ceiling];
        arr[ceiling] = temp;
        return i + 1;
    }

    // Takes a generic array of type T and any Comparator that can compare T types and performs a Merge sort.
    public static <T> void MergeSort(T[] arr, Comparator<? super T> comp) {

        if (arr == null || arr.length <= 1) {
            return;
        }
        MergeSortHelper(arr, comp, 0, arr.length);
    }

    // Method of Merge sort helper that does Insertion sort for subarrays less than 175 elements.
    public static <T> void MergeSortHelper(T[] arr, Comparator<T> comp, int lo, int hi) {
        
        if (hi - lo < 175) {
            InsertionSortHelper(arr, comp, lo, hi);
            return;
        }

        int mid = (lo + hi) / 2;
        MergeSortHelper(arr, comp, lo, mid);
        MergeSortHelper(arr, comp, mid, hi);
        Merge(arr, comp, lo, mid, hi);

    }

    // Method that Merges two subarrays into one sorted array.
    public static <T> void Merge(T[] arr, Comparator<T> comp, int lo, int mid, int hi) {

        T[] left = (T[]) new Object[mid - lo];
        T[] right = (T[]) new Object[hi - mid];

        for (int i = 0; i < left.length; i++) {
            left[i] = arr[lo + i];
        }

        for (int i = 0; i < right.length; i++) {
            right[i] = arr[mid + i];
        }

        int i = 0;
        int j = 0;
        int k = lo;

        while (i < left.length && j < right.length) {
            if (comp.compare(left[i], right[j]) <= 0) {
                arr[k] = left[i];
                i++;
            } else {
                arr[k] = right[j];
                j++;
            }
            k++;
        }

        while (i < left.length) {
            arr[k] = left[i];
            i++;
            k++;
        }

        while (j < right.length) {
            arr[k] = right[j];
            j++;
            k++;
        }
    }
}
