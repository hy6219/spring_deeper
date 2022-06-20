package hello.core;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class HelloLombok {
    private String name;
    private int age;

    //psvm
    public static void main(String[] args) {
        HelloLombok lombok = new HelloLombok();
        lombok.setAge(20);
        lombok.setName("name");
        System.out.println("lombok = "+lombok.getName() + " " + lombok.getAge());
        System.out.println("lombok = " + lombok);
    }
}
