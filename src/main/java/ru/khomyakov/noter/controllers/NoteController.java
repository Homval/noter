package ru.khomyakov.noter.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.khomyakov.noter.domain.Note;
import ru.khomyakov.noter.repo.NoteRepo;


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
    public String addNote(@RequestParam String text, @RequestParam String eventDate,  Model model) {
        Note note = new Note();
        note.setEventDate(eventDate);
        note.setText(text);

        noteRepo.save(note);

        Iterable<Note> notes = noteRepo.findAll();
        model.addAttribute("notes", notes);
        return "index";
    }

    @PostMapping("filter")
    public String filter(@RequestParam String filter, Model model) {
        Iterable<Note> notes;
        if (filter != null && !filter.isEmpty())
            notes = noteRepo.findByEventDate(filter);
        else
            notes = noteRepo.findAll();
        model.addAttribute("notes", notes);
        return "index";
    }
}