package com.example.goalssetting.entity;

import javax.persistence.*;
import java.util.Calendar;

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

    public Sprint()
    {
    }

    Sprint(long id, Calendar startDate, Calendar endDate) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
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
    /*public void setStartDate(String startDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
        try {
            this.startDate = Calendar.getInstance();
            this.startDate.setTime(sdf.parse(startDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }*/
    public void setStartDate(Calendar startDate) {
        this.startDate = startDate;
    }


    public Calendar getEndDate()
    {
        return endDate;
    }
    /*public void setEndDate(String endDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
        try {
            this.endDate = Calendar.getInstance();
            this.endDate.setTime(sdf.parse(endDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }*/

    public void setEndDate(Calendar endDate) {
        this.endDate = endDate;
    }

}
