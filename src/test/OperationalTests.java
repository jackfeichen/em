package test;

import java.util.*;

/**
 */
public class OperationalTests {
    public void TestUpdateHashSet() {
        Set<Integer> hash = new HashSet<Integer>(Arrays.asList(1,2,3,4,5,6,7,8,9,10));
        System.out.printf("Hash count: %d\n", hash.size());
        hash.remove(9);
        hash.remove(5);
        System.out.printf("Updated hash count: %d\n", hash.size());
    }

    public void TestUpdateListOfSets() {
        List<Set<Integer>> hashes = new ArrayList<Set<Integer>>();
        hashes.add(new HashSet<Integer>(Arrays.asList(1,2,3)));
        System.out.printf("Size of original hash: %d\n", hashes.get(0).size());
        hashes.get(0).addAll(Arrays.asList(4,5,6));
        System.out.printf("Size of updated hash: %d\n", hashes.get(0).size());
    }

    public void TestUpdateListWithRandom() {
        List<Integer> list = new ArrayList<Integer>(100);
        for(int i=0; i<100; i++) list.add(i);
        System.out.printf("original list size: %d\n", list.size());
        List<Integer> a = getRandom(list, 10);
        System.out.printf("size of a is: %d\n", a.size());
        System.out.printf("updated list size: %d\n", list.size());
        List<Integer> b = getRandom(list, 20);
        System.out.printf("size of a is: %d\n", a.size());
        System.out.printf("size of b is: %d\n", b.size());
        System.out.printf("updated list size: %d\n", list.size());
    }

    private List<Integer> getRandom(List<Integer> list, int count) {
        Random random = new Random(1000);
        List<Integer> result = new ArrayList<Integer>(count);
        int index;
        for(int i=0; i<count; i++) {
            index = random.nextInt(list.size());
            result.add(list.remove(index));
        }
        return result;
    }

    
    public static void main(String[] args) {
        OperationalTests tests = new OperationalTests();
//        tests.TestUpdateHashSet();
//        tests.TestUpdateListOfSets();
        tests.TestUpdateListWithRandom();
    }
}
