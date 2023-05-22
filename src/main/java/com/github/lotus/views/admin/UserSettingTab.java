package com.github.lotus.views.admin;

import com.github.lotus.data.Role;
import com.github.lotus.data.entity.User;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.textfield.TextField;
import org.springframework.stereotype.Component;

/**
 * @author Evgeny Gribanov
 * @version 22.05.2023
 */
@Component
public class UserSettingTab {

    // todo вынести все названия полей в переменные
    private static final String FIELD_WIDTH = "500px";

    public TextField firstNameTextField(User authenticated) {
        var firstName = new TextField("First Name");
        firstName.setRequired(true);
        firstName.setWidth(FIELD_WIDTH);
        firstName.setValue(authenticated.getFirstName());
        return firstName;
    }

    public TextField surNameTextField(User authenticated) {
        var lastName = new TextField("Second Name");
        lastName.setRequired(true);
        lastName.setWidth(FIELD_WIDTH);
        lastName.setValue(authenticated.getSurName());
        return lastName;
    }

    public TextField patronymicTextField(User authenticated) {
        var lastName = new TextField("Patronymic");
        lastName.setRequired(true);
        lastName.setWidth(FIELD_WIDTH);
        lastName.setValue(authenticated.getPatronymic());
        return lastName;
    }

    public TextField usernameTextField(User authenticated) {
        var lastName = new TextField("Login");
        lastName.setRequired(true);
        lastName.setWidth(FIELD_WIDTH);
        lastName.setValue(authenticated.getUsername());
        return lastName;
    }

    public MultiSelectComboBox<Role> rolesComboBox(User authenticated) {
        var comboBox = new MultiSelectComboBox<Role>("Roles");
        comboBox.setItems(authenticated.getRoles());
        comboBox.select(authenticated.getRoles());
        comboBox.setWidth(FIELD_WIDTH);
        return comboBox;
    }
}
