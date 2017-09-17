package tools.vitruv.applications.pcmjava.gplimplementation.pojotransformations.java2pcm.transformations

import tools.vitruv.applications.pcmjava.gplimplementation.pojotransformations.util.transformationexecutor.EmptyEObjectMappingTransformation
import java.util.ArrayList
import java.util.LinkedList
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.util.EcoreUtil
import org.emftext.language.java.classifiers.Class
import org.emftext.language.java.classifiers.Interface
import org.emftext.language.java.members.ClassMethod
import org.emftext.language.java.members.Method
import org.emftext.language.java.types.TypeReference
import org.palladiosimulator.pcm.repository.BasicComponent
import org.palladiosimulator.pcm.repository.OperationInterface
import org.palladiosimulator.pcm.repository.OperationSignature
import org.palladiosimulator.pcm.seff.ResourceDemandingSEFF
import org.palladiosimulator.pcm.seff.SeffFactory

import static extension tools.vitruv.framework.correspondence.CorrespondenceModelUtil.*
import tools.vitruv.applications.pcmjava.util.PcmJavaUtils
import tools.vitruv.applications.pcmjava.util.java2pcm.Java2PcmUtils
import tools.vitruv.framework.util.command.ResourceAccess

class ClassMethodMappingTransformation extends EmptyEObjectMappingTransformation {

	override getClassOfMappedEObject() {
		return ClassMethod
	}
	
	override setCorrespondenceForFeatures() {
	}

	/**
	 * called when a ClassMethod has been added:
	 * if the class maps to a basic component and implements an interface and the new method matches to the interface method
	 * we create an emtpy SEFF for the method
	 */
	override createEObject(EObject eObject, ResourceAccess resourceAccess) {
		val classMethod = eObject as ClassMethod
		classMethod.createSEFFIfImplementsInterfaceMethod()
	}
	
	override removeEObject(EObject eObject, ResourceAccess resourceAccess){
		return null
	}
	
	/**
	 * Called when the name of the method has been changed
	 * Check if the class method implements an interface an update/generate a new seff if necessary
	 */
	override updateSingleValuedEAttribute(EObject affectedEObject, EAttribute affectedAttribute, Object oldValue,
		Object newValue, ResourceAccess resourceAccess) {
		val oldAffectedEObject = EcoreUtil.copy(affectedEObject) as ClassMethod
		oldAffectedEObject.eSet(affectedAttribute, oldValue)
		checkAndUpdateCorrespondence(affectedEObject, oldAffectedEObject, resourceAccess)
	}
	
	
	/**
     *  called when a parameter has been added
     *  Check if the class method implements an interface an update/generate a new seff if necessray  
     */
	override createNonRootEObjectInList(EObject newAffectedEObject, EObject oldAffectedEObject,
		EReference affectedReference, EObject newValue, int index, EObject[] newCorrespondingEObjects, ResourceAccess resourceAccess) {
		checkAndUpdateCorrespondence(newAffectedEObject, oldAffectedEObject, resourceAccess)
	}
	

	/**
	 * called when a parameter has been deleted/changed
	 * Check if the class method implements an interface an update/generate a new seff if necessray
	 */
	override deleteNonRootEObjectInList(EObject newAffectedEObject, EObject oldAffectedEObject,
		EReference affectedReference, EObject oldValue, int index, EObject[] oldCorrespondingEObjectsToDelete, ResourceAccess resourceAccess) {
		checkAndUpdateCorrespondence(newAffectedEObject, oldAffectedEObject, resourceAccess)
	}


	def private checkAndUpdateCorrespondence(EObject newAffectedEObject, EObject oldAffectedEObject, ResourceAccess resourceAccess) {
		val rdSEFFs = correspondenceModel.getCorrespondingEObjectsByType(oldAffectedEObject, ResourceDemandingSEFF)
		if(!rdSEFFs.nullOrEmpty){
			removeEObject(oldAffectedEObject, resourceAccess)
		}
		val newClassMethod = newAffectedEObject as ClassMethod
		val newSEFFs = newClassMethod.createSEFFIfImplementsInterfaceMethod
		if(!newSEFFs.nullOrEmpty){
			for(newSEFF : newSEFFs){
				correspondenceModel.createAndAddCorrespondence(newSEFF, newClassMethod)
			}			
		}
	}

	def private EObject[] createSEFFIfImplementsInterfaceMethod(ClassMethod classMethod) {
		val classifier = classMethod.containingConcreteClassifier
		val basicComponents = correspondenceModel.getCorrespondingEObjectsByType(classifier, BasicComponent)
		if (basicComponents.nullOrEmpty) {
			return null
		}
		if(!(classifier instanceof Class)){
			return null
		}
		val jaMoPPClass = classifier as Class
		val implementingInterfacesTypeRefs = jaMoPPClass.implements
		val implementingInterfaces = findImplementingInterfacesFromTypeRefs(implementingInterfacesTypeRefs)
		if (implementingInterfaces.nullOrEmpty) {
			return null
		}
		val interfaceMethods = new LinkedList<Method>
		// check if one of the implmenenting interfaces has a correspondence to an OperationInterface
		// if yes: all methods in the Interface are potential methods, which are implmeneted by the new class method    
		for (interface : implementingInterfaces) {
			val opInterfaces = correspondenceModel.getCorrespondingEObjectsByType(interface, OperationInterface)
			if (!opInterfaces.nullOrEmpty) {
				interfaceMethods.addAll(interface.methods)
			}
		}
		
		val equalMethods = interfaceMethods.filter[sameSignature(classMethod)]
		if (equalMethods.nullOrEmpty) {
			return null
		}
		val returnSeffs = new ArrayList<ResourceDemandingSEFF>
		for (method : equalMethods) {
			val opSigs = correspondenceModel.getCorrespondingEObjectsByType(method, OperationSignature)
			if (!opSigs.nullOrEmpty) {
				// create seff for first corresponding OpSig (note: there should be only one)
				val ResourceDemandingSEFF rdSEFF = SeffFactory.eINSTANCE.createResourceDemandingSEFF
				rdSEFF.describedService__SEFF = opSigs.get(0)
				rdSEFF.basicComponent_ServiceEffectSpecification = basicComponents.get(0)
				returnSeffs.add(rdSEFF)
			}
		}
		returnSeffs
	}
	
	def private sameSignature(Method interfaceMethod, ClassMethod classMethod) {
		PcmJavaUtils.hasSameSignature(interfaceMethod, classMethod)
	}
	
	def private findImplementingInterfacesFromTypeRefs(EList<TypeReference> typeReferences) {
		val implementingInterfaces = new ArrayList<Interface>
		for(typeRef : typeReferences){
			val classifier = Java2PcmUtils.getTargetClassifierFromImplementsReferenceAndNormalizeURI(typeRef)
			if(classifier instanceof Interface){
				implementingInterfaces.add(classifier)
			}
		}
		return implementingInterfaces
	}

}
