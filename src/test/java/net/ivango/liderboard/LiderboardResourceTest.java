package net.ivango.liderboard;

import net.ivango.liderboard.rest.LiderboardResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class LiderboardResourceTest {

    private Logger logger = LoggerFactory.getLogger(LiderboardResourceTest.class);
    private LiderboardResource liderboardResource;


    @BeforeClass
    public void init(){
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        liderboardResource = context.getBean(LiderboardResource.class);
    }

    @Test
    private void test1(){
//        liderboardService.getLiderboard();
    }
}
