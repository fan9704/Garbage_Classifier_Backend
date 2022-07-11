package com.bezkoder.spring.datajpa.model;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;



@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "Machine_storage")
public class Machine_storage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "machine")
    private  Machine machine;

    @ManyToOne
    @JoinColumn(name = "garbageType")
    private  Garbage_type garbageType;

    @CreationTimestamp//Use Create time
    @Column(name= "time_stamp")
    private Timestamp time_stamp;

    @Column(name = "storage")
    private double storage;
    public Machine_storage(Machine machine,Garbage_type garbage_type,double storage){
           this.machine=machine;
           this.garbageType=garbage_type;
           this.storage=storage;
    }

}
