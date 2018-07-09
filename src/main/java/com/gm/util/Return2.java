package com.gm.util;

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
        final StringBuilder sb = new StringBuilder("Return2{");
        sb.append("_1=").append(_1);
        sb.append(", _2=").append(_2);
        sb.append('}');
        return sb.toString();
    }
}
