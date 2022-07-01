package org.nicholasshore.astrodia.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;
import java.sql.Timestamp;

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
    String flightCode;
    @NonNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MMM dd, yyyy hh:mm a")
    Timestamp departing;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MMM dd, yyyy hh:mm a")
    @NonNull
    Timestamp arriving;
    Integer seatsAvailable;
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


}