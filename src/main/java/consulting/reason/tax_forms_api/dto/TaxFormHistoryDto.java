package consulting.reason.tax_forms_api.dto;

import lombok.*;

import java.time.ZonedDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class TaxFormHistoryDto {
    private long id;
    private long taxFormId;
    private ZonedDateTime createdAt;
    private String type;
}
