package mir.reactions.reactionsJavaToUml.javaToUmlMethod;

import mir.routines.javaToUmlMethod.RoutinesFacade;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.parameters.OrdinaryParameter;
import org.emftext.language.java.parameters.Parametrizable;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.compound.CreateAndInsertNonRoot;
import tools.vitruv.framework.change.echange.feature.reference.InsertEReference;
import tools.vitruv.framework.userinteraction.UserInteracting;

@SuppressWarnings("all")
class JavaParameterCreatedReaction extends AbstractReactionRealization {
  public JavaParameterCreatedReaction(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  public void executeReaction(final EChange change) {
    InsertEReference<Parametrizable, OrdinaryParameter> typedChange = ((CreateAndInsertNonRoot<Parametrizable, OrdinaryParameter>)change).getInsertChange();
    Parametrizable affectedEObject = typedChange.getAffectedEObject();
    EReference affectedFeature = typedChange.getAffectedFeature();
    OrdinaryParameter newValue = typedChange.getNewValue();
    mir.routines.javaToUmlMethod.RoutinesFacade routinesFacade = new mir.routines.javaToUmlMethod.RoutinesFacade(this.executionState, this);
    mir.reactions.reactionsJavaToUml.javaToUmlMethod.JavaParameterCreatedReaction.ActionUserExecution userExecution = new mir.reactions.reactionsJavaToUml.javaToUmlMethod.JavaParameterCreatedReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(affectedEObject, affectedFeature, newValue, routinesFacade);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return CreateAndInsertNonRoot.class;
  }
  
  private boolean checkChangeProperties(final EChange change) {
    InsertEReference<Parametrizable, OrdinaryParameter> relevantChange = ((CreateAndInsertNonRoot<Parametrizable, OrdinaryParameter>)change).getInsertChange();
    if (!(relevantChange.getAffectedEObject() instanceof Parametrizable)) {
    	return false;
    }
    if (!relevantChange.getAffectedFeature().getName().equals("parameters")) {
    	return false;
    }
    if (!(relevantChange.getNewValue() instanceof OrdinaryParameter)) {
    	return false;
    }
    return true;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (!(change instanceof CreateAndInsertNonRoot)) {
    	return false;
    }
    getLogger().debug("Passed change type check of reaction " + this.getClass().getName());
    if (!checkChangeProperties(change)) {
    	return false;
    }
    getLogger().debug("Passed change properties check of reaction " + this.getClass().getName());
    getLogger().debug("Passed complete precondition check of reaction " + this.getClass().getName());
    return true;
  }
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final Parametrizable affectedEObject, final EReference affectedFeature, final OrdinaryParameter newValue, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.createUmlParameter(affectedEObject, newValue);
    }
  }
}