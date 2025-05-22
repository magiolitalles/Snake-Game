import java.io.*;
import java.util.*;

public class Ranking {
    private static final String ARQUIVO = "ranking.txt";
    private static final int MAX_SCORES = 5;

    public static List<String[]> lerRanking() {
        List<String[]> scores = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(ARQUIVO))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] partes = linha.trim().split(";", 2);
                if (partes.length == 2) {
                    scores.add(new String[]{partes[0], partes[1]});
                }
            }
        } catch (IOException e) {
            // Arquivo pode não existir ainda, sem problemas
        }
        scores.sort((a, b) -> Integer.parseInt(b[1]) - Integer.parseInt(a[1]));
        if (scores.size() > MAX_SCORES) {
            scores = scores.subList(0, MAX_SCORES);
        }
        return scores;
    }

    public static void salvarScore(String nome, int score) {
        List<String[]> scores = lerRanking();
        scores.add(new String[]{nome, String.valueOf(score)});
        scores.sort((a, b) -> Integer.parseInt(b[1]) - Integer.parseInt(a[1]));
        if (scores.size() > MAX_SCORES) {
            scores = scores.subList(0, MAX_SCORES);
        }
        try (PrintWriter pw = new PrintWriter(new FileWriter(ARQUIVO))) {
            for (String[] s : scores) {
                pw.println(s[0] + ";" + s[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}