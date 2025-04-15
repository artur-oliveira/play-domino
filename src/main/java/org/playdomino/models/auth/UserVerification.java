package org.playdomino.models.auth;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.time.ZonedDateTime;

@Entity
@Table(
        name = "user_verification",
        uniqueConstraints = {
                @UniqueConstraint(name = "unique_user_verification_token", columnNames = {"token"})
        }
)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserVerification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String token;

    @Builder.Default
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "expires_at", nullable = false)
    private ZonedDateTime expiresAt = ZonedDateTime.now().plusMinutes(30L);

    @Column(name = "verified_at")
    @Temporal(TemporalType.TIMESTAMP)
    private ZonedDateTime verifiedAt;

    @Column(name = "created_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    @CreationTimestamp
    private ZonedDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_user_verification_user"))
    private User user;
}
