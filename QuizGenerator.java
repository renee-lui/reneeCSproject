import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class QuizGenerator {
    private static List<List<Question>> pastQuizzes = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        List<Question> questionBank = new ArrayList<>();

        System.out.println("Welcome to the Quiz Generator!");

        while (true) {
            System.out.println("\nPlease select an option:");
            System.out.println("1. Review past quizzes");
            System.out.println("2. Add new questions");
            System.out.println("3. Start a new quiz");
            System.out.println("4. Exit");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    reviewPastQuizzes();
                    break;
                case 2:
                    addNewQuestions(questionBank, scanner);
                    break;
                case 3:
                    startNewQuiz(questionBank, scanner);
                    break;
                case 4:
                    System.out.println("Exiting...");
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    public static void reviewPastQuizzes() {
        System.out.println("Reviewing past quizzes...");

        if (pastQuizzes.isEmpty()) {
            System.out.println("No past quizzes found.");
        } else {
            for (int i = 0; i < pastQuizzes.size(); i++) {
                List<Question> quiz = pastQuizzes.get(i);
                System.out.println("Quiz " + (i + 1) + ":");
                for (Question question : quiz) {
                    System.out.println(question.getQuestion());
                }
                System.out.println();
            }
        }
    }

    public static void addNewQuestions(List<Question> questionBank, Scanner scanner) {
        System.out.print("Enter the number of questions you want to add: ");
        int numQuestions = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        for (int i = 0; i < numQuestions; i++) {
            System.out.println("\nQuestion " + (i + 1) + ":");
            System.out.print("Enter the question: ");
            String questionText = scanner.nextLine();

            System.out.print("Enter the answer: ");
            String answer = scanner.nextLine();

            Question question = new Question(questionText, answer);
            questionBank.add(question);

            System.out.println("Question added successfully!");
        }
    }

    public static void startNewQuiz(List<Question> questionBank, Scanner scanner) {
        if (questionBank.isEmpty()) {
            System.out.println("No questions available. Please add questions first.");
            return;
        }

        System.out.print("Enter the number of questions for the quiz: ");
        int numQuestions = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        List<Question> quiz = generateQuiz(questionBank, numQuestions);

        System.out.println("Quiz Started!");

        int score = 0;

        for (Question question : quiz) {
            System.out.println(question.getQuestion());
            System.out.print("Enter your answer: ");
            String userAnswer = scanner.nextLine();

            // Compare the user's answer with the correct answer
            if (userAnswer.equalsIgnoreCase(question.getAnswer())) {
                System.out.println("Correct!");
                score++;
            } else {
                System.out.println("Incorrect!");
            }
        }

        System.out.println("Quiz ended. Your score is: " + score + "/" + numQuestions);

        // Save the quiz for later review
        pastQuizzes.add(quiz);
    }

    public static List<Question> generateQuiz(List<Question> questionBank, int numQuestions) {
        if (numQuestions > questionBank.size()) {
            System.out.println("Insufficient questions in the question bank.");
            return new ArrayList<>();
        }

        // Randomly select questions from the question bank to create the quiz
        List<Question> quiz = new ArrayList<>();
        while (quiz.size() < numQuestions) {
            int randomIndex = (int) (Math.random() * questionBank.size());
            Question selectedQuestion = questionBank.get(randomIndex);
            if (!quiz.contains(selectedQuestion)) {
                quiz.add(selectedQuestion);
            }
        }

        return quiz;
    }
}

class Question {
    private String question;
    private String answer;

    public Question(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }
}