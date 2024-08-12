Feature: Delete Actor

  Scenario: Delete an existing actor
    Given the user is on the add actor page
    When the user adds an actor with the first name "ActorToDelete" and last name "John Doe"
    And the user navigates to the actors page
    And click the actor they have added in the list
    And delete the actor
    Then the actor should not be present in the list

  Scenario: Delete a non-existent actor
    Given the user is on the add actor page
    When the user tries to navigate to a non-existent actor page with ID "999999"
    Then the user should not be able to see any delete button

