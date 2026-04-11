package ru.kpfu.itis.mukminov.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.kpfu.itis.mukminov.model.Note;
import ru.kpfu.itis.mukminov.service.NoteService;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AdminNoteController.class)
class AdminNoteControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private NoteService noteService;

    @Test
    void getAllNotes_ShouldReturnList() throws Exception {
        given(noteService.getAllNotes()).willReturn(List.of(new Note()));

        mockMvc.perform(get("/admin/notes")
                .with(user("admin").roles("ADMIN")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void deleteNote_ShouldCallService() throws Exception {
        mockMvc.perform(delete("/admin/notes/{id}", 1L)
                .with(csrf())
                .with(user("admin").roles("ADMIN")))
                .andExpect(status().isOk());

        verify(noteService).deleteNoteByAdmin(1L);
    }
}