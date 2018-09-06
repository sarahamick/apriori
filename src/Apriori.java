import java.util.*;


public class Apriori {
	/***
	 * The TRANSACTIONS 2-dimensional array holds the full data set for the lab
	 */
    private int[][] data;
    private int supportThreshold;
    private HashSet<ItemSet> logOfFrequentItemSets;

    public Apriori(int[][] dataset, int supportThreshold){
        this.supportThreshold = supportThreshold;
        data = dataset;
        logOfFrequentItemSets = new HashSet<>();
    }

    public List<ItemSet> start() {

        List<ItemSet> finalFrequentItems = new ArrayList<>();
        int k;
        Hashtable<ItemSet, Integer> frequentItemSets = generateFrequentItemSetsLevel1();

        for (k = 1; frequentItemSets.size() > 0; k++) {

            System.out.print("Finding frequent itemsets of length " + (k + 1) + "...");
            frequentItemSets = generateFrequentItemSets(frequentItemSets);
            finalFrequentItems.addAll(frequentItemSets.keySet());

            System.out.println("...found " + frequentItemSets.size());
        }
        // TODO: create association rules from the frequent itemsets

        return finalFrequentItems;
    }

    private Hashtable<ItemSet, Integer> generateFrequentItemSets(Hashtable<ItemSet, Integer> lowerLevelItemSets) {

        Hashtable<ItemSet, Integer> candidateSets = new Hashtable();

        for(ItemSet item1 : lowerLevelItemSets.keySet()){
            for(ItemSet item2 : lowerLevelItemSets.keySet()){
                ItemSet newItem = joinSets(item1, item2);
                if(newItem!=null) candidateSets.put(newItem, 0);
            }
        }
        //TODO: check here if all subsets of 'item' appear in the log of frequent items?
        Hashtable<ItemSet, Integer> frequentItemSets = new Hashtable<>();
        for(ItemSet item : candidateSets.keySet()){
            int itemCount = countSupport(item.set);
            if(itemCount>=supportThreshold) frequentItemSets.put(item, itemCount);
            }

        logOfFrequentItemSets.addAll(frequentItemSets.keySet());

        return frequentItemSets;
    }

    private ItemSet joinSets(ItemSet first, ItemSet second) {

        if(first.equals(second)) return null;

        List<Integer> listHolder = new ArrayList<>();

        for(int i = 0; i < first.set.length; i++){
            boolean contains = containsInt(listHolder, first.set[i]);
            if(contains == false) listHolder.add(first.set[i]);
        }
        for(int j = 0; j < second.set.length; j++){
            boolean contains = containsInt(listHolder, second.set[j]);
            if(contains == false)listHolder.add(second.set[j]);
        }

        int[] newSet = new int[listHolder.size()];
        int counter = 0;
        for(Integer i : listHolder){
            newSet[counter] = i;
            counter++;
        }

        ItemSet candidateItemSet = new ItemSet(newSet);
        return candidateItemSet;
    }

    private boolean containsInt(List<Integer> compareList, int x){
        boolean contains = false;
        for (Integer i : compareList) {
            if (i == x) {
                contains = true;
                break;
            }
        }
        return contains;
    }

    private Hashtable<ItemSet, Integer> generateFrequentItemSetsLevel1() {

        Hashtable<ItemSet, Integer> oneItemSets = new Hashtable<>();

        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                ItemSet item = new ItemSet(new int[]{data[i][j]});
                if(!oneItemSets.containsKey(item)){
                    oneItemSets.put(item, 1);
                }
                else {
                    int count = oneItemSets.get(item);
                    oneItemSets.put(item, ++count);
                }
            }
        }

        List<ItemSet> toDelete = new ArrayList<>();
        for(ItemSet item : oneItemSets.keySet()){
            if(oneItemSets.get(item)<supportThreshold) toDelete.add(item);
        }
        for(ItemSet item : toDelete) oneItemSets.remove(item);

        logOfFrequentItemSets.addAll(oneItemSets.keySet());

        return oneItemSets;
    }

    private int countSupport(int[] itemSet) {
        int matches = 0;
        for(int i = 0; i < data.length; i++){
            int indMatches = 0;
            for(int j = 0; j < data[i].length; j++){
                for(int k = 0; k < itemSet.length; k++){
                    if(data[i][j]==itemSet[k]){
                        indMatches++;
                    }
                }
            }
            if(indMatches==itemSet.length) matches++;
        }
        return matches;
    }

}
