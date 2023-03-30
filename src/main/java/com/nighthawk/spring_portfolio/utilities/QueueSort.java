package com.nighthawk.spring_portfolio.utilities;


class QueueSort {
    
    public static Queue sort(Queue q) {
        
        
        // Base case for recursion
        
        if (q.getSize() < 2) {
            
            return q;
        }
        
        
        // Setting the pivot
        Integer mid = (Integer) q.peek();
        
        // Creating two sides of the pivot
        Queue left = new Queue<>();
        Queue right = new Queue<>();
        
        q.delete();
        
        // Populating each side of the pivot
        while (q.getSize() > 0) {
            
            Integer element = (Integer) q.delete();
            
            if (element < mid) {
                left.add(element);
                
            }
            
            else {
                right.add(element);
            }
        
        }
                
        // Recursively sorting each side of the pivot
        Queue sortedLeft = sort(left);
        Queue sortedRight = sort(right);

        // Putting each side of the pivot together with the pivot itself to form a sorted queue
        Queue sortedComplete = new Queue<>();
        while (sortedLeft.getSize() > 0) {
            sortedComplete.add(sortedLeft.delete());
        }
        sortedComplete.add(mid);
        while (sortedRight.getSize() > 0) {
            sortedComplete.add(sortedRight.delete());
        }

        return sortedComplete;
                    
    }

    public static QueueManager parse(Queue sortedQ) {
        int length = sortedQ.getSize();
        
        Integer[] sortedArr = new Integer[sortedQ.getSize()];
        
        for (int i = 0; i < length; i++) {
            if (i == 0) {
                sortedArr[i] = (Integer) sortedQ.getHead().getData();
            }
            
            else {
                sortedArr[i] = (Integer) sortedQ.next().getData();
            }
        }
        
        return new QueueManager("Sorted", sortedArr);
    }

    

    public static void main(String[] args)
    {
        
        // Initializing the queue to be sorted 
        
        int[] primitiveArr = new int[] {5, 1, 4, 3, 7, 2};
        
        Integer[] objectArr = new Integer[primitiveArr.length];
        
        for(int i = 0; i < primitiveArr.length; i++) {
            objectArr[i] = Integer.valueOf(primitiveArr[i]);
        }
        
        QueueManager original = new QueueManager("Original", objectArr);
        original.printQueue();
        

        Queue sortedQ = QueueSort.sort(original.queue);
        
        QueueManager sorted = parse(sortedQ);

        sorted.printQueue();
        System.out.println(sorted.toJSON());

        
        

    }
}
