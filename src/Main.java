import java.time.LocalDateTime;
import java.util.Scanner;

public class Main extends TaskService {
    public static void main(String[] args) throws IncorrectArgumentException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Вас приветствует ежедневник Светланы Морозовой! Что будем делать?");
        mainMenu();

    }

    public static void mainMenu() {
        System.out.println("1 - Добавить новую задачу;\n" +
                "2 - Редактировать задачу;\n" +
                "3 - Удалить/завершить задачу;\n" +
                "4 - Получить список задач на (дату);\n" +
                "5 - Получить календарь задач;\n" +
                "6 - Получить список всех удаленных/завершенных задач;\n" +
                "7 - Завершить работу программы;\n");
        int number = scanner.nextInt();
        switch (number) {
            case 1:
                System.out.println("Выбрано: добавить новую задачу");
                add();
                System.out.println("Новая задача успешно создана. Выберите следующее действие:");
                mainMenu();
            case 2:
                System.out.println("Выбрано: редактировать задачу");
                System.out.println("Введите id задачи: ");
                int idNumber = scanner.nextInt();
                editTask(idNumber);
            case 3:
                System.out.println("Выбрано: удалить/завершить задачу");
                System.out.println("Введите id задачи: ");
                int idNumberDel = scanner.nextInt();
                TaskService.remove(idNumberDel);
                System.out.println("Задача id=" +idNumberDel+" успешно удалена и перенесена в список завершенных задач. Выберите следующее действие:");
                mainMenu();
            case 4:
                System.out.println("Выбрано: получить список задач на дату");
                System.out.println("Выберите на какую дату получить список задач: " +
                        "1 - Сегодня;" +
                        "2 - Завтра;" +
                        "3 - Произвольная дата;");
                int dateNumber = scanner.nextInt();
                switch (dateNumber) {
                    case 1:
                        System.out.println("Список задач на СЕГОДНЯ:");
                        System.out.println(TaskService.getAllByDate(LocalDateTime.now()));
                        mainMenu();
                    case 2:
                        System.out.println("Список задач на ЗАВТРА:");
                        System.out.println(TaskService.getAllByDate(LocalDateTime.now().plusDays(1)));
                        mainMenu();
                    case 3:
                        System.out.println("Слишком неопределенно... Возможно доработаю :)");
                        mainMenu();
                    default:
                        System.out.println("Выбрано неверное значение, поэтому вот список всех задач сгруппированных по дате:");
                        System.out.println(TaskService.getAllGroupeByDate());
                }
            case 5:
                System.out.println("Полный календарь всех задач, сгруппированных по дате:");
                System.out.println(TaskService.getAllGroupeByDate());
                mainMenu();
            case 6:
                System.out.println("Полный список всех удаленных/завершенных задач:");
                System.out.println(TaskService.getRemovedTasks());
                mainMenu();
            case 7:
                System.out.println("ДО СВИДАНИЯ! Возвращайтесь скорее!");
                scanner.close();
                break;
            default:
                System.out.println("Неверное значение! Попробуйте еще раз");
                mainMenu();
                break;
        }
    }
    public static void editTask(int idNumber) {
        System.out.println("Что будем редактировать:" +
                "1 - Заголовок;\t" +
                "2 - Описание;\t" +
                "3 - Тип задачи: Личная/Рабочая;\t" +
                "4 - Повторяемость;\n" +
                "5 - Завершить редактирование / Выход в главное меню");
        int editNumber = scanner.nextInt();

        switch (editNumber) {
            case 1:
                System.out.println("Введите новый заголовок:");
                String title = scanner.next();
                updateTitle(idNumber, title);
                editTask(idNumber);
            case 2:
                System.out.println("Введите новое описание задачи:");
                String description = scanner.next();
                updateDescription(idNumber, description);
                editTask(idNumber);
            case 3:
                System.out.println("Текущий тип задачи: " + TaskService.getTaskMap().get(idNumber).getType() + ". Выберите тип задачи:\n" +
                        "1 - Личная;\t" +
                        "2 - Рабочая");
                TypeTask type = TaskService.setTypeTask();
                updateTypeTask(idNumber, type);
                editTask(idNumber);
            case 4:
                System.out.println("Текущий тип повторяемости задачи: " + TaskService.getTaskMap().get(idNumber).getAppearsIn() + ". Выберите повторяемость задачи:\n" +
                        "1 - Разовая (однократная);\t" +
                        "2 - Ежедневная;\t" +
                        "3 - Еженедельная;\t" +
                        "4 - Ежемесячная;\t" +
                        "5 - Ежегодная;\t"
                );
                AppearsIn appearsIn = TaskService.setAppearsIn();
                updateRepeatTask(idNumber, appearsIn);
                editTask(idNumber);
            case 5:
                System.out.println("Редактирование задачи завершено");
                mainMenu();
            default:
                System.out.println("Выбрано некорректное значение, редактирование задачи завершеное. Выход в Главное меню");
                mainMenu();
        }

    }

    public static int inputInt() throws IncorrectArgumentException {
        Scanner scannerInput = new Scanner(System.in);
        try {
            int input = scannerInput.nextInt();
            return input;
        }
        catch (RuntimeException e) {
            throw new IncorrectArgumentException("Введено нечисловое значение");
        }
        finally {
            scannerInput.close();
        }
    }
}