import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;
        List<Task> taskList = new ArrayList<>();

        do {
            menu();
            System.out.println("Escolha uma opção: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Digite o título da tarefa:");
                    scanner.nextLine();
                    String title = scanner.nextLine();
                    boolean valid = Validators.validTitle(title);
                    if (!valid) break;
                    System.out.println("Digite a descrição (opcional):");
                    String description = scanner.nextLine();
                    System.out.println("Digite a data de vencimento (dd/MM/yyyy):");
                    String date = scanner.nextLine();
                    try {
                        Validators.validDate(date);
                    } catch (DateTimeParseException exception) {
                        System.err.println("Data inválida: " + exception.getMessage());
                        break;
                    }
                    TaskService.adicionarTarefas(new Task(title, description, date), taskList);
                    System.out.println("Tarefa adicionada!");
                    break;
                case 2:
                    TaskService.listarTarefas(taskList);
                    break;
                case 3:
                    System.out.println("Digite o número da tarefa para concluir:");
                    int id = scanner.nextInt();
                    TaskService.finalizarTarefa(id, taskList);
                    System.out.printf("Tarefa %d concluída!", id);
                    break;
                case 4:
                    System.out.println("Digite o número da tarefa para concluir:");
                    int idRemove = scanner.nextInt();
                    TaskService.removerTarefa(idRemove, taskList);
                    System.out.printf("Tarefa %d removida!\n", idRemove);
                    break;
                case 5:
                    exit = true;
                    System.out.println("Saindo...");
                    break;
            }
        } while (!exit);


        scanner.close();
    }

    public static void menu() {
        System.out.println("=== Gerenciador de Tarefas ===\n" +
                "\n" +
                "1 - Adicionar tarefa\n" +
                "2 - Listar tarefas\n" +
                "3 - Marcar tarefa como concluída\n" +
                "4 - Remover tarefa\n" +
                "5 - Sair");
    }

    public static class Validators {

        public static boolean validTitle(String title) {
            if (title == null || title.isEmpty()) {
                System.err.println("Título nulo ou vazio. Insira novamente!");
                return false;
            }
            return true;
        }

        public static LocalDate validDate(String date) throws DateTimeParseException {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return LocalDate.parse(date, dateTimeFormatter);
        }
    }

    public static class TaskService {

        //adicionar
        public static void adicionarTarefas(Task task, List<Task> taskList) {
            taskList.add(task);
        }

        //listar
        public static void listarTarefas(List<Task> taskList) {
            taskList.forEach(task -> System.out.println(task.toString()));

        }

        //finalizar
        public static void finalizarTarefa(int id, List<Task> taskList) {
            for (Task task : taskList) {
                if (task.getId() == id) {
                    task.setTaskStatus(TaskStatus.DONE);
                }
            }

        }

        //remover
        public static void removerTarefa(int id, List<Task> taskList) {
            if (taskList != null) {
                taskList.removeIf(task -> task.getId() == id);
            }
        }
    }

    public static class Task {

        private int id;
        private String title;
        private String description;
        private String date;
        private TaskStatus taskStatus;
        private static int countId = 1;

        public Task(String title, String description, String date) {
            this.id = countId++;
            this.title = title;
            this.description = description;
            this.date = date;
            this.taskStatus = TaskStatus.DOING;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public TaskStatus getTaskStatus() {
            return taskStatus;
        }

        public void setTaskStatus(TaskStatus taskStatus) {
            this.taskStatus = taskStatus;
        }

        @Override
        public String toString() {
            return "Task{" +
                    "id=" + id +
                    ", title='" + title + '\'' +
                    ", description='" + description + '\'' +
                    ", date='" + date + '\'' +
                    ", taskStatus=" + taskStatus +
                    '}';
        }
    }

    public enum TaskStatus {
        DOING, DONE
    }

}