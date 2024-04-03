package least_common_multiple_with_prime_factors_as_binary_tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Program {
    public static void main(String args[]) {
        var result = calculate(new int[] { 7, 8, 12, 25, 100 });
        System.out.println("Calculation: " + result.calculation);
        System.out.println("LCM: " + result.leastCommonMultiple);
    }
    
    public static LeastCommonMultiple calculate(int[] numbers) {
        var factorTrees = new ArrayList<Node>();
        for (int i = 0; i < numbers.length; i++) {
            factorTrees.add(primeFactors(numbers[i]));
        }
    
        var relevantFactors = new HashMap<Integer, Integer>();
    
        for (var rootNode : factorTrees) {
            var factorCounts = new HashMap<Integer, Integer>();
            // each tree has the prime factors repeated a certain number of times
            countFactors(rootNode, factorCounts);
            // but only the biggest number of times each prime factor is repeated in any given tree is actually relevant
            mapRelevantFactorCounts(factorCounts, relevantFactors);
        }
    
        var LCM = new LeastCommonMultiple();
        var terms = new ArrayList<Integer>();
        
        for (var entry : relevantFactors.entrySet()) {
            for (var i = 0; i < entry.getValue(); i++) {
                terms.add(entry.getKey());
            }
        }
        
        // this is the string representation of what is the calculation made using the prime factors to calculate the least common multiple
        LCM.calculation = String.join(" x ", terms.stream().map(n -> n.toString()).toList());
        LCM.leastCommonMultiple = terms.stream().reduce(1, (a, b) -> a * b);
        LCM.primeFactorsOfEachNumber = factorTrees;
    
        return LCM;
    }
    
    // A given number always have only 1 set of prime factors
    public static Node primeFactors(int number) {
        // this is the root of the binary tree
        var root = new Node(number, null, null, null);
        var node = root;
    
        // While the number is even, 2 is a prime factor
        while (number % 2 == 0) {
            // the prime factors will always be put on the left
            node.left = new Node(2, node, null, null);
            node.right = new Node(number / 2, node, null, null);

            // this is an edge case where the number only had 2 as factors
            if (node.right.value == 2) {
                return root;
            }

            node = node.right;
            number /= 2;
        }
    
        // The number must be odd now and 2 is the only even prime number, so we only consider odd numbers from now on
        // We don't need to loop until the number itself because, by definition, any value bigger than the number's square root cannot be a factor
        for (int i = 3; i <= Math.sqrt(number); i += 2) {
            while (number > i && number % i == 0) {
                // the prime factors will always be put on the left
                node.left = new Node(i, node, null, null);
                node.right = new Node(number / i, node, null, null);
                node = node.right;
                number /= i;
            }
        }
    
        return root;
    }
    
    // counts how many time each prime factor appears for a given number
    public static void countFactors(Node node, Map<Integer, Integer> factors) {
        if (node.isLeaf()) {
            incrementFactorCount(node.value, factors);
            return;
        }
    
        // the prime factors were always put on the left
        incrementFactorCount(node.left.value, factors);
        countFactors(node.right, factors);
    }
    
    public static void incrementFactorCount(int factor, Map<Integer, Integer> factors) {
        if (!factors.containsKey(factor)) {
            factors.put(factor, 0);
        }
        
        factors.put(factor, factors.get(factor) + 1);
    }
    
    public static void mapRelevantFactorCounts(Map<Integer, Integer> treeFactors, Map<Integer, Integer> relevantFactors) {
        for (var entry : treeFactors.entrySet()) {
            if (!relevantFactors.containsKey(entry.getKey()) || entry.getValue() > relevantFactors.get(entry.getKey())) {
                relevantFactors.put(entry.getKey(), entry.getValue());
            }
        }
    }
}
