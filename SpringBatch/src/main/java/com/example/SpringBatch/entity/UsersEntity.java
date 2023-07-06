package com.example.SpringBatch.entity;

import com.example.SpringBatch.DTO.UserEntityBatch;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@NamedNativeQuery(name = "UsersEntity.selectUserDTOBatch" ,
        query= "select  users.id,users.email,users.username,users.fullname ,users.password from users " ,
//        query= "select  users.id,users.email,users.username,users.fullname ,users.password from users FETCH FIRST ROW ONLY " ,
        resultSetMapping = " 1" )
@SqlResultSetMapping(name = " 1" ,
        classes = @ConstructorResult(targetClass = UserEntityBatch.class,
                columns = {
                        @ColumnResult(name = " id" ,type = int.class),
                        @ColumnResult(name = " email" ,type = String.class),
                        @ColumnResult(name = " username" ,type = String.class),
                        @ColumnResult(name = " fullname" ,type = String.class),
                        @ColumnResult(name = " password" ,type = String.class),
                }))

@Entity(name = "users")
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)

public class UsersEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String email;
    private String username;
    private String password;
    private String fullname;
    private String firstname;
    private String lastname;
    private String phone;
    private String country;
    private String avatar;
    private boolean issend;
    @ManyToOne
    @JoinColumn(name = "role_id")
    private RolesEntity roles;

}
