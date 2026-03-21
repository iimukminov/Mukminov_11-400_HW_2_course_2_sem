package ru.kpfu.itis.mukminov.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.mukminov.model.Note;
import ru.kpfu.itis.mukminov.service.CustomUserDetails;
import ru.kpfu.itis.mukminov.service.NoteService;

@Controller
@RequestMapping("/notes")
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping
    public String getMyNotes(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        model.addAttribute("notes", noteService.getUserNotes(userDetails.getUser()));
        return "notes";
    }

    @GetMapping("/public")
    public String getPublicNotes(Model model) {
        model.addAttribute("notes", noteService.getPublicNotes());
        return "public_notes";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("note", new Note());
        return "note_form";
    }

    @PostMapping("/create")
    public String createNote(@ModelAttribute Note note, @AuthenticationPrincipal CustomUserDetails userDetails) {
        noteService.createNote(note, userDetails.getUser());
        return "redirect:/notes";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable("id") Long id, @AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        Note note = noteService.getNoteForEdit(id, userDetails.getUser());
        model.addAttribute("note", note);
        return "note_form";
    }

    @PostMapping("/{id}/edit")
    public String editNote(@PathVariable("id") Long id, @ModelAttribute Note updatedNote, @AuthenticationPrincipal CustomUserDetails userDetails) {
        noteService.updateNote(id, updatedNote, userDetails.getUser());
        return "redirect:/notes";
    }

    @PostMapping("/{id}/delete")
    public String deleteNote(@PathVariable("id") Long id, @AuthenticationPrincipal CustomUserDetails userDetails) {
        noteService.deleteNote(id, userDetails.getUser());
        return "redirect:/notes";
    }
}