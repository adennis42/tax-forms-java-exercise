package consulting.reason.tax_forms_api.utils;

import consulting.reason.tax_forms_api.entity.TaxForm;
import consulting.reason.tax_forms_api.enums.TaxFormStatus;

public class TaxFormStatusUtils {
    public static void save(TaxForm taxForm){
        if(taxForm.getStatus().equals(TaxFormStatus.RETURNED) ||
                taxForm.getStatus().equals(TaxFormStatus.SUBMITTED) ||
                taxForm.getStatus().equals(TaxFormStatus.ACCEPTED)){
            throw new RuntimeException(
                    "Cannot save form id %d, status is %s".formatted(
                            taxForm.getId(),
                            taxForm.getStatus().name()
                    )
            );
        }

        taxForm.setStatus(TaxFormStatus.IN_PROGRESS);
    }

    public static void submit(TaxForm taxForm){
        // Implement
    }

    public static void returnForm(TaxForm taxForm){
        // Implement
    }

    public static void accept(TaxForm taxForm){
        // Implement
    }
}
