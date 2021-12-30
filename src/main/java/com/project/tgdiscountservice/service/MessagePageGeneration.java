package com.project.tgdiscountservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class MessagePageGeneration<T> {

    public List<T> getPage(List<T> collection, int index, String navigateCommand) {

        List<T> result = new ArrayList<>();

        int count = 0;
        if (navigateCommand.equals("-->")) {
            for (int i = index; i < collection.size(); i++) {
                if (count == 5) {
                    break;
                }
                result.add(collection.get(i));
                count++;
            }
        }

        if (navigateCommand.equals("<--")) {
            for (int i = index; i < collection.size(); i--) {
                if (count == 5) {
                    break;
                }
                result.add(collection.get(i));
                count++;
            }
        }

        if (navigateCommand.equals("")) {
            for (int i = index; i < collection.size(); i++) {
                if (count == 5) {
                    break;
                }
                result.add(collection.get(i));
                count++;
            }
        }

        return result;
    }

    public Integer getIndex(int currentIndex) {
        int count = 0;
        while (count < 5) {
            currentIndex++;
            count++;
        }

        return currentIndex;
    }

}
