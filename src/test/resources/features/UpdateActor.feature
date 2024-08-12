Feature: Update Actor

  Scenario Outline: Successful update
    Given the user is on actor's detail page with the URL "http://localhost:5174/actors/<id>/update"
    When the user inputs first name "NewUpdated Forename" into the update actor form
    And the user inputs last name "NewUpdated Surname" into the update actor form
    And the user submits the update actor form
    Then the update actor URL should be "http://localhost:5174/actors/<id>"

    Scenarios:
      | id   |
      | 270  |
      | 273  |
      | 272  |

#  Scenario Outline: Unsuccessful update
#    Given the user is on actor's detail page with the URL "http://localhost:5174/actors/<id>/update"
#    When the user inputs first name "X" into the update actor form
#    And the user inputs last name "Y" into the update actor form
#    And the user submits the update actor form
#    Then an error message for updating actor "An error occurred while updating the actor." should be displayed
#
#    Scenarios:
#      | id   |
#      | 270  |
#      | 271  |
#      | 272  |