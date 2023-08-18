package org.springframework.samples.ai.openai.roles;

import jdk.jfr.Frequency;
import org.springframework.ai.client.AiClient;
import org.springframework.ai.client.Generation;
import org.springframework.ai.prompt.Prompt;
import org.springframework.ai.prompt.PromptTemplate;
import org.springframework.ai.prompt.SystemPromptTemplate;
import org.springframework.ai.prompt.messages.SystemMessage;
import org.springframework.ai.prompt.messages.UserMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cglib.proxy.Mixin;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class RoleController {

	private AiClient aiClient;

	@Value("classpath:/prompts/system-message.st")
	private Resource systemResource;


	@Autowired
	public RoleController(AiClient aiClient) {
		this.aiClient = aiClient;
	}

	@GetMapping("/ai/roles")
	public List<Generation> generate(
			@RequestParam(value = "request", defaultValue = "Tell me about 3 famous pirates from the Golden Age of Piracy and why they did.") String request,
								 @RequestParam(value = "name", defaultValue = "Bob") String name,
								 @RequestParam(value = "voice", defaultValue = "pirate") String voice) {
		UserMessage userMessage = new UserMessage(request);
		SystemPromptTemplate systemPromptTemplate = new SystemPromptTemplate(systemResource);
		SystemMessage systemMessage = systemPromptTemplate.createMessage(Map.of("name", name, "voice", voice));
		Prompt prompt = new Prompt(List.of(userMessage, systemMessage));
		List<Generation> response = aiClient.generate(prompt).getGenerations();
		return response;
	}
}
