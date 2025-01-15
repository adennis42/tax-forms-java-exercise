package consulting.reason.tax_forms_api.service;

import consulting.reason.tax_forms_api.dto.TaxFormDto;
import consulting.reason.tax_forms_api.dto.request.TaxFormDetailsRequest;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

public interface TaxFormService {
    List<TaxFormDto> findAllByYear(Integer year);

    Optional<TaxFormDto> findById(Integer id);

    Optional<TaxFormDto> save(Integer id, TaxFormDetailsRequest taxFormDetailsRequest);

    Optional<TaxFormDto> submit(Integer id);

    Optional<TaxFormDto> returnForm(Integer id);
}
