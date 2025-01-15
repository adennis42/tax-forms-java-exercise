package consulting.reason.tax_forms_api.util;

import consulting.reason.tax_forms_api.dto.TaxFormHistoryDto;
import consulting.reason.tax_forms_api.entity.TaxForm;
import consulting.reason.tax_forms_api.entity.TaxFormHistory;
import consulting.reason.tax_forms_api.enums.TaxFormHistoryType;
import consulting.reason.tax_forms_api.enums.TaxFormStatus;
import consulting.reason.tax_forms_api.exception.TaxFormStatusException;

import java.time.ZonedDateTime;

public class TaxFormStatusUtils {
    public static void save(TaxForm taxForm) throws TaxFormStatusException {
        if (taxForm.getStatus().equals(TaxFormStatus.SUBMITTED) ||
                taxForm.getStatus().equals(TaxFormStatus.ACCEPTED)) {
            throw new TaxFormStatusException(
                    taxForm,
                    TaxFormStatus.IN_PROGRESS
            );
        }

        taxForm.setStatus(TaxFormStatus.IN_PROGRESS);
    }

    public static void submit(TaxForm taxForm) throws TaxFormStatusException {
        if (taxForm.getStatus().equals(TaxFormStatus.SUBMITTED) ||
                taxForm.getStatus().equals(TaxFormStatus.RETURNED) ||
                taxForm.getStatus().equals(TaxFormStatus.NOT_STARTED) ||
                taxForm.getStatus().equals(TaxFormStatus.ACCEPTED)) {
            throw new TaxFormStatusException(
                    taxForm,
                    TaxFormStatus.IN_PROGRESS
            );
        }

        taxForm.setStatus(TaxFormStatus.SUBMITTED);


    }

    public static void returnForm(TaxForm taxForm) throws TaxFormStatusException {
        // Implement with task 4
    }

    public static void accept(TaxForm taxForm) throws TaxFormStatusException {
        // Implement with task 5
    }

}
