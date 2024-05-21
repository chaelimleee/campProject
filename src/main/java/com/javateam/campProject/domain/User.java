package com.javateam.campProject.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Getter
@NoArgsConstructor
@Entity
@SequenceGenerator(
	    name = "BOARD_SEQ_GENERATOR",
	    sequenceName = "BOARD_SEQ",
	    initialValue = 1,
	    allocationSize = 1)
@Table(name="user_tbl")
public class User extends BaseTimeEntity {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
    
    @Id
    @Column(nullable=false, precision=10, scale=0) // number(10,0)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
    			   generator = "BOARD_SEQ_GENERATOR")
    private BigDecimal id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column
    private String picture;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Builder
    public User(String name, String email, String picture, Role role) {
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.role = role;
    }

    public User update(String name, String picture) {
        this.name = name;
        this.picture = picture;

        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }
    
}