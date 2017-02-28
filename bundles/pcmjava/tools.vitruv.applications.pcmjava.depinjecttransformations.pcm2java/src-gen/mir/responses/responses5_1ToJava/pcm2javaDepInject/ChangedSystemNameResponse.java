package mir.responses.responses5_1ToJava.pcm2javaDepInject;

import org.eclipse.emf.ecore.EObject;
import tools.vitruv.extensions.dslsruntime.response.AbstractResponseRealization;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.feature.attribute.ReplaceSingleValuedEAttribute;
import tools.vitruv.framework.userinteraction.UserInteracting;

@SuppressWarnings("all")
class ChangedSystemNameResponse extends AbstractResponseRealization {
  public ChangedSystemNameResponse(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return ReplaceSingleValuedEAttribute.class;
  }
  
  private boolean checkChangeProperties(final ReplaceSingleValuedEAttribute<org.palladiosimulator.pcm.system.System, String> change) {
    EObject changedElement = change.getAffectedEObject();
    // Check model element type
    if (!(changedElement instanceof org.palladiosimulator.pcm.system.System)) {
    	return false;
    }
    
    // Check feature
    if (!change.getAffectedFeature().getName().equals("entityName")) {
    	return false;
    }
    return true;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (!(change instanceof ReplaceSingleValuedEAttribute<?, ?>)) {
    	return false;
    }
    ReplaceSingleValuedEAttribute typedChange = (ReplaceSingleValuedEAttribute)change;
    if (!checkChangeProperties(typedChange)) {
    	return false;
    }
    getLogger().debug("Passed precondition check of response " + this.getClass().getName());
    return true;
  }
  
  public void executeResponse(final EChange change) {
    ReplaceSingleValuedEAttribute<org.palladiosimulator.pcm.system.System, String> typedChange = (ReplaceSingleValuedEAttribute<org.palladiosimulator.pcm.system.System, String>)change;
    mir.routines.pcm2javaDepInject.ChangedSystemNameEffect effect = new mir.routines.pcm2javaDepInject.ChangedSystemNameEffect(this.executionState, this, typedChange);
    effect.applyRoutine();
  }
}