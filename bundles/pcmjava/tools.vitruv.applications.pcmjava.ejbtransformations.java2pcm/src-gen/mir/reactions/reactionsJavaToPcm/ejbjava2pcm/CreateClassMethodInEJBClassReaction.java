package mir.reactions.reactionsJavaToPcm.ejbjava2pcm;

import mir.routines.ejbjava2pcm.RoutinesFacade;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.members.ClassMethod;
import tools.vitruv.applications.pcmjava.ejbtransformations.java2pcm.EJBAnnotationHelper;
import tools.vitruv.applications.pcmjava.ejbtransformations.java2pcm.EJBJava2PcmHelper;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.feature.reference.InsertEReference;
import tools.vitruv.framework.userinteraction.UserInteracting;

@SuppressWarnings("all")
class CreateClassMethodInEJBClassReaction extends AbstractReactionRealization {
  public CreateClassMethodInEJBClassReaction(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  public void executeReaction(final EChange change) {
    InsertEReference<org.emftext.language.java.classifiers.Class, ClassMethod> typedChange = (InsertEReference<org.emftext.language.java.classifiers.Class, ClassMethod>)change;
    org.emftext.language.java.classifiers.Class affectedEObject = typedChange.getAffectedEObject();
    EReference affectedFeature = typedChange.getAffectedFeature();
    ClassMethod newValue = typedChange.getNewValue();
    mir.routines.ejbjava2pcm.RoutinesFacade routinesFacade = new mir.routines.ejbjava2pcm.RoutinesFacade(this.executionState, this);
    mir.reactions.reactionsJavaToPcm.ejbjava2pcm.CreateClassMethodInEJBClassReaction.ActionUserExecution userExecution = new mir.reactions.reactionsJavaToPcm.ejbjava2pcm.CreateClassMethodInEJBClassReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(affectedEObject, affectedFeature, newValue, routinesFacade);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return InsertEReference.class;
  }
  
  private boolean checkChangeProperties(final EChange change) {
    InsertEReference<org.emftext.language.java.classifiers.Class, ClassMethod> relevantChange = (InsertEReference<org.emftext.language.java.classifiers.Class, ClassMethod>)change;
    if (!(relevantChange.getAffectedEObject() instanceof org.emftext.language.java.classifiers.Class)) {
    	return false;
    }
    if (!relevantChange.getAffectedFeature().getName().equals("members")) {
    	return false;
    }
    if (!(relevantChange.getNewValue() instanceof ClassMethod)) {
    	return false;
    }
    return true;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (!(change instanceof InsertEReference)) {
    	return false;
    }
    getLogger().debug("Passed change type check of reaction " + this.getClass().getName());
    if (!checkChangeProperties(change)) {
    	return false;
    }
    getLogger().debug("Passed change properties check of reaction " + this.getClass().getName());
    InsertEReference<org.emftext.language.java.classifiers.Class, ClassMethod> typedChange = (InsertEReference<org.emftext.language.java.classifiers.Class, ClassMethod>)change;
    org.emftext.language.java.classifiers.Class affectedEObject = typedChange.getAffectedEObject();
    EReference affectedFeature = typedChange.getAffectedFeature();
    ClassMethod newValue = typedChange.getNewValue();
    if (!checkUserDefinedPrecondition(affectedEObject, affectedFeature, newValue)) {
    	return false;
    }
    getLogger().debug("Passed complete precondition check of reaction " + this.getClass().getName());
    return true;
  }
  
  private boolean checkUserDefinedPrecondition(final org.emftext.language.java.classifiers.Class affectedEObject, final EReference affectedFeature, final ClassMethod newValue) {
    return (EJBAnnotationHelper.isEJBClass(affectedEObject) && 
      EJBJava2PcmHelper.overridesInterfaceMethod(newValue, affectedEObject));
  }
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final org.emftext.language.java.classifiers.Class affectedEObject, final EReference affectedFeature, final ClassMethod newValue, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.createdClassMethodInEJBClass(affectedEObject, newValue);
    }
  }
}