package mir.routines.pcmToUml;

import java.io.IOException;
import mir.routines.pcmToUml.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Component;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.internal.impl.UMLFactoryImpl;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateUmlComponentRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateUmlComponentRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final RepositoryComponent pcmComponent, final Model umlModel, final Component umlComponent) {
      return umlModel;
    }
    
    public void update0Element(final RepositoryComponent pcmComponent, final Model umlModel, final Component umlComponent) {
      EList<PackageableElement> _packagedElements = umlModel.getPackagedElements();
      _packagedElements.add(umlComponent);
    }
    
    public EObject getElement2(final RepositoryComponent pcmComponent, final Model umlModel, final Component umlComponent) {
      return pcmComponent;
    }
    
    public EObject getElement3(final RepositoryComponent pcmComponent, final Model umlModel, final Component umlComponent) {
      return umlComponent;
    }
    
    public void updateUmlComponentElement(final RepositoryComponent pcmComponent, final Model umlModel, final Component umlComponent) {
      String _entityName = pcmComponent.getEntityName();
      umlComponent.setName(_entityName);
    }
    
    public EObject getCorrepondenceSourceUmlModel(final RepositoryComponent pcmComponent) {
      Repository _repository__RepositoryComponent = pcmComponent.getRepository__RepositoryComponent();
      return _repository__RepositoryComponent;
    }
  }
  
  public CreateUmlComponentRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final RepositoryComponent pcmComponent) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmToUml.CreateUmlComponentRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.pcmToUml.RoutinesFacade(getExecutionState(), this);
    this.pcmComponent = pcmComponent;
  }
  
  private RepositoryComponent pcmComponent;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateUmlComponentRoutine with input:");
    getLogger().debug("   RepositoryComponent: " + this.pcmComponent);
    
    Model umlModel = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlModel(pcmComponent), // correspondence source supplier
    	Model.class,
    	(Model _element) -> true, // correspondence precondition checker
    	null);
    if (umlModel == null) {
    	return;
    }
    initializeRetrieveElementState(umlModel);
    Component umlComponent = UMLFactoryImpl.eINSTANCE.createComponent();
    initializeCreateElementState(umlComponent);
    userExecution.updateUmlComponentElement(pcmComponent, umlModel, umlComponent);
    
    // val updatedElement userExecution.getElement1(pcmComponent, umlModel, umlComponent);
    userExecution.update0Element(pcmComponent, umlModel, umlComponent);
    
    addCorrespondenceBetween(userExecution.getElement2(pcmComponent, umlModel, umlComponent), userExecution.getElement3(pcmComponent, umlModel, umlComponent), "");
    
    postprocessElementStates();
  }
}