package consulting.reason.tax_forms_api.service;

import consulting.reason.tax_forms_api.AbstractServiceTest;
import consulting.reason.tax_forms_api.dto.TaxFormDetailsDto;
import consulting.reason.tax_forms_api.dto.TaxFormDto;
import consulting.reason.tax_forms_api.dto.request.TaxFormDetailsRequest;
import consulting.reason.tax_forms_api.entity.TaxForm;
import consulting.reason.tax_forms_api.enums.TaxFormStatus;
import consulting.reason.tax_forms_api.exception.TaxFormStatusException;
import consulting.reason.tax_forms_api.repository.TaxFormRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TaxFormServiceTest extends AbstractServiceTest {
    @Autowired
    private TaxFormRepository taxFormRepository;
    private TaxFormService taxFormService;
    private TaxForm taxForm;
    private TaxFormDto taxFormDto;

    private Validator validator;
    private final TaxFormDetailsRequest taxFormDetailsRequest = TaxFormDetailsRequest.builder()
            .ratio(0.5)
            .assessedValue(100)
            .appraisedValue(200L)
            .comments("Testing")
            .build();

    @BeforeEach
    void before() {
        taxFormService = new TaxFormServiceImpl(
                taxFormRepository,
                modelMapper
        );

        taxForm = taxFormRepository.save(TaxForm.builder()
                .formName("Test Form 1")
                .formYear(2024)
                .status(TaxFormStatus.NOT_STARTED)
                .build());
        taxFormDto = modelMapper.map(taxForm, TaxFormDto.class);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testFindAll() {
        assertThat(taxFormService.findAllByYear(2024)).containsExactly(taxFormDto);
        assertThat(taxFormService.findAllByYear(2025)).isEmpty();
    }

    @Test
    void testFindById() {
        assertThat(taxFormService.findById(taxForm.getId())).isEqualTo(Optional.of(taxFormDto));
        assertThat(taxFormService.findById(0)).isEmpty();
    }

    @Test
    void testSave() {
        TaxFormDetailsDto taxFormDetailsDto = TaxFormDetailsDto.builder()
                .ratio(0.5)
                .assessedValue(100)
                .appraisedValue(200L)
                .comments("Testing")
                .build();

        Optional<TaxFormDto> taxFormDto1 = taxFormService.save(taxForm.getId(), taxFormDetailsRequest);
        assertThat(taxFormDto1).isPresent();
        assertThat(taxFormDto1.get().getDetails()).isEqualTo(taxFormDetailsDto);

        assertThat(taxFormService.save(0, taxFormDetailsRequest)).isEmpty();
    }

    @ParameterizedTest
    @EnumSource(value = TaxFormStatus.class, names = {
            "SUBMITTED",
            "ACCEPTED"
    })
    void testSaveHandlesInvalidStatus(TaxFormStatus taxFormStatus) {
        taxForm.setStatus(taxFormStatus);

        TaxFormStatusException taxFormStatusException = new TaxFormStatusException(
                taxForm,
                TaxFormStatus.IN_PROGRESS
        );

        assertThatThrownBy(() -> taxFormService.save(taxForm.getId(), taxFormDetailsRequest))
                .isInstanceOf(TaxFormStatusException.class)
                .hasMessage(taxFormStatusException.getMessage());
    }

    @Test
    void testHandleValidationErrors() {
        TaxFormDetailsRequest taxFormDetailsRequestWithInvalidValues = TaxFormDetailsRequest.builder()
                .ratio(1.5)
                .assessedValue(1000000)
                .appraisedValue(1000000L)
                .comments("Testing")
                .build();

        Set<ConstraintViolation<TaxFormDetailsRequest>> constraintViolations = validator.validate(taxFormDetailsRequestWithInvalidValues);
        assertFalse(constraintViolations.isEmpty(), "Validation should fail");
        Set<String> violationMessages = constraintViolations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toSet());
        assertThat(violationMessages).containsExactlyInAnyOrder("Ratio must be less than 1",
                "Assessed value must be less than or equal to 100,000",
                "Appraised value must be less than or equal to 100,000");
    }
}
