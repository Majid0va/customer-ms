package az.atl.customersss.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PinCodeConstraintValidator implements ConstraintValidator<ValidPinCode, String> {
    @Override
    public boolean isValid(String pinCode, ConstraintValidatorContext context) {
        String regex
                = "^[a-zA-Z0-9]{7}$";

        // Compile the ReGex
        Pattern p = Pattern.compile(regex);

        // If the pin code is empty
        // return false
        if (pinCode == null) {
            return false;
        }
        // Pattern class contains matcher() method
        // to find matching between given pin code
        // and regular expression.
        Matcher m = p.matcher(pinCode);

        // Return if the pin code
        // matched the ReGex
        return m.matches();
    }
}

