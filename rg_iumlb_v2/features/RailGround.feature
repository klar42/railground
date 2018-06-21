Feature: Railground
  As an user
  I want to have a simple railway model
  In order to try out formal modeling

  Background:
    Given machine

  Scenario: Intial value
    Then is TRUE formula "path_req = {}"   # No path is requested
    And is TRUE formula "path_curr = {}"   # No path is locked
    And is TRUE formula "path_rel = {}"    # No path is released
    And is disabled transition "add_path_curr" for state machine "path_state"
    And is disabled transition "remove_path_curr" for state machine "path_state"
    And is disabled transition "remove_path_req" for state machine "path_state"
    And is disabled transition "set_path_rel" for state machine "path_state"


  Scenario Outline: EVENT-B - Request path and cancel the reservation again
    Given is enabled event "add_path_req" with "p = <path>"      # can request path
    And is disabled event "remove_path_req" with "p = <path>"    # can not cancel path request
    And is FALSE formula "<path> : path_req"                     # is not requested path
    When fire event "add_path_req" with "p = <path>"             # request path
    Then is TRUE formula "<path> : path_req"                     # is requested path
    And is enabled event "remove_path_req" with "p = <path>"     # can cancel path request
    # No conflicting path can be requested
    And is disabled event "add_path_req" with "p" = "<paths>"    # can not request path
    When fire event "remove_path_req" with "p = <path>"          # cancel path request
    Then is FALSE formula "<path> : path_req"                    # is not requested path
    And is disabled event "remove_path_req" with "p = <path>"    # can not cancel path request
    And is enabled event "add_path_req" with "p = <path>"        # can request path

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


  Scenario Outline: iUML-B - Request path and cancel the reservation again
    Given state machine "path_state:<path>"
    And is enabled transition "add_path_req"                     # can request path
    And is disabled transition "remove_path_req"                 # can not cancel path request
    And is not in state "path_req"                               # is not requested path
    When trigger transition "add_path_req"                       # request path
    Then is in state "path_req"                                  # is requested path
    And is enabled transition "remove_path_req"                  # can cancel path request
    # No conflicting path can be requested
    And is disabled transition "add_path_req"                    # can not request path
    When trigger transition "remove_path_req"                    # cancel path request
    Then is not in state "path_req"                              # is not requested path
    And is disabled transition "remove_path_req"                 # can not cancel path request
    And is enabled transition "add_path_req"                     # can request path

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


  Scenario Outline: EVENT-B - Request and lock path without points and release it again
    Given fire event "add_path_req" with "p = <path>"            # request path
    Then is enabled event "add_path_curr" with "p = <path>"      # can lock path
    And is disabled event "remove_path_curr" with "p = <path>"   # can not unlock path
    And is disabled event "setRailElemPathCurr"
    When fire event "add_path_curr" with "p = <path>"            # lock path
    Then is FALSE formula "<path> : path_req"                    # is not requested path
    And is TRUE formula "<path> : path_curr"                     # is locked path
    And is enabled event "set_path_rel" with "p = <path>"        # can release path
    And is enabled event "set_signal_aspect_proceed" with "sig = <signal> & sig_asp = SIGNAL_ASPECT_PROCEED"
    When fire event "set_signal_aspect_proceed" with "sig = <signal> & sig_asp = SIGNAL_ASPECT_PROCEED"
    Then is disabled event "set_path_rel" with "p = <path>"      # can not release path
    And is enabled event "set_signal_aspect_default" with "sig = <signal>"
    When fire event "set_signal_aspect_default" with "sig = <signal>"
    And is enabled event "set_path_rel" with "p = <path>"        # can release path
    When fire event "set_path_rel" with "p = <path>"             # release path
    And is TRUE formula "<path> : path_curr"                     # is locked path
    And is TRUE formula "<path> : path_rel"                      # is released path
    And is enabled event "remove_path_curr" with "p = <path>"    # can unlock path
    And is disabled event "set_signal_aspect_proceed" with "sig = <signal> & sig_asp = SIGNAL_ASPECT_PROCEED"
    When fire event "remove_path_curr" with "p = <path>"         # unlock path
    And is FALSE formula "<path> : path_curr"                    # is not locked path
    And is FALSE formula "<path> : path_rel"                     # is not released path

  Examples:
    | path | signal |
    | R03  | S2     |
    | R08  | S4     |
    | R09  | S6     |
    | R12  | S8     |


  Scenario Outline: iUML-B - Request and lock path without points and release it again
    Given class instance "SIGNAL:<signal>"
    And state machine "path_state:<path>"
    And trigger transition "add_path_req"                        # request path
    Then is enabled transition "add_path_curr"                   # can lock path
    And is disabled transition "remove_path_curr"                # can not unlock path
    And is disabled method "setRailElemPathCurr"
    When trigger transition "add_path_curr"                      # lock path
    Then is not in state "path_req"                              # is not requested path
    And is in state "path_curr"                                  # is locked path
    And is enabled transition "set_path_rel"                     # can release path
    And is enabled method "set_signal_aspect_proceed" with "sig_asp = SIGNAL_ASPECT_PROCEED"
    When call method "set_signal_aspect_proceed" with "sig_asp = SIGNAL_ASPECT_PROCEED"
    Then is disabled transition "set_path_rel"                   # can not release path
    And is enabled method "set_signal_aspect_default"
    When call method "set_signal_aspect_default"
    And is enabled transition "set_path_rel"                     # can release path
    When trigger transition "set_path_rel"                       # release path
    And is in state "path_curr"                                  # is locked path
    And is in state "path_curr.path_rel"                         # is released path
    And is enabled transition "remove_path_curr"                 # can unlock path
    And is disabled method "set_signal_aspect_proceed" with "sig_asp = SIGNAL_ASPECT_PROCEED"
    When trigger transition "remove_path_curr"                   # unlock path
    And is not in state "path_curr"                              # is not locked path
    And is not in state "path_curr.path_rel"                     # is not released path

  Examples:
    | path | signal |
    | R03  | S2     |
    | R08  | S4     |
    | R09  | S6     |
    | R12  | S8     |


  Scenario Outline: EVENT-B - Request and lock path with points and release it again
    Given fire event "add_path_req" with "p = <path>"            # request path
    And is disabled event "add_path_curr" with "p = <path>"      # can not lock path
    And is disabled event "remove_path_curr" with "p = <path>"   # can not unlock path
    And is enabled event "setRailElemPathCurr" with "elem" = "<points>"
    When fire event "setRailElemPathCurr" with "elem" = "<points>"
    Then is disabled event "setRailElemPathCurr" with "elem" = "<points>"
    When fire event "add_path_curr" with "p = <path>"            # lock path
    Then is FALSE formula "<path> : path_req"                    # is not requested path
    And is TRUE formula "<path> : path_curr"                     # is locked path "<path>"
    And is enabled event "set_path_rel" with "p = <path>"        # can release path
    And is enabled event "set_signal_aspect_proceed" with "sig = <signal> & sig_asp = SIGNAL_ASPECT_PROCEED"
    When fire event "set_signal_aspect_proceed" with "sig = <signal> & sig_asp = SIGNAL_ASPECT_PROCEED"
    Then is disabled event "set_path_rel" with "p = <path>"      # can not release path
    And is enabled event "set_signal_aspect_default" with "sig = <signal>"
    When fire event "set_signal_aspect_default" with "sig = <signal>"
    And is enabled event "set_path_rel" with "p = <path>"        # can release path
    When fire event "set_path_rel" with "p = <path>"             # release path
    And is TRUE formula "<path> : path_curr"                     # is locked path
    And is TRUE formula "<path> : path_rel"                      # is released path
    And is enabled event "remove_path_curr" with "p = <path>"    # can unlock path
    And is disabled event "set_signal_aspect_proceed" with "sig = <signal> & sig_asp = SIGNAL_ASPECT_PROCEED"
    When fire event "remove_path_curr" with "p = <path>"         # unlock path
    And is FALSE formula "<path> : path_curr"                    # is not locked path "<path>"
    And is FALSE formula "<path> : path_rel"                     # is not released path

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


  Scenario Outline: iUML-B - Request and lock path with points and release it again
    Given class instance "SIGNAL:<signal>"
    And state machine "path_state:<path>"
    And trigger transition "add_path_req"                        # request path
    And is disabled transition "add_path_curr"                   # can not lock path
    And is disabled transition "remove_path_curr"                # can not unlock path
    And is enabled event "setRailElemPathCurr" with "elem" = "<points>"
    When fire event "setRailElemPathCurr" with "elem" = "<points>"
    Then is disabled event "setRailElemPathCurr" with "elem" = "<points>"
    When trigger transition "add_path_curr"                      # lock path
    Then is not in state "path_req"                              # is not requested path
    And is in state "path_curr"                                  # is locked path "<path>"
    And is enabled transition "set_path_rel"                     # can release path
    And is enabled method "set_signal_aspect_proceed" with "sig_asp = SIGNAL_ASPECT_PROCEED"
    When call method "set_signal_aspect_proceed" with "sig_asp = SIGNAL_ASPECT_PROCEED"
    Then is disabled transition "set_path_rel"                   # can not release path
    And is enabled method "set_signal_aspect_default"
    When call method "set_signal_aspect_default"
    And is enabled transition "set_path_rel"                     # can release path
    When trigger transition "set_path_rel"                       # release path
    And is in state "path_curr"                                  # is locked path
    And is in state "path_curr.path_rel"                         # is released path
    And is enabled transition "remove_path_curr"                 # can unlock path
    And is disabled method "set_signal_aspect_proceed" with "sig_asp = SIGNAL_ASPECT_PROCEED"
    When trigger transition "remove_path_curr"                   # unlock path
    And is not in state "path_curr"                              # is not locked path "<path>"
    And is not in state "path_curr.path_rel"                     # is not released path

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


  Scenario Outline: EVENT-B - Excluded paths
    Given fire event "add_path_req" with "p = <path1>"           # request 1st path
    And fire event "setRailElemPathCurr" with "elem" = "<points1>"
    And fire event "add_path_req" with "p = <path2>"             # request 2nd path
    And fire event "setRailElemPathCurr" with "elem" = "<points2>"
    When fire event "add_path_curr" with "p = <path1>"           # lock 1st path
    Then is disabled event "add_path_curr" with "p = <path2>"    # can not lock 2nd path
    When fire event "set_path_rel" with "p = <path1>"            # release 1st path
    Then is disabled event "add_path_curr" with "p = <path2>"    # can not lock 2nd path
    When fire event "remove_path_curr" with "p = <path1>"        # unlock 1st path
    Then is enabled event "add_path_curr" with "p = <path2>"     # can lock 2nd path
    And fire event "add_path_curr" with "p = <path2>"            # lock 2nd path
    And fire event "set_path_rel" with "p = <path2>"             # release 2ns path
    And fire event "remove_path_curr" with "p = <path2>"         # unlock 2ns path

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


  Scenario Outline: iUML-B - Excluded paths
    Given trigger transition "add_path_req" for state machine "path_state:<path1>"       # request 1st path
    And fire event "setRailElemPathCurr" with "elem" = "<points1>"
    And trigger transition "add_path_req" for state machine "path_state:<path2>"         # request 2nd path
    And fire event "setRailElemPathCurr" with "elem" = "<points2>"
    When trigger transition "add_path_curr" for state machine "path_state:<path1>"       # lock 1st path
    Then is disabled transition "add_path_curr" for state machine "path_state:<path2>"   # can not lock 2nd path
    When trigger transition "set_path_rel" for state machine "path_state:<path1>"        # release 1st path
    Then is disabled transition "add_path_curr" for state machine "path_state:<path2>"   # can not lock 2nd path
    When trigger transition "remove_path_curr" for state machine "path_state:<path1>"    # unlock 1st path
    Then is enabled transition "add_path_curr" for state machine "path_state:<path2>"    # can lock 2nd path
    And trigger transition "add_path_curr" for state machine "path_state:<path2>"        # lock 2nd path
    And trigger transition "set_path_rel" for state machine "path_state:<path2>"         # release 2nd path
    And trigger transition "remove_path_curr" for state machine "path_state:<path2>"     # unlock 2nd path

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

