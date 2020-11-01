package com.prudential.car.dao.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
public class CarRentalRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  Long id;
    @ManyToOne
    private Car car;
    private Date startRentalDate;
    private Date endRentalDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Date getStartRentalDate() {
        return startRentalDate;
    }

    public void setStartRentalDate(Date startRentalDate) {
        this.startRentalDate = startRentalDate;
    }

    public Date getEndRentalDate() {
        return endRentalDate;
    }

    public void setEndRentalDate(Date endRentalDate) {
        this.endRentalDate = endRentalDate;
    }
}
