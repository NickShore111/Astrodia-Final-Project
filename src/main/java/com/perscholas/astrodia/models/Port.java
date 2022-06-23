package com.perscholas.astrodia.models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@FieldDefaults(level=AccessLevel.PRIVATE)
@Builder
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
    @ManyToOne
    @JoinColumn(name = "regionId", nullable = false)
    Region region;
    @JsonBackReference
    @OneToMany(mappedBy = "port", cascade = CascadeType.ALL, orphanRemoval = true)
    Set<Pad> pads;

    public void addPad(Pad p) {
        this.pads.add(p);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Port port = (Port) o;
        return id.equals(port.id);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    @Override
    public String toString() {
        return "Port{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", region=" + region +
                '}';
    }
}
