Feature: Delete record from Database?
  Everybody wants to know if selected record was deleted from Database

  # Scenario 2
  Scenario: Find by field ID and delete car in Database
    Given Database is filled with data
      |id| color | brand      | model   | type  | hasAlloyRims |
      | 0| blue  | Alfa Romeo | T-Roc   | SUV   | false        |
      | 1| black | Toyoda     | Celica  | sedan | true         |
      | 2| red   | Toyota     | GT-86   | coupe | true         |
    And Database contains exactly 3 records
    When I delete record with ID 2
    Then record with ID 2 is deleted
    And Database contains only 2 elements
    But Id counter remains unchanged: 3
