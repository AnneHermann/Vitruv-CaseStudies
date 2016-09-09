package tools.vitruv.applications.pcmjava.util.java2pcm

import tools.vitruv.framework.util.datatypes.VURI
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.resource.ResourceSet
import org.emftext.language.java.containers.Package
import org.eclipse.emf.ecore.resource.Resource
import tools.vitruv.framework.change.echange.root.InsertRootEObject
import tools.vitruv.domains.java.echange.feature.attribute.JavaReplaceSingleValuedEAttribute
import tools.vitruv.framework.change.description.ConcreteChange
import tools.vitruv.framework.correspondence.CorrespondenceModel
import tools.vitruv.framework.change.processing.ChangeProcessorResult
import tools.vitruv.framework.change.processing.impl.AbstractChangeProcessor
import tools.vitruv.framework.userinteraction.UserInteracting
import tools.vitruv.framework.change.description.TransactionalChange

class Java2PcmPackagePreprocessor extends AbstractChangeProcessor {
    
	new(UserInteracting userInteracting) {
		super(userInteracting);
	}

	/**
     * Special treatment for packages: we have to use the package-info file as input for the
     * transformation and make sure that the packages have resources attached
     *
     * @param change
     *            the change that may contain the newly created package
     */	
	private def void handlePackageInEChange(ConcreteChange change) {
    	if (change.getEChanges.size == 1) {
    		val eChange = change.getEChanges.get(0);
        	if (eChange instanceof InsertRootEObject<?>) {
	            attachPackageToResource(eChange.getNewValue(), change.getURI());
        	} else if (eChange instanceof JavaReplaceSingleValuedEAttribute<?,?>) {
	            prepareRenamePackageInfos(eChange, change.getURI());
        	} // TODO: package deletion
        }
    }

    private def void prepareRenamePackageInfos(JavaReplaceSingleValuedEAttribute<?,?> updateSingleValuedEAttribute,
            VURI vuri) {
        if (updateSingleValuedEAttribute.getOldAffectedEObject() instanceof Package
                && updateSingleValuedEAttribute.getAffectedEObject() instanceof Package) {
            val Package oldPackage = updateSingleValuedEAttribute.getOldAffectedEObject() as Package;
            val Package newPackage = updateSingleValuedEAttribute.getAffectedEObject() as Package;
            this.attachPackageToResource(oldPackage, vuri);
            var String newVURIKey = vuri.toString();
            val String oldPackagePath = oldPackage.getName().replace(".", "/");
            val String newPackagePath = newPackage.getName().replace(".", "/");
            newVURIKey = newVURIKey.replace(oldPackagePath, newPackagePath);
            val VURI newVURI = VURI.getInstance(newVURIKey);
            this.attachPackageToResource(newPackage, newVURI);
        }
    }

    private def void attachPackageToResource(EObject eObject, VURI vuri) {
        if (eObject instanceof Package) {
            val Package newPackage = eObject as Package;
            // attach the package to a resource in order to enable the calculation of
            // a TUID in the transformations
            val ResourceSet resourceSet = new ResourceSetImpl();
            val Resource resource = resourceSet.createResource(vuri.getEMFUri());
            resource.getContents().add(newPackage);
        }
    }
				
	override transformChange(TransactionalChange change, CorrespondenceModel correspondenceModel) {
		val result = new ChangeProcessorResult(change, #[]);
		if (change instanceof ConcreteChange) {
			handlePackageInEChange(change);	
		}
		
		return result;
	}
				
}
