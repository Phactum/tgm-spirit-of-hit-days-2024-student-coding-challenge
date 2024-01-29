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
		Map<String, Object> processVariables = job.getVariablesAsMap();
		if (!processVariables.containsKey("input")){
			LOG.error(INPUT_NOT_FOUND);
			return Map.of("result", INPUT_NOT_FOUND);
		}
		String input = (String) processVariables.get("input");
		LOG.info(input);

		return Map.of("result", formatInputAndCountPeopleUnder30(input));
	}

	private String formatInputAndCountPeopleUnder30(String input) {
		SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");

		String[] delimited = input.split("\\|");
		AtomicInteger result = new AtomicInteger();

		Arrays.stream(delimited).forEach(item -> {
			Pattern pattern = Pattern.compile("^([A-Za-zÀ-ž\\u0370-\\u03FF\\u0400-\\u04FF]+)(\\d+\\.\\d+\\.\\d+)$");
			Matcher matcher = pattern.matcher(item);

			if(matcher.matches()) {
				String date = matcher.group(2);
				Instant instant = null;
				try {
					instant = format.parse(date).toInstant();
					if(instant.isBefore(format.parse("01.02.1999").toInstant())) {
						result.incrementAndGet();
					}
				} catch (ParseException e) {
					throw new RuntimeException(e);
				}
			} else {
				System.out.println("Date not matched: " + item);
			}
		});
		return result.toString();
	}


}
