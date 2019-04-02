package com.es.phoneshop.model.history;

import com.es.phoneshop.model.product.Product;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;

import static junit.framework.TestCase.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class HttpSessionHistoryServiceTest {

    private static HistoryService historyService;
    private static Product p1;
    private static Product p2;
    private static Product p3;
    private static Product p4;

    @BeforeClass
    public static void init() {
        historyService = HttpSessionHistoryService.newInstance();

        p1 = new Product();
        p1.setStock(1);
        p1.setId(1L);
        p1.setCode("aphone1");
        p1.setPrice(new BigDecimal(100));
        p1.setDescription("APhone 1");

        p2 = new Product();
        p2.setStock(1);
        p2.setId(2L);
        p2.setCode("bphone1");
        p2.setPrice(new BigDecimal(200));
        p2.setDescription("BPhone 1");

        p3 = new Product();
        p3.setStock(1);
        p3.setId(3L);
        p3.setCode("cphone2");
        p3.setPrice(new BigDecimal(1000));
        p3.setDescription("CPhone 2");

        p4 = new Product();
        p4.setStock(1);
        p4.setId(4L);
        p4.setCode("cphone1");
        p4.setPrice(new BigDecimal(400));
        p4.setDescription("CPhone 1");
    }

    @Test
    public void putToEmptyHistoryTest() {
        History history = new History();
        historyService.put(history, p1);
        assertEquals(history.getRecentProducts().getFirst(), p1);
    }

    @Test
    public void putOneProductTwiceTest() {
        History history = new History();
        historyService.put(history, p1);
        historyService.put(history, p1);
        assertEquals(1, history.getRecentProducts().size());
    }

    @Test
    public void orderTest() {
        History history = new History();
        historyService.put(history, p1);
        historyService.put(history, p2);
        historyService.put(history, p1);
        assertEquals(history.getRecentProducts().getFirst(), p1);
    }

    @Test
    public void putFourProductsTest() {
        History history = new History();
        historyService.put(history, p1);
        historyService.put(history, p2);
        historyService.put(history, p3);
        historyService.put(history, p4);
        assertEquals(history.getRecentProducts().size(), 3);
        assertEquals(history.getRecentProducts().getFirst(), p4);
        assertEquals(history.getRecentProducts().getLast(), p2);

    }

}
