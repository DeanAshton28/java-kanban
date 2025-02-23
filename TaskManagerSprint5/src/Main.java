import Manager.InMemoryTaskManager;
import Manager.Managers;
import Task.Status;
import Task.Task;
import Task.Epic;
import Task.Subtask;

public class Main {

    private static final InMemoryTaskManager inMemoryTaskManager = Managers.getDefault();

    public static void main(String[] args) {

        addTasks();
        printAllTasks();
        printViewHistory();
    }

    private static void addTasks() {
        Task readNietzsche = new Task("Прочитать книгу Ницше", "На немецком языке");
        inMemoryTaskManager.addTask(readNietzsche);

        Task readNietzscheToUpdate = new Task(readNietzsche.getId(), "Прочитать книгу Ницше",
                "Для начала на русском языке", Status.IN_PROGRESS);
        inMemoryTaskManager.updateTask(readNietzscheToUpdate);
        inMemoryTaskManager.addTask(new Task("Сходить на тренировку", "Сделать 100 берпи"));


        Epic yandexEducation = new Epic("Сдать все задания первого модуля", "Нужно успеть до вечера понедельника");
        inMemoryTaskManager.addEpic(yandexEducation);
        Subtask yandexEducationSubtask1 = new Subtask("Сдать 4й спринт", "Написать трекер задач",
                yandexEducation.getId());
        Subtask yandexEducationSubtask2 = new Subtask("Изучить теорию 5го спринта", "Это было непросто, особенно полиморфизм",
                yandexEducation.getId());
        Subtask yandexEducationSubtask3 = new Subtask("Сдать 5й спринт", "До 3го марта",
                yandexEducation.getId());
        Epic conference = new Epic("Подготовка к конференции", "Доклад");
        inMemoryTaskManager.addEpic(conference);

        inMemoryTaskManager.addEpic(yandexEducation);
        inMemoryTaskManager.addSubtask(yandexEducationSubtask1);
        inMemoryTaskManager.addSubtask(yandexEducationSubtask2);
        inMemoryTaskManager.addSubtask(yandexEducationSubtask3);
        yandexEducationSubtask2.setStatus(Status.DONE);
        inMemoryTaskManager.updateSubtask(yandexEducationSubtask2);
        inMemoryTaskManager.addEpic(conference);

    }

    private static void printAllTasks() {
        System.out.println("Задачи:");
        for (Task task : Main.inMemoryTaskManager.getTasks()) {
            System.out.println(task);
        }
        System.out.println("Эпики:");
        for (Epic epic : Main.inMemoryTaskManager.getEpics()) {
            System.out.println(epic);

            for (Task task : Main.inMemoryTaskManager.getEpicSubtasks(epic)) {
                System.out.println("--> " + task);
            }
        }

        System.out.println("Подзадачи:");
        for (Task subtask : Main.inMemoryTaskManager.getSubtasks()) {
            System.out.println(subtask);
        }
    }

    private static void printViewHistory() {

        Main.inMemoryTaskManager.getTaskByID(1);
        Main.inMemoryTaskManager.getTaskByID(2);
        Main.inMemoryTaskManager.getEpicByID(3);
        Main.inMemoryTaskManager.getTaskByID(1);
        Main.inMemoryTaskManager.getSubtaskByID(4);
        Main.inMemoryTaskManager.getSubtaskByID(5);
        Main.inMemoryTaskManager.getSubtaskByID(6);
        Main.inMemoryTaskManager.getEpicByID(3);
        Main.inMemoryTaskManager.getSubtaskByID(4);
        Main.inMemoryTaskManager.getTaskByID(2);
        Main.inMemoryTaskManager.getSubtaskByID(6);

        System.out.println();
        System.out.println("История просмотров:");
        for (Task task : Main.inMemoryTaskManager.getHistory()) {
            System.out.println(task);
        }
    }
}