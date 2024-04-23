package io.mtk.doc_and_design_pattern.lab4;

import io.mtk.doc_and_design_pattern.lab4.strategies.OutputDataStrategy;

import java.util.List;

public class DataProcessor {
    private OutputDataStrategy outputStrategy;

    public void setOutputStrategy(OutputDataStrategy outputStrategy) {
        this.outputStrategy = outputStrategy;
    }

    public void processData(List<String> data) {
        outputStrategy.execute(data);
    }
}
