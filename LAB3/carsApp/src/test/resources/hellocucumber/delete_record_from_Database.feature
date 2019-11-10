Feature: Delete record from Database?
  Everybody wants to know if selected record was deleted from Database

  # Scenario 2
  Scenario: Find record by field _id and delete car in Database
    Given Database is filled with data
      |id| color | brand      | model   | type  | hasAlloyRims |
      | 0| blue  | Volkswagen | T-Roc   | SUV   | false        |
      | 1| black | Toyoda     | Celica  | sedan | true         |
      | 2| red   | Toyota     | Corolla | coupe | true         |
    And Database contains exactly 3 records
    When I delete record with ID 2
    Then record with ID 2 is deleted
    And Database contains only 2 elements
    But Id counter remains unchanged: 3

  # Scenario 3
  Scenario Outline: Find record by field _model and delete car in Database
    Given Database is filled with data
      |id| color | brand      | model   | type  | hasAlloyRims |
      | 0| blue  | Audi       | Q7'19   | SUV   | false        |
      | 1| black | Toyoda     | GT-86   | sedan | true         |
      | 2| red   | Toyota     | Prius+  | coupe | true         |
    And Database contains exactly 3 records
    And I decide to delete car by field "model"
    When I delete record by regex <regex>
    Then record with id <id> is deleted
    Then result count is <count>
    And database should contain this many records: <should_contain>
    Examples:
      | regex           | count | id | should_contain |
      | ".*'[0-6][7-9]" | 1     | 0  |      2         |
      | ".*-86"         | 1     | 1  |      2         |
      | ".*us\+"        | 1     | 2  |      2         |

