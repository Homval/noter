package ru.khomyakov.noter.repo;

import org.springframework.data.repository.CrudRepository;
import ru.khomyakov.noter.domain.Note;

import java.util.List;

public interface NoteRepo extends CrudRepository<Note, Long> {

    List<Note> findByEventDate(String eventDate);
}
