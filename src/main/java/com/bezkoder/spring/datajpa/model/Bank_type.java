package com.bezkoder.spring.datajpa.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "Bank_type")
public class Bank_type {
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;
    @Column(name = "bank_name")
    private String bank_name;
    @Column(name= "bank_code")
    private String bank_code;

    public Bank_type(String bank_name,String bank_code){
        this.bank_name=bank_name;
        this.bank_code=bank_code;
    }

}
