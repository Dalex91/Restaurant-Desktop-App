package com.example.restaurantdemoapp.user_interface;

import com.example.restaurantdemoapp.business_logic.meal_service.MealService;
import com.example.restaurantdemoapp.business_logic.order_service.OrderService;
import com.example.restaurantdemoapp.domain.Meal;
import com.example.restaurantdemoapp.domain.Order;
import com.example.restaurantdemoapp.domain.OrderStatus;
import com.example.restaurantdemoapp.errors.GenericException;
import com.example.restaurantdemoapp.errors.OrderNotFoundException;
import com.example.restaurantdemoapp.errors.OutOfStockException;
import com.example.restaurantdemoapp.utils.BaseForm;
import com.example.restaurantdemoapp.utils.Constants;
import com.example.restaurantdemoapp.utils.Utils;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

@Component
@Data
public class EmployeeForm extends BaseForm {
    private JPanel panel1;
    private JTextField searchIdOrderTextField;
    private JButton searchButton;
    private JTextField orderDateTimeTextField;
    private JTextArea mealsOrderedArea;
    private JButton createOrderButton;
    private JTextField searchMealField;
    private JButton searchMealButton;
    private JTextField priceTextField;
    private JTextField stockTextField;
    private JTextArea mealsArea;
    private JButton sendOrderButton;
    private JComboBox quantityCombobox;
    private JButton addMealButton;
    private JLabel orderIdLabel;
    private JComboBox comboBox1;
    private JComboBox statusComboBox;
    private JButton changeStatusButton;
    private JTextField totalTextField;

    @Autowired
    private MealService mealService;
    @Autowired
    private OrderService orderService;

    private static final Integer totalOrder = 0;
    private static List<Meal> currentMeals = new ArrayList<> ();
    private static Integer currentOrderId = 0;

    public EmployeeForm() {

        searchButton.addActionListener (e -> {
            handleSearchOrder ();
        });

        createOrderButton.addActionListener (e -> {
            handleFetchingId();
        });

        searchMealButton.addActionListener (e -> {
            handleMealSearch();
        });

        sendOrderButton.addActionListener (e -> {
            handleCreateOrder ();
        });

        addMealButton.addActionListener (e -> {
            handleInsertMeal();
        });

        changeStatusButton.addActionListener (e -> {
            handleChangeStatus();
        });
    }

    private void initCombobox(Integer limit) {
        quantityCombobox.removeAllItems();
        if(limit == 0) {
            quantityCombobox.addItem (Constants.OUT_OF_STOCK);
            return;
        }
        for (int i = 0; i <= limit; i++)
            quantityCombobox.addItem(i);
    }

    private void handleMealSearch() {
        if (Utils.validateFieldsNotEmpty (searchMealField)) {
            try {
                Meal meal = mealService.getMeal (searchMealField.getText ());
                priceTextField.setText (meal.getPrice ().toString ());
                stockTextField.setText (meal.getStock ().toString ());
                initCombobox (meal.getStock ());
            } catch (GenericException e) {
                e.printStackTrace ();
                showMessage (Constants.MEAL_NOT_FOUND);
            }
        } else {
            initCombobox (0);
            showMessage (Constants.NULL_FIELDS);
        }
    }

    private void handleFetchingId() {
        currentOrderId = orderService.getLastIdOrZero () + 1;
        orderIdLabel.setText (Constants.YOUR_ORDER_ID + currentOrderId);
    }

    private void handleInsertMeal() {
        if (Utils.validateFieldsNotEmpty (searchMealField)) {
            mealsArea.append (Constants.MEAL_INFO);
            mealsArea.append (searchMealField.getText ());
            mealsArea.append (Constants.SPACE_X);
            mealsArea.append (quantityCombobox.getSelectedItem ().toString ());
            mealsArea.append (Constants.NEW_LINE);
            Meal newMeal = new Meal(
                    searchMealField.getText (),
                    Double.valueOf (priceTextField.getText ()),
                    Integer.valueOf (quantityCombobox.getSelectedItem ().toString ())
            );
            currentMeals.add (newMeal);
        }
    }

    private void handleSearchOrder() {
        if(Utils.validateFieldsNotEmpty (searchMealField)) {
            try {
                Order order = orderService.getOrder (Integer.valueOf (searchIdOrderTextField.getText ()));
                totalTextField.setText (order.getTotal ().toString ());
                orderDateTimeTextField.setText (order.getOrderDateTime ().toString ());
                statusComboBox.setSelectedItem (order.getStatus ());
                for(Meal meal : order.getMealsCart ()) {
                    mealsOrderedArea.append (meal.toString () + Constants.NEW_LINE);
                }
            } catch (GenericException | OrderNotFoundException e) {
                e.printStackTrace ();
                showMessage (Constants.GENERIC_ERROR);
            }
        }
    }

    private void handleCreateOrder() {
        try {
            orderService.createOrder (
                    currentOrderId,
                    OrderStatus.valueOf (statusComboBox.getSelectedItem ().toString ()),
                    currentMeals
            );
        } catch (GenericException e) {
            e.printStackTrace ();
            showMessage (Constants.GENERIC_ERROR);
        } catch (OutOfStockException e) {
            e.printStackTrace ();
            showMessage (Constants.OUT_OF_STOCK);
        }
    }

    private void handleChangeStatus() {
        try {
            orderService.updateStatus (
                    Integer.valueOf (searchIdOrderTextField.getText ()),
                    OrderStatus.valueOf (String.valueOf (comboBox1.getSelectedItem ()))
            );
        } catch (GenericException e) {
            showMessage (Constants.GENERIC_ERROR);
        }
    }
}
