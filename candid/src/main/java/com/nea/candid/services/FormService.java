package com.nea.candid.services;

import com.nea.candid.data.dbEnties.QuestionsTableEntity;
import com.nea.candid.data.dbEnties.ResponsesTableEntity;
import com.nea.candid.data.dto.ResponseBody;
import com.nea.candid.services.database.FormDbService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FormService {

    private final FormDbService formDbService;

    public FormService(FormDbService formDbService) {
        this.formDbService = formDbService;
    }

    public ResponseBody addResponses(List<ResponsesTableEntity> responsesTableEntities) {

        try{

            formDbService.addResponses(responsesTableEntities);
            return ResponseBody.success("Responses added", 952);

        }catch(Exception e){

            System.out.println(e.getMessage());
            e.printStackTrace();
            return ResponseBody.error("responses not added", 903);

        }

    }

    public ResponseBody addQuestions(QuestionsTableEntity questionsTableEntities) {

        try{

            formDbService.addQuestions(questionsTableEntities);
            return ResponseBody.success("Questions added", 952);

        }catch(Exception e){

            System.out.println(e.getStackTrace());
            System.out.println(e.getMessage());
            return ResponseBody.error("questions not added", 903);

        }
    }

    public ResponseBody markQuestionsAsInactive(long id) {

        try {
            formDbService.markQuestionAsInActive(id);
            return ResponseBody.success("Questions marked as Inactive", 952);
        }catch(Exception e){

            return ResponseBody.error("questions not marked as Inactive", 903);
        }

    }

    public ResponseBody getResponses(long bookingid) {

        try {
            return ResponseBody.success(formDbService.getResponses(bookingid), 955);
        }catch(Exception e){

            return ResponseBody.error("no responses", 904);

        }

    }

    public ResponseBody getQuestions(long id) {

        try{
            return ResponseBody.success(formDbService.getQuestions(id), 955);
        }catch(Exception e){

            return ResponseBody.error("questions not found", 904);

        }

    }

}
