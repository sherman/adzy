package ru.sherman.adzy.util;

import org.testng.annotations.Test;
import ru.sherman.adzy.advertisement.Banner;
import sun.rmi.runtime.Log;

import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.*;

/**
 * Created by IntelliJ IDEA.
 * User: sherman
 * Date: 16.10.11
 * Time: 22:56
 * To change this template use File | Settings | File Templates.
 */
public class IntervalTreeTest {
    @Test
    public void testSearch() throws Exception {
        List<Interval> weights = Arrays.<Interval>asList(
            new Interval(1, 100, new Banner(1, 100)),
            new Interval(101, 200, new Banner(2, 100)),
            new Interval(201, 500, new Banner(3, 300))
        );

        IntervalTree<Banner> weightedBanners = new IntervalTree<Banner>(weights);
        List<Banner> banners = weightedBanners.search(new Interval(100, 100));
        assertEquals(banners.size(), 1);
        assertEquals(banners.get(0), new Banner(1, 100));

        banners = weightedBanners.search(new Interval(501, 501));
        assertNull(banners);
    }
}
