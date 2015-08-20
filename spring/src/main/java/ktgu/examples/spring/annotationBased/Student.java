package ktgu.examples.spring.annotationBased;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by ktgu on 15/8/18.
 */

public class Student {

//    必须要有默认构造函数
    public Student() {
    }

    //@Autowired
    public Student(String name) {
    }

    @Autowired(required = false)
    public Student(String name, int age) {

    }


}
