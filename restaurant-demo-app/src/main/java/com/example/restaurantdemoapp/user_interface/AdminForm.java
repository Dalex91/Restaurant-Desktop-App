package com.example.restaurantdemoapp.user_interface;

import com.example.restaurantdemoapp.business_logic.meal_service.MealService;
import com.example.restaurantdemoapp.business_logic.user_service.UserService;
import com.example.restaurantdemoapp.domain.Meal;
import com.example.restaurantdemoapp.domain.User;
import com.example.restaurantdemoapp.domain.UserType;
import com.example.restaurantdemoapp.errors.CreateAccountException;
import com.example.restaurantdemoapp.errors.GenericException;
import com.example.restaurantdemoapp.utils.BaseForm;
import com.example.restaurantdemoapp.utils.Constants;
import com.example.restaurantdemoapp.utils.Utils;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;

@Component
@Data
public class AdminForm extends BaseForm {
    private JPanel mainMenuPanel;
    private JPanel createUser;
    private JPanel ordersHistory;
    private JPanel statistics;
    private JPanel options;
    private JButton createUserButton;
    private JButton manageMenuButton;
    private JButton statisticsButton;
    private JPanel manageMeals;
    private JButton ordersButton;
    private JLabel firstNameLabel;
    private JLabel passwordLabel;
    private JTextField lastNameField;
    private JLabel lastNameLabel;
    private JButton insertUserButton;
    private JLabel usernameLabel;
    private JTextField usernameField;
    private JComboBox userTypeCombobox;
    private JLabel userTypeLabel;
    private JButton backButton;
    private JLabel mealNamelLabel;
    private JTextField mealAddNameField;
    private JTextField mealAddPriceField;
    private JTextField mealAddStockField;
    private JButton addMealButton;
    private JTextField searchMealField;
    private JButton searchButton;
    private JTextField priceUpdateField;
    private JTextField stockUpdateField;
    private JButton deleteButton;
    private JButton updateMealButton;
    private JTable statisticsTable;
    private JTextField startDateField;
    private JTextField endDateField;
    private JButton seeOrdersButton;
    private JTable table1;
    private JButton exportHistoryButton;
    private JButton exportStatisticsButton;
    private JTextField passwordField;
    private JTextField firstNameField;
    private JPanel currentPanel;

    @Autowired
    private UserService userService;
    @Autowired
    private MealService mealService;

    public AdminForm () {
        currentPanel = options;

        backButton.addActionListener (e -> {
            handleBackPressed ();
        });

        createUserButton.addActionListener (e -> {
            updatePanel (createUser);
        });

        manageMenuButton.addActionListener (e -> {
            updatePanel (manageMeals);
        });

        statisticsButton.addActionListener (e -> {
            updatePanel (statistics);
        });

        ordersButton.addActionListener (e -> {
            updatePanel (ordersHistory);
        });

        insertUserButton.addActionListener (e -> {
            handleAddUser ();
        });

        addMealButton.addActionListener (e -> {
            handleAddMeal ();
        });

        searchButton.addActionListener (e -> {
            handleSearchMeal ();
        });

        updateMealButton.addActionListener (e -> {
            handleUpdateMeal ();
        });

        deleteButton.addActionListener (e -> {
            handleDeleteMeal ();
        });
    }

    private void handleBackPressed () {
        currentPanel.setVisible (false);
        options.setVisible (true);
        currentPanel = options;
    }

    private void updatePanel (JPanel panel) {
        currentPanel.setVisible (false);
        currentPanel = panel;
        currentPanel.setVisible (true);
    }

    private void handleAddUser () {
        if (Utils.validateFieldsNotEmpty (firstNameField, lastNameField, usernameField, passwordField)
                && ! userTypeCombobox.getSelectedItem ().toString ().equals (Constants.EMPTY_STRING)) {

            User newUser = new User (
                    usernameField.getText (),
                    firstNameField.getText (),
                    lastNameField.getText (),
                    Utils.encrypt (passwordField.getText ()),
                    UserType.valueOf (userTypeCombobox.getSelectedItem ().toString ())
            );

            try {
                userService.createAccount (newUser);
                showMessage (Constants.ACCOUNT_SUCCESSFULLY_CREATED);
            } catch (CreateAccountException e) {
                e.printStackTrace ();
                showMessage (Constants.CREATE_ACCOUNT_FAILED);
            }
        } else
            showMessage (Constants.NULL_FIELDS);
    }

    private void handleAddMeal () {
        if (Utils.validateFieldsNotEmpty (mealAddNameField, mealAddPriceField, mealAddStockField)) {
            Meal newMeal = null;
            try {
                newMeal = new Meal (
                        mealAddNameField.getText (),
                        Double.valueOf (mealAddPriceField.getText ()),
                        Integer.valueOf (mealAddStockField.getText ())
                );
            }
            catch(RuntimeException e) {
                e.printStackTrace ();
                showMessage (Constants.INVALID_INPUT);
                return;
            }
            try {
                mealService.createMeal (newMeal);
                showMessage (Constants.MEAL_ADDED_SUCCESSFULLY);
            } catch (GenericException e) {
                e.printStackTrace ();
                showMessage (Constants.GENERIC_ERROR);
            }
        } else
            showMessage (Constants.NULL_FIELDS);
    }

    private void handleSearchMeal () {
        if (Utils.validateFieldsNotEmpty (searchMealField)) {
            try {
                Meal meal = mealService.getMeal (searchMealField.getText ());
                priceUpdateField.setText (meal.getPrice ().toString ());
                stockUpdateField.setText (meal.getStock ().toString ());
            } catch (GenericException e) {
                e.printStackTrace ();
                showMessage (Constants.MEAL_NOT_FOUND);
            }
        } else
            showMessage (Constants.NULL_FIELDS);
    }

    private void handleUpdateMeal () {
        if (Utils.validateFieldsNotEmpty (searchMealField, priceUpdateField, stockUpdateField)) {
            Meal newMeal;
            try {
                newMeal = new Meal (
                        searchMealField.getText (),
                        Double.valueOf (priceUpdateField.getText ()),
                        Integer.valueOf (stockUpdateField.getText ())
                );
            } catch (RuntimeException e) {
                showMessage (Constants.INVALID_INPUT);
                return;
            }
            try {
                mealService.updateMeal (newMeal);
                showMessage (Constants.MEAL_UPDATED_SUCCESSFULLY);
            } catch (GenericException e) {
                e.printStackTrace ();
                showMessage (Constants.MEAL_UPDATE_FAILED);
            }
        }
    }

    private void handleDeleteMeal () {
        if (Utils.validateFieldsNotEmpty (searchMealField)) {
            try {
                mealService.deleteMeal (searchMealField.getText ());
                showMessage (Constants.MEAL_DELETED_SUCCESSFULLY);
            } catch (GenericException e) {
                e.printStackTrace ();
                showMessage (Constants.MEAL_DELETE_FAILED);
            }
        }
    }
}
