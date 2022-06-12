package com.perscholas.astrodia.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "flights")
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String alphaNumId;
    @NonNull
    private Timestamp departing;
    @NonNull
    private Timestamp arriving;
    private Integer seatsAvailable;
    @Column(updatable = false)
    private Timestamp createdAt;
    private Timestamp updatedAt;
    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "launchPadId", nullable = false)
    private Pad launchPad;
    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "arrivalPadId", nullable = false)
    private Pad arrivalPad;
    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shuttleId", nullable = false)
    private Shuttle shuttleId;

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
}