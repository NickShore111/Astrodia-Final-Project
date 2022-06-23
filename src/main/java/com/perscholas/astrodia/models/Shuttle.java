package com.perscholas.astrodia.models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Max;
import java.util.List;
import java.util.Objects;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@FieldDefaults(level=AccessLevel.PRIVATE)
@JsonIgnoreProperties({"hibernateLazyInitializer"})
@Builder
@Entity
@Table(name = "shuttles")
public class Shuttle {
    @Id @NonNull @Length(max = 4)
    String id;
    @NonNull
    String name;
    @NonNull
    Integer passengerCapacity;
    @JsonBackReference
    @OneToMany(mappedBy = "shuttle", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Flight> flights;
    @NonNull
    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "spacelinerId", nullable = false)
    Spaceliner spaceliner;

    public void addFlight(Flight flight) {
        this.flights.add(flight);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shuttle shuttle = (Shuttle) o;
        return id.equals(shuttle.id);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    @Override
    public String toString() {
        return "Shuttle{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", passengerCapacity=" + passengerCapacity +
                ", spaceliner=" + spaceliner +
                '}';
    }
}
