package com.example.restaurantdemoapp.utils;

import javax.swing.*;

public class BaseForm extends JFrame {

    public BaseForm() {
        super();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();
        setSize(Constants.DEFAULT_HEIGHT_WINDOW , Constants.DEFAULT_WIDTH_WINDOW);
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog (null, message);
    }
}
