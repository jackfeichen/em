package classifier;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * User: jackchen
 * This class is used to randomly generate splits for Leave-One-Out-Cross-Validation (LOOCV)
 * It requires properties for the following:
 *  # of labeled documents per group
 *  % of documents to save for testing
 * The result of this generator will provide the following:
 *  List of labeled documents for training
 *  List of unlabeled documents for training (EM only)
 *  List of reserved test documents
 */
public class SplitGenerator {
    private String path;
    private long seed;
    private int topicCount;
    private int labeledCount;
    private int unlabeledCount = 500;
    private double testPercentage;
    private List<List<Integer>> labeledDocs;
    private List<List<Integer>> unlabeledDocs;
    private List<List<Integer>> testDocs;

    public SplitGenerator(String path, long seed, int labeledCount, double testPercentage) {
        this.path = path;
        this.seed = seed;
        this.labeledCount = labeledCount;
        this.testPercentage = testPercentage;
        this.labeledDocs = new ArrayList<List<Integer>>();
        this.unlabeledDocs = new ArrayList<List<Integer>>();
        this.testDocs = new ArrayList<List<Integer>>();
    }

    public void Generate() {
        int testCount;
        List<Integer> files = getFileCounts(path);
        this.topicCount = files.size();
        for(int i=0; i<topicCount; i++) {
            ArrayList<Integer> topic = mapFileCounts(files.get(i));
            // first, set aside the test data
            testCount = (int) (testPercentage * topic.size());
            testDocs.add(getRandom(topic, testCount));
            // next, set the unlabeled files
            unlabeledDocs.add(getRandom(topic, unlabeledCount));
            // finally, obtain all the labeled counts
            labeledDocs.add(getRandom(topic, labeledCount));
        }
    }

    private List<Integer> getRandom(List<Integer> list, int count) {
        Random random = new Random(seed);
        List<Integer> result = new ArrayList<Integer>(count);
        int index;
        for(int i=0; i<count; i++) {
            index = random.nextInt(list.size());
            result.add(list.remove(index));
        }
        return result;
    }

    private static int max = 1200;
    private static Integer[] range = new Integer[max];
    static {
        for(int i=0; i<max; i++) range[i] = i;
    }
    private static ArrayList<Integer> mapFileCounts(int count) {
        return new ArrayList<Integer>(Arrays.asList(Arrays.copyOf(range, count)));
    }
    private static List<Integer> getFileCounts(String path) {
        List<Integer> result = new ArrayList<Integer>();

        File data = new File(path);
        for(File topic : data.listFiles())
            result.add(topic.list().length);
        return result;
    }

    /**
     * Main entry point into the generator
     * @param args
     */
    public static void main(String[] args) {
        // 4 arguments for path, count, percentage, and seed
        SplitGenerator split = new SplitGenerator("../../../data", 100, 15, 0.2);
        split.Generate();
    }
}
