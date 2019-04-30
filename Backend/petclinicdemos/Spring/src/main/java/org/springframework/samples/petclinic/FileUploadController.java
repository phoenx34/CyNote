package org.springframework.samples.petclinic;


import java.io.IOException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import org.springframework.samples.petclinic.storage.StorageFileNotFoundException;
import org.springframework.samples.petclinic.storage.StorageService;
import org.springframework.samples.petclinic.notes.Notes;
import org.springframework.samples.petclinic.notes.NotesRepository;

@Controller
public class FileUploadController {

	@Autowired
    private final StorageService storageService;
	
	@Autowired
    public NotesRepository notes;

    @Autowired
    public FileUploadController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping("/")
    public String listUploadedFiles(Model model) throws IOException {

        model.addAttribute("files", storageService.loadAll().map(
                path -> MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
                        "serveFile", path.getFileName().toString()).build().toString())
                .collect(Collectors.toList()));

        return "uploadForm";
    }
    
    @GetMapping("/{lecName}/{lid}")
    public String uploadFile(Model model, @PathVariable("lecName") String fName, @PathVariable("lid") String lid) throws IOException {

    	System.out.println("Got to uploadFile");
    	System.out.println("LID: "+lid);
    	
        model.addAttribute("files", storageService.loadAll().map(
                path -> MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
                        "serveFile", path.getFileName().toString()).build().toString())
                .collect(Collectors.toList()));
        
        //Destination for form post
        String link = "http://cs309-sd-7.misc.iastate.edu:8080/"+lid;
        System.out.println("Link: "+link);
        
        //Add the parameters required to style and to post data
        model.addAttribute("lecName", fName);
        model.addAttribute("link", link);

        return "uploadForm";
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }
    
    @PostMapping("/{lid}")
    public String upload(@RequestParam("file") MultipartFile file,
    		@RequestParam("name") String name, @PathVariable("lid") String lid,
            RedirectAttributes redirectAttributes) {
    	
    	System.out.println("Got to upload");
    	
    	
    	if(file == null)
    		System.out.println("ERROR: File is null");
    	//System.out.println("NID: "+nid);
    	System.out.println("LID: "+lid);
    	System.out.println("Name: "+name);
    	
    	Integer lidL = new Integer(lid);
    	
        storageService.store(file);
        
        
        String path = "/files/" + file.getOriginalFilename();
        
        Notes note = new Notes();
        note.setAddress(path);
        note.setTitle(name);
        note.setLecNum(lidL);
        note.setNID(1);
        
        notes.save(note);
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");

        return "redirect:/";
    }
    
    
    
    /*
    @PostMapping("/{nid}/{lecnum}/{name}")
>>>>>>> ab4776cb49befcb92bc0bbc7288304d24f1471c6
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
            RedirectAttributes redirectAttributes) {
    	
    	if(file == null)
    		System.out.println("ERROR: File is null");
    	System.out.println("NID: "+nid);
    	System.out.println("LID: "+lid);
    	System.out.println("Name: "+name);
    	
        storageService.store(file);

        String path = "/files/" + file.getOriginalFilename();
        
        Notes note = new Notes();
        note.setAddress(path);
        note.setTitle(name);
        note.setLecNum(lid);
        note.setNID(nid);
        
        notes.saveNote(note);

        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");

        return "redirect:/";
    }
    */

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

}