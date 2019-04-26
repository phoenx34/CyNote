package org.springframework.samples.petclinic.notes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.user.UserController;
import org.springframework.stereotype.Service;

/**
 * NotesService contains most of the functions work with notes object
 * @author Shen Chen
 * @author Marc Issac
 */
@Service
public class NotesService {
	
	@Autowired
	NotesRepository notesRepository;
	
	@Autowired
	NotesController notesController;
	
	
	
	
	/**
	 * Sort the Notes 
	 * @param arr The given Notes array to be sorted 
	 * @return A sorted array of Notes 
	 */
	public Notes[] sortNote(Notes[] arr)
	{
		 sort(arr, arr.length);
		 return arr;
	}
	/**
	 * Insertions sort algorithm
	 * @param s input list 
	 * @param n starting from where to sort
	 */
	// insertion sort
	static void sort(Notes []s, int n) 
	{ 
	    for (int i=1 ;i<n; i++) 
	    { 
	        Notes temp = s[i]; 
	  
	        // Insert s[j] at its correct position 
	        int j = i - 1; 
	        while (j >= 0 && temp.getRating() < s[j].getRating()) 
	        { 
	            s[j+1] = s[j]; 
	            j--; 
	        } 
	        s[j+1] = temp; 
	    } 
	} 
	
	
	
	
	
	
	
	
	
	
	 Notes[] quicksortNote(Notes[] arr)
	{
		quickSort(arr, 0, arr.length-1);
		 return arr;
	}
	
	
	// quick sort according to the rating
    static void quickSort(Notes[] arr, int start, int end){
    	 
        int partition = partition(arr, start, end);
 
        if(partition-1>start) {
            quickSort(arr, start, partition - 1);
        }
        if(partition+1<end) {
            quickSort(arr, partition + 1, end);
        }
    }
 
    
    static int partition(Notes[] arr, int start, int end){
        Notes pivot = arr[end];
 
        for(int i=start; i<end; i++){
            if(arr[i].getRating()<pivot.getRating()){
                Notes temp= arr[start];
                arr[start]=arr[i];
                arr[i]=temp;
                start++;
            }
        }
 
        Notes temp = arr[start];
        arr[start] = pivot;
        arr[end] = temp;
 
        return start;
    }
	
	
	
	
}