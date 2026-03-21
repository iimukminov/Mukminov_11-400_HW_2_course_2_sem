package ru.kpfu.itis.mukminov.controller;

import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.mukminov.model.Note;
import ru.kpfu.itis.mukminov.service.NoteService;

import java.util.List;

@RestController
@RequestMapping("/admin/notes")
public class AdminNoteController {

    private final NoteService noteService;

    public AdminNoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping
    public List<Note> getAllNotes() {
        return noteService.getAllNotes();
    }

    @DeleteMapping("/{id}")
    public void deleteNote(@PathVariable("id") Long id) {
        noteService.deleteNoteByAdmin(id);
    }
}