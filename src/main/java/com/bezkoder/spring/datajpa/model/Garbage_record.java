package com.bezkoder.spring.datajpa.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "Garbage_Record")
public class Garbage_record {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;


    @ManyToOne
    @JoinColumn(name = "garbage_type")
    private Garbage_type garbage_type;

    @Column(name="weight")
    private double weight;

    @ManyToOne
    @JoinColumn(name = "user")
    private  User user;


    @Column(name="time")
    @Temporal(TemporalType.TIME)
    private Date time;


    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "machine_id")
    private  Machine machine_id;

    public Garbage_record(Garbage_type garbage_type, double weight, User user, Machine machine_id){
        this.garbage_type=garbage_type;
        this.weight=weight;
        this.user=user;
        this.machine_id=machine_id;
    }
}