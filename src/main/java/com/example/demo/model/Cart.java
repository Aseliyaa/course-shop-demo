package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name="cart")
public class Cart extends AbstractModel{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int quantity;

    @OneToOne(mappedBy = "cart")
    private User user;

    @ManyToMany
    @JoinTable(name = "cart_courses",
            joinColumns = @JoinColumn(name = "cart_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "course_id", referencedColumnName = "id")
    )
    private List<Course> courses = new ArrayList<>();
}
