package com.perscholas.astrodia.models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "ports")
public class Port {
    @Id
    @NonNull
    private Integer id;
    @NonNull
    private String name;
    @NonNull
    private String location;
    @NonNull
    @JsonManagedReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "regionId", nullable = false)
    private Region regionId;
    @JsonBackReference
    @OneToMany(mappedBy = "port", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Pad> pads;

    public void addPad(Pad p) {
        this.pads.add(p);
    }
}
