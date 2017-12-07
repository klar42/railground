Feature: Railground
  As an user
  I want to have a simple railway model
  In order to try out formal modeling

  Background:
    Given machine

  Scenario: Intial value
    Then expression "PATH_CURR = {}" is TRUE
    And expression "PATH_REQ = {}" is TRUE
    And expression "PATH_REL = {}" is TRUE
    And event "add_PATH_CURR" is disabled
    And event "rem_PATH_CURR" is disabled
    And event "rem_PATH_REQ" is disabled
    And event "set_PATH_REL" is disabled

  Scenario Outline: Request path and cancel the reservation again
    Given can request path "<path>"
    And can not cancel path request "<path>"
    And is not requested path "<path>"
    When request path "<path>"
    Then is requested path "<path>"
    And can cancel path request "<path>"
    # No conflicting path can be requested
    And can not request path "<paths>"
    When cancel path request "<path>"
    Then is not requested path "<path>"
    And can not cancel path request "<path>"
    And can request path "<path>"

  Examples:
    | path | paths                                       |
    | R01  | R01, R02, R04, R05, R06, R09, R10, R11, R13 |
    | R02  | R01, R02, R09, R10, R13                     |
    | R03  | R03, R06, R07                               |
    | R04  | R01, R04, R05, R06, R07, R10, R11, R14      |
    | R05  | R01, R04, R05, R06, R10, R11                |
    | R06  | R01, R03, R04, R05, R06, R07, R10, R11, R14 |
    | R07  | R03, R04, R06, R07, R14                     |
    | R08  | R08                                         |
    | R09  | R01, R02, R09                               |
    | R10  | R01, R02, R04, R05, R06, R10, R11, R13      |
    | R11  | R01, R04, R05, R06, R10, R11                |
    | R12  | R12                                         |
    | R13  | R01, R02, R10, R13                          |
    | R14  | R04, R06, R07, R14                          |


  Scenario Outline: Request Path with points
    Given request path "<path>"
    And can not lock path "<path>"
    And can not unlock path "<path>"
    And event "set_RAIL_ELEM_PATH" is enabled

  Examples:
    | path |
    | R01  |
    | R02  |
    | R04  |
    | R05  |
    | R06  |
    | R07  |
    | R10  |
    | R11  |
    | R13  |
    | R14  |

  Scenario Outline: Request Path without points
    Given request path "<path>"
    Then can lock path "<path>"
    And can not unlock path "<path>"
    And event "set_RAIL_ELEM_PATH" is disabled
    When lock path "<path>"
    Then is not requested path "<path>"
    And is locked path "<path>"
    And can release path "<path>"
    And event "set_SIGNAL_ASPECT_PROCEED" with "sig = <signal> & asp = SIGNAL_ASPECT_PROCEED" is enabled
    When fire event "set_SIGNAL_ASPECT_PROCEED" with "sig = <signal> & asp = SIGNAL_ASPECT_PROCEED"
    Then can not release path "<path>"
    And event "set_SIGNAL_ASPECT_DEFAULT" with "sig = <signal>" is enabled
    When fire event "set_SIGNAL_ASPECT_DEFAULT" with "sig = <signal>"
    Then can release path "<path>"
    When release path "<path>"
    Then is locked path "<path>"
    And is released path "<path>"
    And can unlock path "<path>"
    And event "set_SIGNAL_ASPECT_PROCEED" with "sig = <signal> & asp = SIGNAL_ASPECT_PROCEED" is disabled
    When unlock path "<path>"
    Then is not locked path "<path>"
    And is not released path "<path>"

  Examples:
    | path | signal |
    | R03  | S2     |
    | R08  | S4     |
    | R09  | S6     |
    | R12  | S8     |
