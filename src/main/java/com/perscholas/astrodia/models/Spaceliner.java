package com.perscholas.astrodia.models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.Set;

@Getter @Setter
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@FieldDefaults(level=AccessLevel.PRIVATE)
@Entity
@Table(name = "spaceliners")
public class Spaceliner {
    @Id @NonNull
    String id;
    @NonNull
    String name;
    @JsonBackReference
    @OneToMany(mappedBy = "spaceliner", cascade = CascadeType.ALL, orphanRemoval = true)
    Set<Shuttle> shuttles;

    public void addShuttle(Shuttle shuttle) {
        this.shuttles.add(shuttle);
    }
}
