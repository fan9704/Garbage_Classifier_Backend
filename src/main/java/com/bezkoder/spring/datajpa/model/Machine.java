package com.bezkoder.spring.datajpa.model;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;
import lombok.*;
import org.springframework.lang.Nullable;
import javax.persistence.*;
import java.sql.Blob;
import java.util.HashSet;
import java.util.Set;



@AllArgsConstructor
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
    @Column(name = "machine_picture")
    private Blob machinePicture;
    @OneToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REFRESH})
    @Nullable

    @JoinColumn(name = "user_id",referencedColumnName = "user_id")
    private  User current_user;
    @OneToMany(mappedBy = "machine", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonBackReference
    private Set<Machine_storage> machineStorages = new HashSet<>();

    @OneToMany(mappedBy = "machine_id", cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REFRESH})
    @JsonBackReference
    private Set<Garbage_record> garbage_records = new HashSet<>();




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

    public Machine() {
    }

    public  void addMachineStorage(Machine_storage machineStorage){
        machineStorage.setMachine(this);
        machineStorages.add(machineStorage);
    }


}
