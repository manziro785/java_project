package controllers;

import models.Survey;
import services.SurveyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/surveys")
public class SurveyController {
    private final SurveyService surveyService;

    public SurveyController(SurveyService surveyService) {
        this.surveyService = surveyService;
    }

    @GetMapping
    public List<Survey> getAllSurveys() {
        return surveyService.getAllSurveys();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Survey> getSurveyById(@PathVariable Long id) {
        Optional<Survey> survey = surveyService.getSurveyById(id);
        return survey.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Survey createSurvey(@RequestBody Survey survey) {
        return surveyService.saveSurvey(survey);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSurvey(@PathVariable Long id) {
        surveyService.deleteSurvey(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Survey> updateSurvey(@PathVariable Long id, @RequestBody Survey survey) {
        Optional<Survey> existingSurvey = surveyService.getSurveyById(id);
        if (existingSurvey.isPresent()) {
            survey.setId(id); // Устанавливаем id, чтобы обновить существующую запись
            Survey updatedSurvey = surveyService.saveSurvey(survey);
            return ResponseEntity.ok(updatedSurvey);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
