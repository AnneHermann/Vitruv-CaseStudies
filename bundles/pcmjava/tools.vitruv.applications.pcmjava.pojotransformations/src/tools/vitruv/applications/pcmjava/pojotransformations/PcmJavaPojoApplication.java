package tools.vitruv.applications.pcmjava.pojotransformations;

import java.util.HashSet;
import java.util.Set;

import tools.vitruv.applications.pcmjava.pojotransformations.java2pcm.Java2PcmChangePropagationSpecification;
import tools.vitruv.applications.pcmjava.pojotransformations.pcm2java.Pcm2JavaChangePropagationSpecification;
import tools.vitruv.domains.java.JavaDomainProvider;
import tools.vitruv.domains.pcm.PcmDomainProvider;
import tools.vitruv.framework.applications.VitruvApplication;
import tools.vitruv.framework.domains.VitruvDomain;
import tools.vitruv.framework.propagation.ChangePropagationSpecification;

public class PcmJavaPojoApplication implements VitruvApplication {

	@Override
	public Set<ChangePropagationSpecification> getChangePropagationSpecifications() {
		Set<ChangePropagationSpecification> specs = new HashSet<ChangePropagationSpecification>();
		specs.add(new Pcm2JavaChangePropagationSpecification());
		specs.add(new Java2PcmChangePropagationSpecification());
		return specs;
	}

	@Override
	public String getName() {
		return "PCM <> Java POJO";
	}

	@Override
	public Set<VitruvDomain> getVitruvDomains() {
		return Set.of(new PcmDomainProvider().getDomain(), new JavaDomainProvider().getDomain());
	}

}
