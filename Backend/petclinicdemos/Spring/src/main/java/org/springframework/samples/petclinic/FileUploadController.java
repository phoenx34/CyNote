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
import org.springframework.samples.petclinic.notes.*;

/**
 * This is a flle upload controller 
 * @author Shen Chen
 * @author Marc Issac
 *
 */
@Controller
public class FileUploadController {

    private final StorageService storageService;
    public NotesController notes;

    /**
     * Getter for storageService
     * @param storageService The StorageService object we used 
     */
    @Autowired
    public FileUploadController(StorageService storageService) {
        this.storageService = storageService;
    }

    /**
     * The method lists all of the files 
     * @param model The input Model object 
     * @return "uploadForm"
     * @throws IOException Input Output exception 
     */
    @GetMapping("/")
    public String listUploadedFiles(Model model) throws IOException {

        model.addAttribute("files", storageService.loadAll().map(
                path -> MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
                        "serveFile", path.getFileName().toString()).build().toString())
                .collect(Collectors.toList()));

        return "uploadForm";
    }

    
    
    
    /**
     * The method serves a specific file 
     * @param filename The given file name obatined from the path variable 
     * @return ResponseEntity<Resource>
     */
    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    
    
    
    
    /**
     * The method handles the uploading of a file 
     * @param file The given file 
     * @param redirectAttributes
     * @param nid The note ID
     * @param lid The Lecture ID
     * @return "redirect:/"
     */
    @PostMapping("/{nid}/{lecnum}")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
            RedirectAttributes redirectAttributes, @PathVariable("nid") Integer nid, @PathVariable("lecnum") Integer lid) {
    	
        storageService.store(file);
        Notes note = new Notes();
        note.setLecNum(lid);
        note.setNID(nid);
        notes.saveNote(note);
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");

        return "redirect:/";
    }

    
    /**
     *  The methods handles the file not found exception 
     * @param exc the StorageFileNotFoundException object 
     * @return The build of not found 
     */
    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

}