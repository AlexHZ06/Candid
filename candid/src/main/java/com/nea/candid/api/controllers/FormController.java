package com.nea.candid.api.controllers;

import com.nea.candid.data.dbEnties.QuestionsTableEntity;
import com.nea.candid.data.dbEnties.ResponsesTableEntity;
import com.nea.candid.services.FormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.nea.candid.data.dto.ResponseBody;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/form")
public class FormController {

    private final FormService formService;

    public FormController(FormService formService) {
        this.formService = formService;
    }

    @PostMapping("/photographer/addquestions")
    public ResponseBody addQuestions(@RequestBody QuestionsTableEntity questions) {

        return formService.addQuestions(questions);

    }

    @PostMapping("/client/addresponses")
    public ResponseBody addResponses(@RequestBody List<ResponsesTableEntity> response) {

        return formService.addResponses(response);

    }

    @PostMapping("/getresponses")
    public ResponseBody getResponses(@RequestBody Map<String, Long> body) {

            return formService.getResponses(body.get("bookingId"));

    }

    @PostMapping("/photographer/markquestionsasinactive")
    public ResponseBody markQuestionsAsInactive(@RequestBody Map<String, Long> body){

        return formService.markQuestionsAsInactive(body.get("questionId"));

    }

    @PostMapping("/getquestions")
    public ResponseBody getQuestions(@RequestBody Map<String, Long> body) {

        return formService.getQuestions(body.get("photographerId"));

    }

}
