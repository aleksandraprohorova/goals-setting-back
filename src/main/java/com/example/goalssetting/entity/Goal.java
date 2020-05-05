package com.example.goalssetting.entity;

import javax.persistence.*;

@Entity
@Table(name = "goal")
public class Goal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "id")
    private long id;

    @Column (name = "idSprint")
    private long idSprint;

    @Column (name = "description")
    private String description;

    @Column (name = "isDone")
    private Boolean isDone;

    public Goal()
    {
        this(0, "", false);
    }
    public Goal(long idSprint, String description, Boolean isDone)
    {
        this.idSprint = idSprint;
        this.description = description;
        this.isDone = isDone;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public long getIdSprint() {
        return idSprint;
    }
    public void setIdSprint(long idSprint) {
        this.idSprint = idSprint;
    }

    public Boolean getIsDone() {
        return isDone;
    }
    public void setIsDone(Boolean isDone) {
        this.isDone = isDone;
    }

}
