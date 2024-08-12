Feature: Add Actor

  Background:
    Given the user is on the add actor page

  Scenario: Successful form submission
    When the user enters "John" into the first name field
    And enters "Doe" into the last name field
    And clicks the submit button
    Then a message "Actor added successfully!" should display

  Scenario: Form submission with errors
    When the user enters "J" into the first name field
    And enters "D" into the last name field
    And clicks the submit button
    Then a message "An error occurred while adding the actor." should display

