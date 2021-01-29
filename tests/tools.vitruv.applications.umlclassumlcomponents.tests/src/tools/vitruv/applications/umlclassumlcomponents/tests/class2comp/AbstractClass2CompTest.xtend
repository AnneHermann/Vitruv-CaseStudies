package tools.vitruv.applications.umlclassumlcomponents.tests.class2comp

import org.eclipse.emf.ecore.EObject
import org.eclipse.uml2.uml.Class
import org.eclipse.uml2.uml.Component
import org.eclipse.uml2.uml.Model
import org.eclipse.uml2.uml.Package
import org.eclipse.uml2.uml.UMLFactory
import tools.vitruv.applications.umlclassumlcomponents.class2comp.UmlClass2UmlCompChangePropagation

import static tools.vitruv.applications.umlclassumlcomponents.tests.util.SharedTestUtil.*
import static tools.vitruv.applications.umlclassumlcomponents.util.SharedUtil.*
import org.junit.jupiter.api.BeforeEach
import tools.vitruv.testutils.LegacyVitruvApplicationTest
import java.nio.file.Path

import static org.junit.jupiter.api.Assertions.assertEquals

abstract class AbstractClass2CompTest extends LegacyVitruvApplicationTest {

	protected def Model getRootElement() {
		return Model.from(MODEL_NAME.projectModelPath)
	}

	override protected getChangePropagationSpecifications() {
		return #[new UmlClass2UmlCompChangePropagation()]
	}

	// SaveAndSynchronize & commit all pending userInteractions
	protected def saveAndSynchronizeWithInteractions(EObject object) {
		propagate()
		userInteraction.assertAllInteractionsOccurred()
	}

	@BeforeEach
	def protected setup() {
		initializeTestModel()
	}

	protected def void initializeTestModel() {
		val umlModel = UMLFactory.eINSTANCE.createModel() => [
			name = MODEL_NAME
		]
		resourceAt(MODEL_NAME.projectModelPath).startRecordingChanges => [
			contents += umlModel
		]
		propagate
	}

	private def Path getProjectModelPath(String modelName) {
		Path.of(FOLDER_NAME).resolve(modelName + "." + MODEL_FILE_EXTENSION)
	}

	/***************
	 * Assert Helper:*
	 ****************/
	package def Component assertComponentForClass(Class umlClass, String name) {
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[umlClass]).flatten
		assertEquals(1, correspondingElements.size)
		val umlComponent = correspondingElements.get(0)
		assertTypeAndName(umlComponent, Component, name)
		return umlComponent as Component
	}

	package def Component assertPackageLinkedToComponent(Package classPackage, String componentName) {
		val correspondingElements = correspondenceModel.getCorrespondingEObjects(#[classPackage]).flatten.filter(
			Component)
		assertEquals(1, correspondingElements.size)
		val umlComponent = correspondingElements.get(0)
		assertTypeAndName(umlComponent, Component, componentName)
		return umlComponent
	}

	/*****************
	 * Creation Helper:*
	 ******************/
	package def Class createClassWithoutInteraction(String name) {
		val classPackage = createPackage(name + PACKAGE_SUFFIX)
		return createClassWithoutInteraction(name, classPackage)
	}

	package def Class createClassWithoutInteraction(String name, Package classPackage) {
		val umlClass = UMLFactory.eINSTANCE.createClass()
		umlClass.name = name
		classPackage.packagedElements += umlClass

		return umlClass
	}

	package def Class createClass(String name, int createComponent) {
		val classPackage = createPackage(name + PACKAGE_SUFFIX)
		return createClass(name, classPackage, createComponent)
	}

	package def Class createClass(String name, Package classPackage, int createComponent) {
		val umlClass = createClassWithoutInteraction(name, classPackage)

		// Decide whether to create corresponding Component or not:
		userInteraction.onMultipleChoiceSingleSelection [message.contains('''Should '«name»' be represented by a Component?''')]
			.respondWithChoiceAt(createComponent)

		return umlClass
	}

	package def Package createPackage(String name) {
		createPackage(name, 1, -1)
	}

	package def Package createPackage(String name, int createComponent, int componentSelection) {
		val classPackage = UMLFactory.eINSTANCE.createPackage()
		classPackage.name = name + PACKAGE_SUFFIX
		rootElement.packagedElements += classPackage

		// Decide whether to link this Package to an existing Component
		userInteraction.onMultipleChoiceSingleSelection [message.contains('''link this Package '«classPackage.name»' to an existing Component''')]
			.respondWithChoiceAt(createComponent)
		if (componentSelection >= 0) {
			userInteraction.onMultipleChoiceSingleSelection [message.contains('''Choose an existing Component''')]
				.respondWithChoiceAt(componentSelection)
		}

		return classPackage
	}
}
