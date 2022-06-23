package com.perscholas.astrodia.models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Getter @Setter
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@FieldDefaults(level=AccessLevel.PRIVATE)
@Builder
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Spaceliner that = (Spaceliner) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Spaceliner{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
