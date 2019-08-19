package pl.pjm77.weightliftinglog.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import pl.pjm77.weightliftinglog.models.ChangePassword;

@Component
public class ChangePasswordValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return ChangePassword.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ChangePassword changePassword = (ChangePassword) o;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "oldPassword", "NotBlank");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmOldPassword", "NotBlank");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "newPassword", "NotBlank");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmNewPassword", "NotBlank");

        if (changePassword.getOldPassword().length() < 4
                || changePassword.getOldPassword().length() > 32) {
            errors.rejectValue("oldPassword", "Size.userForm.password");
        }if (changePassword.getConfirmOldPassword().length() < 4
                || changePassword.getConfirmNewPassword().length() > 32) {
            errors.rejectValue("confirmOldPassword", "Size.userForm.password");
        }if (changePassword.getNewPassword().length() < 4
                || changePassword.getNewPassword().length() > 32) {
            errors.rejectValue("newPassword", "Size.userForm.password");
        }if (changePassword.getConfirmNewPassword().length() < 4
                || changePassword.getConfirmNewPassword().length() > 32) {
            errors.rejectValue("confirmNewPassword", "Size.userForm.password");
        }

        if (!changePassword.getOldPassword().equals(changePassword.getConfirmOldPassword())) {
            errors.rejectValue("oldPassword", "Diff.userForm.confirmPassword");
            errors.rejectValue("confirmOldPassword", "Diff.userForm.confirmPassword");
        }
        if (!changePassword.getNewPassword().equals(changePassword.getConfirmNewPassword())) {
            errors.rejectValue("newPassword", "Diff.userForm.confirmPassword");
            errors.rejectValue("confirmNewPassword", "Diff.userForm.confirmPassword");
        }
    }
}
