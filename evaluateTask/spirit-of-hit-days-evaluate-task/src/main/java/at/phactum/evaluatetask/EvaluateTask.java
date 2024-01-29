package at.phactum.evaluatetask;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;

@Component
public class EvaluateTask {
	private final static Logger LOG = LoggerFactory.getLogger(EvaluateTask.class);

	private final static String RESULT_NOT_FOUND = "ProcessVariable 'result' not found";

	@JobWorker(type = "evaluate") // TODO: change type to your "taskXX" XX will be provided
	public Map<String, Object> evaluate(final ActivatedJob job, final JobClient client) {
		Map<String, Object> processVariables = job.getVariablesAsMap();
		if (!processVariables.containsKey("result")){
			LOG.error(RESULT_NOT_FOUND);
			return Map.of("result", RESULT_NOT_FOUND);
		}
		String result = (String) processVariables.get("result");
		LOG.info(result);

		return Map.of("correct", result.equals("890"));
	}

}
