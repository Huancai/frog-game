package com.me.common.util;


import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;

import java.util.*;

/**
 * String util
 *
 * @author wu_hc 【whuancai@163.com】
 */
public final class MeString {

    private MeString() {
    }


    /**
     * string->list<int>
     *
     * @param source
     * @param symbol
     * @return
     */
    public static Set<Integer> toIntSet(String source, String symbol) {
        if (StrUtil.isEmpty(source) || StrUtil.isEmpty(symbol))
            return Collections.emptySet();
        String[] result = source.split(symbol);
        Set<Integer> list = new HashSet<>();

        for (final String s : result) {
            if (StrUtil.isEmpty(s)) {
                continue;
            }
            Integer tmp = Convert.toInt(s.trim());
            if (null == tmp) {
                continue;
            }
            list.add(tmp);
        }
        return list;
    }

    /**
     * string->list<int>
     *
     * @param source
     * @param symbol
     * @return
     */
    public static Set<Long> toLongSet(String source, String symbol) {
        if (StrUtil.isEmpty(source) || StrUtil.isEmpty(symbol))
            return Collections.emptySet();
        String[] result = source.split(symbol);
        Set<Long> list = new HashSet<>();

        for (final String s : result) {
            list.add(Convert.toLong(s));
        }
        return list;
    }

    /**
     * string->list<int>
     *
     * @param source
     * @param symbol
     * @return
     */
    public static List<Integer> toIntList(String source, String symbol) {
        if (StrUtil.isEmpty(source) || StrUtil.isEmpty(symbol))
            return Collections.emptyList();
        String[] result = source.split(symbol);
        List<Integer> list = new ArrayList<>(result.length);

        for (final String s : result) {
            list.add(Convert.toInt(s));
        }
        return list;
    }

    /**
     * string->list<int>
     *
     * @param source
     * @param symbol
     * @return
     */
    public static int[] toIntArray(String source, String symbol) {
        List<Integer> list = toIntList(source, symbol);
        if (null == list)
            return null;
        int[] ret = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            ret[i] = list.get(i).intValue();
        }
        return ret;
    }

    public static <T> String coletToString(Collection<T> objects, String symbol) {
        StringBuilder sb = new StringBuilder(Math.min(objects.size(), 16));
        for (T o : objects) {
            sb.append(o).append(symbol);
        }
        if (sb.length() > 0) {
            return sb.deleteCharAt(sb.length() - 1).toString();
        }
        return null;
    }

    /**
	 *
     * @param source
     * @param symbol
     * @return
     */
    public static int[] toIntArrayByStream(String source, String symbol) {
        return Arrays.stream(source.split(symbol)).mapToInt(s -> Integer.parseInt(s)).toArray();
    }

}
