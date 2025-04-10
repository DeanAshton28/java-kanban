import manager.InMemoryTaskManager;
import manager.Managers;
import manager.TaskManager;
import task.Status;
import task.Task;
import task.Epic;
import task.Subtask;

public class Main {

    private static final TaskManager taskManager = Managers.getDefault();

    public static void main(String[] args) {
        addTasks();
        printAllTasks();
        printViewHistory();
    }

    private static void addTasks() {
        Task readNietzsche = new Task("Прочитать книгу Ницше", "На немецком языке");
        taskManager.addTask(readNietzsche);

        Task readNietzscheToUpdate = new Task(readNietzsche.getId(), "Прочитать книгу Ницше",
                "Для начала на русском языке", Status.IN_PROGRESS);
        taskManager.updateTask(readNietzscheToUpdate);
        taskManager.addTask(new Task("Сходить на тренировку", "Сделать 100 берпи"));

        Epic yandexEducation = new Epic("Сдать все задания первого модуля", "Нужно успеть до вечера понедельника");
        taskManager.addEpic(yandexEducation);
        Subtask yandexEducationSubtask1 = new Subtask("Сдать 4й спринт", "Написать трекер задач",
                yandexEducation.getId());
        Subtask yandexEducationSubtask2 = new Subtask("Изучить теорию 5го спринта", "Это было непросто, особенно полиморфизм",
                yandexEducation.getId());
        Subtask yandexEducationSubtask3 = new Subtask("Сдать 5й спринт", "До 3го марта",
                yandexEducation.getId());

        taskManager.addSubtask(yandexEducationSubtask1);
        taskManager.addSubtask(yandexEducationSubtask2);
        taskManager.addSubtask(yandexEducationSubtask3);

        yandexEducationSubtask2.setStatus(Status.DONE);
        taskManager.updateSubtask(yandexEducationSubtask2);

        Epic conference = new Epic("Подготовка к конференции", "Доклад");
        taskManager.addEpic(conference);
    }

    private static void printAllTasks() {
        System.out.println("Задачи:");
        for (Task task : taskManager.getTasks()) {
            System.out.println(task);
        }
        System.out.println("Эпики:");
        for (Epic epic : taskManager.getEpics()) {
            System.out.println(epic);

            for (Task task : taskManager.getEpicSubtasks(epic)) {
                System.out.println("--> " + task);
            }
        }

        System.out.println("Подзадачи:");
        for (Task subtask : taskManager.getSubtasks()) {
            System.out.println(subtask);
        }
    }

    private static void printViewHistory() {
        taskManager.getTaskByID(1);
        taskManager.getTaskByID(2);
        taskManager.getEpicByID(3);
        taskManager.getTaskByID(1);
        taskManager.getSubtaskByID(4);
        taskManager.getSubtaskByID(5);
        taskManager.getSubtaskByID(6);
        taskManager.getEpicByID(3);
        taskManager.getSubtaskByID(4);
        taskManager.getTaskByID(2);
        taskManager.getSubtaskByID(6);

        System.out.println();
        System.out.println("История просмотров:");
        for (Task task : taskManager.getHistory()) {
            System.out.println(task);
        }
    }
}