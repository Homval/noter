package ru.khomyakov.noter.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("/{id}")
    public String edit(Model model, @PathVariable("id") long id) {
        model.addAttribute("note", noteRepo.findById(id).orElse(new Note()));
        return "edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("note") Note note, @PathVariable("id") long id) {
        Note oldNote = noteRepo.findById(id).orElse(new Note());
        oldNote.setText(note.getText());
        oldNote.setEventDate(note.getEventDate());
        noteRepo.save(oldNote);

        return "redirect:/";
    }
}