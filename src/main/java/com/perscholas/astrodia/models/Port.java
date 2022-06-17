package com.perscholas.astrodia.models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.Set;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@FieldDefaults(level=AccessLevel.PRIVATE)
@Entity
@Table(name = "ports")
public class Port {
    @Id
    @NonNull
    String id;
    @NonNull
    String name;
    @NonNull
    String location;
    @NonNull
    @JsonManagedReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "region", nullable = false)
    Region region;
    @JsonBackReference
    @OneToMany(mappedBy = "port", cascade = CascadeType.ALL, orphanRemoval = true)
    Set<Pad> pads;

    public void addPad(Pad p) {
        this.pads.add(p);
    }
}
