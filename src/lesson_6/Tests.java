package lesson_6;

import java.util.*;
import java.util.stream.Collectors;

public class Tests {
    public static void main(String[] args) {
         test_1();
        test_2();
        test_3();
        test_4();
        test_5();
        test_6();
        test_7();
        test_8();
        test_9();
    }
    /**
     * Получить List чисел в виде текстовых элементов
     */
    public static void test_1() {
        List<Integer> integerList = getIntList();
    integerList.forEach(System.out::println);
    }

    /**
     * Отсортировать список по убыванию
     */
    public static void test_2() {
        List<Integer> integerList = getIntList();
        List sortedList = getIntList().stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        sortedList.forEach(System.out::println);
    }

    /**
     * Получить одну строку текста. Каждый элемент должен начинаться с текста "Number - ".
     * Элементы должны разделяться запятой и пробелом.
     * В начале итоговой строки должен быть текст "Number list: "
     * В конце итоговой строки должен быть текст "end of list.".
     */
    public static void test_3() {
        List<String> stringList = getStringList();
        String stringListline = getStringList().stream().collect(Collectors.joining(", Number - ", "Number list: Number - ", " end of list."));
        System.out.println(stringListline);
    }

    /**
     * Получить мапу со значениями, ключи которых больше трех и меньше девяти
     */
    public static void test_4() {
        Map<Integer, String> map = getMap();
        Map sortedMap = getMap().entrySet().stream().filter(a->a.getKey()>3 && a.getKey()<9).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        System.out.println(sortedMap);
    }

    /**
     * Перемешать все элементы в мапе.
     * Пример результата:
     * Элемент 1: ключ - 5, значение "five"
     * Элемент 2: ключ - 7, значение "seven"
     * Элемент 3: ключ - 2, значение "two"
     * и так далее.
     */
    public static void test_5() {
        final Map<Integer, String> map = getMap();
        List<Integer> keys = new ArrayList<>(getMap().keySet());
        Collections.shuffle(keys);
        System.out.println(map);
        var sortedMap = new LinkedHashMap<>();
        keys.forEach(key->sortedMap.put(key, map.get(key)));
        System.out.println(sortedMap);


    }

    /**
     * Установить во всех элементах isDisplayed = true, и оставить в списке только элементы с value NULL.
     */
    public static void test_6() {
        var elements = getElements();
        System.out.println(elements);
        List<WebElement> res = elements.stream().filter(webElement -> webElement.getValue() == null)
                .collect(Collectors.toList());
        res.forEach(webElement -> webElement.setDisplayed(true));
        System.out.println(res);
    }

    /**
     * Инвертировать isDisplayed параметр каждого элемента и отсортировать список по типу элемента
     * со следующим приоритетом:
     * 1. TEXT
     * 2. INPUT_FIELD
     * 3. CHECKBOX
     * 4. BUTTON
     * 5. RADIO_BUTTON
     * 6. IMAGE
     */
    public static void test_7() {
        Map<Type, Integer> typeToOrderMap = Map.of(
                Type.TEXT,1,
                Type.INPUT_FIELD,2,
                Type.CHECKBOX,3,
                Type.BUTTON,4,
                Type.RADIO_BUTTON,5,
                Type.IMAGE,6
        );
        List<WebElement> elements = getElements()
                .stream().sorted(Comparator.comparingInt((webElement)->typeToOrderMap.get(webElement.getType()))).collect(Collectors.toList());
        elements.forEach(webElement -> webElement.setDisplayed(!webElement.isDisplayed()));
        elements.forEach(webElement -> System.out.println(webElement.getType()));

    }

    /**
     * Создать мапу:
     * ключ - текст
     * значение - тип элемента
     */
    public static void test_8() {
        Map<String, Type> textToTypeMap = new HashMap<>();
        getElements().stream().filter(webElement -> webElement.getText() !=null).forEach(webElement -> {
            if (!textToTypeMap.containsKey(webElement.getText())) {
            textToTypeMap.put(webElement.getText(), webElement.getType());
            }
        });
        System.out.println(textToTypeMap);
    }

    /**
     * Получить список элементов, у которых текст или значение оканчивается на число от 500 и более.
     * И отсортировать по увеличению сначала элементы с текстом, а затем по убыванию элементы со значением.
     */
    public static void test_9() {
        var elements = getElements();
        var textElements = elements.stream()
                .filter(webElement -> webElement.getText() != null)
                .filter(el -> extractInt(el.getText()) > 500)
                .sorted(Comparator.comparing(webElement -> extractInt(webElement.getText()), Comparator.naturalOrder()))
                .collect(Collectors.toList());
        var valElement = elements.stream()
                .filter(webElement -> webElement.getValue() != null)
                .filter(el -> extractInt(el.getValue()) > 500)
                .sorted(Comparator.comparing(webElement -> extractInt(webElement.getValue()), Comparator.reverseOrder()))
                .collect(Collectors.toList());

        var res = new ArrayList<>();
        res.addAll(textElements);
        res.addAll(valElement);
        System.out.println();
    }
    private static int extractInt(String val){
        return Integer.parseInt(val.substring(val.length() - 3).replaceAll("\\D+|\\s+",""));
    }

    public static Map<Integer, String> getMap() {
        Map<Integer, String> map = new HashMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");
        map.put(4, "four");
        map.put(5, "five");
        map.put(6, "six");
        map.put(7, "seven");
        map.put(8, "eight");
        map.put(9, "nine");
        map.put(10, "ten");
        return map;
    }

    public static List<String> getStringList() {
        List<String> list = new ArrayList<>();
        list.add("one");
        list.add("two");
        list.add("three");
        list.add("four");
        list.add("five");
        list.add("six");
        list.add("seven");
        list.add("one");
        list.add("nine");
        list.add("ten");
        return list;
    }

    public static List<Integer> getIntList() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        list.add(6);
        list.add(7);
        list.add(8);
        list.add(9);
        list.add(10);
        return list;
    }

    public static List<WebElement> getElements() {
        List<WebElement> result = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            result.add(new WebElement());
        }
        return result;
    }
}
