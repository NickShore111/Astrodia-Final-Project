package com.perscholas.astrodia.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;


@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@FieldDefaults(level=AccessLevel.PRIVATE)
@Builder
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Region region = (Region) o;
        return id.equals(region.id);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    @Override
    public String toString() {
        return "Region{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}