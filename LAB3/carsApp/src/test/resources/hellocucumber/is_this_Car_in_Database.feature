Feature: Is this Car in Database?
  Everybody wants to know if this Car is in Database

  # Scenario 1
  Scenario Outline: Find Car in Database by Regex
    Given Database is filled with data
            | color | brand      | model  | type  | hasAlloyRims |
            | green | Volkswagen | T-Roc  | SUV   | true         |
            | gray  | Audi       | A4'89  | sedan | true         |
            | brown | Toyota     | GT-86  | coupe | true         |
            | brown | Toyota     | GT-86  | coupe | true         |
            | green | Toyota     | Prius+ | sedan | false        |
    When the searched Car field is set to "model"
    And I search repository by regex <regex>
    Then result count is <count>
    Examples:
      | regex           | count |
      | ".*Roc"         | 1     |
      | ".*'[0-9][0-9]" | 1     |
      | ".*-86"         | 2     |
      | ".*us\+"        | 1     |
      | "nothing"       | 0     |