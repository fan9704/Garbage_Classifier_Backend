package com.bezkoder.spring.datajpa.model;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


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
    private long id;

    @ManyToOne
    @JoinColumn(name = "bank_type_id")
    private  Bank_type bank_type;

    @Column(name= "account_code")
    private String account_code;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    public Bank_acct(Bank_type bank_type,String account_code,User user){
        this.bank_type=bank_type;
        this.account_code=account_code;
        this.user=user;
    }

}
