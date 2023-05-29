package com.github.lotus.views.admin;

import com.github.lotus.data.Role;
import com.github.lotus.data.entity.User;
import com.github.lotus.security.AuthenticatedUser;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import org.springframework.stereotype.Component;

import static com.vaadin.flow.component.button.ButtonVariant.LUMO_PRIMARY;

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
        if (authenticated.getRoles().contains(Role.ADMIN)) comboBox.setReadOnly(false);
        else comboBox.setReadOnly(true);
        comboBox.setWidth(FIELD_WIDTH);
        return comboBox;
    }

    public VerticalLayout addUserDetailsInfo(AuthenticatedUser authenticatedUser) {
        var formLayout = new FormLayout();
        var verticalLayout = new VerticalLayout();
        var saveButton = new Button("Save");

        // выполняем все в рамках 2 запросов к базе (юзер и роль)
        // todo настроить миграции бд
        authenticatedUser.getUser().map(user -> {
            var firstName = firstNameTextField(user);
            var surName = surNameTextField(user);
            var patronymic = patronymicTextField(user);
            var username = usernameTextField(user);
            var roles = rolesComboBox(user);

            formLayout.add(firstName, surName, patronymic, username, roles);
            return true;
        });

        saveButton.addThemeVariants(LUMO_PRIMARY);

        verticalLayout.setPadding(false);
        verticalLayout.add(formLayout, saveButton);
        return verticalLayout;
    }
}
