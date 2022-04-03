package app.service.validators;

import app.exeption.InvalidEntityDataException;

public class ApplicationValidation {
    public static void validateDeclineReason(String reason) throws InvalidEntityDataException {
        if (reason.length() > 255) {
            throw new InvalidEntityDataException("Decline reason is too long. Over 255 characters");
        }
    }
    public static void validateInformation(String info)  throws  InvalidEntityDataException{
        if (info.length() > 500) {
            throw new InvalidEntityDataException("Information is too long. Over 500 characters");
        }
    }
}
