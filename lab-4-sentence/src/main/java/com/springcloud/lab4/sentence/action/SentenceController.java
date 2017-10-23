package com.springcloud.lab4.verb.action;

import com.springcloud.lab4.sentence.service.SentenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

@RestController
public class SentenceController {

    //	This is referencing the RestTemplate we defined earlier:
    @Autowired
    RestTemplate template;

    @Autowired
    DiscoveryClient client;
	
	@Autowired
    SentenceService sentenceService;
	
	@GetMapping("/sentence-lab6")
	public @ResponseBody String getSentence() {
	  return 
		"<h3>Some Sentences</h3><br/>" +	  
		sentenceService.buildSentence() + "<br/><br/>" +
		sentenceService.buildSentence() + "<br/><br/>" +
		sentenceService.buildSentence() + "<br/><br/>" +
		sentenceService.buildSentence() + "<br/><br/>" +
		sentenceService.buildSentence() + "<br/><br/>"
		;
	}

    /**
     * Display a small list of Sentences to the caller:
     */
    @GetMapping("/sentence-lab5")
    public @ResponseBody
    String getSentenceWord() {
        return
            "<h3>Some Sentences</h3><br/>" +
                    buildSentence() + "<br/><br/>" +
                    buildSentence() + "<br/><br/>" +
                    buildSentence() + "<br/><br/>" +
                    buildSentence() + "<br/><br/>" +
                    buildSentence() + "<br/><br/>"
            ;
    }

    @GetMapping("/sentence-lab4")
    public @ResponseBody
    String getSentenceDiffer() {
        return
                getWord("SUBJECT") + " "
                        + getWord2("VERB") + " "
                        + getWord2("ARTICLE") + " "
                        + getWord2("ADJECTIVE") + " "
                        + getWord2("NOUN") + "."
                ;
    }

    /**
     * Assemble a sentence by gathering random words of each part of speech:
     */
    public String buildSentence() {
        String sentence = "There was a problem assembling the sentence!";
        try {
            sentence =
                String.format("%s %s %s %s %s.",
                        getWord("SUBJECT"),
                        getWord("VERB"),
                        getWord("ARTICLE"),
                        getWord("ADJECTIVE"),
                        getWord("NOUN"));
        } catch (Exception e) {
            System.out.println(e);
        }
        return sentence;
    }

    /**
     * Obtain a random word for a given part of speech, where the part
     * of speech is indicated by the given service / client ID:
     */
    public String getWord(String service) {
        return template.getForObject("http://" + service, String.class);

    }

    public String getWord2(String service) {
        List<ServiceInstance> list = client.getInstances(service);
        if (list != null && list.size() > 0) {
            URI uri = list.get(0).getUri();
            if (uri != null) {
                return (new RestTemplate()).getForObject(uri, String.class);
            }
        }
        return null;
    }
}
