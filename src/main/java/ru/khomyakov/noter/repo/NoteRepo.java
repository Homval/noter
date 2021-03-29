package ru.khomyakov.noter.repo;

import org.springframework.data.repository.CrudRepository;
import ru.khomyakov.noter.domain.Note;

public interface NoteRepo extends CrudRepository<Note, Long> {

}
