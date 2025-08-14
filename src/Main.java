import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        int id = 1;
        boolean exit = false;
        Scanner scanner = new Scanner(System.in);

        List<Tarefa> tarefas = new ArrayList<>();
        do {

            System.out.println("=== Gerenciador de Tarefas ===");
            System.out.println("1. Adicionar tarefa");
            System.out.println("2. Listar tarefas");
            System.out.println("3. Marcar tarefa como concluída");
            System.out.println("4. Remover tarefa");
            System.out.println("5. Sair");
            System.out.println("Escolha uma opção: ");
            int choiceNumber = scanner.nextInt();

            switch (choiceNumber) {
                case 1:
                    System.out.println("Digite o título da tarefa:");
                    String title = scanner.next();
                    System.out.println("Digite a descrição (opcional):");
                    String description = scanner.next();
                    System.out.println("Digite a data de vencimento (dd/MM/yyyy):");
                    String date = scanner.next();
                    System.out.println("Tarefa adicionada com sucesso!");

                    TarefaUtils.adicionarTarefas(new Tarefa(id, title, description, date), tarefas);
                case 2:
                    TarefaUtils.listarTarefas(tarefas);
                case 3:
                    TarefaUtils.finalizarTarefa(id, tarefas);
                case 4:
                    TarefaUtils.removerTarefa(tarefas, id);
                case 5:
                    exit = TarefaUtils.exit();
                    break;
            }
            id++;
        } while (exit);

        scanner.close();

    }


    public static class TarefaUtils {

        public static void adicionarTarefas(Tarefa tarefa, List<Tarefa> tarefas) {
            tarefas.add(tarefa);
        }

        public static void listarTarefas(List<Tarefa> tarefas) {
            for (Tarefa tarefa : tarefas) {
                System.out.println(tarefa.toString());
                System.out.println();
            }
        }

        public static void removerTarefa(List<Tarefa> listaTarefas, int id) {
            listaTarefas.removeIf(tarefa -> tarefa.getId() == id);
        }

        public static void finalizarTarefa(int id, List<Tarefa> tarefas) {
            for (Tarefa tarefa : tarefas) {
                if (tarefa.getId() == id) {
                    tarefa.setStatusDaTarefa(StatusDaTarefa.DONE);
                }
            }
        }

        public static boolean exit() {
            return true;
        }

    }

    public static class Tarefa {
        private int id;
        private String title;
        private String description;
        private String localDate;
        private StatusDaTarefa statusDaTarefa;

        public Tarefa() {
        }

        public Tarefa(int id, String title, String description, String localDate) {
            this.id = id;
            if (title != null && !title.isEmpty()) {
                try {
                    this.title = title;
                } catch (TitleException exception) {
                    System.err.println("Titulo não pode ser nulo ou vazio!" + exception.getMessage());
                }
            }

            this.description = description;
            this.localDate = localDate;
            this.statusDaTarefa = StatusDaTarefa.PENDENTE;
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

        public String getDescription() {
            return description;
        }

        public String getLocalDate() {
            return localDate;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public void setLocalDate(String localDate) {
            this.localDate = localDate;
        }

        public StatusDaTarefa getStatusDaTarefa() {
            return statusDaTarefa;
        }

        public void setStatusDaTarefa(StatusDaTarefa statusDaTarefa) {
            this.statusDaTarefa = statusDaTarefa;
        }

        @Override
        public String toString() {
            return "Tarefa{" +
                    "id='" + id + '\'' +
                    ", title='" + title + '\'' +
                    ", description='" + description + '\'' +
                    ", localDate='" + localDate + '\'' +
                    ", statusDaTarefa=" + statusDaTarefa +
                    '}';
        }
    }

    public enum StatusDaTarefa {
        PENDENTE, DONE
    }

    public static class TitleException extends RuntimeException {
        int id;
        String msg;

        public TitleException(String message, int id, String msg) {
            super(message);
            this.id = id;
            this.msg = msg;
        }
    }
}