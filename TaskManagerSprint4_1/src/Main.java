import manager.TaskManager;
import task.Task;
import task.Epic;
import task.Subtask;
import task.Status;
public class Main {

    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();

        Task readNietzsche = new Task("Прочитать книгу Ницше", "На немецком языке");
        Task readNietzscheCreated = taskManager.addTask(readNietzsche);
        System.out.println("Создана задача: " + readNietzscheCreated);
        Task crossfitTraining = new Task("Сходить на тренировку", "Сделать 100 берпи");
        Task crossfitTrainingCreated = taskManager.addTask(crossfitTraining);
        System.out.println("Создана задача 2: " + crossfitTrainingCreated);
        Task readNietzscheToUpdate = new Task(
                readNietzsche.getId(),
                "Пора приниматься за первый том Ницше",
                "Для начала на русском языке",
                Status.IN_PROGRESS
        );
        Task readNietzscheUpdated = taskManager.updateTask(readNietzscheToUpdate);
        System.out.println("Обновлённая задача: " + readNietzscheUpdated);

        Epic yandexEducation = new Epic("Сдать все задания первого модуля", "Нужно успеть до вечера понедельника");
        taskManager.addEpic(yandexEducation);
        System.out.println(yandexEducation);


        Subtask yandexEducationSubtask1 = new Subtask(
                "Сдать 4й спринт",
                "Написать трекер задач",
                Status.NEW,  // Добавлен статус
                yandexEducation.getId()
        );

        Subtask yandexEducationSubtask2 = new Subtask(
                "Изучить теорию 5го спринта",
                "Это было непросто, особенно полиморфизм",
                Status.NEW,  // Добавлен статус
                yandexEducation.getId()
        );

        Epic conference = new Epic("Подготовка к конференции", "Доклад");
        taskManager.addEpic(conference);
        System.out.println(conference);
        Subtask conference1 = new Subtask(
                "Написать тезисы",
                "Подготовить материал для выступления",
                Status.NEW,
                conference.getId()
        );
        taskManager.addSubtask(conference1);



        taskManager.addSubtask(yandexEducationSubtask1);
        taskManager.addSubtask(yandexEducationSubtask2);
        System.out.println(yandexEducationSubtask1);

        readNietzsche.setStatus(Status.IN_PROGRESS);
        taskManager.updateTask(readNietzsche);

        yandexEducationSubtask2.setStatus(Status.DONE);
        taskManager.updateSubtask(yandexEducationSubtask2);
        System.out.println(yandexEducationSubtask2);


        conference1.setStatus(Status.DONE);
        taskManager.updateSubtask(conference1);
        System.out.println(conference1);



        TaskManager.deleteTaskByID(crossfitTraining.getId());
        TaskManager.deleteTaskByID(yandexEducation.getId());


    }

    private static void printAllTasks(TaskManager manager) {
        System.out.println("Задачи:");
        for (Task task : manager.getAllTasks()) {
            System.out.println(task);
            System.out.println("Эпики:");
            for (Epic epic : manager.getAllEpics()) {
                System.out.println(epic);
                System.out.println("Все подзадачи:");
                for (Subtask subtask : manager.getAllSubtasks()) {
                    System.out.println(subtask);
                }


            }
        }
    }
}