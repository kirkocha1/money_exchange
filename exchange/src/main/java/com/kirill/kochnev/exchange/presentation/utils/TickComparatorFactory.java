package com.kirill.kochnev.exchange.presentation.utils;

import com.kirill.kochnev.exchange.domain.models.TickUI;

import java.util.Comparator;

/**
 * Created by kirill on 02.08.17.
 */

/**
 * Factory class for sorting data used for data sorting in {@link com.kirill.kochnev.exchange.presentation.views.adapter.TicksAdapter}
 */
public class TickComparatorFactory {

    public static final String ASK_COMPARATOR = "ASK";
    public static final String SPREAD_COMPARATOR = "SPREAD";
    public static final String BID_COMPARATOR = "BID";
    public static final String DEFAULT = "tool";


    /**
     *
     * @param fieldSort
     * @param isDesc
     * @return new instance of comparator
     */
    public static Comparator<TickUI> create(String fieldSort, boolean isDesc) {
        Comparator<TickUI> comparator;
        switch (fieldSort) {
            case ASK_COMPARATOR:
                comparator = (tick1, tick2) -> isDesc ? resolveIfEquals(tick1, tick2, tick1.getAsk().compareTo(tick2.getAsk())) :
                        revertResult(resolveIfEquals(tick1, tick2, tick1.getAsk().compareTo(tick2.getAsk())));
                break;
            case BID_COMPARATOR:
                comparator = (tick1, tick2) -> isDesc ? resolveIfEquals(tick1, tick2, tick1.getBid().compareTo(tick2.getBid())) :
                        revertResult(resolveIfEquals(tick1, tick2, tick1.getBid().compareTo(tick2.getBid())));
                break;
            case SPREAD_COMPARATOR:
                comparator = (tick1, tick2) -> isDesc ? resolveIfEquals(tick1, tick2, Float.compare(tick1.getSpread(), tick2.getSpread()))
                        : revertResult(resolveIfEquals(tick1, tick2, Float.compare(tick1.getSpread(), tick2.getSpread())));
                break;
            case DEFAULT:
            default:
                comparator = (tick1, tick2) -> isDesc ? Integer.compare(tick1.getType().getOrder(), tick2.getType().getOrder()) :
                        revertResult(Integer.compare(tick1.getType().getOrder(), tick2.getType().getOrder()));
        }
        return comparator;
    }

    private static int resolveIfEquals(TickUI tick1, TickUI tick2, int result) {
        return result == 0 ? tick1.getType().getOrder() > tick2.getType().getOrder() ? 1 : -1 : result;
    }

    private static int revertResult(int res) {
        return res == 1 ? -1 : res == 0 ? 0 : 1;
    }

}
