package com.perscholas.astrodia.models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Getter @Setter
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "spaceliners")
public class Spaceliner {
    @Id @NonNull
    private String id;
    @NonNull
    private String name;
    @JsonBackReference
    @OneToMany(mappedBy = "spaceliner", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Shuttle> shuttles;

    public void addShuttle(Shuttle shuttle) {
        this.shuttles.add(shuttle);
    }
}
