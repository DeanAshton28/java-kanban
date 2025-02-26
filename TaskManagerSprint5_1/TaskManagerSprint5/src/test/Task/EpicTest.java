package test.Task;



import task.Epic;
import org.junit.jupiter.api.Test;
import task.Status;

import static org.junit.jupiter.api.Assertions.*;

class EpicTest {

    @Test
    public void EpicsWithEqualIdShouldBeEqual() {
        Epic epic1 = new Epic(10, "Сдать все задания 5го спринта", "До понедельника", Status.NEW);
        Epic epic2 = new Epic(10, "Подготовиться к собеседованию", "3 марта в 12:00",
                Status.IN_PROGRESS);
        assertEquals(epic1, epic2,
                "Ошибка! Наследники класса Task должны быть равны друг другу, если равен их id;");
    }
}