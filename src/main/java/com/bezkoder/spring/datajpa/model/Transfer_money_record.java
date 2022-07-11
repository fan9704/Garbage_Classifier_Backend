package com.bezkoder.spring.datajpa.model;
import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "Transfer_money_record")
public class Transfer_money_record {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "receiver")
    private  User receiver;

    @Column(name = "amount")
    private BigDecimal amount;

    @CreationTimestamp//Use Create time
    @Column(name= "time_stamp")
    private Timestamp time_stamp;

    @Column(name="bank_name")
    private String bank_name;

    public Transfer_money_record(User receiver, BigDecimal amount, String bank_name) {
        this.receiver = receiver;
        this.amount = amount;
        this.bank_name = bank_name;
    }
}

