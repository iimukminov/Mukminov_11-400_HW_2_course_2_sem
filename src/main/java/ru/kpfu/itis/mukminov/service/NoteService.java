package ru.kpfu.itis.mukminov.service;

import ru.kpfu.itis.mukminov.model.Note;
import ru.kpfu.itis.mukminov.model.User;

import java.util.List;

public interface NoteService {
    List<Note> getUserNotes(User author);
    List<Note> getPublicNotes();
    Note getNoteById(Long id);
    Note getNoteForEdit(Long id, User currentUser);
    void createNote(Note note, User author);
    void updateNote(Long id, Note updatedNote, User currentUser);
    void deleteNote(Long id, User currentUser);

    List<Note> getAllNotes();
    void deleteNoteByAdmin(Long id);
}