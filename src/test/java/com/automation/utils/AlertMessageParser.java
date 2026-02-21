package com.automation.utils;

/**
 * Utility class for parsing alert messages from the application.
 */
public class AlertMessageParser {

    private AlertMessageParser() {
        // Private constructor to prevent instantiation
    }

    /**
     * Extracts account number from alert message.
     * Expected format: "Account created successfully with account Number :1025"
     *
     * @param alertMessage the alert message containing account number
     * @return the extracted account number, or null if not found
     */
    public static String extractAccountNumber(String alertMessage) {
        // Extract the number after "account Number :"
        if (alertMessage != null && alertMessage.contains("account Number :")) {
            String[] parts = alertMessage.split("account Number :");
            if (parts.length > 1) {
                return parts[1].trim();
            }
        }
        return null;
    }

    /**
     * Extracts customer ID from alert message.
     * Expected format: "Customer added successfully with customer id :6"
     *
     * @param alertMessage the alert message containing customer ID
     * @return the extracted customer ID, or null if not found
     */
    public static String extractCustomerId(String alertMessage) {
        // Extract the number after "customer id :"
        if (alertMessage != null && alertMessage.contains("customer id :")) {
            String[] parts = alertMessage.split("customer id :");
            if (parts.length > 1) {
                return parts[1].trim();
            }
        }
        return null;
    }
}
