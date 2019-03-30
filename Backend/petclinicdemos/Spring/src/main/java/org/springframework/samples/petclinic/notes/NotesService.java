package org.springframework.samples.petclinic.notes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.user.UserController;
import org.springframework.stereotype.Service;

@Service
public class NotesService {
	
	@Autowired
	NotesRepository notesRepository;
	
	@Autowired
	NotesController notesController;
	
	public String[] sortNote()
	{
		
	}
	
	// insertion sort
	void sort(String s[], int n) 
	{ 
	    for (int i=1 ;i<n; i++) 
	    { 
	        String temp = s[i]; 
	  
	        // Insert s[j] at its correct position 
	        int j = i - 1; 
	        while (j >= 0 && temp.length() < s[j].length()) 
	        { 
	            s[j+1] = s[j]; 
	            j--; 
	        } 
	        s[j+1] = temp; 
	    } 
	} 
	
	
	// quick sort according to the 
    public static void quickSort(int[] arr, int start, int end){
    	 
        int partition = partition(arr, start, end);
 
        if(partition-1>start) {
            quickSort(arr, start, partition - 1);
        }
        if(partition+1<end) {
            quickSort(arr, partition + 1, end);
        }
    }
 
    public static int partition(int[] arr, int start, int end){
        int pivot = arr[end];
 
        for(int i=start; i<end; i++){
            if(arr[i]<pivot){
                int temp= arr[start];
                arr[start]=arr[i];
                arr[i]=temp;
                start++;
            }
        }
 
        int temp = arr[start];
        arr[start] = pivot;
        arr[end] = temp;
 
        return start;
    }
	
	
	
	
}
