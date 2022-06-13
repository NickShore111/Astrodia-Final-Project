package com.perscholas.astrodia.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.util.Set;


@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "regions")
public class Region {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NonNull
    private String name;
    @NonNull
    private String code;
    @JsonBackReference
    @OneToMany(mappedBy = "regionId", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Port> ports;
}