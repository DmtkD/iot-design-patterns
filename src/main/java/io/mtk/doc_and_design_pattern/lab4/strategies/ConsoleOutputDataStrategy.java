package io.mtk.doc_and_design_pattern.lab4.strategies;

import java.util.List;

public class ConsoleOutputDataStrategy implements OutputDataStrategy {
    @Override
    public void execute(List<String> data) {
        System.out.println("#################  CONSOLE LOGOUT START  #################");

        for (int i = 0; i < data.size(); i++) {
            System.out.printf("%d ====> %s%n", i + 1, data.get(i));
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        System.out.println("##################  CONSOLE LOGOUT END  ##################");
    }
}
