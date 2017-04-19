package mir.routines.pcm2EjbJava;

import com.google.common.base.Objects;
import java.io.IOException;
import mir.routines.pcm2EjbJava.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.Interface;
import org.palladiosimulator.pcm.repository.Repository;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateInterfaceImplementationRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateInterfaceImplementationRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public boolean getCorrespondingModelElementsPreconditionContractsPackage(final Interface interf, final org.emftext.language.java.containers.Package contractsPackage) {
      String _name = contractsPackage.getName();
      boolean _equals = Objects.equal(_name, "contracts");
      return _equals;
    }
    
    public EObject getCorrepondenceSourceContractsPackage(final Interface interf) {
      Repository _repository__Interface = interf.getRepository__Interface();
      return _repository__Interface;
    }
    
    public void callRoutine1(final Interface interf, final org.emftext.language.java.containers.Package contractsPackage, @Extension final RoutinesFacade _routinesFacade) {
      String _entityName = interf.getEntityName();
      _routinesFacade.createJavaInterface(interf, contractsPackage, _entityName);
    }
  }
  
  public CreateInterfaceImplementationRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Interface interf) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcm2EjbJava.CreateInterfaceImplementationRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.pcm2EjbJava.RoutinesFacade(getExecutionState(), this);
    this.interf = interf;
  }
  
  private Interface interf;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateInterfaceImplementationRoutine with input:");
    getLogger().debug("   Interface: " + this.interf);
    
    org.emftext.language.java.containers.Package contractsPackage = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceContractsPackage(interf), // correspondence source supplier
    	org.emftext.language.java.containers.Package.class,
    	(org.emftext.language.java.containers.Package _element) -> userExecution.getCorrespondingModelElementsPreconditionContractsPackage(interf, _element), // correspondence precondition checker
    	null);
    if (contractsPackage == null) {
    	return;
    }
    registerObjectUnderModification(contractsPackage);
    userExecution.callRoutine1(interf, contractsPackage, actionsFacade);
    
    postprocessElements();
  }
}