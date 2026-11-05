package com.nea.candid.services.database;

import com.nea.candid.data.dbEnties.QuestionsTableEntity;
import com.nea.candid.data.dbEnties.ResponsesTableEntity;
import com.nea.candid.data.dto.ResponseBody;
import com.nea.candid.repositories.QuestionTableRepo;
import com.nea.candid.repositories.ResponsesTableRepo;
import org.aspectj.weaver.patterns.TypePatternQuestions;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FormDbService {

    private final QuestionTableRepo questionTableRepo;
    private final ResponsesTableRepo responsesTableRepo;

    public FormDbService(QuestionTableRepo questionTableRepo, ResponsesTableRepo responsesTableRepo) {
        this.questionTableRepo = questionTableRepo;
        this.responsesTableRepo = responsesTableRepo;
    }

    public void addQuestions(QuestionsTableEntity questions) {

        int response = questionTableRepo.saveQuestion(questions);
        if(response == 0) {

            throw new RuntimeException("Not all questions added");

        }

    }

    public void markQuestionAsInActive(long question) {

        int result = questionTableRepo.markInactive(question);
        if(result == 0){

            throw new RuntimeException("Questions have not been marked as Inactive");

        }

    }

    public List<QuestionsTableEntity> getQuestions(long id) {

        List<QuestionsTableEntity> result = questionTableRepo.getActiveQuestions(id);
        if(result == null || result.isEmpty()){

            throw new RuntimeException("No questions found");

        }
        return result;

    }

    public List<ResponsesTableEntity> getResponses(long bookingId) {

        List<ResponsesTableEntity> result = responsesTableRepo.getAllResponses(bookingId);
        if(result == null || result.isEmpty()){

            throw new RuntimeException("No responses found");

        }
        return result;

    }

    public void addResponses(List<ResponsesTableEntity> responses) {

        int[] response = responsesTableRepo.addResponse(responses);
        boolean empty = false;
        for(int i = 0; i < response.length; i++){

            if(response[i] == 0){

                empty = true;

            }

        }
        if(empty) {

            throw new RuntimeException("Not all responses added");


        }

    }

}


