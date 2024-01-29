package at.phactum.studenttask;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;

@Component
public class YourTask {
	private final static Logger LOG = LoggerFactory.getLogger(YourTask.class);

	private final static String INPUT_NOT_FOUND = "ProcessVariable 'input' not found";

	@JobWorker(type = "count_people_under_30_group_01") // TODO: change type according to your group "count_people_under_30_group_XX". XX will be provided.
	public Map<String, Object> countPeopleUnder30(final ActivatedJob job) {
		// TODO: Implement worker to get job and input from process variables from ZeeBe
		String input = "";
		return Map.of("result", formatInputAndCountPeopleUnder30(input));
	}

	private String formatInputAndCountPeopleUnder30(String input) {
		// TODO: Implement logic to find number of people under 30
		return ("");
	}


}
