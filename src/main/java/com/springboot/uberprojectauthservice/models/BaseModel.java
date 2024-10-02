package com.springboot.uberprojectauthservice.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public abstract class BaseModel {

    @Id // this annotation makes this variable id property of this review table.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate // this annotation tells spring that only handle it for object creation
    @Temporal(TemporalType.TIME) // tells format of date object to be stored Time | Date | Timestamp
    @Column(nullable = false , updatable = false)
    protected Date createdAt;


    @LastModifiedDate // tells only for object updation
    @Temporal(TemporalType.TIME)
    @Column(nullable = false)
    protected Date updatedAt;
}