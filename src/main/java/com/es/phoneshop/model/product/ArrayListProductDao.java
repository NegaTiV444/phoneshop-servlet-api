package com.es.phoneshop.model.product;


import java.util.*;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;


public class ArrayListProductDao implements ProductDao {

    private List<Product> products = new ArrayList<>();

    private HashMap<String, Comparator<Product>> comparatorsMap = new HashMap<>();

    private ArrayListProductDao() {
        comparatorsMap.put("descriptor", Comparator.comparing(Product::getDescription));
        comparatorsMap.put("price", Comparator.comparing(Product::getPrice));
    }

    //Singleton initialization on Demand Holder

    private static class SingletonHolder {
        private static final ArrayListProductDao instance = new ArrayListProductDao();
    }

    public static ArrayListProductDao getInstance() {
        return SingletonHolder.instance;
    }

    @Override
    public synchronized Product getProduct(String code) {
        return products.stream()
                .filter(product -> product.getCode().equalsIgnoreCase(code))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Product \"" + code + "\" not found"));
    }

    @Override
    public synchronized Product getProduct(Long id) {
        return products.stream()
                .filter(product -> product.getId().equals(id))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Product with id " + id + " not found"));
    }

    @Override
    public synchronized List<Product> findProducts() {
        return products.stream()
                .filter(isProductCorrect)
                .collect(Collectors.toList());
    }

    @Override
    public synchronized List<Product> findProducts(String query) {

        if ((query == null) || (query.trim().isEmpty()))
            return findProducts();
        String[] terms = query.toLowerCase().split(" ");
        ToIntFunction<Product> getNumberOfTerms = product -> (int) Arrays.stream(terms)
                .filter(product.getDescription().toLowerCase()::contains)
                .count();

        return products.stream()
                .filter(isProductCorrect)
                .filter(product -> Arrays.stream(terms).anyMatch(product.getDescription().toLowerCase()::contains))
                .sorted(Comparator.comparingInt(getNumberOfTerms).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public synchronized List<Product> findProducts(String query, String sortBy, String order) {
        List<Product> requestedProducts = findProducts(query);
        if ((sortBy == null) || (order == null) || (!comparatorsMap.containsKey(sortBy))) {
            return requestedProducts;
        }
        Comparator<Product> comparator = comparatorsMap.get(sortBy);
        if (order.equalsIgnoreCase("desc"))
            comparator = comparator.reversed();
        requestedProducts.sort(comparator);
        return requestedProducts;
    }

    @Override
    public synchronized void save(Product product) {
        if (products.stream()
                .anyMatch(p -> p.getId().equals(product.getId()))) {
            throw new IllegalArgumentException("List already contains product " + product.getDescription());
        } else
            products.add(product);
    }

    @Override
    public synchronized void delete(Long id) {
        products.remove(products.stream()
                .filter(product -> product.getId().equals(id))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Product with id " + id + " not found")));
    }

    private Predicate<Product> isProductCorrect = product -> product.getPrice() != null && product.getStock() > 0;
}
