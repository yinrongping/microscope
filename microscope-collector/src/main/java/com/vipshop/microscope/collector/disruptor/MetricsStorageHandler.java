package com.vipshop.microscope.collector.disruptor;

import java.util.HashMap;

import com.lmax.disruptor.EventHandler;
import com.vipshop.microscope.collector.storager.MessageStorager;
import com.vipshop.microscope.common.metrics.MetricsCategory;

/**
 * Metrics store handler.
 * 
 * @author Xu Fei
 * @version 1.0
 */
public class MetricsStorageHandler implements EventHandler<MetricsEvent> {
	
	private final MessageStorager messageStorager = MessageStorager.getMessageStorager();
	
	@Override
	public void onEvent(MetricsEvent event, long sequence, boolean endOfBatch) throws Exception {
		
		HashMap<String, Object> metrics = event.getResult();
		
		String metricsType = (String) metrics.get("type");
		
		if (metricsType.equals(MetricsCategory.THREAD)) {
			processThreadMetrics(metrics);
			return;
		}
		
		if (metricsType.equals(MetricsCategory.Memory)) {
			processMemoryMetrics(metrics);
			return;
		}
		
		if (metricsType.equals(MetricsCategory.GG)) {
			processGCMetrics(metrics);
			return;
		}
		
		if (metricsType.equals(MetricsCategory.EXCEPTION)) {
			processExceptionMetrics(metrics);
			return;
		}
		
	}
	
	@SuppressWarnings("unchecked")
	private void processExceptionMetrics(HashMap<String, Object> metrics) {
		HashMap<String, Object> stack = (HashMap<String, Object>) metrics.get("stack");
		messageStorager.storage(stack);
	}

	private void processGCMetrics(HashMap<String, Object> metrics) {
		// TODO Auto-generated method stub

	}

	private void processMemoryMetrics(HashMap<String, Object> metrics) {
		// TODO Auto-generated method stub
		
	}

	private void processThreadMetrics(HashMap<String, Object> metrics) {
		// TODO Auto-generated method stub
	}

}