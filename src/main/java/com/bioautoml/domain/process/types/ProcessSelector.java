package com.bioautoml.domain.process.types;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Getter
@Setter
public class ProcessSelector {

    private List<ProcessStrategy> processes = Arrays.asList(new BinaryProblems(), new DnaRna(),
            new MulticlassProblems(), new Protein(), new WNMDnaRna());

    public Optional<ProcessStrategy> getProcessByName(String name){
        return processes.stream()
                .filter(process -> process.getName().equals(name))
                .findFirst();
    }

}
