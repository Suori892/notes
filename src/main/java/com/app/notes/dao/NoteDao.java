package com.app.notes.dao;

import com.app.notes.model.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NoteDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public NoteDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Note> index() {
        return jdbcTemplate.query("SELECT * FROM Note", new BeanPropertyRowMapper<>(Note.class));
    }

    public Note show(int id) {
        return jdbcTemplate.query("SELECT * FROM Note WHERE id=?",
                        new Object[]{id}, new BeanPropertyRowMapper<>(Note.class))
                .stream().findAny().orElse(null);
    }

    public void update(int id, Note updatedNote) {
        jdbcTemplate.update("UPDATE Note SET title=?, text=? WHERE id=?",
                updatedNote.getTitle(), updatedNote.getText(),
                id);
    }

    public void create(Note note) {
        jdbcTemplate.update("INSERT INTO Note(title, text) VALUES (?, ?);",
                note.getTitle(), note.getText());
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Note WHERE id=?", id);
    }
}
