package consulting.reason.tax_forms_api.entity;

import consulting.reason.tax_forms_api.enums.TaxFormHistoryType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "tax_form_histories")
@Entity
public class TaxFormHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "tax_form_id", nullable = false)
    private TaxForm taxForm;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private TaxFormHistoryType type;
}
