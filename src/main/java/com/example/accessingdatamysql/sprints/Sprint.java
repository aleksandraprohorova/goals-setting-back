package com.example.accessingdatamysql.sprints;

import com.example.accessingdatamysql.goals.Goal;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

@Entity
@Table(name = "sprint")
public class Sprint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "id")
    private long id;

    @Column (name = "idUser")
    private long idUser;

    @Column (name = "startDate")
    private Calendar startDate;
    @Column (name = "endDate")
    private Calendar endDate;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "idSprint")
    private List<Goal> goalsList;

    public Sprint()
    {
    }

    Sprint(long id, Calendar startDate, Calendar endDate) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        goalsList = new ArrayList<>();
    }

    public long getId() { return id; }
    public void setId(long id) {
        this.id = id;
    }

    public void setIdUser(long idUser) {
        this.idUser = idUser;
    }
    public long getIdUser() {
        return idUser;
    }

    public Calendar getStartDate()
    {
        return startDate;
    }
    public void setStartDate(String startDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
        try {
            this.startDate = Calendar.getInstance();
            this.startDate.setTime(sdf.parse(startDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public Calendar getEndDate()
    {
        return endDate;
    }
    public void setEndDate(String endDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
        try {
            this.endDate = Calendar.getInstance();
            this.endDate.setTime(sdf.parse(endDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public List<Goal> getGoalsList() {
        return goalsList;
    }
    public void setGoalsList(List<Goal> goalsList)
    {
        this.goalsList = goalsList;
    }


}
