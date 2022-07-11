package com.bezkoder.spring.datajpa.model;

import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Garbage_type")
public class Garbage_type {
//    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "type_name")
    private String type_name;

    @Column(name="unit_price")
    private double unit_price;


    public Garbage_type(String type_name,double unit_price){
         this.type_name=type_name;
         this.unit_price=unit_price;
    }

}
