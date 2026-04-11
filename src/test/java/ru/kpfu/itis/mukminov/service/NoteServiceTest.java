package ru.kpfu.itis.mukminov.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;
import ru.kpfu.itis.mukminov.model.Note;
import ru.kpfu.itis.mukminov.model.User;
import ru.kpfu.itis.mukminov.repository.NoteRepository;
import ru.kpfu.itis.mukminov.service.impl.NoteServiceImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NoteServiceTest {

    @Mock
    private NoteRepository noteRepository;

    @InjectMocks
    private NoteServiceImpl noteService;

    private User owner;
    private User stranger;
    private Note testNote;

    @BeforeEach
    void setUp() {
        owner = new User();
        owner.setId(1L);

        stranger = new User();
        stranger.setId(2L);

        testNote = new Note();
        testNote.setId(100L);
        testNote.setAuthor(owner);
        testNote.setTitle("Original Title");
    }

    @Test
    void getNoteForEdit_Success() {
        when(noteRepository.findById(100L)).thenReturn(Optional.of(testNote));

        Note result = noteService.getNoteForEdit(100L, owner);

        assertNotNull(result);
        assertEquals(owner.getId(), result.getAuthor().getId());
    }

    @Test
    void getNoteForEdit_Forbidden_ShouldThrowException() {
        when(noteRepository.findById(100L)).thenReturn(Optional.of(testNote));

        assertThrows(ResponseStatusException.class, () -> 
            noteService.getNoteForEdit(100L, stranger)
        );
    }

    @Test
    void updateNote_Success() {
        Note updatedData = new Note();
        updatedData.setTitle("New Title");
        
        when(noteRepository.findById(100L)).thenReturn(Optional.of(testNote));

        noteService.updateNote(100L, updatedData, owner);

        assertEquals("New Title", testNote.getTitle());
        verify(noteRepository).save(testNote);
    }

    @Test
    void deleteNote_NotFound_ShouldThrowException() {
        when(noteRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> 
            noteService.deleteNote(999L, owner)
        );
    }
}