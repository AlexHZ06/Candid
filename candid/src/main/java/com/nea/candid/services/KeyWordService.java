package com.nea.candid.services;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class KeyWordService {

    private StanfordCoreNLP pipeline;

    public KeyWordService() {

        Properties props = new Properties();
        props.put("annotators", "tokenize, ssplit, pos, lemma");

        pipeline = new StanfordCoreNLP(props);

    }

    public List<String> extractKeywords(String text) {

        CoreDocument document = new CoreDocument(text);
        pipeline.annotate(document);

        Set<String> keywords = new HashSet<>();

        for(int i = 0; i < document.tokens().size(); i++) {

            CoreLabel token = document.tokens().get(i);
            String pos = token.tag();

            if(pos.startsWith("NN") ||  // Nouns
                    pos.startsWith("JJ") ||  // Adjectives
                    pos.startsWith("VB")) {  // Verbs

                keywords.add(token.lemma().toLowerCase());

            }

        }

        return new ArrayList<>(keywords);

    }

}
