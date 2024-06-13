package com.Court.CRUD.Controller;

public class GenerateString {
    private static final String PREFIX = "C";
    private static final int PADDING_LENGTH = 3;

    public static String generateNextCode(int currentNumber) {
        int nextNumber = currentNumber + 1;
        return PREFIX + String.format("%0" + PADDING_LENGTH + "d", nextNumber);
    }

}
