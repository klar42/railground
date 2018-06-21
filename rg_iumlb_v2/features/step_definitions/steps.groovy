import de.prob.Main
import de.prob.scripting.Api
import de.prob.statespace.State
import de.prob.statespace.StateSpace
import de.prob.statespace.Transition

import cucumber.api.*
import static cucumber.api.groovy.EN.*
import static cucumber.api.groovy.Hooks.*

def quoted = /(?:"|')([^"']*)(?:"|')/
def pair = /${quoted}\s*=\s*${quoted}/
def comment = /(?:\s+#.*)?/
def withOpt = /(?: with ${quoted})?/
def forCI = /(?: for class instance ${quoted})?/
def forSM = /(?: for state machine ${quoted})?/

// Debug

And(~/^printState$/) { ->
    println(space.printState(state))
}


// Event-B

// Setup constants (optionally with the given constants constraints) and initialize the machine.
Given(~/^machine${withOpt}${comment}$/) {
    String formula ->   // Parameter formula is optional
    setupConstantsInitialiseMachine(formula)
}

Given(~/^machine with${comment}$/) {
    String formula ->
    setupConstantsInitialiseMachine(formula)
}

// Fire the given event (optionally with the given parameters constraints).
When(~/^fire event ${quoted}${withOpt}${comment}$/) {
    String eventName, String formula ->   // Parameter formula is optional
    fireEvent(eventName, formula)
}

When(~/^fire event ${quoted} with ${pair}${comment}$/) {
    String eventName, String attrName, List<String> values ->
    fireEvents(eventName, attrName, values)
}

When(~/^fire event ${quoted} with${comment}$/) {
    String eventName, DataTable table ->
    fireEvents(eventName, table.asMaps(String, String))
}

// Check if the given event (optionally with the given parameters constraints) is enabled.
Then(~/^is enabled event ${quoted}${withOpt}${comment}$/) {
    String eventName, String formula ->   // Parameter formula is optional
    assert true == isEventEnabled(eventName, formula)
}

Then(~/^is enabled event ${quoted} with ${pair}${comment}$/) {
    String eventName, String attrName, List<String> values ->
    assert true == areEventsEnabled(eventName, attrName, values)
}

// Check if the given event (optionally with the given parameters constraints) is disabled.
Then(~/^is disabled event ${quoted}${withOpt}${comment}$/) {
    String eventName, String formula ->   // Parameter formula is optional
    assert true == isEventDisabled(eventName, formula)
}

Then(~/^is disabled event ${quoted} with ${pair}${comment}$/) {
    String eventName, String attrName, List<String> values ->
    assert true == areEventsDisabled(eventName, attrName, values)
}

// Check if the given formula evaluates to the given value.
Then(~/^is (FALSE|TRUE) formula ${quoted}${comment}$/) {
    String value, String formula ->
    assert true == isFormula(formula, value)
}


// iUML-B Class Diagrams

// Preset the given class instance for subsequent steps.
Given(~/^class instance ${quoted}${comment}$/) {
    String classId ->
    givenClassId = classId
}

// Call the given method (optionally with the given parameters constraints) of the preset or given classs instance.
When(~/^call method ${quoted}${forCI}${withOpt}${comment}$/) {
    String methodName, String classId, String formula ->   // Parameters classId and formula are optional
    callMethod(methodName, classId, formula)
}

// Check, if the given method (optionally with the given parameters constraints) of the preset or given classs instance is enabled.
Then(~/^is enabled method ${quoted}${forCI}${withOpt}${comment}$/) {
    String methodName, String classId, String formula ->   // Parameters classId and formula are optional
    assert true == isMethodEnabled(methodName, classId, formula)
}

// Check, if the given method (optionally with the given parameters constraints) of the preset or given classs instance is disabled.
Then(~/^is disabled method ${quoted}${forCI}${withOpt}${comment}$/) {
    String methodName, String classId, String formula ->   // Parameters classId and formula are optional
    assert true == isMethodDisabled(methodName, classId, formula)
}

// Check, if the given attribute of the given class instance has or has not the given value.
Then(~/^attribute ${quoted}${forCI} is( not)? ${quoted}${comment}$/) {
    String attrName, String classId, String not, String value ->   // Parameters classId and not are optional
    assert true == isAttribute(classId, attrName, value, not != null)
}


// iUML-B State Machines

// Preset the given state machine for subsequent steps.
Given(~/^state machine ${quoted}${comment}$/) {
    String smId ->
    givenSmId = smId
}

// Trigger the given transition (optionally with the given parameters constraints) of the given or preset state machine.
When(~/^trigger transition ${quoted}${forSM}${withOpt}${comment}$/) {
    String transName, String smId, String formula ->   // Parameters smId and formula are optional
    triggerTransition(transName, smId, formula)
}

// Check, if the given transition (optionally with the given parameters constraints) of the given or preset state machine is enabled.
Then(~/^is enabled transition ${quoted}${forSM}${withOpt}${comment}$/) {
    String transName, String smId, String formula ->   // Parameters smId and formula are optional
    assert true == isTransitionEnabled(transName, smId, formula)
}

// Check, if the given transition (optionally with the given parameters constraints) of the given or preset state machine is disabled.
Then(~/^is disabled transition ${quoted}${forSM}${withOpt}${comment}$/) {
    String transName, String smId, String formula ->   // Parameters smId and formula are optional
    assert true == isTransitionDisabled(transName, smId, formula)
}

// Check, if the given or preset state machine is or is not in the given state.
Then(~/^(?:state machine ${quoted} )?is( not)? in state ${quoted}${comment}$/) {
    String smId, String not, String stateName ->   // Parameters smId and not are optional
    assert true == isInState(smId, stateName, not != null)
}


Before() {
    givenSmId = null
    givenClassId = null
}

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

    void setupConstantsInitialiseMachine(String formula = null) {
        state = space.getRoot()
        Transition trans = findUniqueTransition("\$setup_constants", formula)
        if (trans != null) {
            state = trans.getDestination()
        }
        trans = findUniqueTransition("\$initialise_machine", null)
        if (trans != null) {
            state = trans.getDestination()
        }
    }

    void fireEvent(String eventName, String formula = null) {
        Transition trans = findUniqueTransition(eventName, formula)
        state = trans.getDestination()
    }

    void fireEvents(String eventName, String attrName, List<String> values) {
        for (value in values) {
            String formula = attrName + "=" + value;
            fireEvent(eventName, formula)
        }
    }

    void fireEvents(String eventName, List<Map<String, String>> maps) {
        for (map in maps) {
            String formula = map.collect{it.key + "=" + it.value}.join(" & ")
            fireEvent(eventName, formula)
        }
    }

    boolean isEventEnabled(String eventName, String formula = null) {
        Transition trans = findUniqueTransition(eventName, formula)
        return trans != null
    }

    boolean areEventsEnabled(String eventName, String attrName, List<String> values) {
        boolean result = true
        for (value in values) {
            String formula = attrName + "=" + value;
            result &= isEventEnabled(eventName, formula)
        }
        return result
    }

    boolean isEventDisabled(String eventName, String formula = null) {
        Transition trans = findUniqueTransition(eventName, formula)
        return trans == null
    }

    boolean areEventsDisabled(String eventName, String attrName, List<String> values) {
        boolean result = true
        for (value in values) {
            String formula = attrName + "=" + value;
            result &= isEventDisabled(eventName, formula)
        }
        return result
    }

    // Value can be a literal FALSE or TRUE
    boolean isFormula(String formula, String value) {
        state.eval(formula).toString() == "${value}"
    }

    // Helper methods

    private Transition findUniqueTransition(String eventName, String formula) {
        def form = formula ? [formula] : []
        def transitions = state.findTransitions(eventName, form, 2)
        def transitionCount = transitions.size()
        assert transitionCount <= 1 : "Transition " + eventName + " is not unique"
        if (!transitions.isEmpty()) {
            return transitions[0]
        }
        return null
    }

    private String mergeFormulas(String formula1, String formula2) {
        return formula2 != null ? "(${formula1}) & (${formula2})" : formula1
    }

    // iUML-B
    def cdNodes = []
    def smNodes = [:]
    def machineNode = iumlb_load()

    String givenClassId = null
    String givenSmId = null

    void callMethod(methodName, classId, String formula = null) {
        def (className, classInst) = getClassInstance(classId)
        String selfName = getClassSelfName(className, classInst)
        fireEvent(methodName, mergeFormulas("${selfName} = ${classInst}", formula))
    }

    boolean isMethodEnabled(methodName, classId, String formula = null) {
        def (className, classInst) = getClassInstance(classId)
        String selfName = getClassSelfName(className, classInst)
        return isEventEnabled(methodName, mergeFormulas("${selfName} = ${classInst}", formula))
    }

    boolean isMethodDisabled(methodName, classId, String formula = null) {
        def (className, classInst) = getClassInstance(classId)
        String selfName = getClassSelfNameRaw(className, classInst)
        if (selfName != "") {
            return isEventDisabled(methodName, mergeFormulas("${selfName} = ${classInst}", formula))
        } else {
            return isEventDisabled(methodName, formula)
        }
    }

    boolean isAttribute(String classId, String attrName, String value, boolean not) {
        def (className, classInst) = getClassInstance(classId)
        String selfName = getClassSelfName(className, classInst)
        String ref = not ? "FALSE" : "TRUE"
        return isFormula("${attrName}(${classInst}) = ${value}", ref)
    }

    void triggerTransition(transName, smId, String formula = null) {
        def (smName, smInst) = getStateMachine(smId)
        String selfName = getSelfName(smName, smInst)
        if (selfName != null) {
            fireEvent(transName, mergeFormulas("${selfName} = ${smInst}", formula))
        } else {
            fireEvent(transName, formula)
        }
    }

    boolean isTransitionEnabled(transName, smId, String formula = null) {
        def (smName, smInst) = getStateMachine(smId)
        String selfName = getSelfName(smName, smInst)
        if (selfName != null) {
            return isEventEnabled(transName, mergeFormulas("${selfName} = ${smInst}", formula))
        } else {
            return isEventEnabled(transName, formula)
        }
    }

    boolean isTransitionDisabled(transName, smId, String formula = null) {
        def (smName, smInst) = getStateMachine(smId)
        String selfName = getSelfNameRaw(smName, smInst)
        if (selfName != "") {
            return isEventDisabled(transName, mergeFormulas("${selfName} = ${smInst}", formula))
        } else {
            return isEventDisabled(transName, formula)
        }
    }

    boolean isInState(String smId, String stateName, boolean not) {
        def (smName, smInst) = getStateMachine(smId)
        String ref = not ? "FALSE" : "TRUE"
        assert hasState(smName, stateName) : "Invalid state ${stateName} for state machine ${smName}"
        String stateAttr = stateName.tokenize(".").last()
        String tr = smNodes[smName][0].attribute("translation")
        String selfName = getSelfName(smName, smInst)
        switch(tr) {
            case null: // MULTIVAR
                if (selfName != null) {
                    return isFormula("${smInst} ∈ ${stateAttr}", ref)
                } else {
                    return isFormula("${stateAttr} = TRUE", ref)
                }
                break;
            case "SINGLEVAR":
                if (selfName != null) {
                    return isFormula("${smName}(${smInst}) = ${stateAttr}", ref)
                } else {
                    return isFormula("${smName} = ${stateAttr}", ref)
                }
                break;
            default:
                assert false: "Unsupported translation ${tr} for state machine ${smName}"
                break;
        }
    }

    // Helper methods

    private getStateMachine(String smId) {
        def sm = smId != null ? smId : givenSmId
        assert sm != null : "Missing state machine"
        return sm.tokenize(':')
    }

    private getClassInstance(String classId) {
        def cl = classId != null ? classIdId : givenClassId
        assert cl != null : "Missing class instance"
        return cl.tokenize(':')
    }

    private iumlb_load() {
        XmlParser parser = new XmlParser()
        Node eventbNode = null // Parsed node corresponding to the tested refinement
        String machineName = eventb
        while (machineName != null) {
            Node machineNode = parser.parse(machineName)
            if (eventb == null) {
                eventbNode = machineNode
            }
            for (node in machineNode.children()) {
                if (node.name() == 'ac.soton.eventb.emf.core.extension.persistence.serialisedExtension') {
                    def eClassifier = node.attribute('ac.soton.eventb.emf.core.extension.persistence.eClassifier')
                    def ePackageURI = node.attribute('ac.soton.eventb.emf.core.extension.persistence.ePackageURI')
                    def serialized = node.attribute('ac.soton.eventb.emf.core.extension.persistence.serialised')
                    def serializedNode = parser.parseText(serialized)
                    def nodeName = serializedNode.attribute('name')
                    switch (eClassifier) {   // ePackageURI
                        case "Classdiagram": // http://soton.ac.uk/models/eventb/classdiagrams/2015
                            cdNodes << serializedNode
                            break;
                        case "Statemachine": // http://soton.ac.uk/models/eventb/statemachines/2014
                            if (smNodes[nodeName] != null) {
                                smNodes[nodeName] << serializedNode
                                } else {
                                smNodes[nodeName] = [serializedNode]
                            }
                            break;
                    }
                    // groovy.xml.XmlUtil.serialize(serializedNode, new FileWriter(nodeName + ".xml"))
                }
            }
            machineName = machineNode."org.eventb.core.refinesMachine"[0]?.attribute("org.eventb.core.target")
            if (machineName != null) {
                machineName += ".bum"
            }
        }
        return eventbNode
    }

    // Get the state machine self name.
    private String getSelfNameRaw(String smName, String smInst) {
        String selfName = smNodes[smName][0].attribute("selfName")
        return selfName
    }

    // Get the state machine self name or null if not lifted.
    private String getSelfName(String smName, String smInst) {
        String selfName = getSelfNameRaw(smName, smInst)
        if (smInst != null) {
            // Lifted state machine, selfName must not be empty
            assert selfName != "" : "Superfluous instance for unlifted state machine ${smName}"
            return selfName
        } else {
            // Not lifted state machine, selfName must be empty
            assert selfName == "" : "Missing instance for lifted state machine ${smName}"
            return null
        }
    }

    // Get the class self name (instance must not be null).
    private String getClassSelfNameRaw(String className, String classInst) {
        for (cdNode in cdNodes) {
            for (classNode in cdNode."classes") {
                if (classNode.attribute("name") == className) {
                    String selfName = classNode.attribute("selfName")
                    if (selfName != null) {
                        return selfName
                    }
                }
            }
        }
        return null
    }

    // Get the class self name (instance must not be null).
    private String getClassSelfName(String className, String classInst) {
        assert classInst != null : "Missing instance for class ${className}"
        String selfName = getClassSelfNameRaw(className, classInst)
        assert selfName != null : "Missing selfName for class ${className}"
        return selfName
    }

    // Get the given state node for the given state machine node
    private Node getStateNode(Node smNode, String stateName) {
        for (node in smNode.children()) {
            if ((node.name() == "nodes") && (node.attribute("name") == stateName)) {
                return node;
            }
        }
        return null;
    }

    // Check if the state machine has the particular state
    private boolean hasState(String smName, String stateName) {
        Node smNode = smNodes[smName][0]
        List<String> names = stateName.tokenize(".")
        // TO-DO: Check nested state machines
        Node node = getStateNode(smNode, names[0])
        return node != null;
    }
}

