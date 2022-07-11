package com.bezkoder.spring.datajpa.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "Wallet")
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Nullable
    @Column(name = "change_value")
    private BigDecimal change_value;

    @Column(name = "description")
    private String description;

    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private  User user_id;

    @CreationTimestamp//Use Create time
    @Column(name= "time_stamp")
    @Nullable
    private Timestamp time_stamp;


    public Wallet( BigDecimal change_value,String description,User user_id,Timestamp time_stamp){
        this.change_value=change_value;
        this.description=description;
        this.user_id=user_id;
        this.time_stamp=time_stamp;
    }
    public Wallet( BigDecimal change_value,String description,User user_id){
        this.change_value=change_value;
        this.description=description;
        this.user_id=user_id;
    }
}
