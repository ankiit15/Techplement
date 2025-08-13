import java.util.*;

class Question {
    String questionText;
    List<String> options;
    int correctAnswerIndex;

    public Question(String questionText, List<String> options, int correctAnswerIndex) {
        this.questionText = questionText;
        this.options = options;
        this.correctAnswerIndex = correctAnswerIndex;
    }
}

public class Quiz {
    private static Scanner scanner = new Scanner(System.in);
    private static Map<String, List<Question>> quizzes = new HashMap<>();

    public static void main(String[] args) {
        System.out.println("=== Quiz Generator CLI ===");
        showHelp();

        while (true) {
            System.out.print("\n> Enter command: ");
            String command = scanner.nextLine().trim().toLowerCase();

            switch (command) {
                case "create":
                    createQuiz();
                    break;
                case "add":
                    addQuestion();
                    break;
                case "take":
                    takeQuiz();
                    break;
                case "help":
                    showHelp();
                    break;
                case "exit":
                    System.out.println("Exiting... Goodbye!");
                    return;
                default:
                    System.out.println("Invalid command. Type 'help' for commands.");
            }
        }
    }

    private static void createQuiz() {
        System.out.print("Enter quiz name: ");
        String quizName = scanner.nextLine().trim();
        if (quizzes.containsKey(quizName)) {
            System.out.println("❌ Quiz already exists!");
            return;
        }
        quizzes.put(quizName, new ArrayList<>());
        System.out.println("✅ Quiz '" + quizName + "' created!");
    }

    private static void addQuestion() {
        System.out.print("Enter quiz name: ");
        String quizName = scanner.nextLine().trim();
        if (!quizzes.containsKey(quizName)) {
            System.out.println("❌ Quiz not found!");
            return;
        }

        System.out.print("Enter question: ");
        String questionText = scanner.nextLine();
        List<String> options = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            System.out.print("Enter option " + i + ": ");
            options.add(scanner.nextLine());
        }
        System.out.print("Enter correct option number (1-4): ");
        int correctIndex = Integer.parseInt(scanner.nextLine()) - 1;

        quizzes.get(quizName).add(new Question(questionText, options, correctIndex));
        System.out.println("✅ Question added to quiz '" + quizName + "'.");
    }

    private static void takeQuiz() {
        System.out.print("Enter quiz name: ");
        String quizName = scanner.nextLine().trim();
        if (!quizzes.containsKey(quizName)) {
            System.out.println("❌ Quiz not found!");
            return;
        }

        List<Question> questions = quizzes.get(quizName);
        if (questions.isEmpty()) {
            System.out.println("⚠️ No questions in this quiz!");
            return;
        }

        int score = 0;
        for (int i = 0; i < questions.size(); i++) {
            Question q = questions.get(i);
            System.out.println("\nQ" + (i + 1) + ": " + q.questionText);
            for (int j = 0; j < q.options.size(); j++) {
                System.out.println((j + 1) + ". " + q.options.get(j));
            }
            System.out.print("Your answer (1-4): ");
            int answer = Integer.parseInt(scanner.nextLine()) - 1;

            if (answer == q.correctAnswerIndex) {
                System.out.println("✅ Correct!");
                score++;
            } else {
                System.out.println("❌ Wrong! Correct answer: " + q.options.get(q.correctAnswerIndex));
            }
        }
        System.out.println("\n🎯 Your Score: " + score + "/" + questions.size());
    }

    private static void showHelp() {
        System.out.println("""
        Available Commands:
        create - Create a new quiz
        add    - Add a question to a quiz
        take   - Take a quiz
        help   - Show this help message
        exit   - Exit the application
        """);
    }
}
