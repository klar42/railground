Feature: Railground
  As an user
  I want to have a simple railway model
  In order to try out formal modeling

  Background:
    Given machine

  Scenario: Intial value
    Then is TRUE formula "PATH_REQ = {}"   # No path is requested
    And is TRUE formula "PATH_CURR = {}"   # No path is locked
    And is TRUE formula "PATH_REL = {}"    # No path is released
    And is disabled event "add_PATH_CURR"
    And is disabled event "rem_PATH_CURR"
    And is disabled event "rem_PATH_REQ"
    And is disabled event "set_PATH_REL"

  Scenario Outline: Request path and cancel the reservation again
    Given is enabled event "add_PATH_REQ" with "path = <path>"   # can request path
    And is disabled event "rem_PATH_REQ" with "path = <path>"    # can not cancel path request
    And is FALSE formula "<path> : PATH_REQ"                     # is not requested path
    When fire event "add_PATH_REQ" with "path = <path>"          # request path
    Then is TRUE formula "<path> : PATH_REQ"                     # is requested path
    And is enabled event "rem_PATH_REQ" with "path = <path>"     # can cancel path request
    # No conflicting path can be requested
    And is disabled event "add_PATH_REQ" with "path" = "<paths>" # can not request path
    When fire event "rem_PATH_REQ" with "path = <path>"          # cancel path request
    Then is FALSE formula "<path> : PATH_REQ"                    # is not requested path
    And is disabled event "rem_PATH_REQ" with "path = <path>"    # can not cancel path request
    And is enabled event "add_PATH_REQ" with "path = <path>"     # can request path

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


  Scenario Outline: Request and lock path without points and release it again
    Given fire event "add_PATH_REQ" with "path = <path>"         # request path
    Then is enabled event "add_PATH_CURR" with "path = <path>"   # can lock path
    And is disabled event "rem_PATH_CURR" with "path = <path>"   # can not unlock path
    And is disabled event "set_RAIL_ELEM_PATH"
    When fire event "add_PATH_CURR" with "path = <path>"         # lock path
    Then is FALSE formula "<path> : PATH_REQ"                    # is not requested path
    And is TRUE formula "<path> : PATH_CURR"                     # is locked path "<path>"
    And is enabled event "set_PATH_REL" with "path = <path>"     # can release path
    And is enabled event "set_SIGNAL_ASPECT_PATH" with "sig = <signal> & asp = SIGNAL_ASPECT_PROCEED"
    When fire event "set_SIGNAL_ASPECT_PATH" with "sig = <signal> & asp = SIGNAL_ASPECT_PROCEED"
    Then is disabled event "set_PATH_REL" with "path = <path>" # can not release path
    And is enabled event "set_SIGNAL_ASPECT_DEFAULT" with "sig = <signal>"
    When fire event "set_SIGNAL_ASPECT_DEFAULT" with "sig = <signal>"
    And is enabled event "set_PATH_REL" with "path = <path>" # can release path
    When fire event "set_PATH_REL" with "path = <path>"          # release path
    And is TRUE formula "<path> : PATH_CURR"                     # is locked path
    And is TRUE formula "<path> : PATH_REL"                      # is released path
    And is enabled event "rem_PATH_CURR" with "path = <path>"    # can unlock path
    And is disabled event "set_SIGNAL_ASPECT_PATH" with "sig = <signal> & asp = SIGNAL_ASPECT_PROCEED"
    When fire event "rem_PATH_CURR" with "path = <path>"         # unlock path
    And is FALSE formula "<path> : PATH_CURR"                    # is not locked path "<path>"
    And is FALSE formula "<path> : PATH_REL"                     # is not released path

  Examples:
    | path | signal |
    | R03  | S2     |
    | R08  | S4     |
    | R09  | S6     |
    | R12  | S8     |


  Scenario Outline: Request and lock path with points and release it again
    Given fire event "add_PATH_REQ" with "path = <path>"         # request path
    And is disabled event "add_PATH_CURR" with "path = <path>"   # can not lock path
    And is disabled event "rem_PATH_CURR" with "path = <path>"   # can not unlock path
    And is enabled event "set_RAIL_ELEM_PATH" with "elem" = "<points>"
    When fire event "set_RAIL_ELEM_PATH" with "elem" = "<points>"
    Then is disabled event "set_RAIL_ELEM_PATH" with "elem" = "<points>"
    When fire event "add_PATH_CURR" with "path = <path>"         # lock path
    Then is FALSE formula "<path> : PATH_REQ"                    # is not requested path
    And is TRUE formula "<path> : PATH_CURR"                     # is locked path "<path>"
    And is enabled event "set_PATH_REL" with "path = <path>"     # can release path
    And is enabled event "set_SIGNAL_ASPECT_PATH" with "sig = <signal> & asp = SIGNAL_ASPECT_PROCEED"
    When fire event "set_SIGNAL_ASPECT_PATH" with "sig = <signal> & asp = SIGNAL_ASPECT_PROCEED"
    Then is disabled event "set_PATH_REL" with "path = <path>" # can not release path
    And is enabled event "set_SIGNAL_ASPECT_DEFAULT" with "sig = <signal>"
    When fire event "set_SIGNAL_ASPECT_DEFAULT" with "sig = <signal>"
    And is enabled event "set_PATH_REL" with "path = <path>"     # can release path
    When fire event "set_PATH_REL" with "path = <path>"          # release path
    And is TRUE formula "<path> : PATH_CURR"                     # is locked path
    And is TRUE formula "<path> : PATH_REL"                      # is released path
    And is enabled event "rem_PATH_CURR" with "path = <path>"    # can unlock path
    And is disabled event "set_SIGNAL_ASPECT_PATH" with "sig = <signal> & asp = SIGNAL_ASPECT_PROCEED"
    When fire event "rem_PATH_CURR" with "path = <path>"         # unlock path
    And is FALSE formula "<path> : PATH_CURR"                    # is not locked path "<path>"
    And is FALSE formula "<path> : PATH_REL"                     # is not released path

  Examples:
    | path | signal | points |
    | R01  | S1     | P2, P4 |
    | R02  | S1     | P2     |
    | R04  | S3     | P3, P1 |
    | R05  | S3     | P3, P4 |
    | R06  | S5     | P1, P3 |
    | R07  | S5     | P1     |
    | R10  | S7     | P4, P2 |
    | R11  | S7     | P4, P3 |
    | R13  | S9     | P2     |
    | R14  | S0     | P1     |


  Scenario Outline: Excluded paths
    Given fire event "add_PATH_REQ" with "path = <path1>"         # request 1st path
    And fire event "set_RAIL_ELEM_PATH" with "elem" = "<points1>"
    And fire event "add_PATH_REQ" with "path = <path2>"           # request 2nd path
    And fire event "set_RAIL_ELEM_PATH" with "elem" = "<points2>"
    When fire event "add_PATH_CURR" with "path = <path1>"         # lock 1st path
    Then is disabled event "add_PATH_CURR" with "path = <path2>"  # can not lock 2nd path
    When fire event "set_PATH_REL" with "path = <path1>"          # release 1st path
    Then is disabled event "add_PATH_CURR" with "path = <path2>"  # can not lock 2nd path
    When fire event "rem_PATH_CURR" with "path = <path1>"         # unlock 1st path
    Then is enabled event "add_PATH_CURR" with "path = <path2>"   # can lock 2nd path
    And fire event "add_PATH_CURR" with "path = <path2>"          # lock 2nd path
    And fire event "set_PATH_REL" with "path = <path2>"           # release 2ns path
    And fire event "rem_PATH_CURR" with "path = <path2>"          # unlock 2ns path

  Examples:
    | path1 | points1 | path2 | points2 |
    | R02   | P2      | R05   | P3, P4  |
    | R02   | P2      | R11   | P4, P3  |
    | R05   | P3, P4  | R02   | P2      |
    | R05   | P3, P4  | R07   | P1      |
    | R05   | P3, P4  | R13   | P2      |
    | R05   | P3, P4  | R14   | P1      |
    | R07   | P1      | R05   | P3, P4  |
    | R07   | P1      | R11   | P4, P3  |
    | R11   | P4, P3  | R02   | P2      |
    | R11   | P4, P3  | R07   | P1      |
    | R11   | P4, P3  | R13   | P2      |
    | R11   | P4, P3  | R14   | P1      |
    | R13   | P2      | R05   | P3, P4  |
    | R13   | P2      | R11   | P4, P3  |
    | R14   | P1      | R05   | P3, P4  |
    | R14   | P1      | R11   | P4, P3  |

