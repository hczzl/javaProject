import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ListTest {
    public static void main(String[] args) {
        List<Animal> list = new ArrayList<>();
        list.add(new Animal("小狗", 3, 1));
        list.add(new Animal("小猫", 4, 0));
        list.add(new Animal("小马", 5, 1));
        List<Animal> reusltList = list.stream().filter(animal -> animal.getTag() != 0).sorted(Comparator.comparing(Animal::getAge).reversed()).distinct().collect(Collectors.toList());
        reusltList.forEach(animal -> {
            System.out.print(animal.toString());
            System.out.println("\n");
        });
    }

    static class Animal {
        private String name;
        private Integer age;
        private Integer tag;

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public Integer getAge() {
            return age;
        }

        public Integer getTag() {
            return tag;
        }

        public void setTag(Integer tag) {
            this.tag = tag;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        public Animal(String name, Integer age, Integer tag) {
            this.name = name;
            this.age = age;
            this.tag = tag;
        }
    }
}
