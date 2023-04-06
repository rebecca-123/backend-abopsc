package com.nighthawk.spring_portfolio.utilities;

import java.util.Arrays;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/utilities/")
public class UtilitiesApiController {
    // Function to do a POST request and return JSON queue
    public <T> String getSortedQueueJSON(int[] seriesOfObjects) {
        Integer[] objectArr = new Integer[seriesOfObjects.length];
        
        for(int i = 0; i < seriesOfObjects.length; i++) {
            objectArr[i] = Integer.valueOf(seriesOfObjects[i]);
        }
        
        QueueManager original = new QueueManager("Original", objectArr);        
        Queue sortedQ = QueueSort.sort(original.queue);
        QueueManager sorted = QueueSort.parse(sortedQ);

        return sorted.toJSON();
    }
    
    // @GetMapping("sort")
    // public <T> String sortUtility(@RequestParam(value = "seriesOfObjects") int[] seriesOfObjects) {
    //     return getSortedQueueJSON(seriesOfObjects);
    // }

    @GetMapping("sort")
    public <T> String sortUtility(@RequestParam(value = "seriesOfObjects") String seriesOfObjectsString) {
        int[] seriesOfObjects = Arrays.stream(seriesOfObjectsString.split(","))
                                       .mapToInt(Integer::parseInt)
                                       .toArray();
        return getSortedQueueJSON(seriesOfObjects);
    }

}
