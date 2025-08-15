import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Tarefa> tarefas = new ArrayList<>();
        boolean exit = TarefaUtils.sair();
        int idDaTarefa = 0;

        do {
            Menu.principal();
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Digite o título da tarefa: \n");
                    scanner.nextLine();
                    String title = scanner.nextLine();
                    System.out.print("Digite a descrição (opcional): \n");
                    String description = scanner.nextLine();
                    System.out.print("Digite a data de vencimento (dd/MM/yyyy): \n");
                    String date = scanner.nextLine();
                    System.out.print("Tarefa adicionada com sucesso!");
                    TarefaUtils.adicionarTarefa(new Tarefa(title, description, date), tarefas);
                    break;
                case 2:
                    System.out.println("TODAS TAREFAS: ");
                    TarefaUtils.listarTarefas(tarefas);
                    break;
                case 3:
                    System.out.println("Digite o id da tarefa: ");
                    idDaTarefa = scanner.nextInt();
                    TarefaUtils.finalizarTarefa(idDaTarefa, tarefas);
                    System.out.println("Tarefa marcada como concluída!");
                    break;
                case 4:
                    System.out.println("Digite o id da tarefa a ser removida: ");
                    idDaTarefa = scanner.nextInt();
                    TarefaUtils.removerTarefa(idDaTarefa, tarefas);
                    System.out.println("Tarefa removida!");
                    break;
                case 5:
                    exit = true;
                    break;
            }
            idDaTarefa++;

        } while (!exit);

        scanner.close();

    }

    public static class TarefaUtils {
        public static void adicionarTarefa(Tarefa tarefa, List<Tarefa> tarefas) {
            tarefas.add(tarefa);
        }

        public static void listarTarefas(List<Tarefa> tarefas) {
            tarefas.forEach(System.out::println);

        }

        public static void finalizarTarefa(int id, List<Tarefa> tarefas) {
            for (Tarefa tarefa : tarefas) {
                if (id == tarefa.getId()) {
                    tarefa.setStatusDaTarefa(StatusDaTarefa.DONE);
                }
            }
        }

        public static void removerTarefa(int id, List<Tarefa> tarefas) {
            tarefas.removeIf(tarefa -> id == tarefa.getId());
        }

        public static boolean sair() {
            return false;
        }

        public void validateDate(String date) {
            try {
                date = String.valueOf(LocalDateTime.parse(date));
            } catch (DateTimeParseException e) {
                throw new DateTimeParseException("Data inserida está errada!", e.getMessage(), e.getErrorIndex());
            }
        }

    }

    public static class Menu {
        public static void principal() {
            System.out.println(" " +
                    "=== Gerenciador de Tarefas ===\n" +
                    "        1. Adicionar tarefa\n" +
                    "        2. Listar tarefas\n" +
                    "        3. Marcar tarefa como concluída\n" +
                    "        4. Remover tarefa\n" +
                    "        5. Sair\n" +
                    "        Escolha uma opção: \n");


        }
    }

    public static class Tarefa {
        private int id = 0;
        private String title;
        private String description;
        private StatusDaTarefa statusDaTarefa;
        private String date;

        public Tarefa(String title, String description, String date) {
            id++;
            this.title = title;
            this.description = description;
            this.statusDaTarefa = StatusDaTarefa.DOING;
            this.date = date;
        }

        //getters and setters
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

        public StatusDaTarefa getStatusDaTarefa() {
            return statusDaTarefa;
        }

        public void setStatusDaTarefa(StatusDaTarefa statusDaTarefa) {
            this.statusDaTarefa = statusDaTarefa;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        @Override
        public String toString() {
            return "Tarefa{" +
                    "id=" + id +
                    ", title='" + title + '\'' +
                    ", description='" + description + '\'' +
                    ", statusDaTarefa=" + statusDaTarefa +
                    ", date='" + date + '\'' +
                    '}';
        }
    }

    public enum StatusDaTarefa {
        DOING, DONE
    }

}