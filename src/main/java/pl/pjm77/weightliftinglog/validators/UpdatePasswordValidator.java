package pl.pjm77.weightliftinglog.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import pl.pjm77.weightliftinglog.models.User;

import static pl.pjm77.weightliftinglog.services.UserService.passwordsDontMatch;

/**
 * Used when user updates his any of his existing details (except password).
 */
@Component
public class UpdatePasswordValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    /**
     * Checks if password is not empty, has minimum length and matches existing user password.
     * @param o - user object
     * @param errors - validation errors
     */
    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotBlank");
        if (user.getPassword().length() < 4 || user.getPassword().length() > 32) {
            errors.rejectValue("password", "Size.userForm.password");
        }
        if(passwordsDontMatch(user.getPassword())) {
            errors.rejectValue("password", "Diff.userForm.wrongPassword");
        }
    }
}