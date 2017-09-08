package mir.reactions.reactionsUmlToPcm.umlToPcm;

import mir.routines.umlToPcm.RoutinesFacade;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.uml2.uml.DataType;
import org.eclipse.uml2.uml.Property;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.feature.reference.RemoveEReference;

@SuppressWarnings("all")
class DeletedPropertyFromDataTypeReaction extends AbstractReactionRealization {
  public void executeReaction(final EChange change) {
    RemoveEReference<org.eclipse.uml2.uml.DataType, org.eclipse.uml2.uml.Property> typedChange = (RemoveEReference<org.eclipse.uml2.uml.DataType, org.eclipse.uml2.uml.Property>)change;
    org.eclipse.uml2.uml.DataType affectedEObject = typedChange.getAffectedEObject();
    EReference affectedFeature = typedChange.getAffectedFeature();
    org.eclipse.uml2.uml.Property oldValue = typedChange.getOldValue();
    mir.routines.umlToPcm.RoutinesFacade routinesFacade = new mir.routines.umlToPcm.RoutinesFacade(this.executionState, this);
    mir.reactions.reactionsUmlToPcm.umlToPcm.DeletedPropertyFromDataTypeReaction.ActionUserExecution userExecution = new mir.reactions.reactionsUmlToPcm.umlToPcm.DeletedPropertyFromDataTypeReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(affectedEObject, affectedFeature, oldValue, routinesFacade);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return RemoveEReference.class;
  }
  
  private boolean checkChangeProperties(final EChange change) {
    RemoveEReference<org.eclipse.uml2.uml.DataType, org.eclipse.uml2.uml.Property> relevantChange = (RemoveEReference<org.eclipse.uml2.uml.DataType, org.eclipse.uml2.uml.Property>)change;
    if (!(relevantChange.getAffectedEObject() instanceof org.eclipse.uml2.uml.DataType)) {
    	return false;
    }
    if (!relevantChange.getAffectedFeature().getName().equals("ownedAttribute")) {
    	return false;
    }
    if (!(relevantChange.getOldValue() instanceof org.eclipse.uml2.uml.Property)) {
    	return false;
    }
    return true;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (!(change instanceof RemoveEReference)) {
    	return false;
    }
    getLogger().trace("Passed change type check of reaction " + this.getClass().getName());
    if (!checkChangeProperties(change)) {
    	return false;
    }
    getLogger().trace("Passed change properties check of reaction " + this.getClass().getName());
    getLogger().trace("Passed complete precondition check of reaction " + this.getClass().getName());
    return true;
  }
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final DataType affectedEObject, final EReference affectedFeature, final Property oldValue, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.deleteInnerDeclarationOfProperty(oldValue);
    }
  }
}
