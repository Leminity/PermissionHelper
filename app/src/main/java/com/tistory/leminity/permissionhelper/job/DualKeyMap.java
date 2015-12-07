package com.tistory.leminity.permissionhelper.job;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by leminity on 2015-12-03.
 * <p/>
 * Class Name   : com.jiransecurity.udpclientsample.permission.job.DualKeyMap
 * Description  :
 * History
 * - 2015-12-03 : 최초작성
 */
class DualKeyMap<K1, K2, V>  {

    private Map<K1, Map<K2, V>> mDualKeyMap = new HashMap();

    public void add(K1 k1, K2 k2, V v) {
        Map<K2, V> childMap = mDualKeyMap.get(k1);

        if(childMap == null) {
            childMap = new HashMap();
            mDualKeyMap.put(k1, childMap);
        }

        V previousV = childMap.put(k2, v);
        if(previousV != null)
            throw new IllegalStateException("already exist.[" + previousV + "]");

    }

    public V remove(K1 k1, K2 k2) {
        Map<K2, V> childMap = mDualKeyMap.get(k1);

        V v = childMap.remove(k2);
        if(childMap.size() <= 0) {
            mDualKeyMap.remove(k1);
        }

        return v;
    }

    public boolean removeAllJobByActivity(K1 k1) {
        Map<K2, V> childMap = mDualKeyMap.remove(k1);

        if(childMap != null){
            childMap.clear();
            return true;
        }
        return false;
    }

}
