package mir.routines.umlXpcmRoles_R2L;

import java.io.IOException;
import mir.routines.umlXpcmRoles_R2L.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import org.palladiosimulator.pcm.repository.ProvidedRole;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class ProvidedRole_ElementUpdatedCheckRoutine extends AbstractRepairRoutineRealization {
  private ProvidedRole_ElementUpdatedCheckRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final EObject affectedEObject, @Extension final RoutinesFacade _routinesFacade) {
      if ((affectedEObject instanceof OperationProvidedRole)) {
        OperationProvidedRole role_ = ((OperationProvidedRole)affectedEObject);
        {
          EObject providingEntity__candidate = role_.eContainer();
          if (((providingEntity__candidate != null) && (providingEntity__candidate instanceof InterfaceProvidingRequiringEntity))) {
            InterfaceProvidingRequiringEntity providingEntity_ = ((InterfaceProvidingRequiringEntity) providingEntity__candidate);
            {
              OperationInterface operationInterface__candidate = role_.getProvidedInterface__OperationProvidedRole();
              if (((operationInterface__candidate != null) && (operationInterface__candidate instanceof OperationInterface))) {
                OperationInterface operationInterface_ = ((OperationInterface) operationInterface__candidate);
                {
                  _routinesFacade.providedRole_CreateMapping(role_, operationInterface_, providingEntity_);
                  return;
                }
              }
            }
          }
        }
      }
      if ((affectedEObject instanceof OperationInterface)) {
        OperationInterface operationInterface_ = ((OperationInterface)affectedEObject);
        EList<EObject> _eCrossReferences = operationInterface_.eCrossReferences();
        for (final EObject role__candidate : _eCrossReferences) {
          if (((role__candidate != null) && (role__candidate instanceof OperationProvidedRole))) {
            OperationProvidedRole role__1 = ((OperationProvidedRole) role__candidate);
            OperationInterface _providedInterface__OperationProvidedRole = role__1.getProvidedInterface__OperationProvidedRole();
            boolean _tripleEquals = (operationInterface_ == _providedInterface__OperationProvidedRole);
            if (_tripleEquals) {
              EObject providingEntity__candidate = role__1.eContainer();
              if (((providingEntity__candidate != null) && (providingEntity__candidate instanceof InterfaceProvidingRequiringEntity))) {
                InterfaceProvidingRequiringEntity providingEntity_ = ((InterfaceProvidingRequiringEntity) providingEntity__candidate);
                {
                  _routinesFacade.providedRole_CreateMapping(role__1, operationInterface_, providingEntity_);
                  return;
                }
              }
            }
          }
        }
      }
      if ((affectedEObject instanceof InterfaceProvidingRequiringEntity)) {
        InterfaceProvidingRequiringEntity providingEntity__1 = ((InterfaceProvidingRequiringEntity)affectedEObject);
        EList<ProvidedRole> _providedRoles_InterfaceProvidingEntity = providingEntity__1.getProvidedRoles_InterfaceProvidingEntity();
        for (final ProvidedRole role__candidate_1 : _providedRoles_InterfaceProvidingEntity) {
          if (((role__candidate_1 != null) && (role__candidate_1 instanceof OperationProvidedRole))) {
            OperationProvidedRole role__2 = ((OperationProvidedRole) role__candidate_1);
            {
              OperationInterface operationInterface__candidate = role__2.getProvidedInterface__OperationProvidedRole();
              if (((operationInterface__candidate != null) && (operationInterface__candidate instanceof OperationInterface))) {
                OperationInterface operationInterface__1 = ((OperationInterface) operationInterface__candidate);
                {
                  _routinesFacade.providedRole_CreateMapping(role__2, operationInterface__1, providingEntity__1);
                  return;
                }
              }
            }
          }
        }
      }
      _routinesFacade.providedRole_ElementDeletedCheck(affectedEObject);
    }
  }
  
  public ProvidedRole_ElementUpdatedCheckRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final EObject affectedEObject) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlXpcmRoles_R2L.ProvidedRole_ElementUpdatedCheckRoutine.ActionUserExecution(getExecutionState(), this);
    this.affectedEObject = affectedEObject;
  }
  
  private EObject affectedEObject;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine ProvidedRole_ElementUpdatedCheckRoutine with input:");
    getLogger().debug("   affectedEObject: " + this.affectedEObject);
    
    userExecution.callRoutine1(affectedEObject, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}