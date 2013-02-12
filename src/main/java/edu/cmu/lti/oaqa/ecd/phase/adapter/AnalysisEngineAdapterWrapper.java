package edu.cmu.lti.oaqa.ecd.phase.adapter;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.analysis_engine.service.impl.AnalysisEngineServiceAdapter;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.Parameter;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.ResourceSpecifier;
import org.apache.uima.resource.impl.Parameter_impl;

public abstract class AnalysisEngineAdapterWrapper<A extends AnalysisEngineServiceAdapter, R extends ResourceSpecifier>
    extends JCasAnnotator_ImplBase {

  protected A aeServiceAdapter;
  protected R resourceSpec;

  @Override
  public void initialize(UimaContext aContext)
      throws ResourceInitializationException {
    super.initialize(aContext);

    String[] paraNames = aContext.getConfigParameterNames();
    Parameter[] param = new Parameter[paraNames.length];
    for (int i = 0; i < paraNames.length; i++) {
      String key = paraNames[i];
      String value = (String) aContext.getConfigParameterValue(key).toString();
      Parameter_impl p = new Parameter_impl(key, value);
      param[i] = p;
    }
    resourceSpec = createResourceSpec();
    setResourceSpecParameters(param);
    aeServiceAdapter = createServiceAdapter();
    aeServiceAdapter.initialize(resourceSpec, null);
  }

  @Override
  public void process(JCas jcas) throws AnalysisEngineProcessException {
    aeServiceAdapter.process(jcas);
  }

  @Override
  public void collectionProcessComplete() throws AnalysisEngineProcessException {
    try {
      super.collectionProcessComplete();
      aeServiceAdapter.collectionProcessComplete();
    } finally {
      aeServiceAdapter.destroy();
    }
  }

  abstract protected R createResourceSpec();

  abstract protected void setResourceSpecParameters(Parameter[] params);

  abstract protected A createServiceAdapter();

}
