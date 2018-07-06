package com.gm.util;

import com.google.common.base.Objects.ToStringHelper;

import static com.google.common.base.Objects.toStringHelper;

/**
 * A tuple 2 representing two typed values.
 *
 * @param <Type1> type of tuple value 1
 * @param <Type2> type of tuple value 2
 * @author liamc
 */
public class Return2<Type1, Type2> {

    /**
     * Value of tuple value 1
     */
    public final Type1 _1;
    /**
     * Value of tuple value 2
     */
    public final Type2 _2;

    public Return2(Type1 _1, Type2 _2) {

        this._1 = _1;
        this._2 = _2;
    }

    @Override
    public String toString() {

        ToStringHelper toStringHelper = toStringHelper(this);

        addAttributes(toStringHelper);

        return toStringHelper.toString();
    }

    protected void addAttributes(ToStringHelper toStringHelper) {

        toStringHelper
                .add("1", _1)
                .add("2", _2);
    }
}
