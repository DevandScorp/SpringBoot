package com.springboot.entity;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
public class Message {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    @NotBlank(message = "Please, fill this field")
    @Length(max = 255,message = "Message's length should be less than 255 symbols")
    private String text;
    @NotBlank(message = "Please, fill this field")
    @Length(max = 255,message = "Tag's length should be less than 255 symbols")
    private String tag;
    /**
     * Для того,чтобы знать,кто написал сообщение.
     * С помощью fetch.EAGER мы говорим о том,что хотим установить загрузку сразу.
     * ManyToOne говорит нам о том,что множество сообщений принадлежат одному пользователю.
     * Можно поставить аннотацию наоборот-эффект будет соответствующий
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User author;
    private String filename;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    /**
     * Не забывай,что конструктор по умолчанию должен быть обязательно
     */
    public Message() {
    }

    public Message(String text, String tag, User author) {
        this.text = text;
        this.tag = tag;
        this.author = author;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
