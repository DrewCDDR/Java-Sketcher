package Util;

import java.util.*;
 
public class Radix {
    /**
     * This method finds and returns the highest or maximun value of an array.
     * @param arr The array that is going to be sorted.
     * @param n The size of the array that is going to be sorted.
     * @return The maximum value that the array has.
     */
    public static int getMax(int arr[], int n)
    {
        int mx = arr[0];
        for (int i = 1; i < n; i++)
            if (arr[i] > mx)
                mx = arr[i];
        return mx;
    }
 
    /**
     * This method sorts the array with a specified value of digits.
     * @param arr The array that is going to be sorted.
     * @param n The size of the array that is going to be sorted.
     * @param exp The actual number of digits that can have a number to be
     * sorted.
     */
    public static void countSort(int arr[], int n, int exp)
    {
        int output[] = new int[n];
        int i;
        int count[] = new int[10];
        Arrays.fill(count,0);
 
        for (i = 0; i < n; i++)
            count[ (arr[i]/exp)%10 ]++;
 

        for (i = 1; i < 10; i++)
            count[i] += count[i - 1];
 

        for (i = n - 1; i >= 0; i--)
        {
            output[count[ (arr[i]/exp)%10 ] - 1] = arr[i];
            count[ (arr[i]/exp)%10 ]--;
        }
 
        for (i = 0; i < n; i++)
            arr[i] = output[i];
    }

    /**
     * This method sorts an array with using the radix sort method.
     * @param arr The array to be sorted.
     * @param n The size of the array that is going to be sorted.
     */
    public static void radixsort(int arr[], int n)
    {
        int m = getMax(arr, n);
 
        for (int exp = 1; m/exp > 0; exp *= 10)
            countSort(arr, n, exp);
    }
 
    /**
     * This method prints the information of an array.
     * @param arr The array to be printed.
     */
    public static void print(int arr[])
    {
        for (int i=0; i < arr.length; i++)
            System.out.print(arr[i]+" ");
    }
}