package com.perscholas.astrodia.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.Set;


@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@FieldDefaults(level=AccessLevel.PRIVATE)
@Entity
@Table(name = "regions")
public class Region {

    @Id @NonNull
    String id;
    @NonNull
    String name;
    @JsonBackReference
    @OneToMany(mappedBy = "region", cascade = CascadeType.ALL, orphanRemoval = true)
    Set<Port> ports;

    public void addPort(Port p) {
        this.ports.add(p);
    }
}