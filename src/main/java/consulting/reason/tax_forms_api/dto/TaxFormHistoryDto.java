package consulting.reason.tax_forms_api.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class TaxFormHistoryDto {
    private long id;
    private long taxFormId;
    private String createdAt;
    private String type;
}
