import de.prob.Main
import de.prob.scripting.Api
import de.prob.statespace.State
import de.prob.statespace.StateSpace
import de.prob.statespace.Transition

import cucumber.api.*
import static cucumber.api.groovy.EN.*
import static cucumber.api.groovy.Hooks.*

def quoted = /(?:"|')([^"']*)(?:"|')/

Given(~/^machine$/) { ->
    setupConstantsInitialiseMachine()
}

Given(~/^machine with ${quoted}$/) { String expr ->
    setupConstantsInitialiseMachine(expr)
}

When(~/^fire event ${quoted}$/) { String eventName ->
    fireEvent(eventName)
}

When(~/^fire event ${quoted} with ${quoted}$/) { String eventName, String expr ->
    fireEvent(eventName, expr)
}

Then(~/^event ${quoted} is enabled$/) { String eventName ->
    assert true == isEventEnabled(eventName)
}

Then(~/^event ${quoted} with ${quoted} is enabled$/) { String eventName, String expr ->
    assert true == isEventEnabled(eventName, expr)
}

Then(~/^event ${quoted} is disabled$/) { String eventName ->
    assert true == isEventDisabled(eventName)
}

Then(~/^event ${quoted} with ${quoted} is disabled$/) { String eventName, String expr ->
    assert true == isEventDisabled(eventName, expr)
}

Then(~/^expression ${quoted} is TRUE$/) { String expr ->
    assert true == isExpressionTRUE(expr)
}

Then(~/^expression ${quoted} is FALSE$/) { String expr ->
    assert true == isExpressionFALSE(expr)
}


Before() { }

After() { }

World() {
  World.instance
}

@Singleton
public class World {

    Api api = Main.getInjector().getInstance(Api.class)
    String eventb = System.getProperty("eventb")
    StateSpace space = api.eventb_load(eventb) 
    State state

    void setupConstantsInitialiseMachine(String... expr) {
        state = space.getRoot()
        Transition trans = state.findTransition("\$setup_constants", expr)
        if (trans != null) {
            state = trans.getDestination()
        }
        trans = state.findTransition("\$initialise_machine")
        if (trans != null) {
            state = trans.getDestination()
        }
    }

    void fireEvent(String eventName, String... expr) {
        Transition trans = state.findTransition(eventName, expr)
        state = trans.getDestination()
    }

    boolean isEventEnabled(String eventName, String... expr) {
        Transition trans = state.findTransition(eventName, expr)
        return trans != null
    }

    boolean isEventDisabled(String eventName, String... expr) {
        Transition trans = state.findTransition(eventName, expr)
        return trans == null
    }

    boolean isExpressionTRUE(String expr) {
        return state.eval(expr).toString() == "TRUE"
    }

    boolean isExpressionFALSE(String expr) {
        state.eval(expr).toString() == "FALSE"
    }
}
