package com.es.phoneshop.model.product;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;


public class ArrayListProductDao implements ProductDao {

    private ArrayListProductDao(){}

    //Singleton initialization on Demand Holder

    private static class SingletonHolder{
        private static final ArrayListProductDao instance = new ArrayListProductDao();
    }

    public static ArrayListProductDao getInstance(){
        return SingletonHolder.instance;
    }

    private List<Product> products = new ArrayList<>();

    @Override
    public synchronized Product getProduct(Long id) throws IllegalArgumentException{
        return products.stream()
                       .filter(product -> product.getId()
                       .equals(id))
                       .findAny()
                       .orElseThrow(() -> new IllegalArgumentException("List doesn't contain product with id " + id));
    }


    public synchronized List<Product> findProducts() {
        return products.stream()
                       .filter(product -> product.getPrice() != null && product.getStock() > 0)
                       .collect(Collectors.toList());
    }

    @Override
    public synchronized List<Product> findProducts(String query) {
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

    public synchronized List<Product> findProducts(String query, String sortBy, String order) {
        List<Product> products = findProducts(query);
        Comparator<Product> comparator = null;
        switch (sortBy){
            case "price":
                comparator = Comparator.comparing(Product::getPrice);
                break;
            case "description":
                comparator = Comparator.comparing(Product::getDescription);
                break;
            default:
                comparator = Comparator.comparing(Product::getCode);
        }
        if (order.equalsIgnoreCase("descending"))
            comparator = comparator.reversed();
        products.sort(comparator);
        return products;
    }

    @Override
    public void save(Product product) throws IllegalArgumentException {
        if (products.stream()
                    .anyMatch(p -> p.getId()
                    .equals(product.getId()))) {
            throw new IllegalArgumentException("List already contains product with id " + product.getId());
        }
        else
            products.add(product);
    }

    @Override
    public void delete(Long id) throws IllegalArgumentException {
            products.remove(products.stream()
                                    .filter(product -> product.getId()
                                    .equals(id))
                                    .findAny()
                                    .orElseThrow(() -> new IllegalArgumentException("List doesn't contain product with id " + id)));
    }

    private Predicate<Product> isProductCorrect = product -> product.getPrice() != null && product.getStock() > 0;
}
