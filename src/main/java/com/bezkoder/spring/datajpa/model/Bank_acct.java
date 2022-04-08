package com.bezkoder.spring.datajpa.model;
import lombok.*;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "Bank_acct")
public class Bank_acct {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "bank_type_id")
    private  Bank_type bank_type;

    @Column(name= "account_code")
    private String account_code;

    public Bank_acct(Bank_type bank_type,String account_code){
        this.bank_type=bank_type;
        this.account_code=account_code;
    }

}