package com.bezkoder.spring.datajpa.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "Garbage_record")
public class Garbage_record {
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "garbage_type")
    private  Garbage_type garbage_type;

    @Column(name = "weight")
    private  double weight;

    @ManyToOne
    @JoinColumn(name = "user")
    private  User user;

    @CreationTimestamp//Use Create time
    @Column(name= "time_stamp")
    private Timestamp time_stamp;

    @ManyToOne
    @JoinColumn(name = "machine_id")
    private  Machine machine_id;

    public Garbage_record(Garbage_type garbage_type, double weight, User user, Machine machine_id) {
        this.garbage_type = garbage_type;
        this.weight = weight;
        this.user = user;
//        this.time_stamp = time_stamp;
        this.machine_id = machine_id;
    }
    //
}
