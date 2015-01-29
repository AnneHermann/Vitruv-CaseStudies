package edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.synchronizers.java.compositerefiners

import edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.helper.java.shadowcopy.ShadowCopyFactory
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CompositeChange
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.EMFModelChange
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.EFeatureChange
import org.emftext.language.java.members.Method
import org.emftext.language.java.parameters.Parameter

class JavaMethodParameterNumberChangedByOneCompositeChangeRefiner extends CompositeChangeRefinerBase {
	
	new(ShadowCopyFactory shadowCopyFactory) {
		super(shadowCopyFactory)
	}
	
	override match(CompositeChange change) {
		var CompositeChangeRefinerBase.AddDeleteContainer addAndDeleteChanges = null
		try {
			addAndDeleteChanges = getAddAndDeleteChanges(change)
		} catch (IllegalArgumentException e) {
			return false
		}
		
		val deleteChanges = addAndDeleteChanges.deleteChanges
		val addChanges = addAndDeleteChanges.addChanges
		
		if (Math.abs(deleteChanges.size - addChanges.size) != 1) {
			return false
		}
		
		if (deleteChanges.exists[!(oldAffectedEObject instanceof Method) || !(oldValue instanceof Parameter)]) {
			return false
		}
		
		if (addChanges.exists[!(newAffectedEObject instanceof Method) || !(newValue instanceof Parameter)]) {
			return false
		}
		
		return true
	}
	
	override refine(CompositeChange change) {
		val CompositeChangeRefinerBase.AddDeleteContainer addAndDeleteChanges = getAddAndDeleteChanges(change)
		
		var EFeatureChange<?> refinedChange = null
		if (addAndDeleteChanges.addChanges.size > addAndDeleteChanges.deleteChanges.size) {
			refinedChange = addAndDeleteChanges.addChanges.findFirst[addChange | !addAndDeleteChanges.deleteChanges.exists[(oldValue as Parameter).name.equals((addChange.newValue as Parameter).name)]]
		} else {
			refinedChange = addAndDeleteChanges.deleteChanges.findFirst[deleteChange | !addAndDeleteChanges.addChanges.exists[(newValue as Parameter).name.equals((deleteChange.oldValue as Parameter).name)]]
		}
		
		return new CompositeChangeRefinerResultAtomicTransformations(#[new EMFModelChange(refinedChange, VURI.getInstance(refinedChange.oldAffectedEObject.eResource))]);
	}
	
}