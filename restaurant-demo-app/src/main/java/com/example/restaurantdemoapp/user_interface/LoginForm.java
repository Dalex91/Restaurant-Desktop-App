package com.example.restaurantdemoapp.user_interface;

import com.example.restaurantdemoapp.business_logic.user_service.UserService;
import com.example.restaurantdemoapp.domain.User;
import com.example.restaurantdemoapp.errors.LoginException;
import com.example.restaurantdemoapp.utils.BaseForm;
import com.example.restaurantdemoapp.utils.Constants;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.swing.*;

@Component
@Data
public class LoginForm extends BaseForm {
    private JPanel panel1;
    private JTextField usernameField;
    private JTextField passwordField;
    private JButton loginButton;

    @Autowired
    private UserService userService;

    @Autowired
    private AdminForm adminForm;
    @Autowired
    private EmployeeForm employeeForm;

    public LoginForm() {
        loginButton.addActionListener(e -> {
            if(!usernameField.getText ().equals (Constants.EMPTY_STRING) && !passwordField.getText ().equals (Constants.EMPTY_STRING)) {
                try {
                    User currentUser = userService.login (usernameField.getText (), passwordField.getText ());
                    handleNextView (currentUser);
                } catch (LoginException ex) {
                    ex.printStackTrace ();
                    showMessage (Constants.LOGIN_FAILED);
                }
            }
            else
                showMessage (Constants.NULL_FIELDS);
        });
    }

    private void handleNextView(User user) {
        this.dispose ();
        switch (user.getUserType ()) {
            case ADMIN ->
                setContentPane(adminForm.getMainMenuPanel ());
            case EMPLOYEE ->
                setContentPane(employeeForm.getPanel1 ());
        }
        setVisible (true);
    }

}
