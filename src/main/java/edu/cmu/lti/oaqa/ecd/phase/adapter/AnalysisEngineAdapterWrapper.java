package edu.cmu.lti.oaqa.ecd.phase.adapter;

import java.util.HashMap;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.AnalysisComponent_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.analysis_engine.service.impl.AnalysisEngineServiceAdapter;
import org.apache.uima.cas.AbstractCas;
import org.apache.uima.cas.CAS;
import org.apache.uima.resource.Parameter;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.ResourceSpecifier;
import org.apache.uima.resource.impl.Parameter_impl;

public abstract class AnalysisEngineAdapterWrapper<A extends AnalysisEngineServiceAdapter, R extends ResourceSpecifier>
		extends AnalysisComponent_ImplBase {

	protected A aeServiceAdapter;
	private String brokerUrl, endpoint;
	private int timeout = 5000, getmetatimeout = 5000, cpctimeout = 5000;
	protected R resourceSpec;

	@Override
	public void initialize(UimaContext aContext)
			throws ResourceInitializationException {
		super.initialize(aContext);
		brokerUrl = (String) aContext.getConfigParameterValue("brokerUrl");
		endpoint = (String) aContext.getConfigParameterValue("endpoint");
		timeout = (Integer) aContext.getConfigParameterValue("timeout");
		getmetatimeout = (Integer) aContext
				.getConfigParameterValue("getmetatimeout");
		cpctimeout = (Integer) aContext.getConfigParameterValue("cpctimeout");

		String[] paraNames = aContext.getConfigParameterNames();
        Parameter[] param = new Parameter[paraNames.length];
        for (int i = 0; i < paraNames.length; i++) {
                String key = paraNames[i];
                String value = (String) aContext.getConfigParameterValue(key).toString();
                Parameter_impl p = new Parameter_impl(key,value);
                param[i] = p;
        }
        resourceSpec = createResourceSpec();
        setResourceSpecParameters(param);
        aeServiceAdapter = createServiceAdapter();
        aeServiceAdapter.initialize(resourceSpec,new HashMap());
        
	}
	
	abstract protected R createResourceSpec();
	abstract protected void setResourceSpecParameters(Parameter[] params);
	abstract protected A createServiceAdapter();
	
	@Override
	public void process(AbstractCas aCAS) throws AnalysisEngineProcessException {
		aeServiceAdapter.process((CAS) aCAS);

	}

	@Override
	public boolean hasNext() throws AnalysisEngineProcessException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public AbstractCas next() throws AnalysisEngineProcessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<? extends AbstractCas> getRequiredCasInterface() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getCasInstancesRequired() {
		// TODO Auto-generated method stub
		return 0;
	}

}
