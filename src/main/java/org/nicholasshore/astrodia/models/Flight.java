package org.nicholasshore.astrodia.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Builder
@FieldDefaults(level=AccessLevel.PRIVATE)
@Entity
@Table(name = "flights")
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @NonNull
    String flightCode;
    @NonNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MMM dd, yyyy hh:mm a")
    Timestamp arriving;
    @NonNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MMM dd, yyyy hh:mm a")
    Timestamp departing;
    @NonNull
    Integer seatsAvailable;
    @NonNull
    @NumberFormat(style= NumberFormat.Style.CURRENCY)
    int pricePerSeat;
    @Column(updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    Timestamp createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    Timestamp updatedAt;
    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "launchPadId", nullable = false)
    @NonNull
    Pad launchPad;
    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "arrivalPadId", nullable = false)
    @NonNull
    Pad arrivalPad;
    @JsonManagedReference
    @NonNull
    @ManyToOne
    @JoinColumn(name = "shuttleId", nullable = false)
    Shuttle shuttle;

    @PrePersist
    protected void onCreate() {
        long now = System.currentTimeMillis();
        this.createdAt =  new Timestamp(now);
    }

    @PreUpdate
    protected void onUpdate() {
        long now = System.currentTimeMillis();
        this.updatedAt = new Timestamp(now);
    }

    @Override
    public String toString() {
        return "Flight{" +
                "flightCode='" + flightCode + '\'' +
                ", departing=" + departing +
                ", arriving=" + arriving +
                ", seatsAvailable=" + seatsAvailable +
                ", pricePerSeat=" + pricePerSeat +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", launchPad=" + launchPad +
                ", arrivalPad=" + arrivalPad +
                ", shuttle=" + shuttle +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flight flight = (Flight) o;
        return pricePerSeat == flight.pricePerSeat && Objects.equals(id, flight.id) && Objects.equals(flightCode, flight.flightCode) && departing.equals(flight.departing) && arriving.equals(flight.arriving) && Objects.equals(seatsAvailable, flight.seatsAvailable) && Objects.equals(createdAt, flight.createdAt) && Objects.equals(updatedAt, flight.updatedAt) && launchPad.equals(flight.launchPad) && arrivalPad.equals(flight.arrivalPad) && shuttle.equals(flight.shuttle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, flightCode, departing, arriving, seatsAvailable, pricePerSeat, createdAt, updatedAt, launchPad, arrivalPad, shuttle);
    }
}