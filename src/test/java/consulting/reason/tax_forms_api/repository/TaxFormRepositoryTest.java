package consulting.reason.tax_forms_api.repository;

import consulting.reason.tax_forms_api.dto.TaxFormDetailsDto;
import consulting.reason.tax_forms_api.entity.TaxForm;
import consulting.reason.tax_forms_api.enums.TaxFormStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@DataJpaTest
public class TaxFormRepositoryTest {
    @Autowired
    private TaxFormRepository taxFormRepository;

    @Test
    void testSave(){
        TaxFormDetailsDto taxFormDetailsDto = TaxFormDetailsDto.builder()
                .formField1(10)
                .formField2(20L)
                .formField3("Some tax info")
                .build();

        TaxForm taxForm = TaxForm.builder()
                .year(2024)
                .status(TaxFormStatus.ACCEPTED)
                .details(taxFormDetailsDto)
                .build();

        taxFormRepository.save(taxForm);

        assertThat(taxForm.getId()).isNotNull();
        assertThat(taxForm.getStatus()).isEqualTo(TaxFormStatus.ACCEPTED);
        assertThat(taxForm.getDetails()).isEqualTo(taxFormDetailsDto);
    }
}
