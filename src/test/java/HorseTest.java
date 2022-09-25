import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;

public class HorseTest {

    //Проверить, что при передаче в конструктор первым параметром null, будет выброшено IllegalArgumentException.
    //Для этого нужно воспользоваться методом assertThrows;
    //--------------combination-----------------------
    // Проверить, что при передаче в конструктор первым параметром null, выброшенное исключение будет содержать сообщение "Name cannot be null.".
    //Для этого нужно получить сообщение из перехваченного исключения и воспользоваться методом assertEquals;
    @Test
    public void nameIsNullException() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> new Horse(null, 3, 4));
        assertEquals("Name cannot be null.", e.getMessage());
    }

    //Проверить, что при передаче в конструктор первым параметром пустой строки или строки содержащей только пробельные символы (пробел, табуляция и т.д.),
    //будет выброшено IllegalArgumentException. Чтобы выполнить проверку с разными вариантами пробельных символов, нужно сделать тест параметризованным;
    //--------------combination-----------------------
    //Проверить, что при передаче в конструктор первым параметром пустой строки или строки содержащей только пробельные символы (пробел, табуляция и т.д.),
    //выброшенное исключение будет содержать сообщение "Name cannot be blank.";
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "   ", "\t\t\t", "\t", "\n", "\n\n\n"})
    public void blankFirstParameter(String name) {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> new Horse(name, 3, 4));
        assertEquals("Name cannot be blank.", e.getMessage());
    }

    //Проверить, что при передаче в конструктор вторым параметром отрицательного числа, будет выброшено IllegalArgumentException;
    //--------------combination-----------------------
    //Проверить, что при передаче в конструктор вторым параметром отрицательного числа, выброшенное исключение будет содержать
    //сообщение "Speed cannot be negative.";
    @Test
    public void speedNegativeValueException() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> new Horse("horse", -1, 4));
        assertEquals("Speed cannot be negative.", e.getMessage());
    }

    //Проверить, что при передаче в конструктор третьим параметром отрицательного числа, будет выброшено IllegalArgumentException;
    //--------------combination-----------------------
    //Проверить, что при передаче в конструктор третьим параметром отрицательного числа, выброшенное исключение будет
    //содержать сообщение "Distance cannot be negative.";
    @Test
    public void distanceNegativeValueException() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> new Horse("horse", 3, -1));
        assertEquals("Distance cannot be negative.", e.getMessage());
    }

    //метод getName
    //Проверить, что метод возвращает строку, которая была передана первым параметром в конструктор;
    //--------------combination-----------------------
    //метод getSpeed
    //Проверить, что метод возвращает число, которое было передано вторым параметром в конструктор;
    //--------------combination-----------------------
    //метод getDistance
    //Проверить, что метод возвращает число, которое было передано третьим параметром в конструктор;
    //--------------combination-----------------------
    //Проверить, что метод возвращает ноль, если объект был создан с помощью конструктора с двумя параметрами;
    @Test
    public void getNameGetSpeedGetDistance() {
        Horse horse = new Horse("horse", 3,4);
        Horse horse2 = new Horse("horse", 3);
        assertEquals("horse", horse.getName());
        assertEquals(3, horse.getSpeed());
        assertEquals(4, horse.getDistance());
        assertEquals(0, horse2.getDistance());
    }

    //метод move
    //Проверить, что метод вызывает внутри метод getRandomDouble с параметрами 0.2 и 0.9.
    //Для этого нужно использовать MockedStatic и его метод verify;
    @Test
    public void checkMoveMethodWithSpecialParams() {
        try (MockedStatic<Horse> mockedStatic = mockStatic(Horse.class)){
            new Horse("horse", 3,4).move();
            mockedStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }
    }

    //Проверить, что метод присваивает дистанции значение высчитанное по формуле: distance + speed * getRandomDouble(0.2, 0.9).
    //Для этого нужно замокать getRandomDouble, чтобы он возвращал определенные значения, которые нужно задать параметризовав тест.
    @ParameterizedTest
    @ValueSource(doubles = {0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1.0, 1.1, 7.7})
    public void verifyMoveMethod(double value) {
        try(MockedStatic<Horse> mockedStatic = mockStatic(Horse.class)) {
            Horse horse = new Horse("horse", 3, 4);
            mockedStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(value);
            horse.move();
            assertEquals(4 + 3 * value, horse.getDistance());
        }
    }
}
