package com.bioautoml.domain.process.parameters.service;

import com.bioautoml.domain.process.model.ProcessModel;
import com.bioautoml.domain.process.parameters.enums.LabelType;
import com.bioautoml.domain.process.parameters.form.LabelForm;
import com.bioautoml.domain.process.parameters.model.LabelModel;
import com.bioautoml.domain.process.parameters.repository.LabelRepository;
import com.bioautoml.domain.process.repository.ProcessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LabelService {

    @Autowired
    private LabelRepository labelRepository;

    @Autowired
    private ProcessRepository processRepository;

    public void save(LabelForm labelForm, UUID processId) {
        this.processRepository.findById(processId).ifPresent(processModel -> {
            labelForm.getTrainLabels().forEach(label -> {
                LabelModel labelModel = new LabelModel();
                labelModel.setLabelType(LabelType.TRAIN);
                labelModel.setProcessModel(processModel);
                labelModel.setValue(label);

                this.save(labelModel);
            });

            labelForm.getTestLabels().forEach(label -> {
                LabelModel labelModel = new LabelModel();
                labelModel.setLabelType(LabelType.TEST);
                labelModel.setProcessModel(processModel);
                labelModel.setValue(label);

                this.save(labelModel);
            });
        });
    }

    private void save(LabelModel labelModel){
        this.labelRepository.save(labelModel);
    }

    public Optional<List<LabelModel>> findAllByProcess(ProcessModel processModel){
        return this.labelRepository.findAllByProcessModel(processModel);
    }



}
