package edu.cmu.lti.oaqa.ecd.phase.adapter;

import org.apache.uima.UimaContext;
import org.apache.uima.aae.jms_adapter.JmsAnalysisEngineServiceAdapter;
import org.apache.uima.resource.CustomResourceSpecifier;
import org.apache.uima.resource.Parameter;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.impl.CustomResourceSpecifier_impl;

public class JMSAdapterWrapper
		extends
		AnalysisEngineAdapterWrapper<JmsAnalysisEngineServiceAdapter, CustomResourceSpecifier_impl> {
	private final String JMS_ADAPTER_CLASS_NAME = "org.apache.uima.aae.jms_adapter.JmsAnalysisEngineServiceAdapter";

	@Override
	public void initialize(UimaContext aContext)
			throws ResourceInitializationException {
		super.initialize(aContext);
	}

	@Override
	protected CustomResourceSpecifier_impl createResourceSpec() {
		CustomResourceSpecifier_impl rSpec = new CustomResourceSpecifier_impl();
		rSpec.setResourceClassName(JMS_ADAPTER_CLASS_NAME);
		return rSpec;
	}

	@Override
	protected void setResourceSpecParameters(Parameter[] params) {
		resourceSpec.setParameters(params);
	}

	@Override
	protected JmsAnalysisEngineServiceAdapter createServiceAdapter() {
		return new JmsAnalysisEngineServiceAdapter();
	}

}
