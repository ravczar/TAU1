Feature: Is this Car in Database?
  Everybody wants to know if this Car is in Database

  # Scenario 1
  Scenario Outline: Find Car in Database by Regex
    Given Database is filled with data
            | color | brand      | model  | type  | hasAlloyRims |
            | green | Volkswagen | T-Roc  | SUV   | true         |
            | gray  | Audi       | A6     | sedan | true         |
            | brown | Toyota     | GT-86  | coupe | true         |
            | green | Toyota     | Prius+ | sedan | false        |
    When I search repository by regex <regex>
    Then result count is <count>
    Examples:
      | regex     | count |
      | ".*een"   | 2     |
      | "gray"    | 1     |
      | "brown"   | 1     |
      | "else"    | 0     |
      | "nothing" | 0     |