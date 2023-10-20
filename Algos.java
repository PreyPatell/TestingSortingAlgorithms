import java.util.*;

public class PreyTestingSortingMethods {

    public static void main(String[] args) {
        //create array with size
        int sz = 50000;
        Integer[] arr = new Integer[sz];
        Integer[] backup = new Integer[sz];

        Random r = new Random();

        //add random number from allotted amount
        for (int i = 0; i < arr.length; i++) {
            arr[i] = r.nextInt(93-13)+13;
        }
        //copy arr to the backup
        System.arraycopy(arr, 0, backup, 0, arr.length);

        //create new list and add all arr array values
        List<Integer> list = Arrays.asList(arr);

        //prints the unsorted list in comparison
        System.out.println("Testing execution time of different sorting methods for sorting random numbers: ");
        System.out.println("The unsorted list: " + list);

        //calculate time for collections
        long start = System.nanoTime();
        Collections.sort(list);
        long end = System.nanoTime();
        long time = end - start;
        double d = (double)time;

        //print sorted list
        System.out.println("The sorted list: " + list);

        //printing all times for different sorting methods and unsort array after
        System.out.printf("\nCollections' Sorting Time: %.2f milliseconds\n", d/100000);
        System.out.printf("My Selection-Sort Time: %.2f milliseconds\n", (double)selectionSort(arr)/100000);
        System.arraycopy(backup, 0, arr, 0, arr.length);
        System.out.printf("My Bubble-Sort Time: %.2f milliseconds\n", (double)bubbleSort(arr)/100000);
        System.arraycopy(backup, 0, arr, 0, arr.length);
        System.out.printf("My Insertion-Sort Time: %.2f milliseconds\n", (double)insertionSort(arr)/100000);
        System.arraycopy(backup, 0, arr, 0, arr.length);
        System.out.printf("My Merge-Sort Time: %.2f milliseconds\n", (double)mergeSort(arr)/100000);
        System.arraycopy(backup, 0, arr, 0, arr.length);
        System.out.printf("My Quick-Sort Time: %.2f milliseconds\n", ((double)quickSort(arr,0,arr.length-1)/100000));
        System.arraycopy(backup, 0, arr, 0, arr.length);
        System.out.printf("My Bucket-Sort Time: %.2f milliseconds\n", (double)bucketSort(arr, 0, arr.length-1, 2)/100000);
    }

    public static <T extends Comparable <? super T>> long selectionSort(T[] a) {
        long start = System.nanoTime();
        for(int i = 0; i < a.length - 1; i++) {
            int next = i; //index of next smallest
            //locate the smallest index
            for (int j = i + 1; j < a.length - 1; j++) {
                if (a[j].compareTo(a[next]) < 0) {
                    next = j;
                }
            }
            //swap if index of next smallest grater outer index
            T temp = a[next];
            a[next] = a[i];
            a[i] = temp;
        }
        long end = System.nanoTime();
        return end - start;
    }

    public static <T extends Comparable <? super T>> long bubbleSort(T[] a) {
        long start = System.nanoTime();
        for (int i = 1; i < a.length; i++) { //scan across whole array
            for (int j = 0; j < a.length - i; j++) { //scan across following number
                //swap positions between two values if higher
                if (a[j].compareTo(a[j+1]) > 0) {
                    T temp = a[j];
                    a[j] = a[j+1];
                    a[j+1] = temp;
                }
            }
        }
        long end = System.nanoTime();
        return end - start;
    }

    public static <T extends Comparable <? super T>> long insertionSort(T[] a) {
        long start = System.nanoTime();
        for (int i = 1; i < a.length; i++) { //go through whole array
            T key = a[i]; //make key index
            for (int j = i - 1; j >= 0 && (a[j].compareTo(key) > 0); j--) { //scan following numbers if greater than key to change and check previous values
                a[j+1] = a[j];
                a[j] = key;
            }

        }
        long end = System.nanoTime();
        return end - start;
    }

    public static <T extends Comparable <? super T>> long quickSort(T[] s, int a, int b){
        long start = System.nanoTime();
        if (a >= b){ //trivially sorted
            long end = System.nanoTime();
            return end - start;
        }
        T pivot = s[b]; //assume pivot has moved to last element
        int l = a; //scan right
        int r = b - 1; //scan left

        while(l <= r) {
            while (l <= r && s[l].compareTo(pivot) <= 0) { //find element larger
                l++;
            }
            while (l <= r && s[l].compareTo(pivot) >= 0) { //find element right
                r--;
            }
            if (l < r){
                //swap and shrink range
                T temp = s[l];
                s[l] = s[r];
                s[r] = temp;
                l++;
                r--;
            }
        }
        //put pivot into its final place
        T temp = s[l];
        s[l] = s[b];
        s[b] = temp;
        //make recursive call
        quickSort(s, a, l-1);
        quickSort(s, l+1, b);
        long end = System.nanoTime();
        return end - start;
    }

    public static <T extends Comparable <? super T>> long mergeSort(T[] S) {
        long start = System.nanoTime();
        int n = S.length;
        if (n < 2) { //if array is already sorted
            long end = System.nanoTime();
            return end - start;
        }

        int mid = n/2;
        T[] S1 = Arrays.copyOfRange (S, 0, mid); //copy of first half
        T[] S2 = Arrays.copyOfRange (S, mid, n); //copy of second half

        mergeSort(S1); //sort copy of first half
        mergeSort(S2); //sort copy of second half
        //merge sorted halves back into original
        int i = 0, j = 0;
        while (i + j < n) {
            if (j == S2.length || (i < S1.length && (S1[i].compareTo(S2[j]) < 0) ))
                S[i+j] = S1[i++]; //copy element and change
            else
                S[i+j] = S2[j++]; //copy element and change
        }
        long end = System.nanoTime();
        return end - start;
    }

    public static long bucketSort(Integer[] a, int first, int last, int maxDigits){
        long start = System.nanoTime();
        Vector<Integer>[] bucket = new Vector[10];

        //instantiate each bucket
        for(int i = 0; i < 10; i++){
            bucket[i] = new Vector<>();
        }
        for(int i = 0; i < maxDigits; i++){
            //clear the Vector buckets
            for (int j = 0; j < 10; j++){
                bucket[j].removeAllElements();
            }
            //placing a[index] at the end of bucket[digit]
            for (int index = first; index<=last; index++){
                Integer digit = findDigit(a[index], i);
                bucket[digit].add(a[index]);
            }
            //placing all the buckets back into the array
            int index = 0;
            for(int m = 0; m < 10; m++){
                for (int n = 0; n < bucket[m].size(); n++){
                    a[index++] = bucket[m].get(n);
                }
            }
        }
        long end = System.nanoTime();
        return end - start;
    }
    //the following method extracts the ith digit from a decimal number
    public static Integer findDigit(int number, int i){
        int target = 0;
        for(int k = 0; k <= i; k++){
            target = number % 10;
            number = number / 10;
        }
        return target;
    }
}