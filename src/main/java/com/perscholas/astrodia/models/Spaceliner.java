package com.perscholas.astrodia.models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "spaceliners")
public class Spaceliner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NonNull
    private String name;
    @NonNull
    private String code;
    @JsonBackReference
    @OneToMany(mappedBy = "spacelinerId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Shuttle> shuttles;
}
