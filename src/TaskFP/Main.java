package TaskFP;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Напишите приложение, которое на входе получает через консоль текст, а в ответ выдает статистику:\n" +
                "1. Количество слов в тексте.\n" +
                "2. Топ-10 самых часто упоминаемых слов, упорядоченных по количеству упоминаний в обратном порядке. В случае одинакового количества упоминаний слова должны быть отсортированы по алфавиту.");
        System.out.println("Введи текст для обработки:");
        String text = scanner.useDelimiter("\n").next();
        List<String> textList = new ArrayList<>(List.of(text.split(" ")));

        Comparator<Integer> reverseOrder = (o1, o2) -> (o2 - o1);

        Map<String, Integer> statistics = new TreeMap<>(String::compareTo);
        for (String word : textList) {
            if (statistics.containsKey(word)) {
                int numWord = statistics.get(word) + 1;
                statistics.put(word, numWord);
            } else {
                statistics.put(word, 1);
            }
        }

        System.out.println("Количество слов в тексте:" + statistics.keySet().size());


        Map<Integer, List<String>> statisticsList = new TreeMap<>(reverseOrder);
        for (Map.Entry<String, Integer> entry : statistics.entrySet()) {
            Integer value = entry.getValue();
            String key = entry.getKey();
            if (statisticsList.containsKey(value)) {
                List<String> newTempList = new ArrayList<>(statisticsList.get(value));
                newTempList.add(key);
                newTempList.sort(String::compareToIgnoreCase);
                statisticsList.put(value, newTempList);
            } else {
                statisticsList.put(value, List.of(key));
            }
        }

        System.out.println("Полная статистика упоминаний слов:\n" + statisticsList);
        List<String> topWord = new ArrayList<>();

        for (Map.Entry<Integer, List<String>> entry : statisticsList.entrySet()) {
            Integer key = entry.getKey();
            List<String> value = entry.getValue();
            for (String word : value) {
                topWord.add(key + " - " + word);
            }
        }

        System.out.println("\nTOP-10 слов:");
        topWord.subList(0, topWord.size() > 9 ? 10 : topWord.size()).forEach(System.out::println);
    }
}