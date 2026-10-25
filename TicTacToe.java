import java.util.Random;

/**
 * Игра "Крестики-нолики" (Tic Tac Toe)
 * Двухигрокская консольная игра.
 * 
 * Правила:
 * - Двое игроков по очереди ставят свои знаки на поле 3x3.
 * - Игрок 1 ставит "X", игрок 2 ставит "O".
 * - Первый, собравший 3 своих знака в ряд, выигрывает.
 * - Если все клетки заняты и победителя нет — ничья.
 * 
 * Управление:
 *   Введите номер клетки (1-9) для хода, или:
 *   's' — сделать случайный ход для тестирования.
 *   'q' — выйти из игры.
 */
public class TicTacToe {
    private static final int SIZE = 3;
    private static final int TOTAL_CELLS = SIZE * SIZE;
    private char[][] board;
    private char currentPlayer = 'X'; // 'X' — ход игрока 1, 'O' — игрока 2
    private boolean gameActive = true;
    private boolean isRandomMode = false;
    private Random random = new Random();

    public TicTacToe() {
        board = new char[SIZE][SIZE];
        initializeBoard();
    }

    /**
     * Инициализирует игровое поле символами ' ' (порожние клетки).
     */
    private void initializeBoard() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                board[i][j] = ' ';
            }
        }
    }

    /**
     * Выводит игровое поле на консоль.
     */
    private void printBoard() {
        System.out.println("  1 | 2 | 3");
        System.out.println("-----------");
        System.out.println(" " + board[0][0] + " | " + board[0][1] + " | " + board[0][2]);
        System.out.println("-----------");
        System.out.println(" " + board[1][0] + " | " + board[1][1] + " | " + board[1][2]);
        System.out.println("-----------");
        System.out.println(" " + board[2][0] + " | " + board[2][1] + " | " + board[2][2]);
        System.out.println("  1 | 2 | 3");
    }

    /**
     * Преобразует номер клетки (1-9) в координаты массива.
     * 1->(0,0), 2->(0,1), 3->(0,2), 4->(1,0), ... 9->(2,2)
     */
    private int[] getCoordinatesFromNumber(int cellNumber) {
        int row = (cellNumber - 1) / 3;
        int col = (cellNumber - 1) % 3;
        return new int[]{row, col};
    }

    /**
     * Проверяет, что клетка занята ли.
     */
    private boolean isCellOccupied(int row, int col) {
        return board[row][col] != ' ';
    }

    /**
     * Проверяет, является ли клетка валидной (в пределах поля).
     */
    private boolean isValidCell(int row, int col) {
        return row >= 0 && row < SIZE && col >= 0 && col < SIZE;
    }

    /**
     * Выполняет ход текущего игрока.
     */
    private void makeMove(int cellNumber) {
        int[] coords = getCoordinatesFromNumber(cellNumber);
        int row = coords[0];
        int col = coords[1];

        if (isValidCell(row, col) && !isCellOccupied(row, col)) {
            board[row][col] = currentPlayer;

            if (checkWin(currentPlayer)) {
                System.out.println("🎉 " + currentPlayer + " победил!");
                System.out.println("🏆 Поздравляем, " + (currentPlayer == 'X' ? "игрок 1" : "игрок 2") + " победил!");
                gameActive = false;
            } else if (checkDraw()) {
                System.out.println("🤝 Ничья!");
                System.out.println("Все клетки заняты, победителя нет.");
                gameActive = false;
            } else {
                System.out.println("Ход игрока " + currentPlayer + " записан.");
                swapPlayer();
                printBoard();
            }
        } else {
            System.out.println("❌ Неверный ход. Клетка занята или номер не верный. Попробуйте снова.");
        }
    }

    /**
     * Меняет игрока.
     */
    private void swapPlayer() {
        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
        System.out.println("Теперь ходит: " + currentPlayer + " (игрок " + (currentPlayer == 'X' ? "1" : "2") + ")");
    }

    /**
     * Проверяет выигрыш.
     */
    private boolean checkWin(char player) {
        // Проверка строк
        for (int i = 0; i < SIZE; i++) {
            if (board[i][0] == player && board[i][1] == player && board[i][2] == player) {
                return true;
            }
        }
        // Проверка столбцов
        for (int j = 0; j < SIZE; j++) {
            if (board[0][j] == player && board[1][j] == player && board[2][j] == player) {
                return true;
            }
        }
        // Проверка главной диагонали
        if (board[0][0] == player && board[1][1] == player && board[2][2] == player) {
            return true;
        }
        // Проверка побочной диагонали
        if (board[0][2] == player && board[1][1] == player && board[2][0] == player) {
            return true;
        }
        return false;
    }

    /**
     * Проверяет ничью.
     */
    private boolean checkDraw() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == ' ') {
                    return false; // Есть свободная клетка — игра не закончена
                }
            }
        }
        return true; // Все клетки заняты
    }

    /**
     * Делает случайный ход (для тестов/тренировки).
     */
    private void randomMove() {
        boolean moved = false;
        while (!moved) {
            int row = random.nextInt(SIZE);
            int col = random.nextInt(SIZE);
            if (!isCellOccupied(row, col)) {
                board[row][col] = currentPlayer;
                moved = true;
            }
        }
        if (checkWin(currentPlayer)) {
            System.out.println("🎉 " + currentPlayer + " победил случайно!");
            gameActive = false;
        } else if (checkDraw()) {
            System.out.println("🤝 Ничья!");
            gameActive = false;
        } else {
            System.out.println("Рандомный ход записан.");
            swapPlayer();
            printBoard();
        }
    }

    /**
     * Основной цикл игры.
     */
    public static void main(String[] args) {
        TicTacToe game = new TicTacToe();

        System.out.println("╔══════════════════════════════════════════════╗");
        System.out.println("║         🎮 КРЕСТИКИ-НОЛИКИ 🎮               ║");
        System.out.println("║══════════════════════════════════════════════║");
        System.out.println("║ Правила:                                      ║");
        System.out.println("║   Игрок 1 ставит 'X', Игрок 2 ставит 'O'.   ║");
        System.out.println("║   Собирайте 3 знака в ряд — победа!          ║");
        System.out.println("╚══════════════════════════════════════════════╝");
        System.out.println();

        game.printBoard();

        while (game.gameActive) {
            System.out.println("\nВведите номер клетки (1-9) для хода:");
            System.out.println("  's' — случайный ход (для теста)");
            System.out.println("  'q' — выйти из игры");
            System.out.println("  'h' — правила игры");

            System.out.print("\nВаш выбор: ");

            // Читаем ввод пользователя
            java.util.Scanner scanner = new java.util.Scanner(System.in);
            String input = scanner.nextLine().trim();

            if (input.equals("q")) {
                System.out.println("👋 До свидания! Хорошей игры!");
                game.gameActive = false;
            } else if (input.equals("h")) {
                System.out.println("\nПравила:");
                System.out.println("  - Игра для двоих.");
                System.out.println("  - Игрок 1 ходит 'X', Игрок 2 — 'O'.");
                System.out.println("  - Победитель — первый, собравший 3 знака в ряд.");
                System.out.println("  - Если все клетки заняты без победителя — ничья.");
                System.out.println("  - Введите номер клетки 1-9 (сверху вниз, слева направо).");
                System.out.println("  - Введите 's' для случайного хода.");
                System.out.println("  - Введите 'q' для выхода.");
                System.out.println();
            } else if (input.equals("s")) {
                System.out.println("🎲 Случайный ход:");
                game.randomMove();
            } else {
                // Пытаемся преобразовать ввод в int
                try {
                    int cellNumber = Integer.parseInt(input);
                    if (cellNumber >= 1 && cellNumber <= 9) {
                        game.makeMove(cellNumber);
                    } else {
                        System.out.println("❌ Введите число от 1 до 9.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("❌ Неверный формат ввода. Попробуйте снова.");
                }
            }

            if (game.gameActive) {
                System.out.println();
            }
        }
    }
}
