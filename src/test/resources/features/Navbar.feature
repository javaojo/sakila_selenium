Feature: Navbar Navigation

Scenario Outline:
    Given the user is on the homepage
    When user clicks the "<link>" link
    Then the user should be directed to the "<url>" URL

Scenarios:
      | link       | url                             |
      | Home       | http://localhost:5174/          |
      | Actors     | http://localhost:5174/actors    |
      | Films      | http://localhost:5174/films     |
      | Add Actors | http://localhost:5174/add-actor |
