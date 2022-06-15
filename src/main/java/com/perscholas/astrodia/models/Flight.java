package com.perscholas.astrodia.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "flights")
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String flightCode;
    @NonNull
    private Timestamp departing;
    @NonNull
    private Timestamp arriving;
    private Integer seatsAvailable;
    @NumberFormat(style= NumberFormat.Style.CURRENCY)
    private Double pricePerSeat;
    @Column(updatable = false)
    private Timestamp createdAt;
    private Timestamp updatedAt;
    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "launchPadId", nullable = false)
    @NonNull
    private Pad launchPad;
    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "arrivalPadId", nullable = false)
    @NonNull
    private Pad arrivalPad;
    @JsonManagedReference
    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shuttleId", nullable = false)
    private Shuttle shuttle;
    @Transient
    Date departingDate;
    @Transient
    Date arrivingDate;

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

    public Date getDepartingDate() {
        return new Date(this.departing.getTime());
    }
    public Date getArrivingDate() {
        return new Date(this.arriving.getTime());
    }
}