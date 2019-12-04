package mir.reactions.umlXpcmInterface_L2R;

import mir.routines.umlXpcmInterface_L2R.RoutinesFacade;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.eobject.DeleteEObject;

@SuppressWarnings("all")
public class OnOperationInterfaceParentInterfaceDeletedReaction extends AbstractReactionRealization {
  private DeleteEObject<Interface> deleteChange;
  
  private int currentlyMatchedChange;
  
  public OnOperationInterfaceParentInterfaceDeletedReaction(final RoutinesFacade routinesFacade) {
    super(routinesFacade);
  }
  
  public void executeReaction(final EChange change) {
    if (!checkPrecondition(change)) {
    	return;
    }
    org.eclipse.uml2.uml.Interface affectedEObject = deleteChange.getAffectedEObject();
    				
    getLogger().trace("Passed complete precondition check of Reaction " + this.getClass().getName());
    				
    mir.reactions.umlXpcmInterface_L2R.OnOperationInterfaceParentInterfaceDeletedReaction.ActionUserExecution userExecution = new mir.reactions.umlXpcmInterface_L2R.OnOperationInterfaceParentInterfaceDeletedReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(deleteChange, affectedEObject, this.getRoutinesFacade());
    
    resetChanges();
  }
  
  private boolean matchDeleteChange(final EChange change) {
    if (change instanceof DeleteEObject<?>) {
    	DeleteEObject<org.eclipse.uml2.uml.Interface> _localTypedChange = (DeleteEObject<org.eclipse.uml2.uml.Interface>) change;
    	if (!(_localTypedChange.getAffectedEObject() instanceof org.eclipse.uml2.uml.Interface)) {
    		return false;
    	}
    	this.deleteChange = (DeleteEObject<org.eclipse.uml2.uml.Interface>) change;
    	return true;
    }
    
    return false;
  }
  
  private void resetChanges() {
    deleteChange = null;
    currentlyMatchedChange = 0;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (currentlyMatchedChange == 0) {
    	if (!matchDeleteChange(change)) {
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
    
    public void callRoutine1(final DeleteEObject deleteChange, final Interface affectedEObject, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.operationInterfaceParent_ElementDeletedCheck(affectedEObject);
    }
  }
}