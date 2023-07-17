public class Main {
    public static void main(String[] args)
    {
        SkipListSet<Integer> integers = new SkipListSet<>();
        integers.add(5);
        System.out.println(integers.tail);
    }

}
