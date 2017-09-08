package mir.reactions.reactionsPcmToJava.pcm2depInjectJava;

import mir.routines.pcm2depInjectJava.RoutinesFacade;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.core.entity.InterfaceProvidingEntity;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.feature.reference.ReplaceSingleValuedEReference;

@SuppressWarnings("all")
class ChangedProvidingEntityOfProvidedRoleReaction extends AbstractReactionRealization {
  public void executeReaction(final EChange change) {
    ReplaceSingleValuedEReference<org.palladiosimulator.pcm.repository.OperationProvidedRole, org.palladiosimulator.pcm.core.entity.InterfaceProvidingEntity> typedChange = (ReplaceSingleValuedEReference<org.palladiosimulator.pcm.repository.OperationProvidedRole, org.palladiosimulator.pcm.core.entity.InterfaceProvidingEntity>)change;
    org.palladiosimulator.pcm.repository.OperationProvidedRole affectedEObject = typedChange.getAffectedEObject();
    EReference affectedFeature = typedChange.getAffectedFeature();
    org.palladiosimulator.pcm.core.entity.InterfaceProvidingEntity oldValue = typedChange.getOldValue();
    org.palladiosimulator.pcm.core.entity.InterfaceProvidingEntity newValue = typedChange.getNewValue();
    mir.routines.pcm2depInjectJava.RoutinesFacade routinesFacade = new mir.routines.pcm2depInjectJava.RoutinesFacade(this.executionState, this);
    mir.reactions.reactionsPcmToJava.pcm2depInjectJava.ChangedProvidingEntityOfProvidedRoleReaction.ActionUserExecution userExecution = new mir.reactions.reactionsPcmToJava.pcm2depInjectJava.ChangedProvidingEntityOfProvidedRoleReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(affectedEObject, affectedFeature, oldValue, newValue, routinesFacade);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return ReplaceSingleValuedEReference.class;
  }
  
  private boolean checkChangeProperties(final EChange change) {
    ReplaceSingleValuedEReference<org.palladiosimulator.pcm.repository.OperationProvidedRole, org.palladiosimulator.pcm.core.entity.InterfaceProvidingEntity> relevantChange = (ReplaceSingleValuedEReference<org.palladiosimulator.pcm.repository.OperationProvidedRole, org.palladiosimulator.pcm.core.entity.InterfaceProvidingEntity>)change;
    if (!(relevantChange.getAffectedEObject() instanceof org.palladiosimulator.pcm.repository.OperationProvidedRole)) {
    	return false;
    }
    if (!relevantChange.getAffectedFeature().getName().equals("providingEntity_ProvidedRole")) {
    	return false;
    }
    if (relevantChange.isFromNonDefaultValue() && !(relevantChange.getOldValue() instanceof org.palladiosimulator.pcm.core.entity.InterfaceProvidingEntity)) {
    	return false;
    }
    if (relevantChange.isToNonDefaultValue() && !(relevantChange.getNewValue() instanceof org.palladiosimulator.pcm.core.entity.InterfaceProvidingEntity)) {
    	return false;
    }
    return true;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (!(change instanceof ReplaceSingleValuedEReference)) {
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
    
    public void callRoutine1(final OperationProvidedRole affectedEObject, final EReference affectedFeature, final InterfaceProvidingEntity oldValue, final InterfaceProvidingEntity newValue, @Extension final RoutinesFacade _routinesFacade) {
      final OperationProvidedRole operationProvidedRole = affectedEObject;
      _routinesFacade.removeProvidedRole(operationProvidedRole);
      _routinesFacade.addProvidedRole(operationProvidedRole);
    }
  }
}
