import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class HippodromeTest {

    //Проверить, что при передаче в конструктор null, будет выброшено IllegalArgumentException;
    //--------------combination-----------------------
    //Проверить, что при передаче в конструктор null, выброшенное исключение будет содержать сообщение "Horses cannot be null.";
    @Test
    public void checkThrowExceptionWhenConstructorWithNull() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> new Hippodrome(null));
        assertEquals("Horses cannot be null.", e.getMessage());
    }

    //Проверить, что при передаче в конструктор пустого списка, будет выброшено IllegalArgumentException;
    //--------------combination-----------------------
    //Проверить, что при передаче в конструктор пустого списка, выброшенное исключение будет содержать сообщение "Horses cannot be empty.";
    @Test
    public void checkThrowExceptionWhenConstructorWithEmptyList() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> new Hippodrome(new ArrayList<>()));
        assertEquals("Horses cannot be empty.", e.getMessage());
    }

    //Проверить, что метод возвращает список, который содержит те же объекты и в той же последовательности, что и список который был передан в конструктор.
    //При создании объекта Hippodrome передай в конструктор список из 30 разных лошадей;
    @Test
    public void getHorses() {
        List<Horse> horses = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            horses.add(new Horse("horse" + i, i, i));
        }
        Hippodrome hippodrome = new Hippodrome(horses);
        assertEquals(horses, hippodrome.getHorses());
    }

    //Проверить, что метод вызывает метод move у всех лошадей. При создании объекта Hippodrome передай в конструктор список
    //из 50 моков лошадей и воспользуйся методом verify.
    @Test
    public void move() {
        List<Horse> horses = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            horses.add(mock(Horse.class));
        }
        new Hippodrome(horses).move();
        for (Horse horse : horses) {
            verify(horse).move();
        }
    }

    //Проверить, что метод возвращает лошадь с самым большим значением distance.
    @Test
    public void getWinner() {
        List<Horse> horses = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            horses.add(new Horse("horse" + i, i, i));
        }
        Horse winner = new Horse("Winner", 3,300);
        horses.add(winner);
        Hippodrome hippodrome = new Hippodrome(horses);
        assertSame(winner, hippodrome.getWinner());
    }
}
