package com.app.notes.controller;

import com.app.notes.dao.NoteDao;
import com.app.notes.model.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/notes")
public class NoteController {
    private final NoteDao noteDao;

    @Autowired
    public NoteController(NoteDao noteDao) {
        this.noteDao = noteDao;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("notes", noteDao.index());
        return "/notes/index";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("note") Note note) {
        return "notes/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("note") Note note) {
        noteDao.create(note);
        return "redirect:/notes";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("note", noteDao.show(id));
        return "notes/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("note")  Note note ,
                         @PathVariable("id") int id) {
        noteDao.update(id, note);
        return "redirect:/notes";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        noteDao.delete(id);
        return "redirect:/notes";
    }
}
