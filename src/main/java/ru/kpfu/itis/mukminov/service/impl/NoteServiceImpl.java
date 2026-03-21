package ru.kpfu.itis.mukminov.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ru.kpfu.itis.mukminov.model.Note;
import ru.kpfu.itis.mukminov.model.User;
import ru.kpfu.itis.mukminov.repository.NoteRepository;
import ru.kpfu.itis.mukminov.service.NoteService;

import java.util.List;

@Service
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;

    public NoteServiceImpl(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Note> getUserNotes(User author) {
        return noteRepository.findByAuthor(author);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Note> getPublicNotes() {
        return noteRepository.findByIsPublicTrue();
    }

    @Override
    @Transactional(readOnly = true)
    public Note getNoteById(Long id) {
        return noteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Note not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public Note getNoteForEdit(Long id, User currentUser) {
        Note note = getNoteById(id);
        if (!note.getAuthor().getId().equals(currentUser.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied");
        }
        return note;
    }

    @Override
    @Transactional
    public void createNote(Note note, User author) {
        note.setAuthor(author);
        if (note.getIsPublic() == null) {
            note.setIsPublic(false);
        }
        noteRepository.save(note);
    }

    @Override
    @Transactional
    public void updateNote(Long id, Note updatedNote, User currentUser) {
        Note existingNote = getNoteById(id);

        if (!existingNote.getAuthor().getId().equals(currentUser.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You can only edit your own notes");
        }

        existingNote.setTitle(updatedNote.getTitle());
        existingNote.setContent(updatedNote.getContent());
        existingNote.setIsPublic(updatedNote.getIsPublic() != null ? updatedNote.getIsPublic() : false);

        noteRepository.save(existingNote);
    }

    @Override
    @Transactional
    public void deleteNote(Long id, User currentUser) {
        Note note = getNoteById(id);

        if (!note.getAuthor().getId().equals(currentUser.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You can only delete your own notes");
        }

        noteRepository.delete(note);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Note> getAllNotes() {
        return noteRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteNoteByAdmin(Long id) {
        noteRepository.deleteById(id);
    }
}