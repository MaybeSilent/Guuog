package guuog.nioserver.processor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import guuog.nioserver.processor.BufferPool.ResizeArray;

public class ResizeArrayTest{
    private  ResizeArray testArray;

    /**
     * 创建有5个元素的resizeArray
     */
    @BeforeEach
    public void init(){
        testArray = (new BufferPool()).new ResizeArray(5);
    }

    @Test
    public void testTakeAndPut(){
        System.out.println("=== test take ===");
        
        Assertions.assertEquals(testArray.available() , 0); 
        Assertions.assertEquals(testArray.remineContent(), 5);  
        
        for(int i = 0 ; i < 5 ; i++){
            testArray.put(i); // 向数组中放入相应的值
        }

        Assertions.assertEquals(testArray.available() , 5); 
        Assertions.assertEquals(testArray.remineContent(), 0); 

        Assertions.assertEquals(testArray.put(6), false); //测试内容放置满了之后，还能否继续放置
        
        for(int i = 0 ; i < 5 ; i++){
            Assertions.assertEquals(testArray.take() , i);
        }
        Assertions.assertEquals(testArray.take(), -1);
        
    }

}