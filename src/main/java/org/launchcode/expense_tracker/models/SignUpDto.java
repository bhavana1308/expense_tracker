package org.launchcode.expense_tracker.models;

public record SignUpDto(String firstName, String lastName, String email, String username, char[] password) {

}