package ru.kpfu.itis.mukminov.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.kpfu.itis.mukminov.model.Note;
import ru.kpfu.itis.mukminov.model.User;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findByAuthor(User author);

    List<Note> findByIsPublicTrue();

    @Query(value = "select * from notes n where n.title like %?1%", nativeQuery = true)
    List<Note> searchByTitle(@Param("keyword") String keyword);
}