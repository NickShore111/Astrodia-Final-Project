package com.perscholas.astrodia.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@FieldDefaults(level=AccessLevel.PRIVATE)
@JsonIgnoreProperties({"hibernateLazyInitializer"})
@Entity
@Table(name = "pads")
public class Pad {
    @Id @NonNull
    String id;
    @NonNull
    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "portId", nullable = false)
    Port port;
    @JsonBackReference
    @OneToMany(mappedBy = "launchPad", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Flight> departingFlights;
    @JsonBackReference
    @OneToMany(mappedBy = "arrivalPad", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Flight> arrivingFlights;

    public void addDepartureFlight(Flight f) {
        this.departingFlights.add(f);
    }
    public void addArrivalFlight(Flight f) {
        this.arrivingFlights.add(f);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pad pad = (Pad) o;
        return id.equals(pad.id);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    @Override
    public String toString() {
        return "Pad{" +
                "id='" + id + '\'' +
                ", port=" + port +
                '}';
    }
}
