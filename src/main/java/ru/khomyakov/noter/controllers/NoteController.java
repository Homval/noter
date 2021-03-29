package ru.khomyakov.noter.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.khomyakov.noter.domain.Note;
import ru.khomyakov.noter.repo.NoteRepo;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@Controller
public class NoteController {
    private final NoteRepo noteRepo;

    @Autowired
    public NoteController(NoteRepo noteRepo) {
        this.noteRepo = noteRepo;
    }

    @GetMapping
    public String index(Model model) {
        Iterable<Note> notes = noteRepo.findAll();
        model.addAttribute("notes", notes);
        return "index";
    }

    @PostMapping
    public String add(@RequestParam String text, @RequestParam String eventDate,  Model model) throws ParseException {
        Note note = new Note();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        note.setEventDate(format.parse(eventDate));
        note.setText(text);

        noteRepo.save(note);

        Iterable<Note> notes = noteRepo.findAll();
        model.addAttribute("notes", notes);
        return "index";
    }
}