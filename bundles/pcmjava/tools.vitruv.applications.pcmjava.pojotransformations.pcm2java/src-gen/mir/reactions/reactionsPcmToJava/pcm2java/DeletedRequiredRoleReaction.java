package mir.reactions.reactionsPcmToJava.pcm2java;

import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.core.entity.InterfaceRequiringEntity;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.feature.reference.RemoveEReference;

@SuppressWarnings("all")
class DeletedRequiredRoleReaction extends AbstractReactionRealization {
  private RemoveEReference<InterfaceRequiringEntity, OperationRequiredRole> removeChange;
  
  private int currentlyMatchedChange;
  
  public void executeReaction(final EChange change) {
    if (!checkPrecondition(change)) {
    	return;
    }
    org.palladiosimulator.pcm.core.entity.InterfaceRequiringEntity affectedEObject = removeChange.getAffectedEObject();
    EReference affectedFeature = removeChange.getAffectedFeature();
    org.palladiosimulator.pcm.repository.OperationRequiredRole oldValue = removeChange.getOldValue();
    				
    getLogger().trace("Passed complete precondition check of Reaction " + this.getClass().getName());
    				
    mir.routines.pcm2java.RoutinesFacade routinesFacade = new mir.routines.pcm2java.RoutinesFacade(this.executionState, this);
    mir.reactions.reactionsPcmToJava.pcm2java.DeletedRequiredRoleReaction.ActionUserExecution userExecution = new mir.reactions.reactionsPcmToJava.pcm2java.DeletedRequiredRoleReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(affectedEObject, affectedFeature, oldValue, routinesFacade);
    
    resetChanges();
  }
  
  private void resetChanges() {
    removeChange = null;
    currentlyMatchedChange = 0;
  }
  
  private boolean matchRemoveChange(final EChange change) {
    if (change instanceof RemoveEReference<?, ?>) {
    	RemoveEReference<org.palladiosimulator.pcm.core.entity.InterfaceRequiringEntity, org.palladiosimulator.pcm.repository.OperationRequiredRole> _localTypedChange = (RemoveEReference<org.palladiosimulator.pcm.core.entity.InterfaceRequiringEntity, org.palladiosimulator.pcm.repository.OperationRequiredRole>) change;
    	if (!(_localTypedChange.getAffectedEObject() instanceof org.palladiosimulator.pcm.core.entity.InterfaceRequiringEntity)) {
    		return false;
    	}
    	if (!_localTypedChange.getAffectedFeature().getName().equals("requiredRoles_InterfaceRequiringEntity")) {
    		return false;
    	}
    	if (!(_localTypedChange.getOldValue() instanceof org.palladiosimulator.pcm.repository.OperationRequiredRole)) {
    		return false;
    	}
    	this.removeChange = (RemoveEReference<org.palladiosimulator.pcm.core.entity.InterfaceRequiringEntity, org.palladiosimulator.pcm.repository.OperationRequiredRole>) change;
    	return true;
    }
    
    return false;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (currentlyMatchedChange == 0) {
    	if (!matchRemoveChange(change)) {
    		resetChanges();
    		return false;
    	} else {
    		currentlyMatchedChange++;
    	}
    }
    
    return true;
  }
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final InterfaceRequiringEntity affectedEObject, final EReference affectedFeature, final OperationRequiredRole oldValue, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.removeRequiredRole(oldValue, ((RepositoryComponent) affectedEObject));
    }
  }
}
