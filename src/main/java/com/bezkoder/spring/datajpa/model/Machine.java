package com.bezkoder.spring.datajpa.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.*;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.lang.Nullable;
import com.bezkoder.spring.datajpa.service.UserService;
import javax.persistence.*;
import java.sql.Blob;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "Machine")
public class Machine {
//    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Nullable
    @Column(name = "location")
    private String location;
    @Column(name= "user_lock",columnDefinition = "boolean default false")
    private boolean user_lock;
    @Column(name= "machine_lock",columnDefinition = "boolean default false")
    private boolean machine_lock;
    @Lob
    private Blob machinePicture;
    @OneToOne(cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
    @Nullable
//    @JsonIgnore
    @JoinColumn(name = "user_id",referencedColumnName = "user_id")
    private  User current_user;

    public Machine(String location,boolean user_lock,boolean machine_lock,User current_user){
        this.location=location;
        this.user_lock=user_lock;
        this.machine_lock=machine_lock;
        this.current_user=current_user;
    }
    public Machine(String location,boolean user_lock,boolean machine_lock){
        this.location=location;
        this.user_lock=user_lock;
        this.machine_lock=machine_lock;

    }
}
