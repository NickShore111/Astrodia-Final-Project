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

    @Id @NonNull
    private String id;
    @NonNull
    private String name;
    @JsonBackReference
    @OneToMany(mappedBy = "regionId", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Port> ports;

    public void addPort(Port p) {
        this.ports.add(p);
    }
}