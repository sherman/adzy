package ru.sherman.adzy.algorithm;

import ru.sherman.adzy.advertisement.Banner;
import ru.sherman.adzy.util.Interval;
import ru.sherman.adzy.util.IntervalTree;
import ru.sherman.adzy.util.Randoms;

import java.util.List;

import static com.google.common.base.Preconditions.*;

/**
 * Created by IntelliJ IDEA.
 * User: sherman
 * Date: 16.10.11
 * Time: 23:31
 * To change this template use File | Settings | File Templates.
 */
public class WeightedUniformDistributionBasedBannerSelector implements BannerSelector {
    private final IntervalTree<Banner> weightedBanners;

    public WeightedUniformDistributionBasedBannerSelector(IntervalTree<Banner> weightedBanners) {
        this.weightedBanners = weightedBanners;
    }

    @Override
    public Banner getBanner() {
        Interval<Integer> randomWeight = Randoms.getRandomInterval((Integer) weightedBanners.max);
        // in fact it must return only one banner
        List<Banner> banners = weightedBanners.search(randomWeight);

        if (banners == null)
            return null;

        checkArgument(banners.size() == 1, "I expect only one banner!");
        return banners.get(0);
    }
}
